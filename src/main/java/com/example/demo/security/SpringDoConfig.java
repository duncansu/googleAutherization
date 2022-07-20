package com.example.demo.security;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDoConfig {
    private static final String SECURITY_SCHEME_NAME = "BearerAuth";
    @Bean
    public OpenAPI mallTinyOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Jwttoken 實戰演練")
                                .description("SpringDoc API 演示")
                                .version("v1.0.0")
//                        .license(new License()
//                                .name("Apache 2.0")
//                                .url("https://github.com/macrozheng/mall-learning"))
                )
//                .externalDocs(new ExternalDocumentation()
//                        .description("SpringBoot實戰電商項目mall（50K+Star）全套文檔")
//                        .url("http://www.macrozheng.com"))
                .addSecurityItem(new SecurityRequirement()
                        .addList(SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
                                .name(SECURITY_SCHEME_NAME) .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer") .bearerFormat("JWT"))); } }



