package com.zhi.blog.article.config;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author ted
 * @date 2022/5/12
 */
@OpenAPIDefinition(
        info = @Info(
                title = "zhi-bolg-article",
                version = "1.0.0"
        ),
        security = @SecurityRequirement(name = "Authorization"),
        externalDocs = @ExternalDocumentation(
                description = "jira",
                url = "http://localhost/deploy/README.md"
        )
)
@SecurityScheme(type = SecuritySchemeType.APIKEY, name = "Authorization", scheme = "Authorization", in = SecuritySchemeIn.HEADER)
@Configuration
@Profile(value = {"dev", "test"})
public class SwaggerConfig {

}
