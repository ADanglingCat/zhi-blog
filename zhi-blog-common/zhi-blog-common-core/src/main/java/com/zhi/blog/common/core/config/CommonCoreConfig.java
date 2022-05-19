package com.zhi.blog.common.core.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ted
 * @date 2022/5/13
 **/
@EnableDiscoveryClient
@Configuration
@ComponentScan({"com.zhi.blog.common.core"})
public class CommonCoreConfig {

}
