layui.use(['form','jquery','element'], function () {
    var form = layui.form,
    $=layui.$;

    //生成验证码
    var imgId = document.getElementById("imgVerify");
    getVerify(imgId);


    //监听提交
    form.on('submit(login)', function (data) {
        var loginTime = getCookie('loginTime');
        if(loginTime == null){
            loginTime = 1;
        }
        if(loginTime > 5){
            $("#loginTimeText").html("登陆次数超过五次！");
            layer.msg("明天再来试试");
            return false;
        }else if(loginTime>0){
            $("#loginTimeText").html("密码错误"+loginTime+"次");
        }
        //验证码 验证
        var verifyCode = data.field.inputVerify;
        var requestMap={};
        requestMap.inputStr = verifyCode;

        var msg = {};
        msg.username = data.field.username;
        msg.password = data.field.password;

        $.ajax({
            async: false,
            dataType: "json",
            type: "POST",
            data:JSON.stringify(requestMap),
            url: "/verification/getVerify",
            contentType: 'application/json;charset=UTF-8',
            success: function (data) {
                if (data === 'true' || data === true) {
                    $.ajax({
                        async:false,
                        dataType:"json",
                        type:"POST",
                        data:msg,
                        url:"/manager/manageLogin",
                        // headers: {'Content-Type': 'application/json;charset=utf-8'},
                        success:function(res){
                            if(res.code == -6){
                                //用户名或密码错误
                                layer.msg("用户名或密码错误");
                                setCookie('loginTime',Number(loginTime)+1);
                                return false;
                            }else if(res.code == 0){
                                //登陆成功
                                sessionStorage.setItem('manager',JSON.stringify(res.data));
                                layer.msg("欢迎你" + res.data.managerPhone);
                                location.href = '/admin/index.html';
                            }else{
                                layer.msg("未知错误");
                                setCookie('loginTime',Number(loginTime)+1);
                                return false;
                            }
                        }
                    });
                } else {
                    //错误
                    layer.msg("验证码错误", {icon: 2, time: 1000});
                    setCookie('loginTime',Number(loginTime)+1);
                    return false;
                }
                return false;
            }
        });
        return false;
    });
});