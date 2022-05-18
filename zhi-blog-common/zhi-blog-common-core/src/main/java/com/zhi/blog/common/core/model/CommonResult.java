package com.zhi.blog.common.core.model;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * @author Ted
 * @date 2022/5/13
 **/
@Setter
@AllArgsConstructor
public class CommonResult<T> {
    private Integer code;
    private String msg;
    private T data;

    public static <T> CommonResult<T> success() {
        return success(null);
    }

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(HttpStatus.OK.value(), HttpStatus.OK.name(), data);
    }

    public static <T> CommonResult<T> failure() {
        return new CommonResult<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name(), null);
    }

    public static <T> CommonResult<T> failure(CommonStatus commonStatus) {
        return new CommonResult<>(commonStatus.getCode(), commonStatus.getMsg(), null);
    }

    public boolean assertSuccess() {
        return code == HttpStatus.OK.value();
    }
}
