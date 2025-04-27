package com.kaizenflow.fitsyncai.activityservice.model.dto.request;

import java.time.LocalDateTime;
import java.util.Map;

import com.kaizenflow.fitsyncai.activityservice.model.enums.ActivityType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ActivityCreateDTO(
                @NotBlank(message = "User ID is required") String userId,
                @NotNull(message = "Activity type is required") ActivityType type,
                @NotNull(message = "Duration is required") Integer duration,
                Integer caloriesBurned,
                @NotNull(message = "Start time is required") LocalDateTime startTime,
                Map<String, Object> additionalMetrics) {}
