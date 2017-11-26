package com.ph.powerfiler.util.provider.entity;

import com.ph.powerfiler.model.dto.MeterDto;
import com.ph.powerfiler.model.entity.Meter;
import com.ph.powerfiler.util.PowerfilerTestConstants;

import java.util.ArrayList;
import java.util.List;

public class MeterProvider {

    public Meter createMeterWithMonthJAN(){
        Meter meter  = new Meter();
        meter.setReading(Long.parseLong(PowerfilerTestConstants.METER_READING_JAN));
        meter.setMonth(PowerfilerTestConstants.MONTH_JAN);
        meter.setId(PowerfilerTestConstants.ID);
        return meter;
    }
    public Meter createMeterWithMonthFEB(){
        Meter meter  = new Meter();
        meter.setReading(Long.parseLong(PowerfilerTestConstants.METER_READING_FEB));
        meter.setMonth(PowerfilerTestConstants.MONTH_FEB);
        return meter;
    }
    public Meter createMeterWithMonthNOV(){
        Meter meter  = new Meter();
        meter.setReading(Long.parseLong(PowerfilerTestConstants.METER_READING_NOV));
        meter.setMonth(PowerfilerTestConstants.MONTH_NOV);
        return meter;
    }
    public List<Meter> createMeters(){
        List<Meter> meters = new ArrayList<>();
        Meter meter = createMeterWithMonthJAN();
        meters.add(meter);
        return meters;
    }
}
