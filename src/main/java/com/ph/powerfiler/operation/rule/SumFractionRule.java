package com.ph.powerfiler.operation.rule;

import com.ph.powerfiler.model.dto.ValidationDto;
import com.ph.powerfiler.util.MessageCodeConstants;

import java.util.ArrayList;
import java.util.List;

public class SumFractionRule implements IRule {

    private double totalFraction;
    private String profile;
    private String connectionId;
    public List<ValidationDto> validationDtos;


    public SumFractionRule(String profile, String connectionId, double totalFraction){
        this.profile = profile;
        this.connectionId = connectionId;
        this.totalFraction = totalFraction;
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
        if(new Double(totalFraction).compareTo(new Double("1.0"))!=0){
            ValidationDto validationDto = new ValidationDto(String.format(MessageCodeConstants.TOTAL_FRACTION_NOT_ONE_EXCEPTION_MESSAGE, this.profile, this.connectionId), MessageCodeConstants.TOTAL_FRACTION_NOT_ONE_EXCEPTION_CODE);
            addValidationDto(validationDto);
        }
    }
}
