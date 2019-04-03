layui.use(['table', 'upload', 'form', 'layer'], function () {
    var $ = layui.jquery
        , upload = layui.upload
        ,form = layui.form
        ,layer = layui.layer
        ,table = layui.table;

    var contact = {};  //初始化
    var contactId = getUrlParam("id");
    if(contactId != null){//编辑
        //表单初始化赋值
        contact.id = contactId;
        contact.title = decodeURI(getUrlParam("title"));
        contact.intro = decodeURI(getUrlParam("intro"));
        contact.picture = getUrlParam("picture");
        contact.sort = getUrlParam("sort");
        contact.status = getUrlParam("status");
        $("#titleId").val(contact.title);
        $("#introId").val(contact.intro);
        $("#sortId").val(contact.sort);

        //封面赋值
        $("#pictureId").attr("src",contact.picture);


        $("#contactBtn").append(`
                <label class="layui-form-label">
                </label>
                <button class="layui-btn" lay-filter="update" lay-submit="">
                    修改
                </button>
            `);

        var option = "update";
        var optionUrl = "updateContact";
    }else {
        $("#contactBtn").append(`
                    <label class="layui-form-label">
                    </label>
                    <button class="layui-btn" lay-filter="add" lay-submit="">
                        增加
                    </button>
            `);
        var option = "add";
        var optionUrl = "insertContact";
    }

    //监听提交
    form.on('submit('+ option +')', function (data) {
        if(contact.picture == null){
            layer.msg('请上传图片', {icon: 2, time: 1000});
            return false;
        }
        contact.title = data.field.titleName;
        contact.intro = data.field.introName;
        contact.sort = data.field.sortName;

        $.ajax({
            async: false,
            dataType: "json",
            data: JSON.stringify(contact),
            type: "POST",
            url: "/contact/" + optionUrl,
            contentType: 'application/json;charset=UTF-8',
            success: function (res) {
                if (res.code === 0) {
                    layer.msg('成功', {icon: 1, time: 1000});
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    parent.location.reload();
                } else {
                    layer.msg('未知错误', {icon: 2, time: 1000});
                }
            },error(e){
                console.log(e);
            }
        });
        return false;
    });

    //轮播图上传
    upload.render({
        elem: '#pictureBtnId'
        , url: '/util/uploadImg/'
        ,data: {"path": '/contact/'}
        , before: function (obj) {
            layer.load(); //上传loading
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                $('#pictureId').attr('src', result); //图片链接（base64）
            });
        }
        , done: function (res) {
            layer.closeAll('loading'); //关闭loading
            //上传成功
            if (res.code === 0) {
                contact.picture = res.data;
            }
            //如果上传失败
            else if (res.code > 0) {
                return layer.msg('上传失败');
            }
        }
    });

});