package com.zhi.blog.gateway.config;

import com.zhi.blog.gateway.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author Ted
 * @date 2022/6/24
 **/
@RequiredArgsConstructor
@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfig {
    @Value("${oauth.key}")
    private String publicKey;
    private final AuthorizationManager authorizationManager;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .oauth2ResourceServer().jwt()
                .jwtDecoder(jwtDecoder())
                .and()
                //jwt异常处理
                .accessDeniedHandler((exchange, denied) -> CommonUtil.mutateResponse(exchange.getResponse(), denied))
                .authenticationEntryPoint((exchange, ex) -> CommonUtil.mutateResponse(exchange.getResponse(), ex))
                .and()
                .authorizeExchange()
                //静态资源和授权地址放行
                .pathMatchers("/favicon.ico", "/webjars/**", "/*/*/api-docs/**", "/oauth2/**").permitAll()
                //其余请求进行鉴权
                .anyExchange().access(authorizationManager)
                .and()
                //其他异常处理
                .exceptionHandling()
                .accessDeniedHandler((exchange, denied) -> CommonUtil.mutateResponse(exchange.getResponse(), denied))
                .authenticationEntryPoint((exchange, ex) -> CommonUtil.mutateResponse(exchange.getResponse(), ex))
                .and()
                .csrf().disable()
                //向下游传递header
                .addFilterAfter((exchange, chain) -> ReactiveSecurityContextHolder.getContext()
                        .map(SecurityContext::getAuthentication)
                        .cast(JwtAuthenticationToken.class)
                        .flatMap(temp -> {
                            var request = exchange.getRequest()
                                    .mutate()
                                    .header("tokenInfo", "tokenValue")
                                    .build();
                            return chain.filter(exchange.mutate().request(request).build());
                        }), SecurityWebFiltersOrder.AUTHORIZATION)
                .build();
    }

    public ReactiveJwtDecoder jwtDecoder() throws Exception {
        var rsaPublicKey = (RSAPublicKey) KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey)));
        return NimbusReactiveJwtDecoder.withPublicKey(rsaPublicKey).build();
    }


}

