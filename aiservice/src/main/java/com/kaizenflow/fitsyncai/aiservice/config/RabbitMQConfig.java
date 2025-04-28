package com.kaizenflow.fitsyncai.aiservice.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

        @Value("${rabbitmq.listener.concurrency:5}")
        private int concurrentConsumers;

        @Value("${rabbitmq.listener.max-concurrency:10}")
        private int maxConcurrentConsumers;

        @Value("${rabbitmq.listener.prefetch:250}")
        private int prefetchCount;

        @Bean
        public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(
                        ConnectionFactory connectionFactory, Jackson2JsonMessageConverter messageConverter) {

                SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
                factory.setConnectionFactory(connectionFactory);
                factory.setMessageConverter(messageConverter);
                factory.setConcurrentConsumers(concurrentConsumers);
                factory.setMaxConcurrentConsumers(maxConcurrentConsumers);
                factory.setPrefetchCount(prefetchCount);
                factory.setAcknowledgeMode(AcknowledgeMode.AUTO);

                return factory;
        }

        @Bean
        public Jackson2JsonMessageConverter jsonMessageConverter() {
                return new Jackson2JsonMessageConverter();
        }
}
