// $('#newblog-container').load(/*[[@{/footer/newblog}]]*/"/footer/newblog");
var accessToken = sessionStorage.getItem("access_token");
var nickName = sessionStorage.getItem("nickName");
var avatar = sessionStorage.getItem("avatar");
//当前第几页
var page=1;
var pageNum=1;
var pageSize=10;
//没有token，则直接跳转到首页
if (accessToken == null||sessionStorage.getItem("requestUrl")==null) {
    window.location = "./login.html";
}

$("#nickName").text(nickName);
$("#avatar").attr("src", avatar);

function listTypes(pageNum,pageSize){
    var param={
        "pageNum": pageNum,
        "pageSize": pageSize
    };

    //请求分类接口，进行处理
    $.ajax({
        type: "POST",
        headers: {
            "Authorization":"Bearer "+accessToken
        },
        data: JSON.stringify(param),
        url: sessionStorage.getItem("requestUrl") + "/blog-article/admin/type/list",
        contentType:"application/json;charset=utf-8",
        async: true,
        success: function (res) {
            //返回成功
            if (res.code==200){
                var list=res.data.result;
                $("#total").text(res.data.total);
                $("#pageNum").text(res.data.pages);
                $("#type-body").text('');
                for (var i = 0; i <list.length ; i++) {
                    var str=list[i];
                    var html="<tr align=\"center\">\n" +
                        "                <td>"+str.id+"</td>\n" +
                        "                <td>"+str.name+"</td>\n" +
                        "                <td>\n" +
                        "                    <a href=\"#\" class=\"ui mini teal basic button\">编辑</a>\n" +
                        "                    <a href=\"#\" onclick=\"return confirm('确定要删除该分类吗？三思啊! 删了可就没了！')\"\n" +
                        "                       class=\"ui mini red basic button\">删除</a>\n" +
                        "                </td>\n" +
                        "            </tr>";
                    $("#type-body").append(html);
                }
            }else{
                alert(res.msg);
            }
        },
        error: function (result) {
            alert("网络超时！")
        }
    });
}

//首次加载
listTypes(pageNum,pageSize);

//下一页
$("#next").click(function () {
    page++;
    listTypes(page,pageSize);
});

//上一页
$("#pre").click(function () {
    page--;
    if (page<1)
        page=1
    listTypes(page,pageSize);
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

$('.menu.toggle').click(function () {
    $('.m-item').toggleClass('m-mobile-hide');
});

$('.ui.dropdown').dropdown({
    on: 'hover'
});

//消息提示关闭初始化
$('.message .close')
    .on('click', function () {
        $(this)
            .closest('.message')
            .transition('fade');
    });
