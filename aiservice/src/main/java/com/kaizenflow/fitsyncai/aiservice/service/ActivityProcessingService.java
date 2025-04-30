package com.kaizenflow.fitsyncai.aiservice.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.kaizenflow.fitsyncai.aiservice.model.dto.Activity;
import com.kaizenflow.fitsyncai.aiservice.model.dto.RunningAnalysis;

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
        public void processActivity(Activity activity) throws IOException {
                log.info("Processing activity with ID: {}", activity.id());
                RunningAnalysis recommendation = activityAIService.generateRecommendation(activity);
                log.info("Recommendation generated for activity with ID: {}: {}", activity.id(), recommendation);
        }
}
