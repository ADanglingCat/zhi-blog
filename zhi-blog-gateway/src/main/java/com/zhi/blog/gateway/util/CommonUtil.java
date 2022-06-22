package com.zhi.blog.gateway.util;

import com.zhi.blog.common.core.model.CommonResult;
import com.zhi.blog.common.core.util.JsonUtil;
import com.zhi.blog.gateway.model.CommonStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author Ted
 * @date 2022/6/24
 **/
@Slf4j
public class CommonUtil {
    public static Mono<Void> mutateResponse(ServerHttpResponse response, Throwable exception) {
        log.error("mutateResponse ", exception);
        CommonResult commonResult;
        if (exception instanceof AuthenticationException) {
            commonResult = CommonResult.result(CommonStatus.UNAUTHORIZED);
        } else if(exception instanceof AccessDeniedException) {
            commonResult = CommonResult.result(CommonStatus.FORBIDDEN);
        } else {
            commonResult = CommonResult.failure();
        }
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        var buffer = response.bufferFactory().wrap(JsonUtil.toJsonString(commonResult).getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer)).doOnError(error -> DataBufferUtils.release(buffer));
    }
}
