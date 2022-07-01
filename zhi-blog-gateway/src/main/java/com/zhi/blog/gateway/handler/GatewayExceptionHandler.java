package com.zhi.blog.gateway.handler;

import com.zhi.blog.gateway.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 全局异常处理
 * @author Ted
 * @date 2022/6/11
 **/
@RequiredArgsConstructor
@Component
@Order(-1)
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        return CommonUtil.mutateResponse(exchange.getResponse(), ex);
    }
}
