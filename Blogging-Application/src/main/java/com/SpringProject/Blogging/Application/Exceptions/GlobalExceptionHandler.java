package com.SpringProject.Blogging.Application.Exceptions;

import com.SpringProject.Blogging.Application.Payloads.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ✅ Handle Not Found Exceptions
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
        ApiResponse apiResponse = new ApiResponse(ex.getMessage(), false);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    // ✅ Handle Validation Errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValidException(MethodArgumentNotValidException ex) {

        Map<String, String> resp = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            resp.put(fieldName, message);
        });

        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

    // ✅ Handle Invalid Credentials
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse> badCredentialsException(BadCredentialsException ex) {
        ApiResponse response = new ApiResponse("Invalid Username or Password !!", false);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    // ✅ Handle Illegal Arguments
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> illegalArgumentException(IllegalArgumentException ex) {
        ApiResponse response = new ApiResponse(ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // ✅ Handle JWT Expired
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiResponse> expiredJwtException(ExpiredJwtException ex) {
        ApiResponse response = new ApiResponse("JWT Token has expired !!", false);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    // ✅ Handle Malformed JWT
    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ApiResponse> malformedJwtException(MalformedJwtException ex) {
        ApiResponse response = new ApiResponse("Invalid JWT Token !!", false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // ✅ Catch-all for debug & production stability
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> globalException(Exception ex) {
        ex.printStackTrace(); // Optional: For debugging
        ApiResponse response = new ApiResponse("An unexpected error occurred: " + ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
