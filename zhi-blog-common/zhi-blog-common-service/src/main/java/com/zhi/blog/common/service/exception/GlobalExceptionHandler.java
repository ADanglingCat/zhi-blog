package com.zhi.blog.common.service.exception;

import com.zhi.blog.common.core.model.CommonResult;
import com.zhi.blog.common.service.model.BlogException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Ted
 * @date 2022/5/13
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public CommonResult exceptionHandler(Exception e) {
        log.error("exceptionHandler ", e);
        return CommonResult.failure();
    }

    @ExceptionHandler(BlogException.class)
    public CommonResult blogExceptionHandler(BlogException e) {
        log.error("blogExceptionHandler ", e);
        return CommonResult.failure();
    }
}
