// $('#newblog-container').load(/*[[@{/footer/newblog}]]*/"/footer/newblog");

var requestUrl = sessionStorage.getItem("requestUrl");
if (requestUrl==null){
    window.location="./index.html";
}


//分页获取文章
function listArticles(params) {
    $.ajax({
        type: "POST",
        data: JSON.stringify(params),
        url: requestUrl + "/blog-article/front/article/list",
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (res) {
            //返回成功
            if (res.code == 200) {
                var list = res.data.result;
                for (var i = 0; i < list.length; i++) {
                    var str = list[i];
                    var html = "<li class=\"in-view\" >\n" +
                        "              <div>\n" +
                        "                <time>"+str.createTime+"</time>\n" +
                        "                <div class=\"scientist\" >\n" +
                        "                    <a href='blog.html?id="+str.articleId+"'>\n" +
                        "                      <h3 class=\"state\" style=\"text-align:center;font-size: 16px;color: #000;\">"+str.title+"</h3>\n" +
                        "                    </a>\n" +
                        "                </div>\n" +
                        "              </div>\n" +
                        "            </li>";
                    $("#articles").append(html);
                }
            } else {
                alert(res.msg);
            }
        },
        error: function (result) {
            console.log("网络超时！");
        }
    });
}

listArticles({"pageNum": 1, "pageSize": 200, "status": 2});


$('.menu.toggle').click(function () {
    $('.m-item').toggleClass('m-mobile-hide');
});

// 运行时间统计
function secondToDate(second) {
    if (!second) {
        return 0;
    }
    var time = new Array(0, 0, 0, 0, 0);
    if (second >= 365 * 24 * 3600) {
        time[0] = parseInt(second / (365 * 24 * 3600));
        second %= 365 * 24 * 3600;
    }
    if (second >= 24 * 3600) {
        time[1] = parseInt(second / (24 * 3600));
        second %= 24 * 3600;
    }
    if (second >= 3600) {
        time[2] = parseInt(second / 3600);
        second %= 3600;
    }
    if (second >= 60) {
        time[3] = parseInt(second / 60);
        second %= 60;
    }
    if (second > 0) {
        time[4] = second;
    }
    return time;
}
function setTime() {
    /*此处为网站的创建时间*/
    var create_time = Math.round(new Date(Date.UTC(2020, 01, 25, 15, 15, 15)).getTime() / 1000);
    var timestamp = Math.round((new Date().getTime() + 8 * 60 * 60 * 1000) / 1000);
    currentTime = secondToDate((timestamp - create_time));
    currentTimeHtml = currentTime[0] + '年' + currentTime[1] + '天'
        + currentTime[2] + '时' + currentTime[3] + '分' + currentTime[4]
        + '秒';
    document.getElementById("htmer_time").innerHTML = currentTimeHtml;
}
setInterval(setTime, 1000);