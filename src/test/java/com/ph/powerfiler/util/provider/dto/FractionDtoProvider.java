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
    public TreeMap<Integer, FractionDto> createTreeMapFractionDto(){
        TreeMap<Integer, FractionDto> treeMapFractionDto = new TreeMap<>();
        FractionDto fractionDtoJAN = createFractionDtoWithMonthJAN();
        FractionDto fractionDtoFEB = createFractionDtoWithMonthFEB();
        treeMapFractionDto.put(PowerfilerTestConstants.MONTH_JAN_DECIMAL, fractionDtoJAN);
        treeMapFractionDto.put(PowerfilerTestConstants.MONTH_FEB_DECIMAL, fractionDtoFEB);
        return treeMapFractionDto;
    }
}
