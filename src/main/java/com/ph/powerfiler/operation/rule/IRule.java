package com.ph.powerfiler.operation.rule;

import com.ph.powerfiler.model.dto.ValidationDto;

import java.util.ArrayList;
import java.util.List;

public interface IRule {
    List<ValidationDto> validationDtos = new ArrayList<>();
    boolean isValid();
    List<ValidationDto> getValidationDtos();
    void addValidationDto(ValidationDto validationDto);
    void validate();
}
