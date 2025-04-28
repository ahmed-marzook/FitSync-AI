package com.kaizenflow.fitsyncai.aiservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaizenflow.fitsyncai.aiservice.model.dto.request.CreateRecommendationRequest;
import com.kaizenflow.fitsyncai.aiservice.model.dto.request.UpdateRecommendationRequest;
import com.kaizenflow.fitsyncai.aiservice.model.dto.response.RecommendationResponse;
import com.kaizenflow.fitsyncai.aiservice.service.RecommendationService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/recommendations")
@RequiredArgsConstructor
@Validated
public class RecommendationController {

        private final RecommendationService recommendationService;

        /**
         * Create a new recommendation
         */
        @PostMapping
        public ResponseEntity<RecommendationResponse> createRecommendation(
                        @Valid @RequestBody CreateRecommendationRequest request) {
                RecommendationResponse response = recommendationService.createRecommendation(request);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        /**
         * Get recommendation by ID
         */
        @GetMapping("/{id}")
        public ResponseEntity<RecommendationResponse> getRecommendationById(
                        @PathVariable @NotBlank(message = "ID cannot be blank") String id) {
                RecommendationResponse response = recommendationService.getRecommendationById(id);
                return ResponseEntity.ok(response);
        }

        /**
         * Get all recommendations for a specific user
         */
        @GetMapping("/user/{userId}")
        public ResponseEntity<List<RecommendationResponse>> getRecommendationsByUserId(
                        @PathVariable @NotBlank(message = "User ID cannot be blank") String userId) {
                List<RecommendationResponse> responses = recommendationService.getRecommendationsByUserId(userId);
                return ResponseEntity.ok(responses);
        }

        /**
         * Get all recommendations for a specific activity
         */
        @GetMapping("/activity/{activityId}")
        public ResponseEntity<List<RecommendationResponse>> getRecommendationsByActivityId(
                        @PathVariable @NotBlank(message = "Activity ID cannot be blank") String activityId) {
                List<RecommendationResponse> responses = recommendationService.getRecommendationsByActivityId(activityId);
                return ResponseEntity.ok(responses);
        }

        /**
         * Get all recommendations for a specific user and activity type
         */
        @GetMapping("/user/{userId}/type/{activityType}")
        public ResponseEntity<List<RecommendationResponse>> getRecommendationsByUserIdAndActivityType(
                        @PathVariable @NotBlank(message = "User ID cannot be blank") String userId,
                        @PathVariable @NotBlank(message = "Activity type cannot be blank") String activityType) {
                List<RecommendationResponse> responses =
                                recommendationService.getRecommendationsByUserIdAndActivityType(userId, activityType);
                return ResponseEntity.ok(responses);
        }

        /**
         * Get recommendation for a specific user and activity
         */
        @GetMapping("/user/{userId}/activity/{activityId}")
        public ResponseEntity<RecommendationResponse> getRecommendationByUserIdAndActivityId(
                        @PathVariable @NotBlank(message = "User ID cannot be blank") String userId,
                        @PathVariable @NotBlank(message = "Activity ID cannot be blank") String activityId) {
                RecommendationResponse response =
                                recommendationService.getRecommendationByUserIdAndActivityId(userId, activityId);
                return ResponseEntity.ok(response);
        }

        /**
         * Update an existing recommendation
         */
        @PutMapping("/{id}")
        public ResponseEntity<RecommendationResponse> updateRecommendation(
                        @PathVariable @NotBlank(message = "ID cannot be blank") String id,
                        @Valid @RequestBody UpdateRecommendationRequest request) {
                RecommendationResponse response = recommendationService.updateRecommendation(id, request);
                return ResponseEntity.ok(response);
        }

        /**
         * Delete recommendation by ID
         */
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteRecommendation(
                        @PathVariable @NotBlank(message = "ID cannot be blank") String id) {
                recommendationService.deleteRecommendation(id);
                return ResponseEntity.noContent().build();
        }
}
