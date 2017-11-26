package com.ph.powerfiler.util.provider.object;

import com.ph.powerfiler.exception.ExceptionMessage;
import com.ph.powerfiler.util.MessageCodeConstants;

import java.util.ArrayList;
import java.util.List;

public class ExceptionMessageProvider {

    public ExceptionMessage createExceptionMessage(){
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        List<String>errors  = createErrors();
        exceptionMessage.setErrors(errors);
        return exceptionMessage;
    }
    private List<String> createErrors(){
        List<String>errors = new ArrayList<>();
        errors.add(String.format(MessageCodeConstants.DATA_SENT_EMPTY_ERROR_MESSAGE, "Profile"));
        return errors;
    }
}
