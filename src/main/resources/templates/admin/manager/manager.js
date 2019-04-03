layui.use(['form', 'layer', 'table'], function() {
    var table = layui.table;
    $ = layui.jquery;
    var form = layui.form,
        layer = layui.layer;

    table.render({
        elem: '#managerList',
        id: 'managerTableId',
        toolbar: '#managerToolBar',
        url: '/manager/showAllManager',
        page: true,
        cols: [
            [
                { field: 'id', title: '管理员ID', sort: true },
                { field: 'username', title: '账号', sort: true },
                { field: 'password', title: '密码',sort:true },
                { field: 'status', title:'状态',templet: '#checkboxTpl', unresize: true,
                    templet: function(d) {
                        if(d.status === "1") {
                            return "<input type='checkbox' name='lock' value='" + JSON.stringify(d) + "' title='启用' lay-filter='lockStatus' checked>"
                        }else{
                            return "<input type='checkbox' name='lock' value='" + JSON.stringify(d) + "' title='启用' lay-filter='lockStatus'>"
                        }
                    }},
                { field: 'createTime', title: '创建时间',sort: true,
                    templet: function (d) {
                        return timeFormatter(d.createTime);
                    }
                },
                { field: 'loginTime', title: '最近登陆时间',sort: true,
                    templet: function (d) {
                        return timeFormatter(d.loginTime);
                    }
                },
                { title: '操作', toolbar: '#barManager'},
            ]
        ]
    });

    //监听状态操作
    form.on('checkbox(lockStatus)', function(obj){
        var manager = JSON.parse(this.value);
        if(obj.elem.checked){
            manager.status = "1";
        }else{
            manager.status = "0";
        }
        $.ajax({
            async:false,
            dataType:"json",
            type:"POST",
            data:JSON.stringify(manager),
            url:"/manager/updateManager",
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
    table.on('tool(managerFilter)', function(obj){
        var data = obj.data;
        if(obj.event === 'del'){
            //删除简介
            layer.confirm('真的删除 ' + data.username + ' 吗？', function(index){
                $.ajax({
                    async:false,
                    dataType:"json",
                    type:"POST",
                    url:"/manager/deleteManager",
                    headers: {'Content-Type': 'application/json;charset=utf-8'},
                    data:JSON.stringify(data),
                    success:function(res){
                        if(res.code === 0)
                        {
                            layer.msg('删除成功');
                            obj.del();
                            layer.close(index);
                        }else {
                            layer.msg('删除失败');
                        }
                    },error(e){
                        layer.msg("网络请求失败!");
                    }
                });
            });
        } else if(obj.event === 'edit'){
            //编辑简介
            x_admin_show('编辑简介',
                './manager-add.html?id=' + data.id);
        }
    });

});