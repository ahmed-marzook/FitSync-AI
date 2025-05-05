package com.kaizenflow.fitsyncai.gateway.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.kaizenflow.fitsyncai.gateway.model.request.UserCreateDTO;
import com.kaizenflow.fitsyncai.gateway.model.response.UserDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserClientService {

        private final WebClient webClient;

        public Mono<UserDTO> validateUser(String userId) {
                return webClient
                                .get()
                                .uri("/api/users/{userId}", userId)
                                .retrieve()
                                .onStatus(status -> status.is4xxClientError(), response -> {
                                        if (response.statusCode() == HttpStatus.NOT_FOUND) {
                                                // User not found - return empty result rather than error
                                                log.info("User not found for user ID: {}", userId);
                                                return Mono.empty();
                                        }
                                        // Other client errors - return error
                                        log.error("User validation failed with client error: {}", response.statusCode());
                                        return Mono.error(new IllegalArgumentException("Invalid user ID: " + userId));
                                })
                                .onStatus(status -> status.is5xxServerError(), response -> {
                                        log.error("User validation failed with server error: {}", response.statusCode());
                                        return Mono.error(new RuntimeException("Server error during user validation"));
                                })
                                .bodyToMono(UserDTO.class)
                                .doOnNext(user -> log.info("User validation successful for user ID: {}", userId))
                                .onErrorResume(WebClientResponseException.NotFound.class, e -> {
                                        // Alternative approach to handle 404 if onStatus is not catching it
                                        log.info("User not found for user ID: {}", userId);
                                        return Mono.empty();
                                });
        }

        public Mono<UserDTO> registerUser(UserCreateDTO request) {
                return webClient
                                .post()
                                .uri("/api/users/register")
                                .bodyValue(request)
                                .retrieve()
                                .onStatus(status -> !status.is2xxSuccessful(), response -> {
                                        log.error("User registration failed with status: {}", response.statusCode());
                                        return response.bodyToMono(ErrorResponse.class)
                                                        .flatMap(errorBody -> Mono.error(new RuntimeException("Registration failed: "
                                                                        + errorBody.getBody().getDetail())));
                                })
                                .bodyToMono(UserDTO.class)
                                .doOnNext(response -> log.info("User registration successful for email: {}", response.email()));
        }
}
