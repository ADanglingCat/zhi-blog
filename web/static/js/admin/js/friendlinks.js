
var accessToken = sessionStorage.getItem("access_token");
var nickName = sessionStorage.getItem("nickName");
var avatar = sessionStorage.getItem("avatar");
//没有token，则直接跳转到首页
if (accessToken == null||sessionStorage.getItem("requestUrl")==null) {
    window.location = "./login.html";
}

function next(pageNum,pageSize) {
    listLinks(pageNum,pageSize);
}

function del(id) {
    $.ajax({
        type: "POST",
        headers: {
            "Authorization":"Bearer "+accessToken
        },
        data: JSON.stringify({
            "id": id
        }),
        url: sessionStorage.getItem("requestUrl") + "/blog-friendlinks/admin/friend/links/del",
        contentType:"application/json;charset=utf-8",
        async: true,
        success: function (res) {
            //返回成功
            if (res.code==200){
                alert("删除成功！");
                listLinks(1,10);
            }else{
                alert(res.msg);
            }
        },
        error: function (result) {
            alert("网络超时！")
        }
    });
}

//获取相册图片
function listLinks(pageNum,pageSize) {
    $.ajax({
        type: "POST",
        headers: {
            "Authorization": "Bearer " + accessToken
        },
        data: JSON.stringify({
            "pageNum": pageNum,
            "pageSize": pageSize
        }),
        url: sessionStorage.getItem("requestUrl") + "/blog-friendlinks/admin/friend/links/list",
        contentType: "application/json;charset=utf-8",
        async: true,
        success: function (res) {
            //返回成功
            if (res.code == 200) {
                var list = res.data.result;
                var total = res.data.pages;
                $("#links").text("");
                $("#next").text("");
                for (var i = 0; i < list.length; i++) {
                    var str = list[i];
                    var html = "<tr align=\"center\" >\n" +
                        "            <td>" + str.id + "</td>\n" +
                        "            <td>" + str.blogName + "</td>\n" +
                        "            <td>" + str.blogUrl + "</td>\n" +
                        "            <td>" + str.imageUrl + "</td>\n" +
                        "            <td>" + str.createTime + "</td>\n" +
                        "            <td>\n" +
                        "              <a href=\"#\" class=\"ui mini teal basic button\">编辑</a>\n" +
                        "              <a href=\"#\" onclick='del("+str.id+")'>删除</a>\n" +
                        "            </td>\n" +
                        "          </tr>";
                    $("#links").append(html);
                }

                //分页
                var next = "<tr>\n" +
                    "            <th colspan=\"6\" >\n" +
                    "              <div class=\"ui inverted divided stackable grid\">\n" +
                    "                <div class=\"three wide column\" align=\"center\">\n" +
                    "                  <a class=\"item\" onclick='next(" + (pageNum - 1 <= 0 ? 1 : pageNum - 1) + "," + (pageSize) + ")'>上一页</a>\n" +
                    "                </div>\n" +
                    "\n" +
                    "                <div class=\"ten wide column\" align=\"center\">\n" +
                    "                  <p>第 <span>" + pageNum + "</span> 页，共 <span>" + res.data.pages + "</span> 页\n" +
                    "                </div>\n" +
                    "\n" +
                    "                <div class=\"three wide column\" align=\"center\">\n" +
                    "                  <a class=\"item\" onclick='next(" + (pageNum + 1 > total ? total : pageNum + 1) + "," + (pageSize) + ")'>下一页</a>\n" +
                    "                </div>\n" +
                    "              </div>\n" +
                    "\n" +
                    "              <div align=\"center\">\n" +
                    "                <a href=\"#\">\n" +
                    "                  <button type=\"button\" class=\"ui teal button m-mobile-wide m-margin-top\"><i class=\"pencil icon\"></i>新增</button>\n" +
                    "                </a>\n" +
                    "              </div>\n" +
                    "\n" +
                    "            </th>\n" +
                    "          </tr>";
                $("#next").append(next);
            } else {
                alert(res.msg);
            }
        },
        error: function (result) {
            alert("网络超时！")
        }
    });
}

listLinks(1,10);

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

$('.ui.dropdown').dropdown({
    on : 'hover'
});

//消息提示关闭初始化
$('.message .close')
    .on('click', function () {
        $(this)
            .closest('.message')
            .transition('fade');
    });