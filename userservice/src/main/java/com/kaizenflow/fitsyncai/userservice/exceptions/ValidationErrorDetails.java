package com.kaizenflow.fitsyncai.userservice.exceptions;

import java.time.LocalDateTime;
import java.util.Map;

public record ValidationErrorDetails(
                LocalDateTime timestamp,
                String message,
                String details,
                String errorCode,
                Map<String, String> validationErrors) {}
