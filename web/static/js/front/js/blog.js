
var requestUrl = sessionStorage.getItem("requestUrl");
if (requestUrl==null){
    window.location="./index.html";
}

//接收编辑传递过来的参数
var articleId= window.location.href.split('?')[1].split('=')[1].split('#')[0];
$.ajax({
    type: "POST",
    data: JSON.stringify({
        "id": articleId
    }),
    url: sessionStorage.getItem("requestUrl") + "/blog-article/front/article/getById",
    contentType:"application/json;charset=utf-8",
    async: true,
    success: function (res) {
        //返回成功
        if (res.code==200){
            //TODO 暂定
            $("#content").html(res.data.content);
            //加载目录
            tocbot.init({
                // Where to render the table of contents.
                tocSelector: '.js-toc',
                // Where to grab the headings to build the table of contents.
                contentSelector: '.js-toc-content',
                // Which headings to grab inside of the contentSelector element.
                headingSelector: 'h1, h2, h3',
            });

            //设置其他信息
            $("#authorName").text(res.data.authorName);
            var source;
            if(res.data.source==1)
                source="原创";
            if(res.data.source==2)
                source="转载";
            if(res.data.source==3)
                source="翻译";
            $("#source").text(source);
            $("#commentNum").text(res.data.commentNum);
            $("#authorName2").text(res.data.authorName);
            $("#createTime").text(res.data.createTime);
            $("#publishTime").text(res.data.createTime);
            $("#typeName").text(res.data.typeName);
            $("#viewNum").text(res.data.viewNum);
            $("#isAppreciate").attr("hidden",res.data.isAppreciate==1?false:true);
            $("#explain1").attr("hidden",res.data.isExplain==1?false:true);
            $("#explain2").attr("hidden",res.data.isExplain==1?false:true);
            $("#title").text(res.data.title);
        }else{
            alert(res.msg);
        }
    },
    error: function (result) {
        console.log("网络超时！");
    }
});

//获取评论
function listComments(articleId){
    $.ajax({
        type: "POST",
        data: JSON.stringify({
            "articleId": articleId
        }),
        url: sessionStorage.getItem("requestUrl") + "/blog-comments/comment/list",
        contentType:"application/json;charset=utf-8",
        async: true,
        success: function (res) {
            //返回成功
            if (res.code==200){
                var list=res.data;
                $("#comments").text("");
                $("#comments").append("<h3 class=\"ui dividing header\">评论</h3>");
                if (list==null)
                    return;
                for (var i = 0; i < list.length; i++) {
                    var str=list[i];
                    var p="<div class=\"comment\" id='comment_"+str.commentId+"'>\n" +
                        "              <a class=\"avatar\">\n" +
                        "                <img src=\"../static/images/avatar.png\">\n" +
                        "              </a>\n" +
                        "              <div class=\"content\">\n" +
                        "                <a class=\"author\" >\n" +
                        "                  <span id='nickName'>"+str.nickName+"</span>\n" +
                        "                  <div class=\"ui mini basic teal left pointing label m-padded-mini\">楼主</div>\n" +
                        "                </a>\n" +
                        "                <div class=\"metadata\">\n" +
                        "                  <span class=\"date\">"+str.createTime+"</span>\n" +
                        "                </div>\n" +
                        "                <div class=\"text\">\n" +str.content+
                        "                </div>\n" +
                        "                <div class=\"actions\">\n" +
                        "                  <a class=\"reply\" data-commentid=\"1\" data-commentnickname=\"Matt\" onclick='replyBtn("+'"'+str.commentId+'"'+")'>回复</a>\n" +
                        // "                  <a class=\"delete\" href=\"#\" onclick=\"return confirm('确定要删除该评论吗？三思啊! 删了可就没了！')\" th:if=\"${session.user}\">删除</a>\n" +
                        "                </div>\n"
                        "              </div>";
                    $("#comments").append(p);
                    var childList=str.child;
                    if (childList!=null){
                        $("#comment_"+str.commentId).append("<div class=\"comments\" id='comments_"+str.commentId+"'>\n");
                        for (var j = 0; j < childList.length; j++) {
                            var childStr=childList[j];
                            var child="<div class=\"comment\">\n" +
                                "                  <a class=\"avatar\">\n" +
                                "                    <img src=\"../static/images/avatar.png\">\n" +
                                "                  </a>\n" +
                                "                  <div class=\"content\">\n" +
                                "                    <a class=\"author\" >\n" +
                                "                      <span>"+childStr.nickName+"</span>\n" +
                                // "                      <div class=\"ui mini basic teal left pointing label m-padded-mini\">栈主</div>\n" +
                                "                      &nbsp;<span class=\"m-teal\">@ "+childStr.replyName+"</span>\n" +
                                "                    </a>\n" +
                                "                    <div class=\"metadata\">\n" +
                                "                      <span class=\"date\">"+childStr.createTime+"</span>\n" +
                                "                    </div>\n" +
                                "                    <div class=\"text\">\n" +childStr.content
                                "                    </div>\n" +
                                "                    <div class=\"actions\">\n" +
                                "                      <a class=\"reply\" data-commentid=\"1\" data-commentnickname=\"Matt\" onclick=\"reply(this)\">回复</a>\n" +
                                "                    </div>\n" +
                                "                  </div>\n" +
                                "                </div>"+"</div>";
                            $("#comments_"+str.commentId).append(child);
                        }
                    }

                }
            }else{
                alert(res.msg);
            }
        },
        error: function (result) {
            // console.log("网络超时！");
        }
    });
}

listComments(articleId);
var pcommentId;
function replyBtn(pid) {
    $("#replyH3").attr("hidden",false);
    $("#replyName").text($("#nickName").text());
    pcommentId=pid;
}

function reply() {
    $.ajax({
        type: "POST",
        data: JSON.stringify({
            "articleId": articleId,
            "content": $("#comment").val(),
            "email": $("#email").val(),
            "nickName":$("#nickname").val(),
            "pid": pcommentId
        }),
        url: sessionStorage.getItem("requestUrl") + "/blog-comments/comment/add",
        contentType:"application/json;charset=utf-8",
        async: true,
        success: function (res) {
            //返回成功
            if (res.code==200){
                alert("发布成功！")
                //刷新评论
                listComments(articleId);
            }else{
                alert(res.msg);
            }
        },
        error: function (result) {
            alert("网络超时......")
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

$('#payButton').popup({
    popup : $('.payQR.popup'),
    on : 'click',
    position: 'bottom center'
});

$('.toc.button').popup({
    popup : $('.toc-container.popup'),
    on : 'click',
    position: 'left center'
});

$('.wechat').popup({
    popup : $('.wechat-qr'),
    position: 'left center'
});

var serurl = /*[[#{blog.serurl}]]*/"127.0.0.1:8080";
var url = /*[[@{/blog/{id}(id=${blog.id})}]]*/"";
var qrcode = new QRCode("qrcode", {
    text: serurl+url,
    width: 110,
    height: 110,
    colorDark : "#000000",
    colorLight : "#ffffff",
    correctLevel : QRCode.CorrectLevel.H
});

$('#toTop-button').click(function () {
    $(window).scrollTo(0,500);
});


var waypoint = new Waypoint({
    element: document.getElementById('waypoint'),
    handler: function(direction) {
        if (direction == 'down') {
            $('#toolbar').show(100);
        } else {
            $('#toolbar').hide(500);
        }
        console.log('Scrolled to waypoint!  ' + direction);
    }
})


//评论表单验证
$('.ui.form').form({
    fields: {
        title: {
            identifier: 'content',
            rules: [{
                type: 'empty',
                prompt: '请输入评论内容'
            }
            ]
        },
        content: {
            identifier: 'nickname',
            rules: [{
                type: 'empty',
                prompt: '请输入你的大名'
            }]
        },
        type: {
            identifier: 'email',
            rules: [{
                type: 'email',
                prompt: '请填写正确的邮箱地址'
            }]
        }
    }
});

// $(function () {
//     $("#comment-container").load(/*[[@{/comments/{id}(id=${blog.id})}]]*/"comments/6");
// });


$('#commentpost-btn').click(function () {
    var boo = $('.ui.form').form('validate form');
    if (boo) {
        console.log('校验成功');
        postData();
    } else {
        console.log('校验失败');
    }
});

// function postData() {
//     $("#comment-container").load(/*[[@{/comments}]]*/"",{
//         "parentComment.id" : $("[name='parentComment.id']").val(),
//         "blogId" : $("[name='blogId']").val(),
//         "nickname": $("[name='nickname']").val(),
//         "email"   : $("[name='email']").val(),
//         "content" : $("[name='content']").val()
//     },function (responseTxt, statusTxt, xhr) {
//         $(window).scrollTo($('#goto'),500);
//         clearContent();
//     });
// }

function clearContent() {
    $("[name='nickname']").val('');
    $("[name='email']").val('');
    $("[name='content']").val('');
    $("[name='parentComment.id']").val(-1);
    $("[name='content']").attr("placeholder", "请输入评论信息...");
}

// function reply(obj) {
//     var commentId = $(obj).data('commentid');
//     var commentNickname = $(obj).data('commentnickname');
//     $("[name='content']").attr("placeholder", "@"+commentNickname).focus();
//     $("[name='parentComment.id']").val(commentId);
//     $(window).scrollTo($('#comment-form'),500);
// }
