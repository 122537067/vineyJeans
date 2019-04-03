layui.use(['table', 'upload', 'form', 'layer'], function () {
    var $ = layui.jquery
        , upload = layui.upload
        ,form = layui.form
        ,layer = layui.layer
        ,table = layui.table;

    var banner = {};  //初始化
    var bannerId = getUrlParam("id");
    if(bannerId != null){//编辑
        //表单初始化赋值
        banner.id = bannerId;
        banner.title = decodeURI(getUrlParam("title"));
        banner.intro = decodeURI(getUrlParam("intro"));
        banner.picture = getUrlParam("picture");
        banner.sort = getUrlParam("sort");
        banner.status = getUrlParam("status");
        banner.goodsId = getUrlParam("goodsId");
        $("#titleId").val(banner.title);
        $("#introId").val(banner.intro);
        $("#sortId").val(banner.sort);

        //封面赋值
        $("#pictureId").attr("src",banner.picture);


        $("#bannerBtn").append(`
                <label class="layui-form-label">
                </label>
                <button class="layui-btn" lay-filter="update" lay-submit="">
                    修改
                </button>
            `);

        var option = "update";
        var optionUrl = "updateBanner";
    }else {
        $("#bannerBtn").append(`
                    <label class="layui-form-label">
                    </label>
                    <button class="layui-btn" lay-filter="add" lay-submit="">
                        增加
                    </button>
            `);
        var option = "add";
        var optionUrl = "insertBanner";
    }

    //监听提交
    form.on('submit('+ option +')', function (data) {
        if(banner.picture == null){
            layer.msg('请上传图片', {icon: 2, time: 1000});
            return false;
        }
        banner.title = data.field.titleName;
        banner.intro = data.field.introName;
        banner.sort = data.field.sortName;
        banner.goodsId = data.field.goodsIdName;

        $.ajax({
            async: false,
            dataType: "json",
            data: JSON.stringify(banner),
            type: "POST",
            url: "/banner/" + optionUrl,
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
        ,data: {"path": '/banner/'}
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
                banner.picture = res.data;
            }
            //如果上传失败
            else if (res.code > 0) {
                return layer.msg('上传失败');
            }
        }
    });

    //加载所有商品
    $(function(){
        $.ajax({
            type:"GET",
            url: '/goods/showAllGoods/',
            async: false,
            dataType: "json",
            contentType: 'application/json;charset=UTF-8',
            success:function(data){
                var goodsList = '<option value="" selected="">选择商品编号</option>' + '\n';
                for(var i=0; i<data.data.length; i++){
                    goodsList += '<option value="' + data.data[i].id + '">' + data.data[i].id + '</option>';
                }
                $("#goodsIdId").append(goodsList);
                if(bannerId != null){
                    //下拉框赋值
                    selectValue("goodsIdId",banner.goodsId);
                }
                form.render('select'); //刷新select选择框渲染
            }
        });
    });

    /*下拉框赋值*/
    function selectValue(domId,value){
        $("#"+domId).find("option[value=" + value + "]").attr("selected",true);
        form.render('select');
    }
});