package com.zhi.blog.article.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author Ted
 * @date 2022/7/7
 **/
@Configuration
public class MqConfig {
    @Bean
    public Queue article() {
        // 该队列消息 3s 过期 arguments.put("x-message-ttl",3000);
        // DLX 队列 arguments.put("x-dead-letter-exchange","dlx.exchange");
        return new Queue("article", true);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("directExchange", true, false);
    }

    @Bean
    public Binding directBinding() {
        return BindingBuilder.bind(article()).to(directExchange()).with("articleKey");
    }
}
