package com.kaizenflow.fitsyncai.userservice.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaizenflow.fitsyncai.userservice.model.dto.UserDTO;
import com.kaizenflow.fitsyncai.userservice.model.dto.request.PasswordChangeDTO;
import com.kaizenflow.fitsyncai.userservice.model.dto.request.UserCreateDTO;
import com.kaizenflow.fitsyncai.userservice.model.dto.request.UserUpdateDTO;
import com.kaizenflow.fitsyncai.userservice.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

        private final UserService userService;

        @Autowired
        public UserController(UserService userService) {
                this.userService = userService;
        }

        @PostMapping("/register")
        public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
                UserDTO response = userService.createUser(userCreateDTO);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        //    @PostMapping("/login")
        //    public ResponseEntity<LoginResponseDTO> loginUser(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        //        if (!userService.authenticateUser(userLoginDTO.email(), userLoginDTO.password())) {
        //            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        //        }
        //
        //        UserDTO user = userService.getUserByEmail(userLoginDTO.email());
        //
        //        LoginResponseDTO response = new LoginResponseDTO(token, user);
        //        return ResponseEntity.ok(response);
        //    }

        @GetMapping
        public ResponseEntity<List<UserDTO>> getAllUsers() {
                List<UserDTO> users = userService.getAllUsers();
                return ResponseEntity.ok(users);
        }

        @GetMapping("/{id}")
        public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
                UserDTO user = userService.getUserById(id);
                return ResponseEntity.ok(user);
        }

        @PutMapping("/{id}")
        public ResponseEntity<UserDTO> updateUser(@PathVariable UUID id, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
                UserDTO response = userService.updateUser(id, userUpdateDTO);
                return ResponseEntity.ok(response);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
                userService.deleteUser(id);
                return ResponseEntity.noContent().build();
        }

        @PostMapping("/{id}/change-password")
        public ResponseEntity<Void> changePassword(
                        @PathVariable UUID id, @Valid @RequestBody PasswordChangeDTO passwordChangeDTO) {
                userService.changePassword(
                                id,
                                passwordChangeDTO.currentPassword(),
                                passwordChangeDTO.newPassword(),
                                passwordChangeDTO.confirmPassword());
                return ResponseEntity.ok().build();
        }
}
