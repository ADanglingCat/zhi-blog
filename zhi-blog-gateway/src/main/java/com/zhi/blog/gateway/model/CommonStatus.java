package com.zhi.blog.gateway.model;

import com.zhi.blog.common.core.model.ICommonStatus;
import lombok.AllArgsConstructor;

/**
 * @author Ted
 * @date 2022/6/24
 **/
@AllArgsConstructor
public enum CommonStatus implements ICommonStatus {
    /**
     * 9014
     */
    UNAUTHORIZED(CODE_UNAUTHORIZED, MSG_UNAUTHORIZED),
    FORBIDDEN(CODE_FORBIDDEN, MSG_FORBIDDEN);

    private final Integer code;
    private final String msg;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
