package com.zhi.blog.common.service.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Ted
 * @date 2022/5/19
 **/
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "spring.swagger")
@Data
public class SwaggerProperty {
    private String title;
    private String description;
    private String version;
}
