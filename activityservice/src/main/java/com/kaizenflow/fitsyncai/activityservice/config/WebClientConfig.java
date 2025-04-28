package com.kaizenflow.fitsyncai.activityservice.config;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
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

@Configuration
public class WebClientConfig {

        @Bean
        @LoadBalanced
        public WebClient.Builder loadBalancedWebClientBuilder() {
                return WebClient.builder();
        }

        @Bean
        public WebClient webClient(WebClient.Builder webClientBuilder) {
                // Configure HttpClient with timeout settings
                HttpClient httpClient = HttpClient.create()
                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                                .responseTimeout(Duration.ofMillis(5000))
                                .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                                                .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

                // Configure exchange strategies with increased memory buffer
                ExchangeStrategies strategies = ExchangeStrategies.builder()
                                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                                .build();

                // Create the WebClient with the above configurations
                return webClientBuilder
                                .baseUrl("http://USER-SERVICE")
                                .clientConnector(new ReactorClientHttpConnector(httpClient))
                                .exchangeStrategies(strategies)
                                .filter(logRequest())
                                .filter(logResponse())
                                .defaultHeader("Content-Type", "application/json")
                                .build();
        }

        // Log request details
        private ExchangeFilterFunction logRequest() {
                return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
                        System.out.println("Request: " + clientRequest.method() + " " + clientRequest.url());
                        clientRequest
                                        .headers()
                                        .forEach((name, values) -> values.forEach(value -> System.out.println(name + ": " + value)));
                        return Mono.just(clientRequest);
                });
        }

        // Log response details
        private ExchangeFilterFunction logResponse() {
                return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
                        System.out.println("Response status: " + clientResponse.statusCode());
                        clientResponse
                                        .headers()
                                        .asHttpHeaders()
                                        .forEach((name, values) -> values.forEach(value -> System.out.println(name + ": " + value)));
                        return Mono.just(clientResponse);
                });
        }
}
