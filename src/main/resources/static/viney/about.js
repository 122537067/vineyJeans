//生成验证码
var imgId = document.getElementById("imgVerify");
getVerify(imgId);

$(function(){
    //数据加载
    $.ajax({
        async:false,
        dataType:"json",
        type:"GET",
        url:"/about/showAbout",
        success:function(aboutList){
            for(var i=0;aboutList.length>i;i++) {
                $("#aboutListId").append(`
                    <div class="row">
                        <div class="col-12">
                            <div class="section-title text-center mb-35">
                                <h2>${aboutList[i].title}</h2>
                                <span>${aboutList[i].subtitle}</span>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-8 ml-auto mr-auto">
                            <div class="history-area-content text-center">
                                <p><strong>${aboutList[i].intro}</strong></p>
                                <p>${aboutList[i].content}</p>
                            </div>
                        </div>
                    </div>
                `);
            }
        }
    });
});


function doSubmitForm(){
    var comment = {};
    comment.contact = $("#contact").val();
    comment.customer = $("#customerId").val();
    comment.content = $("#contentId").val();
    var verify = $("#verifyId").val();
    if(verify == "" || comment.contact == "" || comment.customer == "" || comment== ""){
        //留空
        $("#formMsg").show();
    }else{
        //验证码验证
        var requestMap={};
        requestMap.inputStr = verify;
        $.ajax({
            async: false,
            dataType: "json",
            type: "POST",
            data:JSON.stringify(requestMap),
            url: "/verification/getVerify",
            contentType: 'application/json;charset=UTF-8',
            success: function (data) {
                if (data === 'true' || data === true) {
                    getVerify(imgId);   //验证成功刷新验证码
                    $.ajax({
                        async: false,
                        dataType: "json",
                        type: "POST",
                        data: JSON.stringify(comment),
                        url: "/comment/insertComment",
                        contentType: 'application/json;charset=UTF-8',
                        success: function (res) {
                            if (res.code === 0) {
                                //插入成功
                                $("#formMsg").show();
                                $("#formMsg").html("comment success");
                                $("#formMsg").css("color","green");
                            } else {
                                $("#formMsg").show();
                                $("#formMsg").html("comment fail");
                            }
                        }
                    });
                } else {
                    //错误
                    $("#formMsg").show();
                    $("#formMsg").html("verify fail");
                }
            }
        });
    }
}
