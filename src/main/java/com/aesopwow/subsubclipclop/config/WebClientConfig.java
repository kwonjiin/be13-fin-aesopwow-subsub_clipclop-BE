// config/WebClientConfig.java
package com.aesopwow.subsubclipclop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://python-server:5000") // 기본 URL 설정
                .build();
    }
}