// $('#newblog-container').load(/*[[@{/footer/newblog}]]*/"/footer/newblog");
var requestUrl = sessionStorage.getItem("requestUrl");
if (requestUrl==null){
    window.location="./index.html";
}
//接收编辑传递过来的参数
var typeId= window.location.href.split('?')[1]==undefined?null:window.location.href.split('?')[1].split('=')[1];
$.ajax({
    type: "POST",
    data: JSON.stringify({
        "pageNum": 1,
        "pageSize": 100
    }),
    url: sessionStorage.getItem("requestUrl") + "/blog-article/front/type/list",
    contentType:"application/json;charset=utf-8",
    async: true,
    success: function (res) {
        //返回成功
        if (res.code==200){
            var list=res.data.result;
            for (var i = 0; i < list.length; i++) {
                var str=list[i];
                var html="<div class=\"ui labeled button m-margin-tb-tiny\""+' onclick="listArticles(1,10,2,'+str.id+')">\n'+
                    "          <a href=\"#\" class=\"ui basic  button\">"+str.name+"</a>\n" +
                    "          <div class=\"ui basic  left pointing label\">"+str.total+"</div>\n" +
                    "        </div>";
                $("#type").append(html);
            }
        }else{
            alert(res.msg);
        }
    },
    error: function (result) {
        console.log("网络超时！");
    }
});

//分页获取文章
function listArticles(pageNum,pageSize,status,typeId) {
    var params={"pageNum": pageNum, "pageSize": pageSize, "status": status};
    if (typeId!=null){
        params={"pageNum": pageNum, "pageSize": pageSize, "status": status,"typeId":typeId};
    }
    $.ajax({
        type: "POST",
        data: JSON.stringify(params),
        url: sessionStorage.getItem("requestUrl") + "/blog-article/front/article/list",
        contentType: "application/json;charset=utf-8",
        async: true,
        success: function (res) {
            $("#article").text('');
            //返回成功
            if (res.code == 200) {
                var list = res.data.result;
                var total=res.data.pages;
                var p="<div class=\"three wide column\" align=\"center\">\n" +
                    "        <a class=\"item\" onclick='next("+(pageNum-1<=0?1:pageNum-1)+","+(pageSize)+","+(status)+","+(typeId)+")'>上一页</a>\n" +
                    "      </div>\n" +
                    "\n" +
                    "      <div class=\"ten wide column\" align=\"center\">\n" +
                    "        <p> <span></span>"+pageNum+"<span></span> </p>\n" +
                    "      </div>\n" +
                    "\n" +
                    "      <div class=\"three wide column\" align=\"center\">\n" +
                    "        <a class=\"item\" onclick='next("+(pageNum+1>total?total:pageNum+1)+","+(pageSize)+","+(status)+","+(typeId)+")'>下一页</a>\n" +
                    "      </div>";
                $("#pageHelper").text('');
                for (var i = 0; i < list.length; i++) {
                    var str = list[i];
                    var html="<div class=\"ui padded vertical segment m-padded-tb-large\">\n" +
                        "        <div class=\"ui middle aligned mobile reversed stackable grid\" >\n" +
                        "          <div class=\"eleven wide column\">\n" +
                        "            <h3 class=\"ui header\" ><a href='./blog.html?id="+str.articleId+"'>"+str.title+"</a></h3>\n" +
                        "            <p class=\"m-text\">"+str.describe+"</p>\n" +
                        "            <div class=\"ui grid\">\n" +
                        "              <div class=\"eleven wide column\">\n" +
                        "                <div class=\"ui mini horizontal link list\">\n" +
                        "                  <div class=\"item\">\n" +
                        "                    <img src=\"../static/images/me.jpg\"  alt=\"\" class=\"ui avatar image\">\n" +
                        "                    <div class=\"content\"><a href=\"#\" target=\"_blank\" class=\"header\">"+str.authorName+"</a></div>\n" +
                        "                  </div>\n" +
                        "                  <div class=\"item\">\n" +
                        "                    <i class=\"calendar icon\"></i><span>"+str.createTime+"</span>\n" +
                        "                  </div>\n" +
                        "                  <div class=\"item\">\n" +
                        "                    <i class=\"eye icon\"></i> <span>"+str.viewNum+"</span>\n" +
                        "                  </div>\n" +
                        "                  <div class=\"item\">\n" +
                        "                    <i class=\"comment outline icon\"></i> <span>"+str.commentNum+"</span>\n" +
                        "                  </div>\n" +
                        "                </div>\n" +
                        "              </div>\n" +
                        "              <div class=\"right aligned five wide column\">\n" +
                        "                <a href='types.html?id="+str.typeId+"' class=\"ui teal basic label m-padded-tiny m-text-thin\">"+str.typeName+"</a>\n" +
                        "              </div>\n" +
                        "            </div>\n" +
                        "          </div>\n" +
                        "\n" +
                        "          <div class=\"five wide column\">\n" +
                        "            <a href=\"#\" target=\"_blank\">\n" +
                        "              <img src="+'"'+str.imgUrl+'"'+" alt=\"\" class=\"ui rounded image\">\n" +
                        "            </a>\n" +
                        "          </div>\n" +
                        "\n" +
                        "        </div>\n" +
                        "      </div>";
                    $("#article").append(html);
                }
                $("#pageHelper").append(p);
            } else {
                alert(res.msg);
            }
        },
        error: function (result) {
            console.log("网络超时！");
        }
    });
}

function next(pageNum,pageSize,status,typeId) {
    listArticles(pageNum,pageSize,status,typeId);
}

listArticles(1,10,2,typeId);


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