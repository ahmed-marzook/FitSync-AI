package com.kaizenflow.fitsyncai.aiservice.model.dto.response;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Response DTO for recommendation data
 */
public record RecommendationResponse(
                String id,
                String activityId,
                String userId,
                String activityType,
                String recommendation,
                List<String> improvements,
                List<String> suggestions,
                List<String> safety,
                LocalDateTime createdAt,
                LocalDateTime updatedAt) {}
