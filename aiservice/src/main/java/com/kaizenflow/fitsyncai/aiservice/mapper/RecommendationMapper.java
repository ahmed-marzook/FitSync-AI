package com.kaizenflow.fitsyncai.aiservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.kaizenflow.fitsyncai.aiservice.model.document.Recommendation;
import com.kaizenflow.fitsyncai.aiservice.model.dto.response.RecommendationResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RecommendationMapper {

        /**
         * Convert document to response DTO
         */
        RecommendationResponse toResponse(Recommendation recommendation);
}
