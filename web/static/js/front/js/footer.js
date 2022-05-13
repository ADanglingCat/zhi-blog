

function getTotal(){
    $.ajax({
        type: "POST",
        data: JSON.stringify({}),
        url: sessionStorage.getItem("requestUrl") + "/blog-article/front/footer/total",
        contentType:"application/json;charset=utf-8",
        async: true,
        success: function (res) {
            //返回成功
            if (res.code==200){
                $("#todayVisitNum").text(res.data.todayVisitNum);
                $("#articleNum").text(res.data.articleNum);
                $("#commentNum1").text(res.data.commentNum);
                $("#messageNum").text(res.data.messageNum);
                $("#visitNum").text(res.data.visitNum);
            }else{
                console.log(res.msg);
            }
        },
        error: function (result) {
            console.log("网络超时！");
        }
    });
}

$("#footer").append("<!--容器-->\n" +
    "    <div class=\"ui center aligned container\">\n" +
    "        <div class=\"ui inverted divided stackable grid\">\n" +
    "            <div class=\"four wide column\">\n" +
    "                <div style=\"font-size: large;font-weight: bold\"\n" +
    "                     class=\"ui inverted m-text-thin m-text-spaced m-margin-top-max\">联系我\n" +
    "                </div>\n" +
    "                <div class=\"ui inverted link list\">\n" +
    "                    <div href=\"#\" class=\"m-text-thin\">Email：1655378771@qq.com</div>\n" +
    "                    <div href=\"#\" class=\"m-text-thin\">QQ：1655378771</div>\n" +
    "                </div>\n" +
    "            </div>\n" +
    "\n" +
    "            <div class=\"four wide column\">\n" +
    "                <div class=\"ui inverted link list\">\n" +
    "                    <div class=\"item\">\n" +
    "                        <!--微信二维码-->\n" +
    "                        <div style=\"font-size: large;font-weight: bold\" class=\"ui inverted m-text-thin m-text-spaced \">\n" +
    "                            关注公众号\n" +
    "                        </div>\n" +
    "                        <img src=\"../static/images/vxgzh.jpg\" th:src=\"@{/images/vxgzh.jpg}\"\n" +
    "                             class=\"ui m-margin-top rounded image\" alt=\"\" style=\"width: 110px\">\n" +
    "                    </div>\n" +
    "                </div>\n" +
    "            </div>\n" +
    "\n" +
    "            <div class=\"four wide column\">\n" +
    "                <div class=\"ui inverted link list\">\n" +
    "                    <div class=\"item\">\n" +
    "                        <!--微信二维码-->\n" +
    "                        <div style=\"font-size: large;font-weight: bold\" class=\"ui inverted m-text-thin m-text-spaced \">\n" +
    "                            问题交流（QQ群）\n" +
    "                        </div>\n" +
    "                        <img src=\"../static/images/vxgzh.jpg\" th:src=\"@{/images/vxgzh.jpg}\"\n" +
    "                             class=\"ui m-margin-top rounded image\" alt=\"\" style=\"width: 110px\">\n" +
    "                    </div>\n" +
    "                </div>\n" +
    "            </div>\n" +
    "            <!--博客运行时间统计-->\n" +
    "            <div class=\"four wide column\">\n" +
    "                <div style=\"font-size: large;font-weight: bold\"\n" +
    "                     class=\"ui inverted  m-text-thin m-text-spaced m-margin-top\">客栈信息\n" +
    "                </div>\n" +
    "                <!--<p id=\"htmer_time\" class=\"item m-text-thin\"></p>-->\n" +
    "                <div id=\"blog-message\">\n" +
    "                    <div class=\"ui inverted link list\" style=\"align-content: center;margin-top: 10px\">\n" +
    "                        <div class=\"m-text-thin\" style=\"text-align: left;margin-left: 75px;\">\n" +
    "                            文章总数： <h2 class=\"ui orange header m-inline-block m-margin-top-null\"\n" +
    "                                      style=\"font-size:medium;\" id=\"articleNum\">  </h2> 篇\n" +
    "                        </div>\n" +
    "\n" +
    "                        <div class=\"m-text-thin\" style=\"text-align: left;margin-left: 75px\">\n" +
    "                            今日访问总数： <h2 class=\"ui orange header m-inline-block m-margin-top-null\"\n" +
    "                                      style=\"font-size:medium;\" id=\"todayVisitNum\">  </h2> 次\n" +
    "                        </div>\n" +
    "\n" +
    "                        <div class=\"m-text-thin\" style=\"text-align: left;margin-left: 75px\">\n" +
    "                            访问总数： <h2 class=\"ui orange header m-inline-block m-margin-top-null\"\n" +
    "                                      style=\"font-size:medium;\" id=\"visitNum\">  </h2> 次\n" +
    "                        </div>\n" +
    "                        <div class=\"m-text-thin\" style=\"text-align: left;margin-left: 75px\">\n" +
    "                            评论总数： <h2 class=\"ui orange header m-inline-block m-margin-top-null\"\n" +
    "                                      style=\"font-size:medium;\" id=\"commentNum1\">  </h2> 条\n" +
    "                        </div>\n" +
    "                        <div class=\"m-text-thin\" style=\"text-align: left;margin-left: 75px\">\n" +
    "                            留言总数： <h2 class=\"ui orange header m-inline-block m-margin-top-null\"\n" +
    "                                      style=\"font-size:medium;\" id=\"messageNum\">  </h2> 条\n" +
    "                        </div>\n" +
    "                    </div>\n" +
    "                </div>\n" +
    "            </div>\n" +
    "        </div>\n" +
    "        <div class=\"ui inverted section divider\"></div>\n" +
    "        <div style=\"color: #F08047;margin-top: -18px\" class=\"ui inverted m-text-thin m-text-spaced\">我的客栈已营业：<span\n" +
    "                id=\"htmer_time\" class=\"item m-text-thin\"></span> (*๓´╰╯`๓)\n" +
    "        </div>\n" +
    "        <a rel=\"nofollow\" href=\"http://www.beian.miit.gov.cn\" target=\"_blank\">赣ICP备20004408号-1</a>\n" +
    "    </div>\n" +
    "    </div>");

getTotal();