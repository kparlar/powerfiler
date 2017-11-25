package com.ph.powerfiler.operation;

import com.ph.powerfiler.model.dto.FractionDto;
import com.ph.powerfiler.model.dto.MeterDto;
import com.ph.powerfiler.model.entity.Connection;
import com.ph.powerfiler.model.entity.Fraction;
import com.ph.powerfiler.model.entity.Meter;
import com.ph.powerfiler.repository.MeterRepository;
import com.ph.powerfiler.util.PowerfilerTestConstants;
import com.ph.powerfiler.util.provider.dto.MeterDtoProvider;
import com.ph.powerfiler.util.provider.entity.ConnectionProvider;
import com.ph.powerfiler.util.provider.entity.MeterProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    public void validateMeter(){
        //TODO will be written
    }


}
