package com.kaizenflow.fitsyncai.aiservice.model.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Request DTO for creating a new recommendation
 */
public record CreateRecommendationRequest(
                @NotBlank(message = "Activity ID is required") String activityId,
                @NotBlank(message = "User ID is required") String userId,
                @NotBlank(message = "Activity type is required") String activityType,
                @NotBlank(message = "Recommendation is required")
                                @Size(min = 10, max = 5000, message = "Recommendation must be between 10 and 5000 characters")
                                String recommendation,
                @NotNull(message = "Improvements list cannot be null")
                                List<@NotBlank(message = "Improvement items cannot be blank") String> improvements,
                @NotNull(message = "Suggestions list cannot be null")
                                List<@NotBlank(message = "Suggestion items cannot be blank") String> suggestions,
                @NotNull(message = "Safety list cannot be null")
                                List<@NotBlank(message = "Safety items cannot be blank") String> safety) {}
