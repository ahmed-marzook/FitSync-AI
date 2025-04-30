package com.kaizenflow.fitsyncai.aiservice.consumer;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.kaizenflow.fitsyncai.aiservice.model.dto.Activity;
import com.kaizenflow.fitsyncai.aiservice.service.ActivityProcessingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityConsumer {

        private final ActivityProcessingService activityProcessingService;

        @RabbitListener(queues = "${rabbitmq.queue.name:activity.queue}")
        public void processActivity(Activity activity) {
                try {
                        log.info("Received activity from queue: {}", activity);

                        // Process the activity
                        activityProcessingService.processActivity(activity);

                        log.info("Successfully processed activity: {}", activity.id());
                } catch (Exception e) {
                        log.error("Error processing activity: {}", e.getMessage(), e);
                        // For non-recoverable errors, reject the message and don't requeue it
                        throw new AmqpRejectAndDontRequeueException("Non-recoverable error processing activity", e);
                }
        }
}
