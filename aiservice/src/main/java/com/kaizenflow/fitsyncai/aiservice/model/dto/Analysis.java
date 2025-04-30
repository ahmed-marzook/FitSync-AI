package com.kaizenflow.fitsyncai.aiservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Analysis {
        private String overall;
        private String pace;
        private String heartRate;
        private String caloriesBurned;
}
