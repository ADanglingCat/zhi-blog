// $('#newblog-container').load(/*[[@{/footer/newblog}]]*/"/footer/newblog");
var accessToken = sessionStorage.getItem("access_token");
var nickName = sessionStorage.getItem("nickName");
var avatar = sessionStorage.getItem("avatar");

//发布文章
function publish(params){
    //请求分类接口，进行处理
    $.ajax({
        type: "POST",
        headers: {
            "Authorization":"Bearer "+accessToken
        },
        data: JSON.stringify(params),
        url: sessionStorage.getItem("requestUrl") + "/blog-article/admin/article/add",
        contentType:"application/json;charset=utf-8",
        async: false,
        success: function (res) {
            //返回成功
            if (res.code==200){
                alert("文章发布成功！")
                window.location.href="./blogs.html";
            }else{
                alert(res.msg);
            }
        },
        error: function (result) {
            alert("网络超时！")
        }
    });
}

//获取分类列表
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
                for (var i = 0; i <list.length ; i++) {
                    var str=list[i];
                    var html="<div class=\"item\" data-value="+str.id+" onclick=typeFuc("+str.id+")"+">"+str.name+"</div>";
                    $("#menu").append(html);
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
listTypes(1,100);



// $('#newblog-container').load(/*[[@{/footer/newblog}]]*/"/footer/newblog");

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


//初始化Markdown编辑器
var contentEditor;
$(function() {
    contentEditor = editormd("md-content", {
        width   : "100%",
        height  : 640,
        syncScrolling : "single",
        path    : "../../static/lib/editormd/lib/",
        saveHTMLToTextarea : true
//         path    : "/lib/editormd/lib/"
    });
});
$('.menu.toggle').click(function () {
    $('.m-item').toggleClass('m-mobile-hide');
});

$('.ui.dropdown').dropdown({
    on : 'hover'
});

var source=1;
function menuFuc(param){
    source=param;
}

var typeId;
function typeFuc(param){
    typeId=param;
}

//没有token，则直接跳转到首页
if (accessToken == null||sessionStorage.getItem("requestUrl")==null) {
    window.location = "./login.html";
}

function getArticleById() {
    //接收编辑传递过来的参数
    var articleId= window.location.href.split("?")[1].split("=")[1];
    //请求分类接口，进行处理
    $.ajax({
        type: "POST",
        headers: {
            "Authorization":"Bearer "+accessToken
        },
        data: JSON.stringify({
            "id": articleId
        }),
        url: sessionStorage.getItem("requestUrl") + "/blog-article/admin/article/getById",
        contentType:"application/json;charset=utf-8",
        async: true,
        success: function (res) {
            //返回成功
            if (res.code==200){
                //TODO 暂定
                // contentEditor.setHTML(res.data.content);
            }else{
                alert(res.msg);
            }
        },
        error: function (result) {
            alert("网络超时！")
        }
    });
}

// getArticleById();

//保存按钮
$('#save-btn').click(function () {
    var params={
        "content": contentEditor.getHTML(),
        "imgUrl": $("#img-url").val(),
        "isAppreciate": $("#appreciation").prop("checked")==true?1:0,
        "isComment": $("#commentabled").prop("checked")==true?1:0,
        "isExplain": $("#shareStatement").prop("checked")==true?1:0,
        "isRecommend": $("#recommend").prop("checked")==true?1:0,
        "source": source,
        "status": 1,
        "describe":$("#description").val(),
        "title": $("#title").val(),
        "typeId": typeId
    };
    $('[name="published"]').val(false);
    publish(params);
});


$('#publish-btn').click(function () {
    var params={
        "content": contentEditor.getHTML(),
        "imgUrl": $("#img-url").val(),
        "isAppreciate": $("#appreciation").prop("checked")==true?1:0,
        "isComment": $("#commentabled").prop("checked")==true?1:0,
        "isExplain": $("#shareStatement").prop("checked")==true?1:0,
        "isRecommend": $("#recommend").prop("checked")==true?1:0,
        "source": source,
        "status": 2,
        "describe":$("#description").val(),
        "title": $("#title").val(),
        "typeId": typeId
    };
    $('[name="published"]').val(false);
    publish(params);
});



$('.ui.form').form({
    fields : {
        title : {
            identifier: 'title',
            rules: [{
                type : 'empty',
                prompt: '标题：请输入博客标题'
            }]
        },
        content : {
            identifier: 'content',
            rules: [{
                type : 'empty',
                prompt: '标题：请输入博客内容'
            }]
        },
        typeId : {
            identifier: 'type.id',
            rules: [{
                type : 'empty',
                prompt: '标题：请输入博客分类'
            }]
        },
        firstPicture : {
            identifier: 'firstPicture',
            rules: [{
                type : 'empty',
                prompt: '标题：请输入博客首图'
            }]
        },
        description : {
            identifier: 'description',
            rules: [{
                type : 'empty',
                prompt: '标题：请输入博客描述'
            }]
        }
    }
});
