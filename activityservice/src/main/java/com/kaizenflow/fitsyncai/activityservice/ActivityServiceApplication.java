package com.kaizenflow.fitsyncai.activityservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class ActivityServiceApplication {

        public static void main(String[] args) {
                SpringApplication.run(ActivityServiceApplication.class, args);
        }
}
