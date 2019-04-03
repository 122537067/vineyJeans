layui.use(['form', 'layer', 'table'], function() {
    var table = layui.table;
    $ = layui.jquery;
    var form = layui.form,
        layer = layui.layer;

    table.render({
        elem: '#vlrList',
        id: 'vlrTableId',
        url: '/userIp/showAllUserIp',
        page: true,
        cols: [
            [
                { type: 'checkbox'},
                { field: 'id', title: '编号', sort: true },
                { field: 'ip', title: 'IP地址', sort: true },
                { field: 'createTime', title: '登陆时间',sort:true,templet:function(d){
                    return timeFormatter(d.createTime);
                    }},
            ]
        ]
    });

    var active = {
        delVLR: function(){ //删除选中行评论
            var checkStatus = table.checkStatus('vlrTableId')
                ,data = checkStatus.data;
            var ids = [data.length];
            for(var i=0;data.length>i;i++){
                ids[i] = data[i].id;
            }
            console.log(data);
            $.ajax({
                async:false,
                dataType:"json",
                traditional :true,
                type:"POST",
                data:{
                    "ids":ids
                },
                url:"/userIp/deleteUserIp",
                success:function(data){
                    if(data.code === 0){
                        layer.msg("删除" + data.data + "成功");
                    }else {
                        layer.alert("删除失败");
                    }
                    table.reload("vlrTableId");
                }
            });
        }
    };

    $('.vrlToolBar .layui-btn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    
});