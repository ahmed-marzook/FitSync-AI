package com.kaizenflow.fitsyncai.aiservice.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.kaizenflow.fitsyncai.aiservice.model.document.Recommendation;
import com.kaizenflow.fitsyncai.aiservice.model.dto.Activity;
import com.kaizenflow.fitsyncai.aiservice.repository.RecommendationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityProcessingService {

        private final ActivityAIService activityAIService;

        private final RecommendationRepository recommendationRepository;

        /**
         * Process an activity from the queue
         */
        public void processActivity(Activity activity) throws IOException {
                log.info("Processing activity with ID: {}", activity.id());
                Recommendation recommendation = activityAIService.generateRecommendation(activity);
                recommendationRepository.save(recommendation);
                log.info("Recommendation generated for activity with ID: {}: {}", activity.id(), recommendation);
        }
}
