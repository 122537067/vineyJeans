layui.use(['form', 'layer', 'table'], function() {
    var table = layui.table,
        $ = layui.jquery,
        form = layui.form,
        layer = layui.layer;

    table.render({
        elem: '#supCategoryList',
        id: 'supCategoryTableId',
        url: '/supCategory/showAllSupCategory',
        page: true,
        cols: [
            [
                { field: 'name', title: '分类名',edit: 'text', sort: true },
                { field: 'status', title:'状态',templet: '#checkboxTpl', unresize: true,
                    templet: function(d) {
                        if(d.status === '1') {
                            return "<input type='checkbox' name='lock' value='" + JSON.stringify(d) + "' title='启用' lay-filter='lockStatus' checked>"
                        }else{
                            return "<input type='checkbox' name='lock' value='" + JSON.stringify(d) + "' title='启用' lay-filter='lockStatus'>"
                        }
                    }},
                { title: '操作', toolbar: '#barSupCategory'}
            ]
        ]
    });

    //监听状态操作
    form.on('checkbox(lockStatus)', function(obj){
        var supCategory = JSON.parse(this.value);
        if(obj.elem.checked){
            supCategory.status = 1;
        }else{
            supCategory.status = 0;
        }
        $.ajax({
            async:false,
            dataType:"json",
            type:"POST",
            data:JSON.stringify(supCategory),
            url:"/supCategory/updateSupCategory",
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

    //监听单元格编辑
    table.on('edit(supCategoryFilter)', function(obj){
        var value = obj.value //得到修改后的值
            ,data = obj.data //得到所在行所有键值
            ,field = obj.field; //得到字段
        console.log(obj);
        $.ajax({
            async:false,
            dataType:"json",
            type:"POST",
            data:JSON.stringify(data),
            url:"/supCategory/updateSupCategory",
            contentType: 'application/json;charset=UTF-8',
            success:function(res){
                if(res.code === 0){
                    layer.msg("修改成功");
                }else{
                    layer.msg("修改失败");
                }
            }
        });
    });

    //监听工具条
    table.on('tool(supCategoryFilter)', function(obj){
        var data = obj.data;
        if(obj.event === 'del'){
            //删除父级分类
            layer.confirm('真的删除 ' + data.name + ' 吗？', function(index){
                $.ajax({
                    async:false,
                    dataType:"json",
                    type:"POST",
                    url:"/supCategory/deleteSupCategory",
                    headers: {'Content-Type': 'application/json;charset=utf-8'},
                    data:JSON.stringify(data),
                    success:function(res){
                        if(res.code === 0)
                        {
                            layer.msg('删除成功');
                            obj.del();
                            layer.close(index);
                        }else if(res.code === -4) {
                            layer.msg('存在' + res.data + '个分类是此父级分类,删除失败');
                        }else{
                            layer.msg('删除失败');
                        }
                    },error(e){
                        layer.msg("网络请求失败!");
                    }
                });
            });
        }
    });

});