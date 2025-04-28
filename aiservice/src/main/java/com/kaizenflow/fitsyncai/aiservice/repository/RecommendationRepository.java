package com.kaizenflow.fitsyncai.aiservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.kaizenflow.fitsyncai.aiservice.model.document.Recommendation;

@Repository
public interface RecommendationRepository extends MongoRepository<Recommendation, String> {

        /**
         * Find all recommendations for a specific user
         */
        List<Recommendation> findByUserId(String userId);

        /**
         * Find all recommendations for a specific activity
         */
        List<Recommendation> findByActivityId(String activityId);

        /**
         * Find all recommendations for a specific user and activity type
         */
        List<Recommendation> findByUserIdAndActivityType(String userId, String activityType);

        /**
         * Find recommendation for a specific user and activity
         */
        Optional<Recommendation> findByUserIdAndActivityId(String userId, String activityId);
}
