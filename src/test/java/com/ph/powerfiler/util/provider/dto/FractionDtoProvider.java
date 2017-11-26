package com.ph.powerfiler.util.provider.dto;

import com.ph.powerfiler.model.dto.FractionDto;
import com.ph.powerfiler.model.dto.MeterDto;
import com.ph.powerfiler.util.PowerfilerTestConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class FractionDtoProvider {

    public FractionDto createFractionDtoWithMonthJAN(){
        return new FractionDto(PowerfilerTestConstants.PROFILE_A, PowerfilerTestConstants.MONTH_JAN, PowerfilerTestConstants.FRACTION_JAN);
    }
    public FractionDto createFractionDtoWithMonthFEB(){
        return new FractionDto(PowerfilerTestConstants.PROFILE_A, PowerfilerTestConstants.MONTH_FEB, PowerfilerTestConstants.FRACTION_FEB);
    }
    public FractionDto createFractionDtoWithMonthMAR(){
        return new FractionDto(PowerfilerTestConstants.PROFILE_A, PowerfilerTestConstants.MONTH_MAR, PowerfilerTestConstants.FRACTION_MAR);
    }
    public FractionDto createFractionDtoWithMonthAPR(){
        return new FractionDto(PowerfilerTestConstants.PROFILE_A, PowerfilerTestConstants.MONTH_APR, PowerfilerTestConstants.FRACTION_APR);
    }
    public FractionDto createFractionDtoWithMonthMAY(){
        return new FractionDto(PowerfilerTestConstants.PROFILE_A, PowerfilerTestConstants.MONTH_MAY, PowerfilerTestConstants.FRACTION_MAY);
    }
    public FractionDto createFractionDtoWithMonthJUN(){
        return new FractionDto(PowerfilerTestConstants.PROFILE_A, PowerfilerTestConstants.MONTH_JUN, PowerfilerTestConstants.FRACTION_JUN);
    }
    public FractionDto createFractionDtoWithMonthJUL(){
        return new FractionDto(PowerfilerTestConstants.PROFILE_A, PowerfilerTestConstants.MONTH_JUL, PowerfilerTestConstants.FRACTION_JUL);
    }
    public FractionDto createFractionDtoWithMonthAGU(){
        return new FractionDto(PowerfilerTestConstants.PROFILE_A, PowerfilerTestConstants.MONTH_AGU, PowerfilerTestConstants.FRACTION_AGU);
    }
    public FractionDto createFractionDtoWithMonthSEP(){
        return new FractionDto(PowerfilerTestConstants.PROFILE_A, PowerfilerTestConstants.MONTH_SEP, PowerfilerTestConstants.FRACTION_SEP);
    }
    public FractionDto createFractionDtoWithMonthOCT(){
        return new FractionDto(PowerfilerTestConstants.PROFILE_A, PowerfilerTestConstants.MONTH_OCT, PowerfilerTestConstants.FRACTION_OCT);
    }
    public FractionDto createFractionDtoWithMonthNOV(){
        return new FractionDto(PowerfilerTestConstants.PROFILE_A, PowerfilerTestConstants.MONTH_NOV, PowerfilerTestConstants.FRACTION_NOV);
    }
    public FractionDto createFractionDtoWithMonthDEC(){
        return new FractionDto(PowerfilerTestConstants.PROFILE_A, PowerfilerTestConstants.MONTH_DEC, PowerfilerTestConstants.FRACTION_DEC);
    }
    public FractionDto createNotValidFractionDtoWithMonthDEC(){
        return new FractionDto(PowerfilerTestConstants.PROFILE_A, PowerfilerTestConstants.MONTH_DEC, PowerfilerTestConstants.FRACTION_NOT_VALID);
    }
    public List<FractionDto> createFractionDtos(){
        List<FractionDto> fractionDtos = new ArrayList<>();
        FractionDto fractionDto = createFractionDtoWithMonthJAN();
        fractionDtos.add(fractionDto);
        return fractionDtos;
    }

    public FractionDto[] createFractionDtoArray(){
        FractionDto fractionDto = createFractionDtoWithMonthJAN();
        FractionDto[] fractionDtos = new FractionDto[]{fractionDto};
        return fractionDtos;
    }
    public TreeMap<Integer, FractionDto> createValidTreeMapFractionDto(){
        TreeMap<Integer, FractionDto> treeMapFractionDto = new TreeMap<>();
        FractionDto fractionDtoJAN = createFractionDtoWithMonthJAN();
        FractionDto fractionDtoFEB = createFractionDtoWithMonthFEB();
        FractionDto fractionDtoMAR = createFractionDtoWithMonthMAR();
        FractionDto fractionDtoAPR = createFractionDtoWithMonthAPR();
        FractionDto fractionDtoMAY = createFractionDtoWithMonthMAY();
        FractionDto fractionDtoJUN = createFractionDtoWithMonthJUN();
        FractionDto fractionDtoJUL = createFractionDtoWithMonthJUL();
        FractionDto fractionDtoAGU = createFractionDtoWithMonthAGU();
        FractionDto fractionDtoSEP = createFractionDtoWithMonthSEP();
        FractionDto fractionDtoOCT = createFractionDtoWithMonthOCT();
        FractionDto fractionDtoNOV = createFractionDtoWithMonthNOV();
        FractionDto fractionDtoDEC = createFractionDtoWithMonthDEC();

        treeMapFractionDto.put(PowerfilerTestConstants.MONTH_JAN_DECIMAL, fractionDtoJAN);
        treeMapFractionDto.put(PowerfilerTestConstants.MONTH_FEB_DECIMAL, fractionDtoFEB);
        treeMapFractionDto.put(PowerfilerTestConstants.MONTH_MAR_DECIMAL, fractionDtoMAR);
        treeMapFractionDto.put(PowerfilerTestConstants.MONTH_APR_DECIMAL, fractionDtoAPR);
        treeMapFractionDto.put(PowerfilerTestConstants.MONTH_MAY_DECIMAL, fractionDtoMAY);
        treeMapFractionDto.put(PowerfilerTestConstants.MONTH_JUN_DECIMAL, fractionDtoJUN);
        treeMapFractionDto.put(PowerfilerTestConstants.MONTH_JUL_DECIMAL, fractionDtoJUL);
        treeMapFractionDto.put(PowerfilerTestConstants.MONTH_AGU_DECIMAL, fractionDtoAGU);
        treeMapFractionDto.put(PowerfilerTestConstants.MONTH_SEP_DECIMAL, fractionDtoSEP);
        treeMapFractionDto.put(PowerfilerTestConstants.MONTH_OCT_DECIMAL, fractionDtoOCT);
        treeMapFractionDto.put(PowerfilerTestConstants.MONTH_NOV_DECIMAL, fractionDtoNOV);
        treeMapFractionDto.put(PowerfilerTestConstants.MONTH_DEC_DECIMAL, fractionDtoDEC);
        return treeMapFractionDto;
    }

    public TreeMap<Integer, FractionDto> createNotValidTreeMapFractionDto(){
        TreeMap<Integer, FractionDto> treeMapFractionDto = new TreeMap<>();
        FractionDto fractionDtoJAN = createFractionDtoWithMonthJAN();
        FractionDto fractionDtoFEB = createFractionDtoWithMonthFEB();
        FractionDto fractionDtoMAR = createFractionDtoWithMonthMAR();
        FractionDto fractionDtoAPR = createFractionDtoWithMonthAPR();
        FractionDto fractionDtoMAY = createFractionDtoWithMonthMAY();
        FractionDto fractionDtoJUN = createFractionDtoWithMonthJUN();
        FractionDto fractionDtoJUL = createFractionDtoWithMonthJUL();
        FractionDto fractionDtoAGU = createFractionDtoWithMonthAGU();
        FractionDto fractionDtoSEP = createFractionDtoWithMonthSEP();
        FractionDto fractionDtoOCT = createFractionDtoWithMonthOCT();
        FractionDto fractionDtoNOV = createFractionDtoWithMonthNOV();
        FractionDto fractionDtoDEC = createNotValidFractionDtoWithMonthDEC();

        treeMapFractionDto.put(PowerfilerTestConstants.MONTH_JAN_DECIMAL, fractionDtoJAN);
        treeMapFractionDto.put(PowerfilerTestConstants.MONTH_FEB_DECIMAL, fractionDtoFEB);
        treeMapFractionDto.put(PowerfilerTestConstants.MONTH_MAR_DECIMAL, fractionDtoMAR);
        treeMapFractionDto.put(PowerfilerTestConstants.MONTH_APR_DECIMAL, fractionDtoAPR);
        treeMapFractionDto.put(PowerfilerTestConstants.MONTH_MAY_DECIMAL, fractionDtoMAY);
        treeMapFractionDto.put(PowerfilerTestConstants.MONTH_JUN_DECIMAL, fractionDtoJUN);
        treeMapFractionDto.put(PowerfilerTestConstants.MONTH_JUL_DECIMAL, fractionDtoJUL);
        treeMapFractionDto.put(PowerfilerTestConstants.MONTH_AGU_DECIMAL, fractionDtoAGU);
        treeMapFractionDto.put(PowerfilerTestConstants.MONTH_SEP_DECIMAL, fractionDtoSEP);
        treeMapFractionDto.put(PowerfilerTestConstants.MONTH_OCT_DECIMAL, fractionDtoOCT);
        treeMapFractionDto.put(PowerfilerTestConstants.MONTH_NOV_DECIMAL, fractionDtoNOV);
        treeMapFractionDto.put(PowerfilerTestConstants.MONTH_DEC_DECIMAL, fractionDtoDEC);
        return treeMapFractionDto;
    }
}
