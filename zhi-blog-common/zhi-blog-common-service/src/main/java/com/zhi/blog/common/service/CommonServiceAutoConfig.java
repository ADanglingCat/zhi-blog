package com.zhi.blog.common.service;

import com.zhi.blog.common.service.model.SwaggerProperty;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Ted
 * @date 2022/5/13
 **/
@Configuration
@RequiredArgsConstructor
@ComponentScan
public class CommonServiceAutoConfig {
    private final SwaggerProperty swaggerProperty;

    @Profile({"dev", "test"})
    @Bean
    public OpenAPI commonOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title(swaggerProperty.getTitle())
                        .description(swaggerProperty.getDescription())
                        .version(swaggerProperty.getVersion()))
                .schemaRequirement("Authorization",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .name("Authorization")
                                .in(SecurityScheme.In.HEADER))
                .addSecurityItem(new SecurityRequirement().addList("Authorization"));
    }
}
