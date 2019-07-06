
$(function () {
    tableGenerater(null,null,null,null);

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

                for (var i = 0; i < res.times.length; i++) {

                    times.push(res.times[i]);

                    jobs.push(res.jobs[i]);
                    recommendSkills.push(res.recommendSkills[i]);
                    coreSkills.push(res.coreSkills[i]);

                }
                tableGenerater(times,jobs,recommendSkills,coreSkills);
            },
        }
    );

});

function tableGenerater(times,jobs,recommendSkills,coreSkills) {
    var history="";

    // for (var i = 0; i < times.length; i++) {
    //     history+="          <section class=\"py-5\">\n" +
    //         "            <div class=\"row\">\n" +
    //         "              <div class=\"col-lg-10\">\n" +
    //         "                <div class=\"card\">\n" +
    //         "                  <div class=\"card-header\">\n" +
    //         "                    <h6 class=\"text-uppercase mb-0\">"+times[i]+"</h6>\n" +
    //         "                  </div>\n" +
    //         "                  <div class=\"card-body\">\n" +
    //         "                    <table class=\"table card-text\">\n" +
    //         "                      <thead>\n" +
    //         "                      <tr>\n" +
    //         "                        <th>推荐职业</th>\n" +
    //         "                        <th>推荐技能</th>\n" +
    //         "                        <th>核心技能</th>\n" +
    //         "                      </tr>\n" +
    //         "                      </thead>\n" +
    //         "                      <tbody>\n" +
    //         "                      <tr>\n" +
    //         "                        <td>"+jobs[i][0]+"</td>\n" +
    //         "                        <td>"+jobs[i][1]+"</td>\n" +
    //         "                        <td>"+jobs[i][2]+"</td>\n" +
    //         "                      </tr>\n" +
    //         "                      <tr>\n" +
    //         "                        <td>"+recommendSkills[i][0]+"</td>\n" +
    //         "                        <td>"+recommendSkills[i][1]+"</td>\n" +
    //         "                        <td>"+recommendSkills[i][2]+"</td>\n" +
    //         "                      </tr>\n" +
    //         "                      <tr>\n" +
    //         "                        <td>"+coreSkills[i][0]+"</td>\n" +
    //         "                        <td>"+coreSkills[i][1]+"</td>\n" +
    //         "                        <td>"+coreSkills[i][2]+"</td>\n" +
    //         "                      </tr>\n" +
    //         "                      </tbody>\n" +
    //         "                    </table>\n" +
    //         "                  </div>\n" +
    //         "                </div>\n" +
    //         "              </div>\n" +
    //         "            </div>\n" +
    //         "          </section>"
    //
    // }


    for (var i = 0; i < 5; i++) {
        history+="          <section class=\"py-5\">\n" +
            "            <div class=\"row\">\n" +
            "              <div class=\"col-lg-10\">\n" +
            "                <div class=\"card\">\n" +
            "                  <div class=\"card-header\">\n" +
            "                    <h6 class=\"text-uppercase mb-0\">2019/07/04 21:00:00</h6>\n" +
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
            "                        <td>Mark</td>\n" +
            "                        <td>Otto</td>\n" +
            "                        <td>@mdo</td>\n" +
            "                      </tr>\n" +
            "                      <tr>\n" +
            "                        <td>Jacob</td>\n" +
            "                        <td>Thornton</td>\n" +
            "                        <td>@fat</td>\n" +
            "                      </tr>\n" +
            "                      <tr>\n" +
            "                        <td>Larry</td>\n" +
            "                        <td>the Bird</td>\n" +
            "                        <td>@twitter</td>\n" +
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




