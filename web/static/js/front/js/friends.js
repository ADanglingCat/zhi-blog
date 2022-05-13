var requestUrl = sessionStorage.getItem("requestUrl");
if (requestUrl==null){
    window.location="./index.html";
}

//获取推荐的文章
function listFriendLinks() {
    $.ajax({
        type: "POST",
        data: JSON.stringify({"pageNum": 1, "pageSize": 100}),
        url: sessionStorage.getItem("requestUrl") + "/blog-friendlinks/front/friend/links/list",
        contentType: "application/json;charset=utf-8",
        async: true,
        success: function (res) {
            //返回成功
            if (res.code == 200) {
                var list = res.data.result;
                $("#friends").text("");
                for (var i = 0; i < list.length; i++) {
                    var str = list[i];
                    var html = "<div class=\"m-margin-tb-tiny four wide column\">\n" +
                        "                        <a href='"+str.blogUrl+"' class=\"class_outer\" target=\"_blank\">\n" +
                        "                            <div align=\"center\">\n" +
                        "                                <div class=\"friends-link\">\n" +
                        "                                    <img src='"+str.imageUrl+"'  alt=\"\" class=\"friends-link-image\">\n" +
                        "                                    <div class=\"m-margin-top\">\n" +
                        "                                     <h4 class=\"m-font-size-text-friends m-margin-tb-tiny\">"+str.blogName+"</h4>\n" +
                        "                                    </div>\n" +
                        "                                </div>\n" +
                        "                            </div>\n" +
                        "                        </a>\n" +
                        "                    </div>";
                    $("#friends").append(html);
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

listFriendLinks();

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


$('.menu.toggle').click(function () {
    $('.m-item').toggleClass('m-mobile-hide');
});


