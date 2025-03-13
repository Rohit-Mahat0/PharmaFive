package com.pharmafive.exception;

import com.pharmafive.dto.RegistrationResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handles exceptions in a centralized manner.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle custom RegistrationException.
     */
    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<RegistrationResponseDTO> handleRegistrationException(RegistrationException ex) {
        RegistrationResponseDTO errorResponse = new RegistrationResponseDTO();
        errorResponse.setSuccess(false);
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setData(null);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle validation errors thrown by @Valid (MethodArgumentNotValidException).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RegistrationResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // You can build a list of all validation errors here
        StringBuilder sb = new StringBuilder("Validation error(s): ");
        ex.getBindingResult().getFieldErrors().forEach(error ->
            sb.append("[")
              .append(error.getField())
              .append(": ")
              .append(error.getDefaultMessage())
              .append("] ")
        );

        RegistrationResponseDTO errorResponse = new RegistrationResponseDTO();
        errorResponse.setSuccess(false);
        errorResponse.setMessage(sb.toString());
        errorResponse.setData(null);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Fallback for any other unhandled exceptions.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RegistrationResponseDTO> handleAllOtherExceptions(Exception ex) {
        RegistrationResponseDTO errorResponse = new RegistrationResponseDTO();
        errorResponse.setSuccess(false);
        errorResponse.setMessage("An unexpected error occurred: " + ex.getMessage());
        errorResponse.setData(null);

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
