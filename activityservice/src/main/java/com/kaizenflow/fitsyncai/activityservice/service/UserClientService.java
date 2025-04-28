package com.kaizenflow.fitsyncai.activityservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserClientService {

        private final WebClient webClient;

        public Mono<String> validateUser(String userId) {
                return webClient
                                .get()
                                .uri("/api/users/{userId}", userId)
                                .retrieve()
                                .onStatus(status -> !status.is2xxSuccessful(), response -> {
                                        log.error("User validation failed with status: {}", response.statusCode());
                                        return Mono.error(new IllegalArgumentException("Invalid user ID: " + userId));
                                })
                                .bodyToMono(String.class)
                                .doOnNext(response -> log.info("User validation successful for user ID: {}", userId));
        }
}
