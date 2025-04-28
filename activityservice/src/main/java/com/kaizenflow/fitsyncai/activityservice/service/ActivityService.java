package com.kaizenflow.fitsyncai.activityservice.service;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
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
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityService {

        private final ActivityRepository activityRepository;
        private final ActivityMapper activityMapper;
        private final UserClientService userClientService;

        @Value("${rabbitmq.exchange.name:default.exchange}")
        private String exchangeName;

        @Value("${rabbitmq.routing.key:default.routing.key}")
        private String routingKey;

        private final RabbitTemplate rabbitTemplate;

        public Mono<ActivityDTO> createActivity(ActivityCreateDTO activityCreateDTO) {
                log.info("Creating new activity for user: {}", activityCreateDTO.userId());
                return userClientService
                                .validateUser(activityCreateDTO.userId())
                                .flatMap(response -> {
                                        // Wrap both blocking operations (DB save and RabbitMQ publish) in a single Mono.fromCallable
                                        // to execute them on a bounded elastic scheduler
                                        return Mono.fromCallable(() -> {
                                                                // Convert DTO to entity
                                                                Activity activity = activityMapper.toEntity(activityCreateDTO);

                                                                // Save activity to database (blocking call)
                                                                Activity savedActivity = activityRepository.save(activity);

                                                                // Convert to DTO
                                                                ActivityDTO activityDTO = activityMapper.toDto(savedActivity);

                                                                // Publish to RabbitMQ (also potentially blocking)
                                                                try {
                                                                        rabbitTemplate.convertAndSend(exchangeName, routingKey, activityDTO);
                                                                        log.info("Activity published to RabbitMQ: {}", activityDTO);
                                                                } catch (AmqpException e) {
                                                                        log.error("Failed to publish activity to RabbitMQ: {}", e.getMessage(), e);
                                                                        // Consider implementing a retry mechanism or failed messages table
                                                                }

                                                                return activityDTO;
                                                        })
                                                        .subscribeOn(Schedulers.boundedElastic());
                                })
                                .onErrorResume(e -> {
                                        log.error("Error creating activity: {}", e.getMessage(), e);
                                        return Mono.error(e);
                                });
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
