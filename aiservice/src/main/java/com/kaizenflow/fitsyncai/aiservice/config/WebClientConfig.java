package com.kaizenflow.fitsyncai.aiservice.config;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.util.retry.Retry;

@Configuration
public class WebClientConfig {
        private static final Logger log = LoggerFactory.getLogger(WebClientConfig.class);

        // List of non-sensitive headers that are safe to log
        private static final List<String> SAFE_HEADERS = List.of("Content-Type", "Accept", "User-Agent");

        @Value("${gemini.api.url}")
        private String geminiApiUrl;

        @Value("${gemini.api.connect-timeout:10000}")
        private int connectTimeout;

        @Value("${gemini.api.read-timeout:30000}")
        private int readTimeout;

        @Value("${gemini.api.write-timeout:10000}")
        private int writeTimeout;

        @Value("${gemini.api.response-timeout:30000}")
        private int responseTimeout;

        @Value("${gemini.api.retry-attempts:3}")
        private int retryAttempts;

        @Value("${gemini.api.retry-initial-backoff:8000}")
        private long retryInitialBackoff;

        @Bean
        public WebClient webClient() {
                // Configure HttpClient with timeout settings
                HttpClient httpClient = HttpClient.create()
                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout)
                                .responseTimeout(Duration.ofMillis(responseTimeout))
                                .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS))
                                                .addHandlerLast(new WriteTimeoutHandler(writeTimeout, TimeUnit.MILLISECONDS)));

                // Configure exchange strategies with increased memory buffer
                ExchangeStrategies strategies = ExchangeStrategies.builder()
                                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                                .build();

                return WebClient.builder()
                                .baseUrl(geminiApiUrl)
                                .clientConnector(new ReactorClientHttpConnector(httpClient))
                                .exchangeStrategies(strategies)
                                .filter(logRequest())
                                .filter(logResponse())
                                .filter(retryFilter())
                                .defaultHeader("Content-Type", "application/json")
                                .build();
        }

        // Retry filter for handling 5xx errors, especially 503
        private ExchangeFilterFunction retryFilter() {
                return (request, next) -> next.exchange(request)
                                .flatMap(response -> {
                                        if (response.statusCode().is5xxServerError()) {
                                                return Mono.error(new RuntimeException("Server error: " + response.statusCode()));
                                        }
                                        return Mono.just(response);
                                })
                                .retryWhen(Retry.backoff(retryAttempts, Duration.ofMillis(retryInitialBackoff))
                                                .filter(throwable -> throwable instanceof RuntimeException
                                                                && throwable.getMessage() != null
                                                                && throwable.getMessage().contains("Server error"))
                                                .doBeforeRetry(retrySignal ->
                                                                log.warn("Retrying after server error. Attempt: {}", retrySignal.totalRetries() + 1)));
        }

        // Log request details
        private ExchangeFilterFunction logRequest() {
                return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
                        if (log.isDebugEnabled()) {
                                log.debug("Request: {} {}", clientRequest.method(), clientRequest.url());
                                clientRequest.headers().forEach((name, values) -> {
                                        if (SAFE_HEADERS.contains(name)) {
                                                values.forEach(value -> log.debug("{}={}", name, value));
                                        } else {
                                                log.debug("{}=<masked>", name);
                                        }
                                });
                        }
                        return Mono.just(clientRequest);
                });
        }

        // Log response details
        private ExchangeFilterFunction logResponse() {
                return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
                        if (log.isDebugEnabled()) {
                                log.debug("Response status: {}", clientResponse.statusCode());
                                // Only log headers at trace level
                                if (log.isTraceEnabled()) {
                                        clientResponse
                                                        .headers()
                                                        .asHttpHeaders()
                                                        .forEach((name, values) -> values.forEach(value -> log.trace("{}={}", name, value)));
                                }
                        }

                        // Log error responses at WARN level
                        if (clientResponse.statusCode().isError()) {
                                log.warn("Error response: {}", clientResponse.statusCode());
                        }

                        return Mono.just(clientResponse);
                });
        }
}
