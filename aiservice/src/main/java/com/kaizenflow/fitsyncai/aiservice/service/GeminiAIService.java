package com.kaizenflow.fitsyncai.aiservice.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class GeminiAIService {

        @Value("${gemini.api.key}")
        private String geminiApiKey;

        private final WebClient webClient;

        public String generateRecommendation(String prompt) {
                log.info("Generating recommendation for user: and activity type: ");
                Map<String, Object> requestBody = Map.of("contents", Map.of("parts", Map.of("text", prompt)));
                return webClient
                                .post()
                                .uri(uriBuilder -> uriBuilder
                                                .path("/v1beta/models/gemini-2.0-flash:generateContent")
                                                .queryParam("key", geminiApiKey)
                                                .build())
                                .bodyValue(requestBody)
                                .retrieve()
                                .bodyToMono(String.class)
                                .block();
        }
}
