package com.kaizenflow.fitsyncai.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.kaizenflow.fitsyncai.userservice.model.dto.UserDTO;
import com.kaizenflow.fitsyncai.userservice.model.dto.request.UserCreateDTO;
import com.kaizenflow.fitsyncai.userservice.model.dto.request.UserUpdateDTO;
import com.kaizenflow.fitsyncai.userservice.model.entity.Users;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

        UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

        UserDTO toDto(Users user);

        @Mapping(target = "id", ignore = true)
        @Mapping(target = "createdAt", ignore = true)
        @Mapping(target = "updatedAt", ignore = true)
        @Mapping(target = "role", constant = "USER")
        Users toEntity(UserCreateDTO userCreateDTO);

        @Mapping(target = "id", ignore = true)
        @Mapping(target = "email", ignore = true)
        @Mapping(target = "createdAt", ignore = true)
        @Mapping(target = "updatedAt", ignore = true)
        @Mapping(target = "role", ignore = true)
        void updateEntityFromDto(UserUpdateDTO userUpdateDTO, @MappingTarget Users user);
}
