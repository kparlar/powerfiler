package com.ph.powerfiler.operation.rule;

import com.ph.powerfiler.model.dto.MeterDto;
import com.ph.powerfiler.model.dto.ValidationDto;
import com.ph.powerfiler.util.MessageCodeConstants;

import java.util.ArrayList;
import java.util.List;

public class MeterComparisionWithNextDataRule implements IRule {

    private MeterDto meterDto;
    private MeterDto meterDtoNext;
    private List<ValidationDto> validationDtos;

    public MeterComparisionWithNextDataRule(MeterDto meterDto, MeterDto meterDtoNext){
        this.meterDto= meterDto;
        this.meterDtoNext = meterDtoNext;
        this.validationDtos = new ArrayList<>();
    }


    @Override
    public List<ValidationDto> getValidationDtos() {
        return validationDtos;
    }

    @Override
    public void addValidationDto(ValidationDto validationDto) {
        validationDtos.add(validationDto);
    }

    @Override
    public void validate() {
        if(meterDtoNext!=null){
            Long currentReading = Long.parseLong(meterDto.getMeterReading());
            Long nextReading = Long.parseLong(meterDtoNext.getMeterReading());
            if(nextReading<currentReading){
                ValidationDto validationDto = new ValidationDto(
                        String.format(MessageCodeConstants.METER_HAS_TO_BE_SMALLER_OR_EQ_TO_NEXT_READING_EXCEPTION_MESSAGE, "Meter", meterDto.getProfile(), meterDto.getConnectionId(), meterDto.getMonth()), MessageCodeConstants.METER_HAS_TO_BE_SMALLER_OR_EQ_TO_NEXT_READING_EXCEPTION_CODE);
                addValidationDto(validationDto);
            }
        }

    }
}
