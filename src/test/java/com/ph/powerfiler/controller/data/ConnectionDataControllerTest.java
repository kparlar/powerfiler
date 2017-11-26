package com.ph.powerfiler.controller.data;

import com.ph.powerfiler.exception.ExceptionMessage;
import com.ph.powerfiler.exception.PowerfilerException;
import com.ph.powerfiler.model.dto.FractionValueDto;
import com.ph.powerfiler.model.dto.MeterValueDto;
import com.ph.powerfiler.operation.ConnectionOperation;
import com.ph.powerfiler.operation.MeterOperation;
import com.ph.powerfiler.util.PowerfilerTestConstants;
import com.ph.powerfiler.util.provider.dto.FractionValueDtoProvider;
import com.ph.powerfiler.util.provider.dto.MeterValueDtoProvider;
import com.ph.powerfiler.util.provider.object.ExceptionMessageProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class ConnectionDataControllerTest {

    @InjectMocks
    private ConnectionDataController connectionDataController;


    @Mock
    private ConnectionOperation connectionOperation;

    private MeterValueDtoProvider meterValueDtoProvider;
    private ExceptionMessageProvider exceptionMessageProvider;
    private FractionValueDtoProvider fractionValueDtoProvider;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        meterValueDtoProvider = new MeterValueDtoProvider();
        exceptionMessageProvider = new ExceptionMessageProvider();
        fractionValueDtoProvider = new FractionValueDtoProvider();
    }

    @Test
    public void addMeterGivenValidDataWhenAddingMeterThenHttpStatusOK() throws PowerfilerException {
        MeterValueDto meterValueDto = meterValueDtoProvider.createMeterValueDtoWithMonthJAN();
        when(connectionOperation.addMeter(anyString(), any(MeterValueDto.class))).thenReturn(new ExceptionMessage());
        ResponseEntity<ExceptionMessage> responseEntity = connectionDataController.addMeter(PowerfilerTestConstants.CONNECTION_ID_0001, meterValueDto);
        Assert.assertTrue("No Exception Message is expecting and HttpStatus has to be OK", responseEntity.getBody().getErrors().size() == 0 && responseEntity.getStatusCode() == HttpStatus.OK);
    }
    @Test
    public void addMeterGivenNotValidDataWhenAddingMeterThenHttpStatusBadRequest() throws PowerfilerException {
        ExceptionMessage exceptionMessage = exceptionMessageProvider.createExceptionMessage();
        MeterValueDto meterValueDto = meterValueDtoProvider.createMeterValueDtoWithMonthJAN();
        when(connectionOperation.addMeter(anyString(), any(MeterValueDto.class))).thenReturn(exceptionMessage);
        ResponseEntity<ExceptionMessage> responseEntity = connectionDataController.addMeter(PowerfilerTestConstants.CONNECTION_ID_0001, meterValueDto);
        Assert.assertTrue("Exception Message is expecting and HttpStatus has to be Bad Request",
                exceptionMessage.getErrors().get(0).equalsIgnoreCase(responseEntity.getBody().getErrors().get(0))
                        && responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST);
    }

    @Test(expected = PowerfilerException.class)
    public void addMeterGivenNotValidDataWhenErrorInAddingMeterThenPowerfilerException() throws PowerfilerException {
        MeterValueDto meterValueDto = meterValueDtoProvider.createMeterValueDtoWithMonthJAN();
        doThrow(PowerfilerException.class).when(connectionOperation).addMeter(anyString(), any(MeterValueDto.class));
        connectionDataController.addMeter(PowerfilerTestConstants.CONNECTION_ID_0001, meterValueDto);
    }

    @Test
    public void deleteMeterGivenValidDataWhenDeletingMeterThenHttpStatusOK() throws PowerfilerException {
        when(connectionOperation.deleteMeter(anyString(), anyString())).thenReturn(new ExceptionMessage());
        ResponseEntity<ExceptionMessage> responseEntity = connectionDataController.deleteMeter(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.MONTH_JAN);
        Assert.assertTrue("No Exception Message is expecting and HttpStatus has to be OK", responseEntity.getBody().getErrors().size() == 0 && responseEntity.getStatusCode() == HttpStatus.OK);
    }
    @Test
    public void deleteMeterGivenNotValidDataWhenDeletingMeterThenHttpStatusBadRequest() throws PowerfilerException {
        ExceptionMessage exceptionMessage = exceptionMessageProvider.createExceptionMessage();
        when(connectionOperation.deleteMeter(anyString(), anyString())).thenReturn(exceptionMessage);
        ResponseEntity<ExceptionMessage> responseEntity = connectionDataController.deleteMeter(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.MONTH_JAN);
        Assert.assertTrue("Exception Message is expecting and HttpStatus has to be Bad Request",
                exceptionMessage.getErrors().get(0).equalsIgnoreCase(responseEntity.getBody().getErrors().get(0))
                        && responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST);
    }

    @Test(expected = PowerfilerException.class)
    public void deleteMeterGivenNotValidDataWhenErrorInDeletingMeterThenPowerfilerException() throws PowerfilerException {
        doThrow(PowerfilerException.class).when(connectionOperation).deleteMeter(anyString(), anyString());
        connectionDataController.deleteMeter(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.MONTH_JAN);
    }
    @Test
    public void updateMeterGivenValidDataWhenUpdatetingMeterThenHttpStatusOK() throws PowerfilerException {
        when(connectionOperation.updateMeter(anyString(), anyString(), anyString())).thenReturn(new ExceptionMessage());
        ResponseEntity<ExceptionMessage> responseEntity = connectionDataController.updateMeter(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.MONTH_JAN, PowerfilerTestConstants.METER_READING_JAN);
        Assert.assertTrue("No Exception Message is expecting and HttpStatus has to be OK", responseEntity.getBody().getErrors().size() == 0 && responseEntity.getStatusCode() == HttpStatus.OK);
    }
    @Test
    public void updateMeterGivenNotValidDataWhenUpdatetingMeterThenHttpStatusBadRequest() throws PowerfilerException {
        ExceptionMessage exceptionMessage = exceptionMessageProvider.createExceptionMessage();
        when(connectionOperation.updateMeter(anyString(), anyString(), anyString())).thenReturn(exceptionMessage);
        ResponseEntity<ExceptionMessage> responseEntity = connectionDataController.updateMeter(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.MONTH_JAN, PowerfilerTestConstants.METER_READING_JAN);
        Assert.assertTrue("Exception Message is expecting and HttpStatus has to be Bad Request",
                exceptionMessage.getErrors().get(0).equalsIgnoreCase(responseEntity.getBody().getErrors().get(0))
                        && responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST);
    }
    @Test(expected = PowerfilerException.class)
    public void updateMeterGivenNotValidDataWhenErrorInUpdatingMeterThenPowerfilerException() throws PowerfilerException {
        doThrow(PowerfilerException.class).when(connectionOperation).updateMeter(anyString(), anyString(), anyString());
        connectionDataController.updateMeter(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.MONTH_JAN, PowerfilerTestConstants.METER_READING_JAN);
    }

    @Test
    public void addFractionGivenValidDataWhenAddingFractionThenHttpStatusOK() throws PowerfilerException {
        FractionValueDto fractionValueDto = fractionValueDtoProvider.createFractionValueDto();
        when(connectionOperation.addFraction(anyString(), any(FractionValueDto.class))).thenReturn(new ExceptionMessage());
        ResponseEntity<ExceptionMessage> responseEntity = connectionDataController.addFraction(PowerfilerTestConstants.CONNECTION_ID_0001, fractionValueDto);
        Assert.assertTrue("No Exception Message is expecting and HttpStatus has to be OK", responseEntity.getBody().getErrors().size() == 0 && responseEntity.getStatusCode() == HttpStatus.OK);
    }
    @Test
    public void addFractionGivenNotValidDataWhenAddingFractionThenHttpStatusBadRequest() throws PowerfilerException {
        ExceptionMessage exceptionMessage = exceptionMessageProvider.createExceptionMessage();
        FractionValueDto fractionValueDto = fractionValueDtoProvider.createFractionValueDto();
        when(connectionOperation.addFraction(anyString(), any(FractionValueDto.class))).thenReturn(exceptionMessage);
        ResponseEntity<ExceptionMessage> responseEntity = connectionDataController.addFraction(PowerfilerTestConstants.CONNECTION_ID_0001, fractionValueDto);
        Assert.assertTrue("Exception Message is expecting and HttpStatus has to be Bad Request",
                exceptionMessage.getErrors().get(0).equalsIgnoreCase(responseEntity.getBody().getErrors().get(0))
                        && responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST);
    }
    @Test(expected = PowerfilerException.class)
    public void addFractionGivenNotValidDataWhenErrorInAddingFractionThenPowerfilerException() throws PowerfilerException {
        FractionValueDto fractionValueDto = fractionValueDtoProvider.createFractionValueDto();
        doThrow(PowerfilerException.class).when(connectionOperation).addFraction(anyString(), any(FractionValueDto.class));
        connectionDataController.addFraction(PowerfilerTestConstants.CONNECTION_ID_0001, fractionValueDto);
    }
    @Test
    public void deleteFractionGivenValidDataWhenDeletingFractionThenHttpStatusOK() throws PowerfilerException {
        when(connectionOperation.deleteFraction(anyString(), anyString())).thenReturn(new ExceptionMessage());
        ResponseEntity<ExceptionMessage> responseEntity = connectionDataController.deleteFraction(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.MONTH_JAN);
        Assert.assertTrue("No Exception Message is expecting and HttpStatus has to be OK", responseEntity.getBody().getErrors().size() == 0 && responseEntity.getStatusCode() == HttpStatus.OK);
    }
    @Test
    public void deleteFractionGivenNotValidDataWhenDeletingFractionThenHttpStatusBadRequest() throws PowerfilerException {
        ExceptionMessage exceptionMessage = exceptionMessageProvider.createExceptionMessage();
        when(connectionOperation.deleteFraction(anyString(), anyString())).thenReturn(exceptionMessage);
        ResponseEntity<ExceptionMessage> responseEntity = connectionDataController.deleteFraction(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.MONTH_JAN);
        Assert.assertTrue("Exception Message is expecting and HttpStatus has to be Bad Request",
                exceptionMessage.getErrors().get(0).equalsIgnoreCase(responseEntity.getBody().getErrors().get(0))
                        && responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST);
    }
    @Test(expected = PowerfilerException.class)
    public void deleteFractionGivenNotValidDataWhenErrorInDeletingFractionThenPowerfilerException() throws PowerfilerException {
        doThrow(PowerfilerException.class).when(connectionOperation).deleteFraction(anyString(), anyString());
        connectionDataController.deleteFraction(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.MONTH_JAN);
    }

    @Test
    public void updateFractionGivenValidDataWhenUpdatetingFractionThenHttpStatusOK() throws PowerfilerException {
        when(connectionOperation.updateFraction(anyString(), anyString(), anyString())).thenReturn(new ExceptionMessage());
        ResponseEntity<ExceptionMessage> responseEntity = connectionDataController.updateFraction(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.MONTH_JAN, PowerfilerTestConstants.METER_READING_JAN);
        Assert.assertTrue("No Exception Message is expecting and HttpStatus has to be OK", responseEntity.getBody().getErrors().size() == 0 && responseEntity.getStatusCode() == HttpStatus.OK);
    }
    @Test
    public void updateFractionGivenNotValidDataWhenUpdatetingFractionThenHttpStatusBadRequest() throws PowerfilerException {
        ExceptionMessage exceptionMessage = exceptionMessageProvider.createExceptionMessage();
        when(connectionOperation.updateFraction(anyString(), anyString(), anyString())).thenReturn(exceptionMessage);
        ResponseEntity<ExceptionMessage> responseEntity = connectionDataController.updateFraction(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.MONTH_JAN, PowerfilerTestConstants.METER_READING_JAN);
        Assert.assertTrue("Exception Message is expecting and HttpStatus has to be Bad Request",
                exceptionMessage.getErrors().get(0).equalsIgnoreCase(responseEntity.getBody().getErrors().get(0))
                        && responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST);
    }
    @Test(expected = PowerfilerException.class)
    public void updateFractionGivenNotValidDataWhenErrorInUpdatingFractionThenPowerfilerException() throws PowerfilerException {
        doThrow(PowerfilerException.class).when(connectionOperation).updateFraction(anyString(), anyString(), anyString());
        connectionDataController.updateFraction(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.MONTH_JAN, PowerfilerTestConstants.METER_READING_JAN);
    }
}
