package com.example.sunbaseassignment.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Define customerAlreadyExists exception as an inner static class
    public static class customerAlreadyExists extends RuntimeException {
        public customerAlreadyExists(String message) {
            super(message);
        }
    }

    // Define customerNotFound exception as an inner static class
    public static class customerNotFound extends RuntimeException {
        public customerNotFound(String message) {
            super(message);
        }
    }

    // Handling customerAlreadyExists exception
    @ExceptionHandler(customerAlreadyExists.class)
    public ResponseEntity<String> handleCustomerAlreadyExistsException(customerAlreadyExists ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Handling customerNotFound exception
    @ExceptionHandler(customerNotFound.class)
    public ResponseEntity<String> handleCustomerNotFoundException(customerNotFound ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Handling any other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex) {
        return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
