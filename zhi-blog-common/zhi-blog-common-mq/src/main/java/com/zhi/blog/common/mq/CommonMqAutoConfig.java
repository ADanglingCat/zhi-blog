package com.zhi.blog.common.mq;

import com.zhi.blog.common.core.util.JsonUtil;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ted
 * @date 2022/7/6
 **/

@ComponentScan
@Configuration
public class CommonMqAutoConfig {
    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter(JsonUtil.getObjectMapper());
    }
}
