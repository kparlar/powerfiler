package com.ph.powerfiler.operation.rule;

import com.ph.powerfiler.exception.ExceptionMessageCodeConstants;
import com.ph.powerfiler.model.dto.ValidationDto;

import java.util.List;

public class SumFractionRule implements IRule {

    boolean isValid;
    private double totalFraction;
    private String profile;
    private String connectionId;


    public SumFractionRule(String profile, String connectionId, double totalFraction){
        this.profile = profile;
        this.connectionId = connectionId;
        this.totalFraction = totalFraction;


    }

    @Override
    public boolean isValid() {
        return isValid;
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

        if(new Double(totalFraction).compareTo(new Double("1.0"))!=0){
            ValidationDto validationDto = new ValidationDto(String.format(ExceptionMessageCodeConstants.TOTAL_FRACTION_NOT_ONE_EXCEPTION_MESSAGE, this.profile, this.connectionId),ExceptionMessageCodeConstants.TOTAL_FRACTION_NOT_ONE_EXCEPTION_CODE);
            addValidationDto(validationDto);
        }
    }
}
