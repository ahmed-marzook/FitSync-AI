package com.kaizenflow.fitsyncai.aiservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class AIServiceApplication {

        public static void main(String[] args) {
                SpringApplication.run(AIServiceApplication.class, args);
        }
}
