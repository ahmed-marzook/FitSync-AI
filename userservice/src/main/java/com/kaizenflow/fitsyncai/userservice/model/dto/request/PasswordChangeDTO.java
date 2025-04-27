package com.kaizenflow.fitsyncai.userservice.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PasswordChangeDTO(
                @NotBlank(message = "Current password is required") String currentPassword,
                @NotBlank(message = "New password is required")
                                @Size(min = 8, message = "New password must be at least 8 characters")
                                @Pattern(
                                                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
                                                message =
                                                                "New password must contain at least one digit, one lowercase, one uppercase, and one special character")
                                String newPassword,
                @NotBlank(message = "Password confirmation is required") String confirmPassword) {}
