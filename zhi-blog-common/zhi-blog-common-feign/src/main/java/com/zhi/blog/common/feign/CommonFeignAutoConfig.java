package com.zhi.blog.common.feign;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Ted
 * @date 2022/5/20
 **/
@Configuration
@ComponentScan
public class CommonFeignAutoConfig {

    @Profile({"dev", "test"})
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }
}
