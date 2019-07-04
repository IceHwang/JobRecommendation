var jobs = ["job", "job", "job"];
var recommendSkills = ["recommendSkills","recommendSkills","recommendSkills"];
var coreSkills = ["coreSkills","coreSkills","coreSkills"];

$(function () {


    $.ajax({
            url: "/user/recommend",
            dataType: "json",
            async: true,
            type: "post",//增 //get 查 pot 更新 //delete
            data: {
                "preferedJob":"",
                "skillArray" :""
            },
            success: function (res) {
                for (var i = 0; i < 3; i++) {
                    jobs[i]=res.jobs[i];
                    recommendSkills[i]=res.recommendSkills[i];
                    coreSkills[i]=res.coreSkills[i];
                }
                putUpResult();

            },
        }
    );

});


function putUpResult() {
    for (var i = 0; i < 3; i++) {
        $("#job"+i).text(jobs[i]);
        $("#recommendSkills"+i).text("推荐技能:"+recommendSkills[i]);
        $("#coreSkills"+i).text("核心技能:"+coreSkills[i]);

    }

}
