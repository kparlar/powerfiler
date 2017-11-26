package com.ph.powerfiler.operation.rule;

import com.ph.powerfiler.util.MessageCodeConstants;
import com.ph.powerfiler.model.dto.MeterDto;
import com.ph.powerfiler.model.dto.ValidationDto;

import java.util.ArrayList;
import java.util.List;

public class ConsumptionWithToleranceRule implements IRule {

    private MeterDto meterDto;
    private Long previousReading;
    private Long totalConsumption;
    private double fraction;
    private List<ValidationDto> validationDtos;

    public ConsumptionWithToleranceRule(MeterDto meterDto, Long previousReading, Long totalConsumption, double fraction){
        this.meterDto = meterDto;
        this.previousReading = previousReading;
        this.totalConsumption  = totalConsumption;
        this.fraction = fraction;
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
        Long currentReading = Long.parseLong(this.meterDto.getMeterReading());
        double consumption = currentReading - this.previousReading;
        double monthlyConsumption = this.totalConsumption * this.fraction;
        double toleranceValue = monthlyConsumption * Double.parseDouble("0.25");
        double monthlyCounsumptionUpperLimit = monthlyConsumption + toleranceValue;
        double montlyConsumptionLowerLimit = monthlyConsumption - toleranceValue;
        if( consumption < montlyConsumptionLowerLimit || consumption > monthlyCounsumptionUpperLimit){
            ValidationDto validationDto = new ValidationDto(
                    String.format(MessageCodeConstants.CONSUMPTION_TOLERANCE_ERROR_MESSAGE,  this.meterDto.getProfile(), this.meterDto.getConnectionId(), this.meterDto.getMonth()), MessageCodeConstants.METER_HAS_TO_BE_BIGGER_OR_EQ_TO_PREVIOUS_READING_EXCEPTION_CODE);
            addValidationDto(validationDto);
        }
    }
}
