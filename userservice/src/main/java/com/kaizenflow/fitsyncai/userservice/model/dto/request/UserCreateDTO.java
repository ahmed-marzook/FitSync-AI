package com.kaizenflow.fitsyncai.userservice.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserCreateDTO(
                @NotBlank(message = "Email is required") @Email(message = "Please provide a valid email address") String email,
                @NotBlank(message = "Password is required")
                                @Size(min = 8, message = "Password must be at least 8 characters")
                                @Pattern(
                                                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
                                                message =
                                                                "Password must contain at least one digit, one lowercase, one uppercase, and one special character")
                                String password,
                @NotBlank(message = "First name is required") String firstName,
                @NotBlank(message = "Last name is required") String lastName) {}
