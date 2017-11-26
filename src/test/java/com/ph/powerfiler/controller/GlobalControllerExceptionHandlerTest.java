package com.ph.powerfiler.controller;

import com.ph.powerfiler.exception.ExceptionMessage;
import com.ph.powerfiler.util.PowerfilerTestConstants;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import static org.junit.Assert.assertEquals;

public class GlobalControllerExceptionHandlerTest {
    @InjectMocks
    private GlobalControllerExceptionHandler globalControllerExceptionHandler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void handleExceptionGivenExceptionThenErrorStatus() {
        ResponseEntity<ExceptionMessage> response =
                globalControllerExceptionHandler.handleException(new Exception(
                        PowerfilerTestConstants.EXCEPTION_TEXT));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }


    @Test
    public void handleRestClientExceptionGivenExceptionThenErrorStatus() {
        ResponseEntity<ExceptionMessage> response =
                globalControllerExceptionHandler.handleRestClientException(new RestClientException(
                        PowerfilerTestConstants.EXCEPTION_TEXT));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }



}
