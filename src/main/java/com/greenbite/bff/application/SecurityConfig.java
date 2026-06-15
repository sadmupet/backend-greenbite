package com.greenbite.bff.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Evita bloqueos en peticiones POST, PATCH o DELETE
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Da paso libre absoluto a Swagger y endpoints
            );
        return http.build();
    }
}