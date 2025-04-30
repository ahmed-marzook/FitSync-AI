package com.kaizenflow.fitsyncai.aiservice.model.document;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.kaizenflow.fitsyncai.aiservice.model.dto.RunningAnalysis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "recommendation")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recommendation {
        @Id
        private String id;

        private String activityId;
        private String userId;
        private String activityType;
        private RunningAnalysis recommendation;

        @CreatedDate
        private LocalDateTime createdAt;

        @LastModifiedDate
        private LocalDateTime updatedAt;
}
