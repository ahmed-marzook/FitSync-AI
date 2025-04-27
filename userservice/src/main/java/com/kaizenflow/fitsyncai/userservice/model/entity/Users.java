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
public class Users {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID id;

        @Email
        @Column(unique = true, nullable = false)
        private String email;

        @Column(nullable = false)
        private String password;

        private String firstName;
        private String lastName;

        @Enumerated(EnumType.STRING)
        private UserRole role = UserRole.USER;

        @CreationTimestamp
        private LocalDateTime createdAt;

        @UpdateTimestamp
        private LocalDateTime updatedAt;
}
