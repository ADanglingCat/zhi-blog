package com.zhi.blog.common.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Ted
 * @date 2022/5/13
 **/
@Data
@AllArgsConstructor
public class CommonResult {
    private Integer code;
    private String msg;
    private Object data;

    public static CommonResult success() {
        return success(null);
    }

    public static CommonResult success(Object data) {
        return new CommonResult(CommonStatus.CODE_OK, CommonStatus.MSG_OK, data);
    }

    public static CommonResult failure() {
        return failure(CommonStatus.MSG_SERVER_ERROR);
    }

    public static CommonResult failure(String msg) {
        return new CommonResult(CommonStatus.CODE_SERVER_ERROR, msg, null);
    }

    public static CommonResult result(CommonStatus commonStatus) {
        return result(commonStatus, null);
    }

    public static CommonResult result(CommonStatus commonStatus, Object data) {
        return new CommonResult(commonStatus.getCode(), commonStatus.getMsg(), data);
    }

    public boolean assertSuccess() {
        return CommonStatus.CODE_OK.equals(code);
    }
}
