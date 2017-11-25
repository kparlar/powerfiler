package com.ph.powerfiler.operation;

import com.ph.powerfiler.model.entity.Fraction;
import com.ph.powerfiler.model.entity.HasFraction;
import com.ph.powerfiler.model.entity.HasMeter;
import com.ph.powerfiler.model.entity.Meter;
import com.ph.powerfiler.repository.HasMeterRepository;
import com.ph.powerfiler.util.provider.entity.HasMeterProvider;
import com.ph.powerfiler.util.provider.entity.MeterProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class HasMeterOperationTest {

    @InjectMocks
    private HasMeterOperation hasMeterOperation;
    @Mock
    private HasMeterRepository hasMeterRepository;

    private HasMeterProvider hasMeterProvider;
    private MeterProvider meterProvider;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        hasMeterProvider = new HasMeterProvider();
        meterProvider = new MeterProvider();
    }

    @Test
    public void saveGivenValidDataWhenSavingThenHasMeter(){
        HasMeter hasMeter = hasMeterProvider.createHasMeter();
        when(hasMeterRepository.save(any(HasMeter.class))).thenReturn(hasMeter);
        HasMeter result = hasMeterOperation.save(hasMeter.getFromConnection(), hasMeter.getToMeter());
        Assert.assertTrue("Not same HasMeter is returned", hasMeter.getId().equalsIgnoreCase(result.getId()));
    }
    @Test
    public void deleteAllGivenValidMeterWhenDeletingThenVoid(){
        Meter meter = meterProvider.createMeterWithMonthJAN();
        doNothing().when(hasMeterRepository).deleteAllByToMeter(any(Meter.class));
        hasMeterOperation.deleteAll(meter);
        verify(hasMeterRepository, atMost(1)).deleteAllByToMeter(any(Meter.class));
    }


}
