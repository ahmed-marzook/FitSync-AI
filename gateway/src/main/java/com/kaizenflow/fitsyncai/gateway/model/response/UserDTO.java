package com.kaizenflow.fitsyncai.gateway.model.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserDTO(
                UUID userGuid,
                String email,
                String firstName,
                String lastName,
                String role,
                LocalDateTime createdAt,
                LocalDateTime updatedAt) {}
