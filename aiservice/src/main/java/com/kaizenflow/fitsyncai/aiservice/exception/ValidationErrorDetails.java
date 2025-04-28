package com.kaizenflow.fitsyncai.aiservice.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ValidationErrorDetails(
                LocalDateTime timestamp,
                String message,
                String details,
                String errorCode,
                Map<String, String> validationErrors) {}
