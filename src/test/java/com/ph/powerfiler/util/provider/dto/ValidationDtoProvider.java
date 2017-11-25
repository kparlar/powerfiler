package com.ph.powerfiler.util.provider.dto;

import com.ph.powerfiler.model.dto.ValidationDto;
import com.ph.powerfiler.util.PowerfilerTestConstants;

import java.util.ArrayList;
import java.util.List;

public class ValidationDtoProvider {

    public ValidationDto createValidationDto(){
        ValidationDto validationDto = new ValidationDto(
                PowerfilerTestConstants.VALIDATION_ERROR_MESSAGE, PowerfilerTestConstants.VALIDATION_ERROR_CODE);
        return validationDto;
    }
    public List<ValidationDto> createValidationDtos(){
        List<ValidationDto> validationDtos = new ArrayList<>();
        ValidationDto validationDto = createValidationDto();
        validationDtos.add(validationDto);
        return validationDtos;
    }
}
