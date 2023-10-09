package com.gnnny.parcelwizard.presentation.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<Object> handle(IllegalArgumentException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handle(RuntimeException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.internalServerError().body(new ErrorResponse(e.getMessage()));
    }

    record ErrorResponse(String reason) {

    }
}
