package com.kaizenflow.fitsyncai.userservice.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ErrorDetails> handleResourceNotFoundException(
                        ResourceNotFoundException exception, WebRequest request) {

                ErrorDetails errorDetails = new ErrorDetails(
                                LocalDateTime.now(), exception.getMessage(), request.getDescription(false), "RESOURCE_NOT_FOUND");

                return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(ResourceAlreadyExistsException.class)
        public ResponseEntity<ErrorDetails> ResourceAlreadyExistsException(
                        ResourceAlreadyExistsException exception, WebRequest request) {

                ErrorDetails errorDetails = new ErrorDetails(
                                LocalDateTime.now(), exception.getMessage(), request.getDescription(false), "RESOURCE_ALREADY_EXISTS");

                return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ErrorDetails> handleIllegalArgumentException(
                        IllegalArgumentException exception, WebRequest request) {

                ErrorDetails errorDetails = new ErrorDetails(
                                LocalDateTime.now(), exception.getMessage(), request.getDescription(false), "INVALID_ARGUMENT");

                return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        }

        @Override
        protected ResponseEntity<Object> handleMethodArgumentNotValid(
                        MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

                Map<String, String> validationErrors = new HashMap<>();
                ex.getBindingResult()
                                .getFieldErrors()
                                .forEach(error -> validationErrors.put(error.getField(), error.getDefaultMessage()));

                ValidationErrorDetails errorDetails = new ValidationErrorDetails(
                                LocalDateTime.now(),
                                "Validation failed",
                                request.getDescription(false),
                                "VALIDATION_FAILED",
                                validationErrors);

                return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception, WebRequest request) {

                ErrorDetails errorDetails = new ErrorDetails(
                                LocalDateTime.now(), exception.getMessage(), request.getDescription(false), "INTERNAL_SERVER_ERROR");

                return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
        }
}
