package com.zhi.blog.common.core.model;

/**
 * @author Ted
 * @date 2022/5/13
 **/
public interface CommonStatus {

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
