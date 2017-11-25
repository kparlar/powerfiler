package com.ph.powerfiler.operation;

import com.ph.powerfiler.exception.ExceptionMessageCodeConstants;
import com.ph.powerfiler.model.dto.MeterDto;
import com.ph.powerfiler.model.dto.ValidationDto;
import com.ph.powerfiler.model.entity.Connection;
import com.ph.powerfiler.model.entity.Meter;
import com.ph.powerfiler.operation.rule.MeterComparisionWithNextDataRule;
import com.ph.powerfiler.operation.rule.MeterComparisionWithPreviousDataRule;
import com.ph.powerfiler.repository.MeterRepository;
import com.ph.powerfiler.util.PowerfilerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MeterOperation {
    @Autowired
    private MeterRepository meterRepository;
    @Autowired
    private HasMeterOperation hasMeterOperation;
    @Autowired
    PowerfilerUtil powerfilerUtil;

    public void saveAndRelateWithConnection(Connection connection, MeterDto[] meterDtos){
        for(MeterDto meterDto: meterDtos){
            long reading = Long.parseLong(meterDto.getMeterReading());
            Meter meter = save(meterDto.getMonth(), reading);
            hasMeterOperation.save(connection, meter);
        }
    }

    private Meter save(String month, long reading) {
        Meter meter = new Meter();
        meter.setMonth(month);
        meter.setReading(reading);
        return meterRepository.save(meter);
    }
    public Meter update(Meter meter) {
        return meterRepository.save(meter);
    }

    public boolean delete(String id){
        Meter meter  = get(id);
        if(meter!=null){
            hasMeterOperation.deleteAll(meter);
            meterRepository.delete(meter);
            return true;
        }else{
            return false;
        }
    }
    public Meter getMeter(String connectionId, String month){
        return meterRepository.getMeter(connectionId, month);
    }

    public List<ValidationDto> validateMeter(MeterDto meterDto){
        List<ValidationDto> validationDtos = new ArrayList<>();
        String previousMonth = powerfilerUtil.findPreviousMonth(meterDto.getMonth());
        String nextMonth = powerfilerUtil.findNextMonth(meterDto.getMonth());
        if(previousMonth == null && nextMonth == null){
            ValidationDto validationDto = new ValidationDto(ExceptionMessageCodeConstants.NEXT_PREVIOUS_MONTH_NOT_FOUND_EXCEPTION_MESSAGE, ExceptionMessageCodeConstants.NEXT_PREVIOUS_MONTH_NOT_FOUND_EXCEPTION_CODE);
            validationDtos.add(validationDto);
            return validationDtos;
        }
        if(previousMonth!=null) {
            Meter previousMeter = getMeter(meterDto.getConnectionId(), previousMonth);
            MeterDto meterDtoPrevious = new MeterDto(meterDto.getConnectionId(), meterDto.getProfile(), previousMonth, String.valueOf(previousMeter.getReading()));
            MeterComparisionWithPreviousDataRule meterComparisionWithPreviousDataRule = new MeterComparisionWithPreviousDataRule(meterDto, meterDtoPrevious);
            meterComparisionWithPreviousDataRule.validate();
            validationDtos.addAll(meterComparisionWithPreviousDataRule.getValidationDtos());
        }
        if(nextMonth!=null){
            Meter nextMeter = getMeter(meterDto.getConnectionId(), nextMonth);
            MeterDto meterDtoNext = new MeterDto(meterDto.getConnectionId(), meterDto.getProfile(), nextMonth, String.valueOf(nextMeter.getReading()));
            MeterComparisionWithNextDataRule meterComparisionWithNextDataRule = new MeterComparisionWithNextDataRule(meterDto, meterDtoNext);
            meterComparisionWithNextDataRule.validate();
            validationDtos.addAll(meterComparisionWithNextDataRule.getValidationDtos());
        }



        return validationDtos;
    }

    public Meter get(String id){
        return meterRepository.findOne(id);
    }
}
