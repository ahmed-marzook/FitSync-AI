package com.kaizenflow.fitsyncai.activityservice.model.dto;

import java.time.LocalDateTime;
import java.util.Map;

import com.kaizenflow.fitsyncai.activityservice.model.enums.ActivityType;

public record ActivityDTO(
                String id,
                String userId,
                ActivityType type,
                Integer duration,
                Integer caloriesBurned,
                LocalDateTime startTime,
                Map<String, Object> additionalMetrics,
                LocalDateTime createdAt,
                LocalDateTime updatedAt) {}
