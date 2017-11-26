package com.ph.powerfiler.controller;

import com.ph.powerfiler.exception.ExceptionMessage;
import com.ph.powerfiler.util.MessageCodeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.UUID;


@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ExceptionMessage> handleException(Exception e) {
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        String exceptionId = UUID.randomUUID().toString();
        exceptionMessage.getErrors().add(exceptionId + "-" + e.getMessage());
        log.error("java.lang.exception with id " + exceptionId, e);
        return new ResponseEntity<>(exceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RestClientException.class)
    protected ResponseEntity<ExceptionMessage> handleRestClientException(RestClientException e) {
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        String exceptionId = UUID.randomUUID().toString();
        exceptionMessage.getErrors().add(exceptionId + "-" + e.getMessage());
        log.error(MessageCodeConstants.REST_CLIENT_EXCEPTION_MESSAGE, MessageCodeConstants.REST_CLIENT_EXCEPTION_CODE, exceptionId,  e);
        return new ResponseEntity<>(exceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
