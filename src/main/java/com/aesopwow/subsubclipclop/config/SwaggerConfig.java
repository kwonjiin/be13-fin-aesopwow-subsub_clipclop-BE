package com.aesopwow.subsubclipclop.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${springdoc.api-docs.title}")
    private String title;

    @Bean
    public OpenAPI customOpenAPI() {

        Server server = new Server().url("https://api.dagudok-service.com:8010");

        return new OpenAPI()
                .addServersItem(server)
                .info(new Info()
                        .title(title)
                        .version("0.0.2")
                        .description("Spring API입니다..1"));

    }
}
