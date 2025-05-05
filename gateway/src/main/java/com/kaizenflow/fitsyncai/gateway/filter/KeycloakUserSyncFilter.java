package com.kaizenflow.fitsyncai.gateway.filter;

import java.text.ParseException;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
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
                String token = exchange.getRequest().getHeaders().getFirst("Authorization");
                UserCreateDTO request = extractUserFromToken(token);
                if (StringUtils.isBlank(request.userGuid().toString()) || token.isBlank()) {
                        // If headers are missing or blank, return unauthorized
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
        }

        private UserCreateDTO extractUserFromToken(String token) {
                try {
                        String tokenValue = token.replace("Bearer ", "").trim();
                        SignedJWT signedJWT = SignedJWT.parse(tokenValue);
                        JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();

                        return new UserCreateDTO(
                                        jwtClaimsSet.getStringClaim("email"),
                                        "lMsO1,yd+42T",
                                        jwtClaimsSet.getStringClaim("given_name"),
                                        jwtClaimsSet.getStringClaim("family_name"),
                                        UUID.fromString(jwtClaimsSet.getStringClaim("sub")));
                } catch (ParseException e) {
                        throw new RuntimeException(e);
                }
        }
}
