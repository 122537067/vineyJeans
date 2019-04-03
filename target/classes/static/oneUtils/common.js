var IP = "http://" + window.location.host + "/";

function getUrl() {
    return IP;
}

/*获取地址栏参数的函数*/
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

//mysql datetime 转换 yyyy-mm-dd hh:mm:ss
function timeFormatter(value) {
    var dateee = new Date(value).toJSON();
    var date = new Date(+new Date(dateee)+8*3600*1000).toISOString().replace(/T/g,' ').replace(/\.[\d]{3}Z/,'')
    return date;
}

//验证码生成
function getVerify(obj) {
    obj.src = IP + "/verification/createVerify?" + Math.random();
}

//验证码验证
//正确返回 true
//错误返回 false
function checkVerify(value) {
    var $ = layui.jquery;
    var requestMap={};
    requestMap.inputStr = value;
    $.ajax({
        async: false,
        dataType: "json",
        type: "POST",
        data:JSON.stringify(requestMap),
        url: IP + "verification/getVerify",
        contentType: 'application/json;charset=UTF-8',
        success: function (data) {
            if(data === 'true' || data === true){
                return true;
            }else{
                return false;
            }
        }
    });
}

//电话号码验证
//正确返回 true
//错误返回 false
function checkPhone(phone) {
    var isPhone = /^[1][3,4,5,7,8][0-9]{9}$/;//手机号码
    var isMob= /^0?1[3|4|5|8][0-9]\d{8}$/;// 座机格式
    if(isMob.test(phone)||isPhone.test(phone)){
        return true;
    }
    else{
        return false;
    }
}

//邮箱验证
//正确返回 true
//错误返回 false
function checkEmail(email) {
    // var reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$"); //正则表达式
    var reg = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
    if(email.value === ""){ //输入不能为空
        return false;
    }else if(!reg.test(email)){ //正则验证不通过，格式不对
        return false;
    }else{
        return true;
    }
}


//取Cookie的值
function getCookie(name) {
    var arg = name + "=";
    var alen = arg.length;
    var clen = document.cookie.length;
    var i = 0;
    while (i < clen) {
        var j = i + alen;
        if (document.cookie.substring(i, j) == arg) return getCookieVal(j);
        i = document.cookie.indexOf(" ", i) + 1;
        if (i == 0) break;
    }
    return null;
}
//写入到Cookie
//name:cookie名称  value:cookie值
function setCookie(name, value) {
    var Days = 30;
    var exp = new Date();
    exp.setTime(exp.getTime() + 60 * 1000 * 60 * 12);//过期时间 12 小时
    document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
}

function getCookieVal(offset) {
    var endstr = document.cookie.indexOf(";", offset);
    if (endstr == -1) endstr = document.cookie.length;
    return unescape(document.cookie.substring(offset, endstr));
}

//移除第一个和最后一个元素
function removeStartEnd(str){
    str = str.substr(1);
    str = str.substr(0,str.length-1);
    return str;
}

//复制url到剪贴板
function copyUrl()
{
    var clipBoardContent=this.location.href;
    console.log(clipBoardContent);
    document.execCommand("Copy");
    alert("复制成功!分享你的链接吧");
}

//IP存cookie
var userIp = getCookie("userIp");
if(userIp == null) {
    $.ajax({
        async: true,
        type: "POST",
        url: "/userIp/getIp",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        success: function (res) {
            setCookie("userIp", res);
        }
    });
}

