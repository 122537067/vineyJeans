var IP = getUrl();
//创建富文本编辑器
var E = window.wangEditor;
var editor = new E('#editor');
// editor.customConfig.uploadImgServer = IP + 'goods/uploadGoodsContentImg/';    // 配置服务器端地址
editor.customConfig.uploadFileName = 'imgFile';
editor.customConfig.customUploadImg = function (files, insert) {
    //文章内容上传图片
    var daw = new FormData();
    daw.append("path","/goods/detail/")
    for (var i = 0; i < files.length; i++) {
        daw.append("imgFile", files[i]);
    }
    index = layer.load(1, {
        shade: [0.1, '#fff'] //0.1透明度的白色背景
    });

    $.ajax({
        url:'/util/wangEditorUploadImg/',
        type: "POST",
        data: daw,
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        success: function (res) {
            layer.close(index);
            if (res.code == 0) {
                for (var j = 0; j < res.data.length; j++) {
                    insert(res.data[j]);
                }
            } else {
                alert(res.msg);
                return;
            }
        },
    });
};
editor.create();

layui.use(['table', 'upload', 'form', 'layer'], function () {
    var $ = layui.jquery
        , upload = layui.upload;
    var form = layui.form,
        layer = layui.layer;
    var table = layui.table;

    var goods={};
    var i = -1;

    //封面图片上传
    upload.render({
        elem: '#coverBtnId'
        , url: '/util/uploadImg/'
        ,data: {"path": '/goods/cover/'}
        , before: function (obj) {
            layer.load(); //上传loading
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                $('#coverId').attr('src', result); //图片链接（base64）
            });
        }
        , done: function (res) {
            layer.closeAll('loading'); //关闭loading
            //上传成功
            if (res.code === 0) {
                goods.cover = res.data;
            }
            //如果上传失败
            else if (res.code > 0) {
                return layer.msg('上传失败');
            }
        }
    });

    //多图片上传
    upload.render({
        elem: '#pictureBtnId'
        ,url: '/util/uploadImg/'
        ,data: {"path": '/goods/picture/'}
        ,multiple: true
        ,before: function(obj){
            layer.load(); //上传loading
            //预读本地文件示例，不支持ie8
            obj.preview(function(index, file, result){
            });
        }
        ,done: function(res){
            //上传完毕
            layer.closeAll('loading'); //关闭loading
            //上传成功
            if (res.code === 0) {
                i++;
                $('#pictureId').append('<img src="'+ res.data + '" class="layui-upload-img" style="width: 200px" id="pictureImg' + i + '" name="'+ res.data+'">&nbsp;');
                //事件绑定
                $("#pictureId img").unbind();
                $("#pictureId img").click(function () {
                    var imgId = this.id;
                    var path = this.name;
                    $.ajax({
                        type:"POST",
                        url: "/util/deleteImg",
                        data: {
                            path : path,
                        },
                        success: function(data){
                            if(data.code === 0){
                                $("#"+imgId).remove();
                            }
                        }
                    })
                });
            }
            //如果上传失败
            else if (res.code < 0) {
                return layer.msg('上传失败');
            }
        }
    });

    var goodsId = getUrlParam("id");
    if(goodsId!=null){
        //编辑
        $("#goodsBtn").append(`
            <label class="layui-form-label">
            </label>
            <button class="layui-btn" lay-filter="update" lay-submit="">
                修改
            </button>
        `);

        //编号不可以编辑
        $("#goodsIdId").attr("readOnly",true);
        $("#goodsIdId").css("backgroundColor","#dddddd");

        $.ajax({
            async:true,
            dataType:"json",
            type:"GET",
            url:"/goods/selectGoodsById",
            data:{
                "id":goodsId,
            },
            success:function(res){
                goods = res.data;
                //表单初始赋值
                $("#goodsIdId").val(goods.id);
                $("#goodsNameId").val(goods.name);
                $("#goodsIntroId").val(goods.intro);
                $("#originalPriceId").val(goods.originalPrice);
                $("#discountId").val(goods.discount);
                $("#discountPriceId").val(goods.discountPrice);
                $("#weightId").val(goods.weight);
                //下拉框赋值
                $("#supCategoryId").find("option[value=" + goods.supCategoryId + "]").attr("selected",true);
                updateCategory(goods.supCategoryId,goods.categoryId);
                form.render('select');
                //封面赋值
                $("#coverId").attr("src",goods.cover);
                //多图赋值
                var pic = removeStartEnd(goods.picture);
                var picArray = pic.split(",");
                for(var j=0;picArray.length>j;j++){
                    $('#pictureId').append('<img src="'+ picArray[j] + '" class="layui-upload-img" style="width: 200px" id="pictureImg' + j + '" name="'+ picArray[j] +'">&nbsp;');
                    //事件绑定
                    $("#pictureId img").unbind();
                    $("#pictureId img").click(function () {
                        var imgId = this.id;
                        var path = this.name;
                        $.ajax({
                            type:"POST",
                            url: "/util/deleteImg",
                            data: {
                                path : path,
                            },
                            success: function(data){
                                if(data.code === 0){
                                    $("#"+imgId).remove();
                                }
                            }
                        })
                    });
                    i = j;  //赋值对应后续增加的图片
                }

                //编辑器赋值
                editor.txt.html(goods.description);
            }
        });

        var option = "update";
        var optionUrl = "updateGoods";
    }else {
        //新增
        $("#goodsBtn").append(`
                <label class="layui-form-label">
                </label>
                <button class="layui-btn" lay-filter="add" lay-submit="">
                    增加
                </button>
        `);

        var option = "add";
        var optionUrl = "insertGoods";
    }

    //加载父级分类
    $(function(){
        $.ajax({
            type:"GET",
            url: '/supCategory/showAllSupCategory/',
            async: false,
            dataType: "json",
            contentType: 'application/json;charset=UTF-8',
            success:function(data){
                var supCategoryList = '<option value="" selected="">选择父级分类</option>' + '\n';
                for(var i=0; i<data.data.length; i++){
                    supCategoryList += '<option value="' + data.data[i].id + '">' + data.data[i].name + '</option>';
                }
                $("#supCategoryId").append(supCategoryList);
                form.render('select'); //刷新select选择框渲染
            }
        });
    });

    //二级下拉框刷新
    function updateCategory(value,select){
        var supCategory = value;
        $.ajax({
            type: 'GET',
            url: '/category/showCategoryBySupId/',
            data: {
                "supCategoryId":supCategory
            },
            dataType:  'json',
            success: function(res){
                $("#categoryId").empty();
                var categoryList = '<option value="" selected="">选择分类</option>' + '\n';
                for(var i=0; i<res.data.length; i++){
                    categoryList += '<option value="' + res.data[i].id + '">' + res.data[i].name + '</option>';
                }
                $("#categoryId").append(categoryList);
                if(select != null){
                    $("#categoryId").find("option[value=" + goods.categoryId + "]").attr("selected",true);
                }
                form.render('select'); //刷新select选择框渲染
            }
        });
    }

    //下拉框联动
    form.on('select(supCategoryFilter)', function(data){
        updateCategory(data.value);
    });

    //监听提交->增加或修改商品
    form.on('submit(' + option +')', function (data) {
        if (goods.cover == null) {
            layer.msg("请上传封面图", {icon: 2, time: 1500});
            return false;
        }

        goods.id = data.field.goodsIdName;
        goods.categoryId = data.field.categoryName;
        goods.name = data.field.goodsNameName;
        goods.originalPrice = data.field.originalPriceName;
        goods.discountPrice = data.field.discountPriceName;
        goods.discount = data.field.discountName;
        goods.weight = data.field.weightName;
        goods.intro = data.field.goodsIntroName;
        goods.picture = ",";
        //富文本内容
        goods.description = editor.txt.html();
        //遍历图片
        $("#pictureId img").each(function(){
            goods.picture += $(this).attr('src') + ",";
        });
        console.log(goods.picture);
        if(goods.picture == ",,"){
            //如果没有图片则不加
            goods.picture = null;
        }
        //请求添加或修改
        $.ajax({
            async: false,
            dataType: "json",
            data: JSON.stringify(goods),
            type: "POST",
            url: "/goods/" + optionUrl,
            contentType: 'application/json;charset=UTF-8',
            success: function (res) {
                if (res.code === 0) {
                    layer.msg('成功', {icon: 1, time: 1000});
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    parent.location.reload();
                } else if(res.code === -2){
                    layer.msg('已存在编号', {icon: 2, time: 1000});
                } else {
                    layer.msg('未知错误', {icon: 2, time: 1000});
                }
            }
        });
        return false;
    });
});