layui.use(['form', 'layer', 'table'], function() {
    var table = layui.table;
    $ = layui.jquery;
    var form = layui.form,
        layer = layui.layer;

    table.render({
        elem: '#commentList',
        id: 'commentTableId',
        toolbar: '#commentToolBar',
        url: '/comment/showAllComment',
        page: true,
        cols: [
            [
                { type: 'checkbox'},
                { field: 'id', title: '编号', sort: true },
                { field: 'createTime', title: '留言时间', sort: true,templet: function(d){
                        return timeFormatter(d.createTime);
                    }},
                { field: 'content', title: '评论内容', sort: true },
                { field: 'customer', title: '留言用户名', sort: true },
                { field: 'contact', title: '联系方式',sort:true},
            ]
        ]
    });

    //监听表格复选框选择
    table.on('checkbox(commentFilter)', function(obj){

    });

    var active = {
        delComment: function(){ //删除选中行评论
            var checkStatus = table.checkStatus('commentTableId')
                ,data = checkStatus.data;
            $.ajax({
                async:false,
                dataType:"json",
                type:"POST",
                data:JSON.stringify(data),
                url:"/comment/deleteComment",
                contentType: 'application/json;charset=UTF-8',
                success:function(data){
                    if(data.code === 0){
                        layer.msg("删除" + data.data);
                    }else {
                        layer.alert("删除失败");
                    }
                    table.reload("commentTableId");
                }
            });
        }
    };

    $('.commentToolBar .layui-btn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

});