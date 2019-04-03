
function addCase() {
    layer.open({
        type: 2,
        title: '添加商品',
        shadeClose: true,
        shade: false,
        maxmin: true, //开启最大化最小化按钮
        area: ['90%', '90%'],
        content: 'goods-add.html'
    });
}

layui.use(['form', 'layer', 'table','slider'], function() {
    var table = layui.table;
    $ = layui.jquery;
    var form = layui.form,
        layer = layui.layer,
        slider = layui.slider;

    var paramLowPrice;
    var paramHeightPrice;
    var paramLowDiscount;
    var paramHeightDiscount;

    //开启价钱范围选择
    slider.render({
        elem: '#priceSliderId'
        ,value: 0 //初始值
        ,range: true //范围选择
        ,min: 0 //最小值
        ,max: 1000 //最大值
        ,change: function(vals){
            paramLowPrice = vals[0];
            paramHeightPrice = vals[1];
        }
    });

    //开启价钱范围选择
    slider.render({
        elem: '#discountSliderId'
        ,value: 0 //初始值
        ,range: true //范围选择
        ,min: 0 //最小值
        ,max: 100 //最大值
        ,change: function(vals){
            paramLowDiscount = vals[0];
            paramHeightDiscount = vals[1];
        }
    });


    table.render({
        elem: '#goodsList',
        id: 'goodsTableId',
        toolbar:true,
        defaultToolbar: ['filter', 'print', 'exports'],
        url: '/goods/showAllGoodsVo',
        page: true,
        cols: [
            [
                { field: 'id', title: '编号', sort: true },
                { field: 'categoryName', title:'分类', sort: true},
                { field: 'supCategoryName', title:'父级分类', sort:true},
                { field: 'cover', title: '封面',templet: function(d){
                    return "<img src='" + d.cover + "' height='60px'/>";
                }},
                { field: 'name', title: '名称', sort:true},
                { field: 'originalPrice', title:'原价', sort:true},
                { field: 'discountPrice', title:'折后价', sort:true},
                { field: 'discount', title:'折扣', sort:true},
                { field: 'weight', title:'权重', sort:true},
                { field: 'status', title:'显示状态',sort:true, templet: '#checkboxTpl', unresize: true,
                    templet: function(d) {
                        if(d.status === "1") {
                            return "<input type='checkbox' name='lock' value='" + JSON.stringify(d) + "' title='启用' lay-filter='lockStatus' checked>"
                        }else{
                            return "<input type='checkbox' name='lock' value='" + JSON.stringify(d) + "' title='启用' lay-filter='lockStatus'>"
                        }
                    }},
                { title: '操作', toolbar: '#barGoods'},
                { field: 'createTime', title: '创建时间',sort: true,
                    templet: function (d) {
                        return timeFormatter(d.createTime);
                    }
                },
            ]
        ]
    });

    //监听状态操作
    form.on('checkbox(lockStatus)', function(obj){
        var goods = JSON.parse(this.value);
        if(obj.elem.checked){
            goods.status = 1;
        }else{
            goods.status = 0;
        }
        $.ajax({
            async:false,
            dataType:"json",
            type:"POST",
            data:JSON.stringify(goods),
            url:IP + "goods/updateGoods",
            contentType: 'application/json;charset=UTF-8',
            success:function(data){
                if(data.code == 0){
                    layer.msg("修改成功");
                }else{
                    layer.msg("修改失败");
                }
            }
        });
    });

    //监听工具条
    table.on('tool(goodsFilter)', function(obj){
        var data = obj.data;
        if(obj.event === 'del'){
            //删除服务信息
            layer.confirm('真的删除 ' + data.name + ' 吗？', function(index){
                $.ajax({
                    async:false,
                    dataType:"json",
                    type:"POST",
                    url:"/goods/deleteGoods",
                    headers: {'Content-Type': 'application/json;charset=utf-8'},
                    data:JSON.stringify(data),
                    success:function(res){
                        if(res.code === 0)
                        {
                            layer.msg('删除成功');
                            obj.del();
                            layer.close(index);
                        }else if(res.code === -5){
                            layer.msg('有' + res.data +'个轮播图存在此商品，删除失败');
                        } else{
                            layer.msg('删除失败');
                        }
                    },error(e){
                        layer.msg("网络请求失败!");
                    }
                });
            });
        } else if(obj.event === 'edit'){
            //编辑服务信息
            x_admin_show('编辑商品  ' + data.name,
                './goods-add.html?id=' + data.id);
        }
    });


    //加载分类
    $(function(){
        $.ajax({
            type:"GET",
            url: '/category/showCategory/',
            async: false,
            dataType: "json",
            contentType: 'application/json;charset=UTF-8',
            success:function(data){
                var categoryClass = '<option value="" selected="">选择分类</option>' + '\n';
                for(var i=0; i<data.data.length; i++){
                    categoryClass += '<option value="' + data.data[i].id + '">' + data.data[i].name + '</option>';
                }
                $("#searchCategory").append(categoryClass);
                form.render('select'); //刷新select选择框渲染
            }
        });
    });

    //模糊查询
    var $ = layui.$, active = {
        reload: function(){
            var paramId = $("#searchId").val();
            var paramName = $("#searchName").val();
            var paramCategory = $("#searchCategory").val();

            //执行重载
            table.reload('goodsTableId', {
                url: '/goods/showAllGoodsVoByParamas',
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                ,where: {
                    paramId:paramId,
                    paramName:paramName,
                    paramCategory:paramCategory,
                    paramLowPrice:paramLowPrice,
                    paramHeightPrice:paramHeightPrice,
                    paramLowDiscount:paramLowDiscount,
                    paramHeightDiscount:paramHeightDiscount
                }
            });
        }
    };

    $('.goodsTable .layui-btn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

});