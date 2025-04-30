package com.kaizenflow.fitsyncai.aiservice.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RunningAnalysis {
        private Analysis analysis;
        private List<Improvement> improvements;
        private List<Suggestion> suggestions;
        private List<String> safety;
}
