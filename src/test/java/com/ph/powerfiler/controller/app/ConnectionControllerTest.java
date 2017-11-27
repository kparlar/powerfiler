package com.ph.powerfiler.controller.app;

import com.ph.powerfiler.exception.ExceptionMessage;
import com.ph.powerfiler.exception.PowerfilerException;
import com.ph.powerfiler.model.dto.ConsumptionDto;
import com.ph.powerfiler.model.dto.ProfileDto;
import com.ph.powerfiler.model.entity.Fraction;
import com.ph.powerfiler.model.entity.Meter;
import com.ph.powerfiler.operation.ConnectionOperation;
import com.ph.powerfiler.util.PowerfilerTestConstants;
import com.ph.powerfiler.util.provider.dto.ConsumptionDtoProvider;
import com.ph.powerfiler.util.provider.object.ExceptionMessageProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
public class ConnectionControllerTest {

    @InjectMocks
    private ConnectionController connectionController;

    @Mock
    private ConnectionOperation connectionOperation;

    private ExceptionMessageProvider exceptionMessageProvider;
    private ConsumptionDtoProvider consumptionDtoProvider;


    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        exceptionMessageProvider = new ExceptionMessageProvider();
        consumptionDtoProvider = new ConsumptionDtoProvider();
    }



    @Test
    public void getConsumptionGivenValidDataWhenGettingConsumptionThenHttpStatusOK() throws PowerfilerException {
        ConsumptionDto consumptionDto = consumptionDtoProvider.createConsumptionDto();
        when(connectionOperation.getConsumption(eq(PowerfilerTestConstants.CONNECTION_ID_0001), anyString(), anyString())).thenReturn(consumptionDto);
        ResponseEntity<ConsumptionDto> responseEntity = connectionController.getConsumption(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.MONTH_JAN, PowerfilerTestConstants.MONTH_FEB);
        Assert.assertTrue("Consumption has to be 1 and HttpStatus has to be OK", responseEntity.getBody().getResult().equalsIgnoreCase(PowerfilerTestConstants.CONSUMPTION_RESULT) &&
                responseEntity.getStatusCode() == HttpStatus.OK);
    }
    @Test(expected = PowerfilerException.class)
    public void getConsumptionGivenNotValidDataWhenErrorOccurredInConsumptionThenPowerfilerException() throws PowerfilerException {
        doThrow(PowerfilerException.class).when(connectionOperation).getConsumption(eq(PowerfilerTestConstants.CONNECTION_ID_0001), anyString(), anyString());
        connectionController.getConsumption(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.MONTH_JAN, PowerfilerTestConstants.MONTH_FEB);
    }
}
