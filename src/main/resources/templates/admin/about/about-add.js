layui.use(['table', 'upload', 'form', 'layer'], function () {
    var $ = layui.jquery
        , upload = layui.upload
        ,form = layui.form
        ,layer = layui.layer
        ,table = layui.table;

    var about = {};  //初始化
    var aboutId = getUrlParam("id");
    if(aboutId != null){//编辑
        //表单初始化赋值
        $.ajax({
            async:false,
            dataType:"json",
            type:"GET",
            url:"/about/showAboutById",
            data:{
                "aboutId" : aboutId
            },
            success:function(res){
                about = res;
                //表单赋值
                $("#titleId").val(about.title);
                $("#subtitleId").val(about.subtitle);
                $("#introId").val(about.intro);
                $("#contentId").val(about.content);
                $("#sortId").val(about.sort);

            }
        });

        $("#aboutBtn").append(`
                <label class="layui-form-label">
                </label>
                <button class="layui-btn" lay-filter="update" lay-submit="">
                    修改
                </button>
            `);

        var option = "update";
        var optionUrl = "updateAbout";
    }else {
        $("#aboutBtn").append(`
                    <label class="layui-form-label">
                    </label>
                    <button class="layui-btn" lay-filter="add" lay-submit="">
                        增加
                    </button>
            `);
        var option = "add";
        var optionUrl = "insertAbout";
    }

    //监听提交
    form.on('submit('+ option +')', function (data) {
        about.title = data.field.titleName;
        about.subtitle = data.field.subtitleName;
        about.intro = data.field.introName;
        about.sort = data.field.sortName;
        about.content = data.field.contentName;

        $.ajax({
            async: false,
            dataType: "json",
            data: JSON.stringify(about),
            type: "POST",
            url: "/about/" + optionUrl,
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

});