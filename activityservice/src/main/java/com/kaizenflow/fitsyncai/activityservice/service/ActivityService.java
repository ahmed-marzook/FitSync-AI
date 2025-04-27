package com.kaizenflow.fitsyncai.activityservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kaizenflow.fitsyncai.activityservice.exception.ResourceNotFoundException;
import com.kaizenflow.fitsyncai.activityservice.mapper.ActivityMapper;
import com.kaizenflow.fitsyncai.activityservice.model.document.Activity;
import com.kaizenflow.fitsyncai.activityservice.model.dto.ActivityDTO;
import com.kaizenflow.fitsyncai.activityservice.model.dto.request.ActivityCreateDTO;
import com.kaizenflow.fitsyncai.activityservice.model.dto.request.ActivityUpdateDTO;
import com.kaizenflow.fitsyncai.activityservice.model.enums.ActivityType;
import com.kaizenflow.fitsyncai.activityservice.repository.ActivityRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityService {

        private final ActivityRepository activityRepository;
        private final ActivityMapper activityMapper;

        public ActivityDTO createActivity(ActivityCreateDTO activityCreateDTO) {
                log.info("Creating new activity for user: {}", activityCreateDTO.userId());
                Activity activity = activityMapper.toEntity(activityCreateDTO);
                Activity savedActivity = activityRepository.save(activity);
                return activityMapper.toDto(savedActivity);
        }

        public ActivityDTO getActivityById(String id) {
                log.info("Fetching activity with id: {}", id);
                Activity activity = activityRepository
                                .findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Activity not found with id: " + id));
                return activityMapper.toDto(activity);
        }

        public Page<ActivityDTO> getAllActivities(Pageable pageable) {
                log.info("Fetching all activities with pagination");
                Page<Activity> activityPage = activityRepository.findAll(pageable);
                return activityPage.map(activityMapper::toDto);
        }

        public Page<ActivityDTO> getActivitiesByUserId(String userId, Pageable pageable) {
                log.info("Fetching activities for user: {}", userId);
                Page<Activity> activityPage = activityRepository.findByUserId(userId, pageable);
                return activityPage.map(activityMapper::toDto);
        }

        public Page<ActivityDTO> getActivitiesByType(ActivityType type, Pageable pageable) {
                log.info("Fetching activities of type: {}", type);
                Page<Activity> activityPage = activityRepository.findByType(type, pageable);
                return activityPage.map(activityMapper::toDto);
        }

        public Page<ActivityDTO> getActivitiesByUserIdAndType(String userId, ActivityType type, Pageable pageable) {
                log.info("Fetching activities for user: {} and type: {}", userId, type);
                Page<Activity> activityPage = activityRepository.findByUserIdAndType(userId, type, pageable);
                return activityPage.map(activityMapper::toDto);
        }

        public ActivityDTO updateActivity(String id, ActivityUpdateDTO activityUpdateDTO) {
                log.info("Updating activity with id: {}", id);
                Activity existingActivity = activityRepository
                                .findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Activity not found with id: " + id));

                activityMapper.updateEntityFromDto(activityUpdateDTO, existingActivity);
                Activity updatedActivity = activityRepository.save(existingActivity);
                return activityMapper.toDto(updatedActivity);
        }

        public void deleteActivity(String id) {
                log.info("Deleting activity with id: {}", id);
                if (!activityRepository.existsById(id)) {
                        throw new ResourceNotFoundException("Activity not found with id: " + id);
                }
                activityRepository.deleteById(id);
        }
}
