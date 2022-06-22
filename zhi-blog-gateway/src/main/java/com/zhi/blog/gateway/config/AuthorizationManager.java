package com.zhi.blog.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Ted
 * @date 2022/6/24
 **/
@Slf4j
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    private static final Map<String, String> AUTH_MAP = new HashMap<>();
    @PostConstruct
    public void initAuthMap() {
        //dodo 2022/6/28 initAuthMap redis
        AUTH_MAP.put("/zhi-blog-article/article", "SCOPE_default");
    }

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext context) {
        var exchange = context.getExchange();
        var request = exchange.getRequest();
        var path = request.getURI().getPath();

        var pathMatcher = new AntPathMatcher();
        String authorities = AUTH_MAP.get(request.getURI().getPath());

        if (request.getMethod() == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }

        return authentication
                .filter(Authentication::isAuthenticated)
                .filter(temp -> temp instanceof JwtAuthenticationToken)
                .cast(JwtAuthenticationToken.class)
                .doOnNext(temp -> {
                    log.info("check getHeaders {}", temp.getToken().getHeaders());
                    log.info("check getTokenAttributes {}", temp.getTokenAttributes());
                })
                .flatMapIterable(JwtAuthenticationToken::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(temp -> Objects.equals(temp,authorities))
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));

    }

    @Override
    public Mono<Void> verify(Mono<Authentication> authentication, AuthorizationContext object) {
        return ReactiveAuthorizationManager.super.verify(authentication, object);
    }
}
