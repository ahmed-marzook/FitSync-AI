package com.kaizenflow.fitsyncai.userservice.model.dto.response;

import com.kaizenflow.fitsyncai.userservice.model.dto.UserDTO;

public record LoginResponseDTO(String token, UserDTO user) {}
