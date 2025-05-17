package com.kaizenflow.fitsyncai.aiservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kaizenflow.fitsyncai.aiservice.exception.ResourceNotFoundException;
import com.kaizenflow.fitsyncai.aiservice.mapper.RecommendationMapper;
import com.kaizenflow.fitsyncai.aiservice.model.document.Recommendation;
import com.kaizenflow.fitsyncai.aiservice.model.dto.response.RecommendationResponse;
import com.kaizenflow.fitsyncai.aiservice.repository.RecommendationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecommendationService {

        private final RecommendationRepository recommendationRepository;
        private final RecommendationMapper recommendationMapper;

        /**
         * Get recommendation by ID
         */
        public RecommendationResponse getRecommendationById(String id) {
                Recommendation recommendation = findRecommendationById(id);
                return recommendationMapper.toResponse(recommendation);
        }

        /**
         * Get all recommendations for a specific user
         */
        public List<RecommendationResponse> getRecommendationsByUserId(String userId) {
                List<Recommendation> recommendations = recommendationRepository.findByUserId(userId);
                return recommendations.stream().map(recommendationMapper::toResponse).collect(Collectors.toList());
        }

        /**
         * Get all recommendations for a specific activity
         */
        public RecommendationResponse getRecommendationsByActivityId(String activityId) {
                Recommendation recommendations = recommendationRepository.findByActivityId(activityId);
                return recommendationMapper.toResponse(recommendations);
        }

        /**
         * Get all recommendations for a specific user and activity type
         */
        public List<RecommendationResponse> getRecommendationsByUserIdAndActivityType(String userId, String activityType) {
                List<Recommendation> recommendations =
                                recommendationRepository.findByUserIdAndActivityType(userId, activityType);
                return recommendations.stream().map(recommendationMapper::toResponse).collect(Collectors.toList());
        }

        /**
         * Get recommendation for a specific user and activity
         */
        public RecommendationResponse getRecommendationByUserIdAndActivityId(String userId, String activityId) {
                Recommendation recommendation = recommendationRepository
                                .findByUserIdAndActivityId(userId, activityId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Recommendation not found for userId: " + userId + " and activityId: " + activityId));
                return recommendationMapper.toResponse(recommendation);
        }

        /**
         * Delete recommendation by ID
         */
        public void deleteRecommendation(String id) {
                if (!recommendationRepository.existsById(id)) {
                        throw new ResourceNotFoundException("Recommendation not found with id: " + id);
                }
                recommendationRepository.deleteById(id);
        }

        /**
         * Helper method to find recommendation by ID
         */
        private Recommendation findRecommendationById(String id) {
                return recommendationRepository
                                .findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Recommendation not found with id: " + id));
        }
}
