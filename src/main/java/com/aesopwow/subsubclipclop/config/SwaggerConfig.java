package com.aesopwow.subsubclipclop.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Spring API",
                description = "OTT 구독 고객관리 서비스 API 입니다",
                version = "v0.0.1"
        )
)
public class SwaggerConfig {

    Server server = new Server().url("http://api.dagudok-service.com:8010");

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addServersItem(server)
                .components(new Components().addSecuritySchemes(
                        "bearer-auth",
                        new SecurityScheme()
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .type(SecurityScheme.Type.HTTP)
                ))
                .addSecurityItem(
                        new SecurityRequirement().addList("bearer-auth")
                );
    }
}
