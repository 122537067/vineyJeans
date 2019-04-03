layui.use('laydate', function () {
    var $ = layui.jquery

    //处理权限->添加权限记得修改
    var manager = JSON.parse(sessionStorage.getItem("manager"));
    for(var i=1;i<=11;i++) {
        if(manager.roleId.indexOf("," + i + ",") <= -1){
            $('.' + i).hide();
        }
    }

    $("#managerPhone").append(manager.username);
    var loginTime = timeFormatter(manager.loginTime);
    $("#managerLoginTime").append("登陆时间"+loginTime);

});

