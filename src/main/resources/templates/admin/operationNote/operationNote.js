layui.use(['form', 'layer', 'table'], function() {
    var table = layui.table;
    $ = layui.jquery;
    var form = layui.form,
        layer = layui.layer;

    table.render({
        elem: '#operationNoteList',
        id: 'operationNoteTableId',
        url: '/managerOperation/showManagerOperation',
        toolbar:true,
        defaultToolbar: ['filter', 'print', 'exports'],
        page: true,
        cols: [
            [
                { field: 'managerId', title: '操作ID', sort: true },
                { field: 'ip', title: '操作IP地址', sort: true },
                { field: 'operation', title: '操作', sort: true },
                { field: 'msg', title: '操作内容', sort: true },
                { field: 'opResult', title: '操作结果', sort: true },
                { field: 'time', title: '操作时间',sort:true,templet:function(d){
                    return timeFormatter(d.time);
                    }},
            ]
        ]
    });

    //模糊查询
    var $ = layui.$, active = {
        reload: function(){
            var paramOp = $('#operationNoteDo').val();
            var paramRes = $('#operationNoteRes').val();
            var paramIp = $('#operationNoteIp').val();
            var paramManager = $('#operationNoteId').val();

            //执行重载
            table.reload('operationNoteTableId', {
                url: '/managerOperation/showManagerOperationByParams',
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                ,where: {
                    paramOp:paramOp,
                    paramRes:paramRes,
                    paramIp:paramIp,
                    paramManager:paramManager,
                }
            });
        }
    };

    $('.operationNoteTable .layui-btn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

});