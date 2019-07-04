var time = 3;
$(function () {

    $("#email, #password, #password-ag").focus(restore);
    $("#email").blur(checkEmail);
    $("#password").blur(checkPassword);
    $("#password-ag").blur(checkPassAg());
    $("#submit").click(checkAll);

});

function checkEmail() {
    var flag = true;
    var email = $("#email").val();

    if (email === "") {
        setError("请输入邮箱！");
        flag = false;
    } else if (!email.match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)) {
        setError("请输入合法邮箱！");
        flag = false;
    }
    return flag;
}

//check whether pass is empty
function checkPassword() {

    var flag = true;

    if ($("#password").val() === "") {
        flag = false;
        setError("请输入密码！");
    }
    return flag;
}

function checkPassAg() {
    var flag = true;
    if ($("#password").val() !== $("#password-ag").val()) {
        setError("两次输入的密码不一致！");
        flag = false;
    }
    return flag;
}

function checkAll() {
    if ((!checkPassword() && checkEmail() && checkPassAg())) {
        return false;
    }else {
        var email = $("#email").val();
        var password = $("#password").val();



        $.ajax({
                url: "/user/register",
                dataType: "json",
                async: true,
                type: "post",//增 //get 查 pot 更新 //delete
                data: {
                    "email":email,
                    "password":password
                      },
                success: function (res) {
                    if (res.status) {
                        okStyle();
                        regOk();
                    } else {
                        setError(res.errmsg);
                    }
                },
            }
        );

    }
}

//clear error info
function restore() {
    $(".err").text("");
}

//set error info
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
        var info = "register succeed，" + time + "s后跳转登陆界面";
        setError(info);
        time--;
    } else {
        window.location.href = "../index.html";
    }
}