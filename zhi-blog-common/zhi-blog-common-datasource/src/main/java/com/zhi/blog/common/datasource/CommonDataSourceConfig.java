package com.zhi.blog.common.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author Ted
 * @date 2022/6/20
 **/
@Configuration
public class CommonDataSourceConfig {
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource dataSource() {
        return new DruidDataSource();
    }
}
