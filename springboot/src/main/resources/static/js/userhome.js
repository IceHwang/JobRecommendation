

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

                    for (var i = 0; i < res.jobs.length; i++) {
                        jobs.push(res.jobs[i]);
                    }
                    for (var i = 0; i < res.skills.length; i++) {
                        skills.push(res.skills[i]);
                    }
                    tableGenerater();

                }


            },
        }
    );

});


function tableGenerater() {

    var job_table="";
    for (var i = 0; i < Math.ceil(jobs.length/4); i++) {
        job_table+="<tr>\n";

        for (var j = 0; j < 4; j++) {
            var index=i*4+j;
            if (index===jobs.length)
                break;
            job_table+="" +
                "<td  >\n" +
                "\t\t\t\t\t <a onClick=\"clickJob('"+index+"')\"  class=\"message card px-5 py-2 mb-4 bg-hover-gradient-primary no-anchor-style\">\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t <p class=\"mb-0 mt-3 mt-lg-0\">"+jobs[index]+"</p>\t\t\n" +
                "\t\t\t\t\t\t <div id=\"tick_img_job"+index+"\" class=\"img\" hidden='hidden'><img src=\"/img/tick.png\"></div>\t  \n" +
                "\t\t\t\t\t </a>\n" +
                "                  </td>" +
                "" +
                "";
        }
        job_table+="</tr>";
    }
    $("#job_table").html(job_table);


    var skill_table="";
    for (var i = 0; i < Math.ceil(skills.length/4); i++) {
        skill_table+="<tr>\n";

        for (var j = 0; j < 4; j++) {
            var index=i*4+j;
            if (index===skills.length)
                break;
            skill_table+="" +
                "<td  >\n" +
                "\t\t\t\t\t <a onClick=\"clickSkill('"+index+"')\" class=\"message card px-5 py-2 mb-4 bg-hover-gradient-primary no-anchor-style\">\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t <p class=\"mb-0 mt-3 mt-lg-0\">"+skills[index]+"</p>\t\t\n" +
                "\t\t\t\t\t\t <div id=\"tick_img_skill"+index+"\" class=\"img\" hidden='hidden'><img src=\"/img/tick.png\"></div>\t  \n" +
                "\t\t\t\t\t </a>\n" +
                "                  </td>" +
                "" +
                "";
        }
        skill_table+="</tr>";
    }
    $("#skill_table").html(skill_table);


}


function clickJob(index)
{
    for (var i = 0; i < jobs.length; i++) {
        var name="#tick_img_job"+i;
        $(name).attr("hidden","hidden");
    }
    $("#tick_img_job"+index).removeAttr("hidden");

}


function clickSkill(index)
{

    var flag=$("#tick_img_skill"+index).attr("hidden");
    if (flag==="hidden")
    {
        $("#tick_img_skill"+index).removeAttr("hidden");
    }
    else
    {
        $("#tick_img_skill"+index).attr("hidden","hidden")
    }


}

function choose() {
    var preferedJob;
    var skillList=[];
    for (var i = 0; i < jobs.length; i++)
    {
        var flag=$("#tick_img_job"+i).attr("hidden");
        if (flag===undefined)
            preferedJob=jobs[i];
        else
            continue;
    }
    for (var i = 0; i < skills.length; i++)
    {
        var flag=$("#tick_img_skill"+i).attr("hidden");
        if (flag===undefined)
            skillList.push(skills[i]);
        else
            continue;
    }

    $.ajax({
            url: "/user/choose",
            dataType: "json",
            async: true,
            type: "post",//增 //get 查 pot 更新 //delete
            data: {
                "skillList":JSON.stringify(skillList),
                "preferedJob":preferedJob
            },
            success: function (res) {
                if (res.status)
                {
                    window.location.href = "recommend";
                }
                else
                {

                }


            },
        }
    );
}