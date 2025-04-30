package com.kaizenflow.fitsyncai.userservice.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaizenflow.fitsyncai.userservice.exceptions.ResourceNotFoundException;
import com.kaizenflow.fitsyncai.userservice.mapper.UserMapper;
import com.kaizenflow.fitsyncai.userservice.model.dto.UserDTO;
import com.kaizenflow.fitsyncai.userservice.model.dto.request.UserCreateDTO;
import com.kaizenflow.fitsyncai.userservice.model.dto.request.UserUpdateDTO;
import com.kaizenflow.fitsyncai.userservice.model.entity.User;
import com.kaizenflow.fitsyncai.userservice.repository.UserRepository;

@Service
public class UserService {

        private final UserRepository userRepository;

        private final UserMapper userMapper;

        @Autowired
        public UserService(UserRepository userRepository, UserMapper userMapper) {
                this.userRepository = userRepository;
                this.userMapper = userMapper;
        }

        public UserDTO createUser(UserCreateDTO userCreateDTO) {
                Optional<UserDTO> existingUser = userRepository.findByEmail(userCreateDTO.email());
                if (existingUser.isPresent()) {
                        return existingUser.get();
                }

                User user = userMapper.toEntity(userCreateDTO);

                User savedUser = userRepository.save(user);
                return userMapper.toDto(savedUser);
        }

        public UserDTO getUserById(UUID id) {
                User user = userRepository
                                .findByUserGuid(id)
                                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
                return userMapper.toDto(user);
        }

        public List<UserDTO> getAllUsers() {
                return userRepository.findAll().stream().map(userMapper::toDto).collect(Collectors.toList());
        }

        public UserDTO updateUser(UUID id, UserUpdateDTO userUpdateDTO) {
                User user = userRepository
                                .findByUserGuid(id)
                                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

                userMapper.updateEntityFromDto(userUpdateDTO, user);

                //                if (userUpdateDTO.password() != null) {
                //                        user.setPassword(passwordEncoder.encode(userUpdateDTO.password()));
                //                }

                User updatedUser = userRepository.save(user);
                return userMapper.toDto(updatedUser);
        }

        public void deleteUser(UUID id) {
                if (!userRepository.existsById(id)) {
                        throw new ResourceNotFoundException("User not found with id: " + id);
                }
                userRepository.deleteById(id);
        }

        public void changePassword(UUID userId, String currentPassword, String newPassword, String confirmPassword) {
                //        // Validate that new password and confirm password match
                //        if (!newPassword.equals(confirmPassword)) {
                //            throw new IllegalArgumentException("New password and confirmation password do not match");
                //        }
                //
                //        // Get the user
                //        Users user = userRepository.findById(userId)
                //                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
                //
                //        // Check if the current password is correct
                //        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
                //            throw new AuthenticationException("Current password is incorrect");
                //        }
                //
                //        // Update the password
                //        user.setPassword(passwordEncoder.encode(newPassword));
                //        userRepository.save(user);
        }
}
