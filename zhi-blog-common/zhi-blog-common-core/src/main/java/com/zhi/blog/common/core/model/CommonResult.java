package com.zhi.blog.common.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ted
 * @date 2022/5/13
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult {
    private Integer code;
    private String msg;
    private Object data;

    public static CommonResult success() {
        return success(null);
    }

    public static CommonResult success(Object data) {
        return new CommonResult(ICommonStatus.CODE_OK, ICommonStatus.MSG_OK, data);
    }

    public static CommonResult failure() {
        return failure(ICommonStatus.MSG_SERVER_ERROR);
    }

    public static CommonResult failure(String msg) {
        return new CommonResult(ICommonStatus.CODE_SERVER_ERROR, msg, null);
    }

    public static CommonResult result(ICommonStatus commonStatus) {
        return result(commonStatus, null);
    }

    public static CommonResult result(ICommonStatus commonStatus, Object data) {
        return new CommonResult(commonStatus.getCode(), commonStatus.getMsg(), data);
    }

    public boolean assertSuccess() {
        return ICommonStatus.CODE_OK.equals(code);
    }
}
