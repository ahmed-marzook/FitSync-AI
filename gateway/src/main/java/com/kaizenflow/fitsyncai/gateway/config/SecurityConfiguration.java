package com.kaizenflow.fitsyncai.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.reactive.CorsWebFilter;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

        private final String[] permittedUrls = {
                "/actuator/**",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/docs/**",
                "/docs/bookingservice/v3/api-docs/**",
                "/docs/inventoryservice/v3/api-docs/**",
                "/bookingservice/v3/api-docs/**",
                "/inventoryservice/v3/api-docs/**"
        };

        @Bean
        public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, CorsWebFilter corsFilter) {
                return http
                        .csrf(ServerHttpSecurity.CsrfSpec::disable)
                        .cors(Customizer.withDefaults()) // Enable CORS
                        .authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec
                                .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Always allow OPTIONS requests
                                .pathMatchers(permittedUrls).permitAll()
                                .anyExchange().authenticated())
                        .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                        .build();
        }
}