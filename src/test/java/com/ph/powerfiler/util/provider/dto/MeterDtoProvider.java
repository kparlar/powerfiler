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
    public MeterDto createMeterDtoWithMonthMAR(){
        return new MeterDto(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.PROFILE_A, PowerfilerTestConstants.MONTH_MAR, PowerfilerTestConstants.METER_READING_MAR);
    }
    public MeterDto createMeterDtoWithMonthAPR(){
        return new MeterDto(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.PROFILE_A, PowerfilerTestConstants.MONTH_APR, PowerfilerTestConstants.METER_READING_APR);
    }
    public MeterDto createMeterDtoWithMonthMAY(){
        return new MeterDto(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.PROFILE_A, PowerfilerTestConstants.MONTH_MAY, PowerfilerTestConstants.METER_READING_MAY);
    }
    public MeterDto createMeterDtoWithMonthJUN(){
        return new MeterDto(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.PROFILE_A, PowerfilerTestConstants.MONTH_JUN, PowerfilerTestConstants.METER_READING_JUN);
    }
    public MeterDto createMeterDtoWithMonthJUL(){
        return new MeterDto(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.PROFILE_A, PowerfilerTestConstants.MONTH_JUL, PowerfilerTestConstants.METER_READING_JUL);
    }
    public MeterDto createMeterDtoWithMonthAGU(){
        return new MeterDto(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.PROFILE_A, PowerfilerTestConstants.MONTH_AGU, PowerfilerTestConstants.METER_READING_AGU);
    }
    public MeterDto createMeterDtoWithMonthSEP(){
        return new MeterDto(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.PROFILE_A, PowerfilerTestConstants.MONTH_SEP, PowerfilerTestConstants.METER_READING_SEP);
    }
    public MeterDto createMeterDtoWithMonthOCT(){
        return new MeterDto(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.PROFILE_A, PowerfilerTestConstants.MONTH_OCT, PowerfilerTestConstants.METER_READING_OCT);
    }
    public MeterDto createMeterDtoWithMonthNOV(){
        return new MeterDto(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.PROFILE_A, PowerfilerTestConstants.MONTH_NOV, PowerfilerTestConstants.METER_READING_NOV);
    }
    public MeterDto createMeterDtoWithMonthDEC(){
        return new MeterDto(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.PROFILE_A, PowerfilerTestConstants.MONTH_DEC, PowerfilerTestConstants.METER_READING_DEC);
    }
    public MeterDto createNotValidMeterDtoWithMonthDEC(){
        return new MeterDto(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.PROFILE_A, PowerfilerTestConstants.MONTH_DEC, PowerfilerTestConstants.METER_READING_NOT_VALID);
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

    public TreeMap<Integer, MeterDto> createValidTreeMapMeterDto(){
        TreeMap<Integer, MeterDto> treeMapMeterDto = new TreeMap<>();
        MeterDto meterDtoJAN = createMeterDtoWithMonthJAN();
        MeterDto meterDtoFEB = createMeterDtoWithMonthFEB();
        MeterDto meterDtoMAR = createMeterDtoWithMonthMAR();
        MeterDto meterDtoAPR = createMeterDtoWithMonthAPR();
        MeterDto meterDtoMAY = createMeterDtoWithMonthMAY();
        MeterDto meterDtoJUN = createMeterDtoWithMonthJUN();
        MeterDto meterDtoJUL = createMeterDtoWithMonthJUL();
        MeterDto meterDtoAGU = createMeterDtoWithMonthAGU();
        MeterDto meterDtoSEP = createMeterDtoWithMonthSEP();
        MeterDto meterDtoOCT = createMeterDtoWithMonthOCT();
        MeterDto meterDtoNOV = createMeterDtoWithMonthNOV();
        MeterDto meterDtoDEC = createMeterDtoWithMonthDEC();
        treeMapMeterDto.put(PowerfilerTestConstants.MONTH_JAN_DECIMAL, meterDtoJAN);
        treeMapMeterDto.put(PowerfilerTestConstants.MONTH_FEB_DECIMAL, meterDtoFEB);
        treeMapMeterDto.put(PowerfilerTestConstants.MONTH_MAR_DECIMAL, meterDtoMAR);
        treeMapMeterDto.put(PowerfilerTestConstants.MONTH_APR_DECIMAL, meterDtoAPR);
        treeMapMeterDto.put(PowerfilerTestConstants.MONTH_MAY_DECIMAL, meterDtoMAY);
        treeMapMeterDto.put(PowerfilerTestConstants.MONTH_JUN_DECIMAL, meterDtoJUN);
        treeMapMeterDto.put(PowerfilerTestConstants.MONTH_JUL_DECIMAL, meterDtoJUL);
        treeMapMeterDto.put(PowerfilerTestConstants.MONTH_AGU_DECIMAL, meterDtoAGU);
        treeMapMeterDto.put(PowerfilerTestConstants.MONTH_SEP_DECIMAL, meterDtoSEP);
        treeMapMeterDto.put(PowerfilerTestConstants.MONTH_OCT_DECIMAL, meterDtoOCT);
        treeMapMeterDto.put(PowerfilerTestConstants.MONTH_NOV_DECIMAL, meterDtoNOV);
        treeMapMeterDto.put(PowerfilerTestConstants.MONTH_DEC_DECIMAL, meterDtoDEC);

        return treeMapMeterDto;
    }

    public TreeMap<Integer, MeterDto> createNotValidTreeMapMeterDto(){
        TreeMap<Integer, MeterDto> treeMapMeterDto = new TreeMap<>();
        MeterDto meterDtoJAN = createMeterDtoWithMonthJAN();
        MeterDto meterDtoFEB = createMeterDtoWithMonthFEB();
        MeterDto meterDtoMAR = createMeterDtoWithMonthMAR();
        MeterDto meterDtoAPR = createMeterDtoWithMonthAPR();
        MeterDto meterDtoMAY = createMeterDtoWithMonthMAY();
        MeterDto meterDtoJUN = createMeterDtoWithMonthJUN();
        MeterDto meterDtoJUL = createMeterDtoWithMonthJUL();
        MeterDto meterDtoAGU = createMeterDtoWithMonthAGU();
        MeterDto meterDtoSEP = createMeterDtoWithMonthSEP();
        MeterDto meterDtoOCT = createMeterDtoWithMonthOCT();
        MeterDto meterDtoNOV = createMeterDtoWithMonthNOV();
        MeterDto meterDtoDEC = createNotValidMeterDtoWithMonthDEC();
        treeMapMeterDto.put(PowerfilerTestConstants.MONTH_JAN_DECIMAL, meterDtoJAN);
        treeMapMeterDto.put(PowerfilerTestConstants.MONTH_FEB_DECIMAL, meterDtoFEB);
        treeMapMeterDto.put(PowerfilerTestConstants.MONTH_MAR_DECIMAL, meterDtoMAR);
        treeMapMeterDto.put(PowerfilerTestConstants.MONTH_APR_DECIMAL, meterDtoAPR);
        treeMapMeterDto.put(PowerfilerTestConstants.MONTH_MAY_DECIMAL, meterDtoMAY);
        treeMapMeterDto.put(PowerfilerTestConstants.MONTH_JUN_DECIMAL, meterDtoJUN);
        treeMapMeterDto.put(PowerfilerTestConstants.MONTH_JUL_DECIMAL, meterDtoJUL);
        treeMapMeterDto.put(PowerfilerTestConstants.MONTH_AGU_DECIMAL, meterDtoAGU);
        treeMapMeterDto.put(PowerfilerTestConstants.MONTH_SEP_DECIMAL, meterDtoSEP);
        treeMapMeterDto.put(PowerfilerTestConstants.MONTH_OCT_DECIMAL, meterDtoOCT);
        treeMapMeterDto.put(PowerfilerTestConstants.MONTH_NOV_DECIMAL, meterDtoNOV);
        treeMapMeterDto.put(PowerfilerTestConstants.MONTH_DEC_DECIMAL, meterDtoDEC);

        return treeMapMeterDto;
    }
}
