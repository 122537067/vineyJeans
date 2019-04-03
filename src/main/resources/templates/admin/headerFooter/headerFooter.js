var headerFooter = {};

layui.use(['upload', 'form', 'layer'], function () {
    var $ = layui.jquery
        , upload = layui.upload;
    var form = layui.form,
        layer = layui.layer;

    $(function(){
        $.ajax({
            async:false,
            type:"GET",
            url:"/headerFooter/showHeaderFooter",
            success:function(res){
                headerFooter = res;
                //图片赋值
                $("#topLogoImgId").attr("src",headerFooter.topLogo);
                $("#footerLogoImgId").attr("src",headerFooter.footerLogo);

                //表单赋值
                $("#copyrightId").val(headerFooter.copyright);
                $("#nameId").val(headerFooter.name);
            }
        });
    });


    //logo上传
    upload.render({
        elem: '#topLogoId'
        , url: '/util/uploadImg/'
        ,data: {"path": '/header/'}
        , before: function (obj) {
            layer.load(); //上传loading
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                $('#topLogoImgId').attr('src', result); //图片链接（base64）
            });
        }
        , done: function (res) {
            layer.closeAll('loading'); //关闭loading
            //上传成功
            if (res.code == 0) {
                headerFooter.topLogo = res.data;
            }
            //如果上传失败
            else if (res.code > 0) {
                return layer.msg('上传失败');
            }
        }
    });

    //二维码上传
    upload.render({
        elem: '#footerLogoId'
        , url: '/util/uploadImg/'
        ,data: {"path": '/footer/'}
        , before: function (obj) {
            layer.load(); //上传loading
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                $('#footerLogoImgId').attr('src', result); //图片链接（base64）
            });
        }
        , done: function (res) {
            layer.closeAll('loading'); //关闭loading
            //上传成功
            if (res.code == 0) {
                headerFooter.footerLogo = res.data;
            }
            //如果上传失败
            else if (res.code > 0) {
                return layer.msg('上传失败');
            }
        }
    });


    //监听修改->修改页面头尾信息
    form.on('submit(update)', function (data) {
        if (headerFooter.footerLogo == null || headerFooter.topLogo == null) {
            layer.msg("请上传图片", {icon: 2, time: 1500});
            return false;
        }
        headerFooter.copyright = data.field.copyrightName;
        headerFooter.name = data.field.nameName;


        $.ajax({
            async: false,
            dataType: "json",
            data: JSON.stringify(headerFooter),
            type: "POST",
            url: "/headerFooter/updateHeaderFooter",
            contentType: 'application/json;charset=UTF-8',
            success: function (res) {
                if (res.code == 0) {
                    layer.msg('修改成功', {icon: 1, time: 1000});
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    parent.location.reload();
                } else {
                    layer.msg('未知错误', {icon: 2, time: 1000});
                }
            }
        });
        return false;
    });

});