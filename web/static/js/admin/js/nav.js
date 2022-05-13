
//退出登录
function logout() {
    var accessToken = sessionStorage.getItem("access_token");
    //没有token，则直接跳转到首页
    if (accessToken == null||sessionStorage.getItem("requestUrl")==null) {
        window.location = "./login.html";
    }

    $.ajax({
        type: "POST",
        headers: {
            "Authorization":"Bearer "+accessToken
        },
        data: JSON.stringify({}),
        url: sessionStorage.getItem("requestUrl") + "/blog-auth-server/oauth/logout",
        contentType:"application/json;charset=utf-8",
        async: false,
        success: function (res) {
            //返回成功
            if (res.code==200){
                //删除令牌
                sessionStorage.removeItem("access_token");
                sessionStorage.removeItem("refresh_token");
                sessionStorage.removeItem("nickName");
                sessionStorage.removeItem("avatar");
                window.location = "./login.html";
            }else{
                alert(res.msg);
            }
        },
        error: function (result) {
            console.log("网络超时！");
        }
    });
}