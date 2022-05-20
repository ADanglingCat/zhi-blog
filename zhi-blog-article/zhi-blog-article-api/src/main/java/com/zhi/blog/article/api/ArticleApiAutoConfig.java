package com.zhi.blog.article.api;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ted
 * @date 2022/5/20
 **/
@EnableFeignClients
@Configuration
@ComponentScan
public class ArticleApiAutoConfig {
}
