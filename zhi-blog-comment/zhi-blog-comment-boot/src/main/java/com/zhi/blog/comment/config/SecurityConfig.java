package com.zhi.blog.comment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author Ted
 * @date 2022/6/29
 **/
@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .oauth2Login(Customizer.withDefaults())
                .oauth2Client()
                .and()
                .logout()
                .and()
                .build();
    }
}
