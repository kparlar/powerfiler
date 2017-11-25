package com.ph.powerfiler.util.provider.dto;

import com.ph.powerfiler.model.dto.MeterDto;
import com.ph.powerfiler.model.dto.MeterDto;
import com.ph.powerfiler.util.PowerfilerTestConstants;

import java.util.ArrayList;
import java.util.List;

public class MeterDtoProvider {

    public MeterDto createMeterDtoWithMonthJAN(){
        return new MeterDto(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.PROFILE_A, PowerfilerTestConstants.MONTH_JAN, PowerfilerTestConstants.METER_READING_JAN);
    }

    public List<MeterDto> createMeterDtos(){
        List<MeterDto> meterDtos = new ArrayList<>();
        MeterDto meterDto = createMeterDtoWithMonthJAN();
        meterDtos.add(meterDto);
        return meterDtos;
    }
    public MeterDto[] createMeterDtoArray(){
        MeterDto meterDto = createMeterDtoWithMonthJAN();
        MeterDto[] meterDtos = new MeterDto[]{meterDto};
        return meterDtos;
    }
}
