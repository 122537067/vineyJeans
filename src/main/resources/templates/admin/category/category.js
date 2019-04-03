layui.use(['form', 'layer', 'table'], function() {
    var table = layui.table,
        $ = layui.jquery,
        form = layui.form,
        layer = layui.layer;

    table.render({
        elem: '#categoryList',
        id: 'categoryTableId',
        url: '/category/showAllCategoryVo',
        page: true,
        cols: [
            [
                { field: 'name', title: '分类名',sort: true },
                { field: 'supName', title:'上级分类名',sort:true },
                { field: 'status', title:'状态',templet: '#checkboxTpl', unresize: true,
                    templet: function(d) {
                        if(d.status === '1') {
                            return "<input type='checkbox' name='lock' value='" + JSON.stringify(d) + "' title='启用' lay-filter='lockStatus' checked>"
                        }else{
                            return "<input type='checkbox' name='lock' value='" + JSON.stringify(d) + "' title='启用' lay-filter='lockStatus'>"
                        }
                    }},
                { title: '操作', toolbar: '#barCategory'}
            ]
        ]
    });

    //监听状态操作
    form.on('checkbox(lockStatus)', function(obj){
        var category = JSON.parse(this.value);
        if(obj.elem.checked){
            category.status = 1;
        }else{
            category.status = 0;
        }
        $.ajax({
            async:false,
            dataType:"json",
            type:"POST",
            data:JSON.stringify(category),
            url:"/category/updateCategory",
            contentType: 'application/json;charset=UTF-8',
            success:function(data){
                if(data.code === 0){
                    layer.msg("修改成功");
                }else{
                    layer.msg("修改失败");
                }
            }
        });
    });

    //监听工具条
    table.on('tool(categoryFilter)', function(obj){
        var data = obj.data;
        if(obj.event === 'del'){
            //删除父级分类
            layer.confirm('真的删除 ' + data.name + ' 吗？', function(index){
                $.ajax({
                    async:false,
                    dataType:"json",
                    type:"POST",
                    url:"/category/deleteCategory",
                    headers: {'Content-Type': 'application/json;charset=utf-8'},
                    data:JSON.stringify(data),
                    success:function(res){
                        if(res.code === 0)
                        {
                            layer.msg('删除成功');
                            obj.del();
                            layer.close(index);
                        }else if(res.code === -3) {
                            layer.msg('存在' + res.data + '件商品是此分类,删除失败');
                        }else{
                            layer.msg('删除失败');
                        }
                    },error(e){
                        layer.msg("网络请求失败!");
                    }
                });
            });
        }else if(obj.event === 'edit'){
            x_admin_show('编辑二级分类',
                './category-add.html?id=' + data.id
                + '&supCategoryId=' + data.supCategoryId
                + '&name=' + encodeURI(encodeURI(data.name))
                + '&status=' + data.status);
        }
    });

});