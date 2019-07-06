
var modelList=[];
var selectedModel="";

function clickUpload() {
    $('#avatar').click();
}

$(document).ready(function () {
    $('#avatar').change(function () {
        var file = $('#avatar')[0].files[0];
        upload(file);
    })
});

function getObjectURL(file) {
    var url = null;
    // 下面函数执行的效果是一样的，只是需要针对不同的浏览器执行不同的 js 函数而已
    if (window.createObjectURL != undefined) { // basic
        url = window.createObjectURL(file);
    } else if (window.URL != undefined) { // mozilla(firefox)
        url = window.URL.createObjectURL(file);
    } else if (window.webkitURL != undefined) { // webkit or chrome
        url = window.webkitURL.createObjectURL(file);
    }
    return url;
}

function upload(file) {
    var objUrl = getObjectURL(file);
    $('#preview').attr('src', objUrl);
    var form = new FormData();
    form.append('file', file);
    $('#processing_img').removeAttr("hidden");
    $.ajax({
        url: "/user/upload",
        dataType: 'json',
        async: true,
        processData: false,
        contentType: false,
        type: 'POST',
        data: form,
        success: function (result) {
            console.log(result.status);
            if (result.status) {
                alert(result.status);
                $('#processing_img').attr("hidden","hidden");
                selectFlush();
            }
        },
        error: function (xhr) {
            alert(xhr.status);
            $('#processing_img').attr("hidden","hidden");
            selectFlush();
        }
    })
}

function confirmModel() {

    var modelName=$("#model_select").val();
    alert(modelName)
    $.ajax({
            url: "/user/confirm_model",
            dataType: "json",
            async: true,
            type: "post",//增 //get 查 pot 更新 //delete
        data: {
            "modelName":modelName
        },
            success: function (res) {
                if (res.status)
                {
                    alert("OK")
                    selectFlush();
                }
                else
                {
                    alert("Failed")
                    selectFlush();
                }


            },
        }
    );

    selectFlush();
}

function selectFlush() {
    $.ajax({
            url: "/user/get_modelList",
            dataType: "json",
            async: true,
            type: "post",//增 //get 查 pot 更新 //delete
            data: {
            },
            success: function (res) {
                if (res.status)
                {
                    selectedModel=res.selectedModel;
                    modelList=[]
                    for (var i = 0; i < res.modelList.length; i++) {
                        modelList.push(res.modelList[i]);
                        updateSelect();
                    }
                }
                else
                {
                    selectedModel="";
                    modelList=[];
                    updateSelect();
                }


            },
        }
    );


}


$(function () {
    selectFlush();
});


function updateSelect()
{
    var options="";
    for (var i = 0; i < modelList.length; i++) {
        options+="<option>"+modelList[i]+"</option>\n"
    }
    $("#model_select").html(options);
    $("#model_select").val(selectedModel);

}