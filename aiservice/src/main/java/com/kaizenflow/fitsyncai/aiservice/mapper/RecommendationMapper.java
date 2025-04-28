package com.kaizenflow.fitsyncai.aiservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.kaizenflow.fitsyncai.aiservice.model.document.Recommendation;
import com.kaizenflow.fitsyncai.aiservice.model.dto.request.CreateRecommendationRequest;
import com.kaizenflow.fitsyncai.aiservice.model.dto.request.UpdateRecommendationRequest;
import com.kaizenflow.fitsyncai.aiservice.model.dto.response.RecommendationResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RecommendationMapper {

        /**
         * Convert document to response DTO
         */
        RecommendationResponse toResponse(Recommendation recommendation);

        /**
         * Create document from request DTO
         */
        Recommendation toDocument(CreateRecommendationRequest request);

        /**
         * Update existing document from request DTO
         * @param recommendation the existing document to update
         * @param request the request with updated values
         */
        @Mapping(target = "id", ignore = true)
        @Mapping(target = "userId", ignore = true)
        @Mapping(target = "activityId", ignore = true)
        @Mapping(target = "createdAt", ignore = true)
        @Mapping(target = "updatedAt", ignore = true)
        void updateDocument(@MappingTarget Recommendation recommendation, UpdateRecommendationRequest request);
}
