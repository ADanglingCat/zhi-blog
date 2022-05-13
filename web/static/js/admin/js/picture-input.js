
var accessToken = sessionStorage.getItem("access_token");
var nickName = sessionStorage.getItem("nickName");
var avatar = sessionStorage.getItem("avatar");
//没有token，则直接跳转到首页
if (accessToken == null||sessionStorage.getItem("requestUrl")==null) {
    window.location = "./login.html";
}

//上传文件
function upload() {
    var formData = new FormData();
    formData.append("file",$("#file")[0].files[0]);
    formData.append("name",$("#name").val());
    formData.append("location",$("#location").val());
    formData.append("description",$("#description").val());
    $.ajax({
        url: sessionStorage.getItem("requestUrl") + "/blog-picture/admin/picture/add",
        headers: {
            "Authorization":"Bearer "+accessToken
        },
        type:'post',
        data: formData,
        contentType: false,
        processData: false,
        success:function(res){
            console.log(res.data);
            //返回成功
            if (res.code==200){
                alert("上传成功！")
            }else{
                alert(res.msg);
            }
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

$('.ui.dropdown').dropdown({
    on : 'hover'
});

$('.ui.form').form({
    fields : {
        title : {
            identifier: 'pictureaddress',
            rules: [{
                type : 'empty',
                prompt: '请输入照片地址'
            }]
        }
    }
});