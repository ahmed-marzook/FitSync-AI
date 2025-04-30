package com.kaizenflow.fitsyncai.aiservice.model.dto;

import java.util.List;

public record RunningAnalysis(
                Analysis analysis, List<Improvement> improvements, List<Suggestion> suggestions, List<String> safety) {}
