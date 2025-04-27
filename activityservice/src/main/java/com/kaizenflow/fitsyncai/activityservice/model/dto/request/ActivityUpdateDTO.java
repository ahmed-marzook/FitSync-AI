package com.kaizenflow.fitsyncai.activityservice.model.dto.request;

import java.time.LocalDateTime;
import java.util.Map;

import com.kaizenflow.fitsyncai.activityservice.model.enums.ActivityType;

public record ActivityUpdateDTO(
                ActivityType type,
                Integer duration,
                Integer caloriesBurned,
                LocalDateTime startTime,
                Map<String, Object> additionalMetrics) {}
