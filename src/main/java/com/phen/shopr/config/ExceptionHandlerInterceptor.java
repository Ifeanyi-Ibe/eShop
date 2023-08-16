package com.phen.shopr.config;

import com.phen.shopr.error.CustomErrorResponse;
import com.phen.shopr.error.ValidationError;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.util.*;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerInterceptor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomErrorResponse> handle(IllegalArgumentException exc) {
//        log.info(exc.getMessage(), exc);

        CustomErrorResponse error = new CustomErrorResponse();

        error.setMessage(exc.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

@ExceptionHandler({ ConstraintViolationException.class })
public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
    List<String> errors = new ArrayList<>();
    for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
        errors.add(violation.getPropertyPath() + ": "
                + violation.getMessage());
    }

    ValidationError apiError = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Validation failed.", errors);

     return new ResponseEntity<>(apiError, new HttpHeaders(),
     apiError.getStatus());
}

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<CustomErrorResponse> handle(NoSuchElementException exc) {
//        log.info(exc.getMessage(), exc);

        CustomErrorResponse error = new CustomErrorResponse();

        error.setMessage(exc.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomErrorResponse> handle(AccessDeniedException exc) {
//        log.info(exc.getMessage(), exc);

        CustomErrorResponse error = new CustomErrorResponse();

        error.setMessage(exc.getMessage());
        error.setStatus(HttpStatus.FORBIDDEN.value());
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<CustomErrorResponse> handle(AuthenticationException exc) {
//        log.info(exc.getMessage(), exc);

        CustomErrorResponse error = new CustomErrorResponse();

        error.setMessage(exc.getMessage());
        error.setStatus(HttpStatus.UNAUTHORIZED.value());
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handle(Exception exc) {
//        log.info(exc.getMessage(), exc);

        CustomErrorResponse error = new CustomErrorResponse();

        error.setMessage("Server error. Please contact admin.");
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
