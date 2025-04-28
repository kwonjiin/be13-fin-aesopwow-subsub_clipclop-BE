//package com.aesopwow.subsubclipclop.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable() // CSRF 보호 비활성화
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(
//                                "/swagger-ui/**",
//                                "/swagger-ui.html"
//                        ).permitAll() // 인증 없이 접근 허용
//                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
//                )
//                .formLogin(); // 기본 로그인 페이지 사용
//
//
//        return http.build();
//    }
//}
