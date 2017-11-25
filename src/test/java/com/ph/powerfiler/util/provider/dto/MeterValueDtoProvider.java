package com.ph.powerfiler.util.provider.dto;

import com.ph.powerfiler.model.dto.MeterValueDto;
import com.ph.powerfiler.util.PowerfilerTestConstants;

public class MeterValueDtoProvider {

    public MeterValueDto createMeterValueDtoWithMonthJAN(){
        return new MeterValueDto(PowerfilerTestConstants.MONTH_JAN, PowerfilerTestConstants.METER_READING_JAN);
    }
}
