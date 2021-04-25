package com.projects.demo.ratelimiter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.projects.demo.ratelimiter.exception.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    ObjectMapper objectMapper = new ObjectMapper();

    @ExceptionHandler(value
            = { DataIntegrityViolationException.class })
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }


    @ExceptionHandler(value = { ValidationException.class })
    protected ResponseEntity<Object> handleValidationError(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex,
                getErrorMessage(HttpStatus.BAD_REQUEST.value(), ex.getMessage()),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    String getErrorMessage(int statusCode, String message) {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("status", statusCode);
        node.put("isError", true);
        node.put("message", message);
        return node.toString();
    }
}

