package com.kaizenflow.fitsyncai.activityservice.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.kaizenflow.fitsyncai.activityservice.model.document.Activity;
import com.kaizenflow.fitsyncai.activityservice.model.enums.ActivityType;

@Repository
public interface ActivityRepository extends MongoRepository<Activity, String> {

        Page<Activity> findByUserId(String userId, Pageable pageable);

        Page<Activity> findByType(ActivityType type, Pageable pageable);

        Page<Activity> findByUserIdAndType(String userId, ActivityType type, Pageable pageable);

        // Additional useful query methods

        List<Activity> findByUserIdOrderByStartTimeDesc(String userId);

        @Query("{'userId': ?0, 'startTime': {'$gte': ?1, '$lte': ?2}}")
        List<Activity> findByUserIdAndStartTimeBetween(String userId, LocalDateTime startDate, LocalDateTime endDate);

        @Query("{'userId': ?0, 'type': ?1, 'startTime': {'$gte': ?2, '$lte': ?3}}")
        List<Activity> findByUserIdAndTypeAndStartTimeBetween(
                        String userId, ActivityType type, LocalDateTime startDate, LocalDateTime endDate);

        Page<Activity> findByCaloriesBurnedGreaterThan(Integer caloriesBurned, Pageable pageable);

        @Query("{'additionalMetrics.?0': {'$exists': true}}")
        List<Activity> findByMetricExists(String metricName);

        @Query("{'additionalMetrics.?0': ?1}")
        List<Activity> findByMetricValue(String metricName, Object metricValue);

        long countByUserIdAndType(String userId, ActivityType type);

        Optional<Activity> findFirstByUserIdOrderByStartTimeDesc(String userId);
}
