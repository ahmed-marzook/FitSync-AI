package com.kaizenflow.fitsyncai.aiservice.model.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record Activity(
                String id,
                String userId,
                String type,
                Integer duration,
                Integer caloriesBurned,
                LocalDateTime startTime,
                Map<String, Object> additionalMetrics,
                LocalDateTime createdAt,
                LocalDateTime updatedAt) {}
