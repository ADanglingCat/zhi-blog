package com.zhi.blog.common.core.model;

/**
 * @author Ted
 * @date 2022/5/13
 **/
public interface CommonStatus {
    String MSG_OK = "成功";
    String MSG_CLIENT_ERROR = "请求异常";
    String MSG_SERVER_ERROR = "服务器异常";

    Integer CODE_OK = 200;
    Integer CODE_CLIENT_ERROR = 400;
    Integer CODE_SERVER_ERROR = 500;

    /**
     * 各模块自定义状态码
     * @return 状态码
     */
    Integer getCode();

    /**
     * 各模块自定义返回信息
     * @return 信息
     */
    String getMsg();
}
