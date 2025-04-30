package com.kaizenflow.fitsyncai.userservice.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kaizenflow.fitsyncai.userservice.model.dto.UserDTO;
import com.kaizenflow.fitsyncai.userservice.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
        Optional<UserDTO> findByEmail(String email);

        boolean existsByEmail(String email);

        Optional<User> findByUserGuid(UUID userGuid);
}
