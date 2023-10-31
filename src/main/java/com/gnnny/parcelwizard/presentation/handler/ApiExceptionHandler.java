package com.gnnny.parcelwizard.presentation.handler;

import com.gnnny.parcelwizard.infrastructure.external.common.NonExistentTrackingNumberException;
import com.gnnny.parcelwizard.infrastructure.external.common.ThirdPartyAPIConnectionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NonExistentTrackingNumberException.class)
    public ResponseEntity<Object> handle(NonExistentTrackingNumberException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(
        { IllegalArgumentException.class, IllegalStateException.class }
    )
    public ResponseEntity<Object> handle(RuntimeException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ThirdPartyAPIConnectionException.class)
    public ResponseEntity<Object> handle(ThirdPartyAPIConnectionException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.internalServerError().body(new ErrorResponse(e.getMessage()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handle(Exception e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(e.getMessage());
    }

    record ErrorResponse(String reason) {

    }
}
