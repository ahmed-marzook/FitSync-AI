package com.kaizenflow.fitsyncai.gateway.filter;

import java.text.ParseException;
import java.util.UUID;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.kaizenflow.fitsyncai.gateway.model.request.UserCreateDTO;
import com.kaizenflow.fitsyncai.gateway.service.UserClientService;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class KeycloakUserSyncFilter implements WebFilter {
        private final UserClientService userClientService;

        public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
                // Skip authentication for OPTIONS requests (CORS preflight)
                if (exchange.getRequest().getMethod() == HttpMethod.OPTIONS) {
                        log.debug("Skipping authentication for OPTIONS request");
                        return chain.filter(exchange);
                }

                String token = exchange.getRequest().getHeaders().getFirst("Authorization");

                // If token is null or empty, continue with the chain
                // Spring Security will handle unauthorized requests appropriately
                if (token == null || token.isBlank()) {
                        log.debug("No Authorization header found, delegating to security chain");
                        return chain.filter(exchange);
                }

                try {
                        UserCreateDTO request = extractUserFromToken(token);
                        if (request == null || request.userGuid() == null) {
                                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                                return exchange.getResponse().setComplete();
                        }

                        return userClientService
                                        .validateUser(request.userGuid().toString())
                                        .flatMap(userDTO -> {
                                                // User exists, continue with the filter chain
                                                if (userDTO.email() == null) {
                                                        return userClientService.registerUser(request).then(chain.filter(exchange));
                                                }
                                                return chain.filter(exchange);
                                        })
                                        .onErrorResume(error -> {
                                                log.error("Error in authentication filter: {}", error.getMessage());
                                                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                                                return exchange.getResponse().setComplete();
                                        });
                } catch (Exception e) {
                        log.error("Failed to process authentication token: {}", e.getMessage());
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                }
        }

        private UserCreateDTO extractUserFromToken(String token) {
                try {
                        if (token == null || !token.startsWith("Bearer ")) {
                                return null;
                        }

                        String tokenValue = token.replace("Bearer ", "").trim();
                        SignedJWT signedJWT = SignedJWT.parse(tokenValue);
                        JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();

                        // Ensure required claims exist
                        String sub = jwtClaimsSet.getStringClaim("sub");
                        if (sub == null) {
                                log.error("Token is missing required 'sub' claim");
                                return null;
                        }

                        return new UserCreateDTO(
                                        jwtClaimsSet.getStringClaim("email"),
                                        "lMsO1,yd+42T",
                                        jwtClaimsSet.getStringClaim("given_name"),
                                        jwtClaimsSet.getStringClaim("family_name"),
                                        UUID.fromString(sub));
                } catch (ParseException e) {
                        log.error("Failed to parse JWT token: {}", e.getMessage());
                        return null;
                } catch (Exception e) {
                        log.error("Unexpected error processing token: {}", e.getMessage());
                        return null;
                }
        }
}
