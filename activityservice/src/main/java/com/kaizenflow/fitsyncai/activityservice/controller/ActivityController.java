package com.kaizenflow.fitsyncai.activityservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kaizenflow.fitsyncai.activityservice.model.dto.ActivityDTO;
import com.kaizenflow.fitsyncai.activityservice.model.dto.request.ActivityCreateDTO;
import com.kaizenflow.fitsyncai.activityservice.model.dto.request.ActivityUpdateDTO;
import com.kaizenflow.fitsyncai.activityservice.model.enums.ActivityType;
import com.kaizenflow.fitsyncai.activityservice.service.ActivityService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/activities")
@RequiredArgsConstructor
@Slf4j
public class ActivityController {

        private final ActivityService activityService;

        @PostMapping
        public Mono<ResponseEntity<ActivityDTO>> createActivity(@Valid @RequestBody ActivityCreateDTO dto) {
                return activityService.createActivity(dto).map(result -> new ResponseEntity<>(result, HttpStatus.CREATED));
        }

        @GetMapping("/{id}")
        public ResponseEntity<ActivityDTO> getActivity(@PathVariable String id) {
                log.info("REST request to get activity : {}", id);
                ActivityDTO activityDTO = activityService.getActivityById(id);
                return ResponseEntity.ok(activityDTO);
        }

        @GetMapping
        public ResponseEntity<Page<ActivityDTO>> getAllActivities(
                        @RequestParam(required = false) String userId,
                        @RequestParam(required = false) ActivityType type,
                        @PageableDefault(size = 20) Pageable pageable) {
                log.info("REST request to get activities with filters userId: {}, type: {}", userId, type);

                Page<ActivityDTO> page;
                if (userId != null && type != null) {
                        page = activityService.getActivitiesByUserIdAndType(userId, type, pageable);
                } else if (userId != null) {
                        page = activityService.getActivitiesByUserId(userId, pageable);
                } else if (type != null) {
                        page = activityService.getActivitiesByType(type, pageable);
                } else {
                        page = activityService.getAllActivities(pageable);
                }

                return ResponseEntity.ok(page);
        }

        @PutMapping("/{id}")
        public ResponseEntity<ActivityDTO> updateActivity(
                        @PathVariable String id, @RequestBody ActivityUpdateDTO activityUpdateDTO) {
                log.info("REST request to update activity : {}", id);
                ActivityDTO result = activityService.updateActivity(id, activityUpdateDTO);
                return ResponseEntity.ok(result);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteActivity(@PathVariable String id) {
                log.info("REST request to delete activity : {}", id);
                activityService.deleteActivity(id);
                return ResponseEntity.noContent().build();
        }
}
