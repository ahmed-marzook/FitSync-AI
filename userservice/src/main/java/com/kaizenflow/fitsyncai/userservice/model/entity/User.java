package com.kaizenflow.fitsyncai.userservice.model.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.kaizenflow.fitsyncai.userservice.model.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private UUID userGuid;

        @Email
        @Column(unique = true, nullable = false)
        private String email;

        @Column(nullable = false)
        private String password;

        @Column(length = 50)
        private String firstName;

        @Column(length = 50)
        private String lastName;

        @Enumerated(EnumType.STRING)
        @Builder.Default
        private UserRole role = UserRole.USER;

        private LocalDateTime lastLoginAt;

        @CreationTimestamp
        private LocalDateTime createdAt;

        @UpdateTimestamp
        private LocalDateTime updatedAt;
}
