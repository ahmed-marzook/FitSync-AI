package com.kaizenflow.fitsyncai.userservice.model.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.kaizenflow.fitsyncai.userservice.model.enums.UserRole;

public record UserDTO(
                UUID id,
                String email,
                String firstName,
                String lastName,
                UserRole role,
                LocalDateTime createdAt,
                LocalDateTime updatedAt) {}
