var jobs = [];
var skills = [];

$(function () {


    $.ajax({
            url: "/user/get_info",
            dataType: "json",
            async: true,
            type: "post",//增 //get 查 pot 更新 //delete
            data: {

            },
            success: function (res) {
                if (res.status)
                {
                    for (var i = 0; i < res.jobs; i++) {
                        jobs.push(res.jobs[i]);
                    }
                    for (var i = 0; i < res.skills; i++) {
                        skills.push(res.skills[i]);
                    }
                }

                tableGenerater();

            },
        }
    );

});


function tableGenerater() {


}
