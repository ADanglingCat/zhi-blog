package com.zhi.blog.common.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Ted
 * @date 2022/5/13
 **/
@Data
@AllArgsConstructor
public class CommonResult<T> {
    private Integer code;
    private String msg;
    private T data;

    public static <T> CommonResult<T> success() {
        return success(null);
    }

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(200, "OK", data);
    }

    public static <T> CommonResult<T> failure() {
        return new CommonResult<>(500, "INTERNAL_SERVER_ERROR", null);
    }

    public static <T> CommonResult<T> failure(CommonStatus commonStatus) {
        return new CommonResult<>(commonStatus.getCode(), commonStatus.getMsg(), null);
    }

    public boolean assertSuccess() {
        return code == 200;
    }
}
