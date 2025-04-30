package com.kaizenflow.fitsyncai.aiservice.model.dto.response;

import java.time.LocalDateTime;

import com.kaizenflow.fitsyncai.aiservice.model.dto.RunningAnalysis;

/**
 * Response DTO for recommendation data
 */
public record RecommendationResponse(
                String id,
                String activityId,
                String userId,
                String activityType,
                RunningAnalysis recommendation,
                LocalDateTime createdAt,
                LocalDateTime updatedAt) {}
