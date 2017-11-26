package com.ph.powerfiler.util.provider.dto;

import com.ph.powerfiler.model.dto.MeterDto;
import com.ph.powerfiler.model.dto.MeterDto;
import com.ph.powerfiler.util.PowerfilerTestConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class MeterDtoProvider {

    public MeterDto createMeterDtoWithMonthJAN(){
        return new MeterDto(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.PROFILE_A, PowerfilerTestConstants.MONTH_JAN, PowerfilerTestConstants.METER_READING_JAN);
    }
    public MeterDto createMeterDtoWithMonthFEB(){
        return new MeterDto(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.PROFILE_A, PowerfilerTestConstants.MONTH_FEB, PowerfilerTestConstants.METER_READING_FEB);
    }
    public MeterDto createMeterDtoWithMonthDEC(){
        return new MeterDto(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.PROFILE_A, PowerfilerTestConstants.MONTH_DEC, PowerfilerTestConstants.METER_READING_DEC);
    }
    public MeterDto createMeterDtoWithNotValidMonth(){
        return new MeterDto(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.PROFILE_A, PowerfilerTestConstants.MONTH_NOT_VALID, PowerfilerTestConstants.METER_READING_JAN);
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

    public TreeMap<Integer, MeterDto> createTreeMapMeterDto(){
        TreeMap<Integer, MeterDto> treeMapMeterDto = new TreeMap<>();
        MeterDto meterDtoJAN = createMeterDtoWithMonthJAN();
        MeterDto meterDtoFEB = createMeterDtoWithMonthFEB();
        treeMapMeterDto.put(PowerfilerTestConstants.MONTH_JAN_DECIMAL, meterDtoJAN);
        treeMapMeterDto.put(PowerfilerTestConstants.MONTH_FEB_DECIMAL, meterDtoFEB);
        return treeMapMeterDto;
    }
}
