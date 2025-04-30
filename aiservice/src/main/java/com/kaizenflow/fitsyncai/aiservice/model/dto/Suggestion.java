package com.kaizenflow.fitsyncai.aiservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Suggestion {
        private String workout;
        private String description;
}
