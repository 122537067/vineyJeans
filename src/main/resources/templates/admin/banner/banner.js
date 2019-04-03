layui.use(['form', 'layer', 'table'], function() {
    var table = layui.table,
        $ = layui.jquery,
        form = layui.form,
        layer = layui.layer;

    table.render({
        elem: '#bannerList',
        id: 'bannerTableId',
        url: '/banner/showAllBanner',
        page: true,
        cols: [
            [
                { field: 'title', title: '标题',sort: true },
                { field: 'sort', title: '排序',sort: true },
                { field: 'goodsId', title: '商品编号',sort: true },
                { field: 'picture', title:'图片',sort:true,
                    templet:function(d){
                        return "<img src='" + d.picture + "' height='60px'/>";
                    }},
                { field: 'status', title:'状态',templet: '#checkboxTpl', unresize: true,sort:true,
                    templet: function(d) {
                        if(d.status === '1') {
                            return "<input type='checkbox' name='lock' value='" + JSON.stringify(d) + "' title='启用' lay-filter='lockStatus' checked>"
                        }else{
                            return "<input type='checkbox' name='lock' value='" + JSON.stringify(d) + "' title='启用' lay-filter='lockStatus'>"
                        }
                    }},
                { title: '操作', toolbar: '#barBanner'}
            ]
        ]
    });

    //监听状态操作
    form.on('checkbox(lockStatus)', function(obj){
        var banner = JSON.parse(this.value);
        if(obj.elem.checked){
            banner.status = 1;
        }else{
            banner.status = 0;
        }
        $.ajax({
            async:false,
            dataType:"json",
            type:"POST",
            data:JSON.stringify(banner),
            url:"/banner/updateBanner",
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
    table.on('tool(bannerFilter)', function(obj){
        var data = obj.data;
        if(obj.event === 'del'){
            //删除父级分类
            layer.confirm('真的删除 ' + data.name + ' 吗？', function(index){
                $.ajax({
                    async:false,
                    dataType:"json",
                    type:"POST",
                    url:"/banner/deleteBanner",
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
            x_admin_show('编辑商品',
                './banner-add.html?id=' + data.id
                + '&goodsId=' + data.goodsId
                + '&title=' + encodeURI(encodeURI(data.title))
                + '&intro=' + encodeURI(encodeURI(data.intro))
                + '&picture=' + data.picture
                + '&sort=' + data.sort
                + '&status=' + data.status);
        }
    });

});