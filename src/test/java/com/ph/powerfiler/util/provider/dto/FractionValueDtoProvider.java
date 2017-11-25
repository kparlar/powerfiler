package com.ph.powerfiler.util.provider.dto;

import com.ph.powerfiler.model.dto.FractionValueDto;
import com.ph.powerfiler.util.PowerfilerTestConstants;

public class FractionValueDtoProvider {

    public FractionValueDto createFractionValueDto(){
        FractionValueDto fractionValueDto = new FractionValueDto(PowerfilerTestConstants.MONTH_JAN, PowerfilerTestConstants.FRACTION_JAN);
        return fractionValueDto;
    }
}
