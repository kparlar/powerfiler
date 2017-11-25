package com.ph.powerfiler.controller;

import com.ph.powerfiler.exception.ExceptionMessage;
import com.ph.powerfiler.util.MessageCodeConstants;
import com.ph.powerfiler.exception.PowerfilerException;
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
    @ExceptionHandler(PowerfilerException.class)
    protected ResponseEntity<ExceptionMessage> handleCnfException(PowerfilerException e) {
            ExceptionMessage exceptionMessage = new ExceptionMessage();
        String exceptionId = UUID.randomUUID().toString();
        if (e.isShowMessage()) {
            exceptionMessage.getErrors().add(exceptionId + "-" + e.getErrorMessage());
        } else {
            exceptionMessage.getErrors().add(exceptionId + "-" + e.getErrorCode());
        }
        log.error("com.ph.powerfiler.exception.PowerfilerException: with id" + exceptionId, e.toString());
        if (e.getCarriedOverException() != null) {
            log.error("Carried over Exception of PowerfilerException with id " + exceptionId,
                    e.getCarriedOverException());
        }
        HttpStatus status = e.getStatus();
        if (status == null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(exceptionMessage, status);
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
