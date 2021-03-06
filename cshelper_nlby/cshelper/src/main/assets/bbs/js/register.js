var time = 3; //设置跳转时间
$(function () {

    $("#name, #email, #pass, #pass-ag").focus(restore);
    $("#email").blur(checkEmail);
    $("#name").blur(checkName);
    $("#pass").blur(checkPass);
    $("#pass-ag").blur(checkPassAg);
    $("#submit").click(checkAll);

});

function checkName() {
    var name = $("#name").val();
    var flag = true;

    if(name === ""){
        setError("请输入用户名！");
        flag = false;
    }else if(name.length < 4 && name.length > 10){
        setError("4~10个字符长度了解一下！");
        flag = false;
    }
    return flag;
}

function checkEmail() {
    var flag = true;
    var email = $("#email").val();

    if(email === ""){
        setError("请输入邮箱！");
        flag = false;
    } else if (!email.match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)){
        setError("请输入合法邮箱！");
        flag = false;
    }
    return flag;
}


function checkPass() {

    var flag = true;

    if($("#pass").val() === ""){
        flag = false;
        setError("请输入密码！");
    }
    return flag;
}

function checkPassAg() {
    var flag = true;
    if ($("#pass").val() !== $("#pass-ag").val()){
        setError("两次输入的密码不一致！");
        flag = false;
    }
    return flag;
}

function checkAll() {
    if(!(checkName() && checkPass() && checkEmail() && checkPassAg())){
        return false;
    } else {
        var nickname = $("#name").val();
        var email = $("#email").val();
        var pass = $("#pass").val();

        $.ajax({
                url: "http://123.56.15.54:8080/anbbs/user/register",
                dataType: "json",
                async: true,
                type: "post",
                data: {
                    "nickname": nickname,
                    "username": email,
                    "password": pass
                },
                success: function (res) {
                    if(res.status){
                        okStyle();  //注册成功变成绿色
                        regOk();    //显示信息并跳转到登录界面
                    } else {
                        setError(res.message);
                    }
                },
            }
        );


        // regOk();
    }
}
//清楚错误信息，即不显示文字
function restore() {
    $(".err").text("");
}

//设置错误信息
function setError(info) {
    $(".err").text(info);
}

function okStyle() {
    $(".err").css({
        color: "#fff",
        background: "#89ee90"
    });
}

function regOk() {
    setTimeout(regOk, 1000);
    if (time > 0) {
        var info = "注册成功，" + time + "s后跳转登陆界面";
        setError(info);
        time--;
    } else {
        window.location.href = "login.html";
    }
}