package com.ph.powerfiler.operation.rule;

import com.ph.powerfiler.model.dto.MeterDto;
import com.ph.powerfiler.model.dto.ValidationDto;
import com.ph.powerfiler.util.MessageCodeConstants;

import java.util.ArrayList;
import java.util.List;

public class MeterComparisionWithPreviousDataRule implements IRule {

   private MeterDto meterDto;
   private MeterDto meterDtoPrevious;
   private List<ValidationDto> validationDtos;

   public MeterComparisionWithPreviousDataRule(MeterDto meterDto, MeterDto meterDtoPrevious){
    this.meterDto = meterDto;
    this.meterDtoPrevious = meterDtoPrevious;
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
       if(meterDtoPrevious!=null) {
           Long currentReading = Long.parseLong(meterDto.getMeterReading());
           Long previousReading = Long.parseLong((meterDtoPrevious.getMeterReading()));
           if (previousReading > currentReading) {
               ValidationDto validationDto = new ValidationDto(
                       String.format(MessageCodeConstants.METER_HAS_TO_BE_BIGGER_OR_EQ_TO_PREVIOUS_READING_EXCEPTION_MESSAGE, "Meter", meterDto.getProfile(), meterDto.getConnectionId(), meterDto.getMonth()), MessageCodeConstants.METER_HAS_TO_BE_BIGGER_OR_EQ_TO_PREVIOUS_READING_EXCEPTION_CODE);
               addValidationDto(validationDto);
           }
       }

    }
}
