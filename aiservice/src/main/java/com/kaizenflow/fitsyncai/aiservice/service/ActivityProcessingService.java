package com.kaizenflow.fitsyncai.aiservice.service;

import org.springframework.stereotype.Service;

import com.kaizenflow.fitsyncai.aiservice.model.dto.Activity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityProcessingService {

        private final ActivityAIService activityAIService;

        /**
         * Process an activity from the queue
         */
        public void processActivity(Activity activity) {
                // Implementation depends on your business requirements
                // For example:
                // - Update analytics
                // - Send notifications
                // - Trigger other business processes
                // - etc.

                log.info("Processing activity with ID: {}", activity.id());
                // Your business logic here
                String recommendation = activityAIService.generateRecommendation(activity);
                log.info("Recommendation generated for activity with ID: {}: {}", activity.id(), recommendation);
        }
}
