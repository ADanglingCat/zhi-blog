// $('#newblog-container').load(/*[[@{/footer/newblog}]]*/"/footer/newblog");
var requestUrl = sessionStorage.getItem("requestUrl");
if (requestUrl==null){
    window.location="./index.html";
}
//获取关键词/**/
var keyword= window.location.href.split('?')[1].split('=')[1].split('#')[0];
if (keyword!=null){
    list(1,10,keyword);
}

function next(pageNum,pageSize,keyword) {
    list(pageNum,pageSize,keyword);
}

//分页获取文章
function list(pageNum,pageSize,keyword) {
    $.ajax({
        type: "POST",
        data: JSON.stringify({
            "keyword": keyword,
            "pageNum": pageNum,
            "pageSize": pageSize
        }),
        url: sessionStorage.getItem("requestUrl") + "/blog-article/front/article/search",
        contentType: "application/json;charset=utf-8",
        async: true,
        success: function (res) {
            //返回成功
            if (res.code == 200) {
                var list = res.data.result;
                var total=res.data.pages;
                $("#article").text("");
                var header="<div class=\"ui top attached segment\">\n" +
                    "        <div class=\"ui middle aligned two column grid\">\n" +
                    "          <div class=\"column\">\n" +
                    "            <h3 class=\"ui teal header\">搜索结果</h3>\n" +
                    "          </div>\n" +
                    "          <div class=\"right aligned column\">\n" +
                    "            共 <h2 class=\"ui orange header m-inline-block m-text-thin\"> "+res.data.total+" </h2> 个\n" +
                    "          </div>\n" +
                    "        </div>\n" +
                    "      </div>";
                $("#article").append(header);
                if (list==null)
                    return;
                for (var i = 0; i < list.length; i++) {
                    var str = list[i];
                    var article = "<div class=\"ui top attached segment\">\n" +
                        "        <div class=\"ui padded vertical segment m-padded-tb-large\">\n" +
                        "          <div class=\"ui middle aligned mobile reversed stackable grid\" >\n" +
                        "            <div class=\"eleven wide column\">\n" +
                        "              <h3 class=\"ui header\" ><a href='./blog.html?id="+str.articleId+"'>"+str.title+"</a></h3>\n" +
                        "              <p class=\"m-text m-margin-top-max\">"+str.describe+"</p>\n" +
                        "              <div class=\"ui grid m-margin-top-max\">\n" +
                        "                <div class=\"eleven wide column\">\n" +
                        "                  <div class=\"ui mini horizontal link list\">\n" +
                        "                    <div class=\"item\">\n" +
                        "                      <img src=\"../static/images/me.jpg\" alt=\"\" class=\"ui avatar image\">\n" +
                        "                      <div class=\"content\"><a href=\"#\" target=\"_blank\" class=\"header\">"+str.authorName+"</a></div>\n" +
                        "                    </div>\n" +
                        "                    <div class=\"item\">\n" +
                        "                      <i class=\"calendar icon\"></i><span>"+str.createTime+"</span>\n" +
                        "                    </div>\n" +
                        "                    <div class=\"item\">\n" +
                        "                      <i class=\"eye icon\"></i> <span>"+str.viewNum+"</span>\n" +
                        "                    </div>\n" +
                        "                  </div>\n" +
                        "                </div>\n" +
                        "                <div class=\"right aligned five wide column\">\n" +
                        "                  <a href=\"#\" target=\"_blank\" class=\"ui teal basic label m-padded-tiny m-text-thin\">"+str.typeName+"</a>\n" +
                        "                </div>\n" +
                        "              </div>\n" +
                        "            </div>\n" +
                        "\n" +
                        "            <!--博文首图-->\n" +
                        "            <div class=\"five wide column\">\n" +
                        "              <a href='./blog.html?id="+str.articleId+"'>\n" +
                        "                <img src=\""+str.imgUrl+"\" alt=\"\" class=\"ui rounded image\">\n" +
                        "              </a>\n" +
                        "            </div>\n" +
                        "\n" +
                        "          </div>\n" +
                        "        </div>\n" +
                        "      </div>";

                    $("#article").append(article);
                }
                var next="\n" +
                    "      <div class=\"ui bottom attached segment m-opacity stackable grid\">\n" +
                    "        <div class=\"three wide column\" align=\"center\">\n" +
                    "          <a class=\"item\" onclick='next("+(pageNum-1<=0?1:pageNum-1)+","+(pageSize)+",\""+(keyword)+"\")'>上一页</a>\n" +
                    "        </div>\n" +
                    "\n" +
                    "        <div class=\"ten wide column\" align=\"center\">\n" +
                    "          <p> <span></span> "+pageNum+" <span></span> </p>\n" +
                    "        </div>\n" +
                    "\n" +
                    "        <div class=\"three wide column\" align=\"center\">\n" +
                    "          <a class=\"item\" onclick='next("+(pageNum+1>total?total:pageNum+1)+","+(pageSize)+",\""+(keyword)+"\")'>下一页</a>\n" +
                    "        </div>\n" +
                    "      </div>";
                $("#article").append(next);
            } else {
                alert(res.msg);
            }
        },
        error: function (result) {
            console.log("网络超时！");
        }
    });
}


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