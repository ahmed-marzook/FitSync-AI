package com.kaizenflow.fitsyncai.activityservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.kaizenflow.fitsyncai.activityservice.model.document.Activity;
import com.kaizenflow.fitsyncai.activityservice.model.dto.ActivityDTO;
import com.kaizenflow.fitsyncai.activityservice.model.dto.request.ActivityCreateDTO;
import com.kaizenflow.fitsyncai.activityservice.model.dto.request.ActivityUpdateDTO;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ActivityMapper {

        ActivityDTO toDto(Activity activity);

        Activity toEntity(ActivityCreateDTO activityCreateDTO);

        void updateEntityFromDto(ActivityUpdateDTO activityUpdateDTO, @MappingTarget Activity activity);
}
