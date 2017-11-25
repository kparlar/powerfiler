package com.ph.powerfiler.util.provider.dto;

import com.ph.powerfiler.model.dto.FractionDto;
import com.ph.powerfiler.util.PowerfilerTestConstants;

import java.util.ArrayList;
import java.util.List;

public class FractionDtoProvider {

    public FractionDto createFractionDto(){
        return new FractionDto(PowerfilerTestConstants.PROFILE_A, PowerfilerTestConstants.MONTH_JAN, PowerfilerTestConstants.FRACTION_JAN);
    }
    public List<FractionDto> createFractionDtos(){
        List<FractionDto> fractionDtos = new ArrayList<>();
        FractionDto fractionDto = createFractionDto();
        fractionDtos.add(fractionDto);
        return fractionDtos;
    }
}
