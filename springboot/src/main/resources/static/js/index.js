$(function () {

    $("#email, #password").focus(restore);
    $("#email").blur(checkEmail);
    $("#password").blur(checkPassword);
    $("#submit").click(checkAll);
});

function checkEmail() {
    let flag;
    let email = $("#email").val();

    if (email === "") {
        setError("请输入邮箱！");
        flag = false;
    } else if (!email.match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)) {
        setError("请输入合法邮箱！");
        flag = false;
    } else {
        flag = true;
    }
    return flag;
}

//check whether pass is empty
function checkPassword() {

    let flag = true;

    if ($("#password").val() === "") {
        flag = false;
        setError("请输入密码！");
    }
    return flag;
}

function checkAll() {

    if (!(checkEmail() && checkPassword())) {
        return false;
    } else {
        //ajax submit
        let email = $("#email").val();
        let password = $("#password").val();

        $.ajax({
            url: "http://localhost:8080/user/login",
            dataType: "json",
            async: true,
            type: "post",
            data: {
                "username": email,
                "password": password
            },
            success: function (res) {
                if (res.status) {
                    sessionStorage.user = JSON.stringify(res.data);
                    window.location.href = "../index.html";
                } else {
                    setError(res.message);
                }
            }
        });
    }
}

//clear error info
function restore() {
    $(".err").text("");
}

//set error info
function setError(info) {
    $(".err").text(info);
    window.location.href = "html/404.html";
}
