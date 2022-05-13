// $('#newblog-container').load(/*[[@{/footer/newblog}]]*/"/footer/newblog");
var requestUrl = sessionStorage.getItem("requestUrl");
if (requestUrl==null){
    window.location="./index.html";
}
$(function() {
    $('.rthumbnail').responsivegallery();
});

$('.menu.toggle').click(function () {
    $('.m-item').toggleClass('m-mobile-hide');
});

function listPicture(pageNum,pageSize) {
    $.ajax({
        type: "POST",
        data: JSON.stringify({
            "pageNum": pageNum,
            "pageSize": pageSize
        }),
        url: sessionStorage.getItem("requestUrl") + "/blog-picture/front/picture/list",
        contentType:"application/json;charset=utf-8",
        async: true,
        success: function (res) {
            //返回成功
            if (res.code==200){
                var list=res.data.result;
                $("#picture-main").text("");
                for (var i = 0; i < list.length; i++) {
                    var str=list[i];
                    var p="<article class=\"thumb\">\n" +
                        "        <div class=\"ma5-gallery\">\n" +
                        "            <div class=\"rthumbnail\">\n" +
                        "                <a href='"+str.url+"'>\n" +
                        "                    <img class=\"picture-zmki_px\" src='"+str.url+"'>\n" +
                        "                </a>\n" +
                        "                <div class=\"m-picture-text\">"+str.name+"</div>\n" +
                        "                <div  class=\"rcaption\">\n" +
                        "                    <div style=\"font-size: large;\">"+str.name+"</div>\n" +
                        "                    <div style=\"font-size: small\" class=\"m-margin-top\">"+str.createTime+"&nbsp;"+str.location+"</div>\n" +
                        "                    <p style=\"font-size: small\">"+str.description+"</p>\n" +
                        "                </div>\n" +
                        "            </div>\n" +
                        "\n" +
                        "        </div>\n" +
                        "    </article>";
                    $("#picture-main").append(p);
                }
                //加载响应式插件
                $('.rthumbnail').responsivegallery();
            }else{
                alert(res.msg);
            }
        },
        error: function (result) {
            console.log("网络超时！");
        }
    });
}


listPicture(1,100);

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
