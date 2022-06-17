package com.zhi.blog.gateway.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhi.blog.common.core.model.CommonResult;
import com.zhi.blog.common.core.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Ted
 * @date 2022/6/11
 **/
@RequiredArgsConstructor
@Component
@Order(-1)
@Slf4j
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        log.error("handle ", ex);
        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(ex);
        }
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return response.writeWith(Mono.fromSupplier(() -> {
            try {
                return response.bufferFactory().wrap(
                        JsonUtil.getObjectMapper().writeValueAsBytes(CommonResult.failure(ex.getMessage())));
            } catch (JsonProcessingException e) {
                return response.bufferFactory().wrap(new byte[0]);
            }
        }));
    }
}
