package com.ph.powerfiler.exception;

import com.ph.powerfiler.model.dto.ValidationDto;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExceptionMessage {
    private List<String> errors;
    private List<ValidationDto> validationDtos;
    public ExceptionMessage() {
        this.setErrors(new ArrayList<>());
    }

    public void setValidationDtos(List<ValidationDto> validationDtos) {
        this.validationDtos = validationDtos;
    }

    public List<ValidationDto> getValidationDtos() {
        return validationDtos;
    }



    public List<String> getErrors() {
        if(!CollectionUtils.isEmpty(validationDtos)){
            return validationDtos.stream().map(x-> String.format("Code:%s;Message:%s",x.getCode(), x.getMessage())).collect(Collectors.toList());
        }else{
            return this.errors;
        }
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        if(!CollectionUtils.isEmpty(validationDtos)){
           List<String> errorMessage =  validationDtos.stream().map(x-> String.format("Code:%s;Message:%s",x.getCode(), x.getMessage())).collect(Collectors.toList());
            return "ErrorMessage [errors=" + String.join(",", errorMessage) + "]";
        }else{
            return "ErrorMessage [errors=" + String.join(",", errors) + "]";
        }
    }

}
