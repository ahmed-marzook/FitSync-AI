package com.kaizenflow.fitsyncai.activityservice.exception;

import java.time.LocalDateTime;

public record ErrorDetails(LocalDateTime timestamp, String message, String details, String errorCode) {}
