package com.zhi.blog.common.service.exception;

import com.zhi.blog.common.core.model.CommonResult;
import com.zhi.blog.common.core.util.CoreUtil;
import com.zhi.blog.common.service.model.BlogException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Ted
 * @date 2022/5/13
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public CommonResult exceptionHandler(Exception e) {
        CoreUtil.error(e);
        return CommonResult.failure();
    }

    @ExceptionHandler(BlogException.class)
    public CommonResult blogExceptionHandler(BlogException e) {
        CoreUtil.error(e);
        return CommonResult.failure();
    }
}
