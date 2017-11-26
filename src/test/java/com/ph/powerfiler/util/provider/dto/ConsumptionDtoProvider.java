package com.ph.powerfiler.util.provider.dto;

import com.ph.powerfiler.model.dto.ConsumptionDto;
import com.ph.powerfiler.util.PowerfilerTestConstants;

public class ConsumptionDtoProvider {

    public ConsumptionDto createConsumptionDto(){
        return new ConsumptionDto(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.MONTH_JAN, PowerfilerTestConstants.MONTH_FEB, PowerfilerTestConstants.CONSUMPTION_RESULT);
    }
}
