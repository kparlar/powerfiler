package com.ph.powerfiler.operation;

import com.ph.powerfiler.model.dto.FractionDto;
import com.ph.powerfiler.model.dto.MeterDto;
import com.ph.powerfiler.model.dto.ValidationDto;
import com.ph.powerfiler.model.entity.Connection;
import com.ph.powerfiler.model.entity.Fraction;
import com.ph.powerfiler.model.entity.Meter;
import com.ph.powerfiler.repository.MeterRepository;
import com.ph.powerfiler.util.MessageCodeConstants;
import com.ph.powerfiler.util.PowerfilerTestConstants;
import com.ph.powerfiler.util.PowerfilerUtil;
import com.ph.powerfiler.util.provider.dto.MeterDtoProvider;
import com.ph.powerfiler.util.provider.entity.ConnectionProvider;
import com.ph.powerfiler.util.provider.entity.MeterProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

public class MeterOperationTest {

    @InjectMocks
    private MeterOperation meterOperation;

    @Mock
    private MeterRepository meterRepository;
    @Mock
    private HasMeterOperation hasMeterOperation;
    @Mock
    private PowerfilerUtil powerfilerUtil;

    private ConnectionProvider connectionProvider;
    private MeterDtoProvider meterDtoProvider;
    private MeterProvider meterProvider;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        connectionProvider = new ConnectionProvider();
        meterDtoProvider = new MeterDtoProvider();
        meterProvider = new MeterProvider();
    }

    @Test
    public void saveAndRelateWithConnectionGivenValidDataWhenSavingThenVoid(){
        Connection connection = connectionProvider.createConnection();
        MeterDto[] meterDtos = meterDtoProvider.createMeterDtoArray();
        Meter meter = meterProvider.createMeterWithMonthJAN();
        when(meterRepository.save(any(Meter.class))).thenReturn(meter);
        when(hasMeterOperation.save(any(Connection.class), any(Meter.class))).thenReturn(null);
        meterOperation.saveAndRelateWithConnection(connection, meterDtos);
        verify(hasMeterOperation, atLeastOnce()).save(any(Connection.class), any(Meter.class));
    }

    @Test
    public void deleteGivenMeterIdWhenNoDataFoundThenFalse() {
        when(meterRepository.findOne(eq(PowerfilerTestConstants.ID))).thenReturn(null);
        boolean isDeleted = meterOperation.delete(PowerfilerTestConstants.ID);
        Assert.assertTrue("False is Expected", !isDeleted);
    }

    @Test
    public void deleteGivenMeterIdWhenMeterFoundThenTrue() {
        Meter meter = meterProvider.createMeterWithMonthJAN();
        when(meterRepository.findOne(eq(PowerfilerTestConstants.ID))).thenReturn(meter);
        doNothing().when(hasMeterOperation).deleteAll(any(Meter.class));
        doNothing().when(meterRepository).delete(any(Meter.class));
        boolean isDeleted = meterOperation.delete(PowerfilerTestConstants.ID);
        Assert.assertTrue("True is Expected", isDeleted);
    }

    @Test
    public void updateGivenMeterWhenUpdatingThenMeter(){
        Meter meter = meterProvider.createMeterWithMonthJAN();
        when(meterRepository.save(any(Meter.class))).thenReturn(meter);
        Meter result = meterOperation.update(meter);
        Assert.assertTrue("Same Meter is expected", meter.getId().equalsIgnoreCase(result.getId()));
    }
    @Test
    public void getMeterGivenValidDataWhenGettingMeterThenMeter(){
        Meter meter = meterProvider.createMeterWithMonthJAN();
        when(meterRepository.getMeter(eq(PowerfilerTestConstants.CONNECTION_ID_0001), eq(PowerfilerTestConstants.MONTH_JAN))).thenReturn(meter);
        Meter result = meterOperation.getMeter(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.MONTH_JAN);
        Assert.assertTrue("Same Meter is expected", meter.getId().equalsIgnoreCase(result.getId()));
    }

    @Test
    public void validateMeterGivenNotValidDataWhenNoValidMonthFoundThenValidationWithError(){
        MeterDto meterDto = meterDtoProvider.createMeterDtoWithNotValidMonth();
        when(powerfilerUtil.findPreviousMonth(eq(meterDto.getMonth()))).thenReturn(null);
        when(powerfilerUtil.findNextMonth(eq(meterDto.getMonth()))).thenReturn(null);
        List<ValidationDto> validationDtos = meterOperation.validateMeter(meterDto);
        Assert.assertTrue("Error is expected", validationDtos.get(0).getMessage().equalsIgnoreCase(MessageCodeConstants.NEXT_PREVIOUS_MONTH_NOT_FOUND_EXCEPTION_MESSAGE));
    }
    @Test
    public void validateMeterGivenValidDataWhenNextMonthReadingIsSmallerThanCurrentOneThenValidationWithError(){
        MeterDto meterDto = meterDtoProvider.createMeterDtoWithMonthJAN();
        when(powerfilerUtil.findPreviousMonth(eq(meterDto.getMonth()))).thenReturn(null);
        when(powerfilerUtil.findNextMonth(eq(meterDto.getMonth()))).thenReturn(PowerfilerTestConstants.MONTH_FEB);
        Meter meter = meterProvider.createMeterWithMonthJAN();
        meter.setReading(new Long(0));
        when(meterRepository.getMeter(eq(PowerfilerTestConstants.CONNECTION_ID_0001), eq(PowerfilerTestConstants.MONTH_FEB))).thenReturn(meter);

        List<ValidationDto> validationDtos = meterOperation.validateMeter(meterDto);
        Assert.assertTrue("Error is expected", validationDtos.get(0).getCode().equalsIgnoreCase(MessageCodeConstants.METER_HAS_TO_BE_SMALLER_OR_EQ_TO_NEXT_READING_EXCEPTION_CODE));
    }
    @Test
    public void validateMeterGivenValidDataWhenNextMonthReadingIsBiggerOrEqualToCurrentOneThenSuccess(){
        MeterDto meterDto = meterDtoProvider.createMeterDtoWithMonthJAN();
        when(powerfilerUtil.findPreviousMonth(eq(meterDto.getMonth()))).thenReturn(null);
        when(powerfilerUtil.findNextMonth(eq(meterDto.getMonth()))).thenReturn(PowerfilerTestConstants.MONTH_FEB);
        Meter meter = meterProvider.createMeterWithMonthJAN();
        when(meterRepository.getMeter(eq(PowerfilerTestConstants.CONNECTION_ID_0001), eq(PowerfilerTestConstants.MONTH_FEB))).thenReturn(meter);
        List<ValidationDto> validationDtos = meterOperation.validateMeter(meterDto);
        Assert.assertTrue("Error is not expected", validationDtos.size() == 0);
    }

    @Test
    public void validateMeterGivenValidDataWhenPreviousMonthReadingIsBiggerThanCurrentOneThenValidationWithError(){
        MeterDto meterDto = meterDtoProvider.createMeterDtoWithMonthDEC();
        when(powerfilerUtil.findPreviousMonth(eq(meterDto.getMonth()))).thenReturn(PowerfilerTestConstants.MONTH_NOV);
        when(powerfilerUtil.findNextMonth(eq(meterDto.getMonth()))).thenReturn(null);
        Meter meter = meterProvider.createMeterWithMonthNOV();
        meter.setReading(new Long(13));
        when(meterRepository.getMeter(eq(PowerfilerTestConstants.CONNECTION_ID_0001), eq(PowerfilerTestConstants.MONTH_NOV))).thenReturn(meter);

        List<ValidationDto> validationDtos = meterOperation.validateMeter(meterDto);
        Assert.assertTrue("Error is expected", validationDtos.get(0).getCode().equalsIgnoreCase(MessageCodeConstants.METER_HAS_TO_BE_BIGGER_OR_EQ_TO_PREVIOUS_READING_EXCEPTION_CODE));
    }
    @Test
    public void validateMeterGivenValidDataWhenPreviousMonthReadingIsSmallerOrEqualToCurrentOneThenSuccess(){
        MeterDto meterDto = meterDtoProvider.createMeterDtoWithMonthDEC();
        when(powerfilerUtil.findPreviousMonth(eq(meterDto.getMonth()))).thenReturn(PowerfilerTestConstants.MONTH_NOV);
        when(powerfilerUtil.findNextMonth(eq(meterDto.getMonth()))).thenReturn(null);
        Meter meter = meterProvider.createMeterWithMonthNOV();
        when(meterRepository.getMeter(eq(PowerfilerTestConstants.CONNECTION_ID_0001), eq(PowerfilerTestConstants.MONTH_NOV))).thenReturn(meter);
        List<ValidationDto> validationDtos = meterOperation.validateMeter(meterDto);
        Assert.assertTrue("Error is not expected", validationDtos.size() == 0);
    }

    @Test
    public void getGivenValidDataWhenGettingMeterThanMeter(){
        Meter meter = meterProvider.createMeterWithMonthJAN();
        when(meterRepository.findOne(PowerfilerTestConstants.ID)).thenReturn(meter);
        Meter result = meterOperation.get(PowerfilerTestConstants.ID);
        Assert.assertTrue("Meter is not expecting Meter", result.getId().equalsIgnoreCase(meter.getId()));
    }


}
