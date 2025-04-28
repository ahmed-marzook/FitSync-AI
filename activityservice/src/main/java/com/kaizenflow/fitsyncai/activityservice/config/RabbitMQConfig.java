package com.kaizenflow.fitsyncai.activityservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

        @Value("${spring.rabbitmq.host:localhost}")
        private String host;

        @Value("${spring.rabbitmq.port:5672}")
        private int port;

        @Value("${spring.rabbitmq.username:guest}")
        private String username;

        @Value("${spring.rabbitmq.password:guest}")
        private String password;

        @Value("${rabbitmq.exchange.name:default.exchange}")
        private String exchangeName;

        @Value("${rabbitmq.queue.name:activity.queue}")
        private String queueName;

        @Value("${rabbitmq.routing.key:default.routing.key}")
        private String routingKey;

        @Bean
        public Queue queue() {
                return new Queue(queueName, true);
        }

        @Bean
        public ConnectionFactory connectionFactory() {
                CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
                connectionFactory.setHost(host);
                connectionFactory.setPort(port);
                connectionFactory.setUsername(username);
                connectionFactory.setPassword(password);
                return connectionFactory;
        }

        @Bean
        public RabbitTemplate rabbitTemplate() {
                RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
                rabbitTemplate.setMessageConverter(jsonMessageConverter());
                return rabbitTemplate;
        }

        @Bean
        public MessageConverter jsonMessageConverter() {
                return new Jackson2JsonMessageConverter();
        }

        @Bean
        public DirectExchange exchange() {
                return new DirectExchange(exchangeName);
        }

        @Bean
        public Binding binding(Queue queue, DirectExchange exchange) {
                return BindingBuilder.bind(queue).to(exchange).with(routingKey);
        }
}
