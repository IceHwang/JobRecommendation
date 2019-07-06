
$(function () {

    $.ajax({
            url: "/user/get_history",
            dataType: "json",
            async: true,
            type: "post",//增 //get 查 pot 更新 //delete
            data: {
            },
            success: function (res) {
                var times = [];
                var jobs = [];
                var recommendSkills = [];
                var coreSkills = [];
                for (var i = 0; i < res.timeList.length; i++) {
                    var obj = JSON.parse(res.infoList[i]);
                    times.push(res.timeList[i]);

                    jobs.push(obj["jobs"]);
                    recommendSkills.push(obj["recommendSkills"]);
                    coreSkills.push(obj["coreSkills"]);

                }
                tableGenerater(times,jobs,recommendSkills,coreSkills);
            },
        }
    );

});

function transform(obj) {
    if (obj=="")
        return "无";
    else
        return obj;
}

function tableGenerater(times,jobs,recommendSkills,coreSkills) {
    var history="";

    for (var i = 0; i < times.length; i++) {
        history+="          <section class=\"py-5\">\n" +
            "            <div class=\"row\">\n" +
            "              <div class=\"col-lg-10\">\n" +
            "                <div class=\"card\">\n" +
            "                  <div class=\"card-header\">\n" +
            "                    <h6 class=\"text-uppercase mb-0\">"+times[i]+"</h6>\n" +
            "                  </div>\n" +
            "                  <div class=\"card-body\">\n" +
            "                    <table class=\"table card-text\">\n" +
            "                      <thead>\n" +
            "                      <tr>\n" +
            "                        <th>推荐职业</th>\n" +
            "                        <th>推荐技能</th>\n" +
            "                        <th>核心技能</th>\n" +
            "                      </tr>\n" +
            "                      </thead>\n" +
            "                      <tbody>\n" +
            "                      <tr>\n" +
            "                        <td>"+transform(jobs[i][0])+"</td>\n" +
            "                        <td>"+transform(recommendSkills[i][0])+"</td>\n" +
            "                        <td>"+transform(coreSkills[i][0])+"</td>\n" +
            "                      </tr>\n" +
            "                      <tr>\n" +
            "                        <td>"+transform(jobs[i][1])+"</td>\n" +
            "                        <td>"+transform(recommendSkills[i][1])+"</td>\n" +
            "                        <td>"+transform(coreSkills[i][1])+"</td>\n" +
            "                      </tr>\n" +
            "                      <tr>\n" +
            "                        <td>"+transform(jobs[i][2])+"</td>\n" +
            "                        <td>"+transform(recommendSkills[i][2])+"</td>\n" +
            "                        <td>"+transform(coreSkills[i][2])+"</td>\n" +
            "                      </tr>\n" +
            "                      </tbody>\n" +
            "                    </table>\n" +
            "                  </div>\n" +
            "                </div>\n" +
            "              </div>\n" +
            "            </div>\n" +
            "          </section>"

    }

    $("#history_container").html(history);

}




