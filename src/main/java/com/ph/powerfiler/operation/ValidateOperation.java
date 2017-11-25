package com.ph.powerfiler.operation;

import com.ph.powerfiler.model.dto.ConnectionTreeMapDto;
import com.ph.powerfiler.model.dto.FractionDto;
import com.ph.powerfiler.model.dto.MeterDto;
import com.ph.powerfiler.model.dto.ValidationDto;
import com.ph.powerfiler.operation.rule.ConsumptionWithToleranceRule;
import com.ph.powerfiler.operation.rule.MeterComparisionWithNextDataRule;
import com.ph.powerfiler.operation.rule.MeterComparisionWithPreviousDataRule;
import com.ph.powerfiler.operation.rule.SumFractionRule;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Component
public class ValidateOperation {


    public void validation(HashMap<String, HashMap<String, ConnectionTreeMapDto>> profileConnectionsMap, List<String[]> validProfileConnections, List<ValidationDto> validationDtos){
        Set<String> profiles = profileConnectionsMap.keySet();
        for(String profile: profiles){
            HashMap<String, ConnectionTreeMapDto> connectionTreeMapHasMapDto = profileConnectionsMap.get(profile);
            Collection<ConnectionTreeMapDto> connectionTreeMapDtos = connectionTreeMapHasMapDto.values();
            for (ConnectionTreeMapDto connectionTreeMapDto: connectionTreeMapDtos){
                boolean isValid = validatingConnection(profile, connectionTreeMapDto, validationDtos);
                if(isValid)
                    validProfileConnections.add(new String[]{profile, connectionTreeMapDto.getConnectionId()});
            }
        }

    }
    private boolean validatingConnection(String profile, ConnectionTreeMapDto connectionTreeMapDto, List<ValidationDto> validationDtos) {
       Long firstMonthReading = Long.parseLong(connectionTreeMapDto.getMeterDtos().firstEntry().getValue().getMeterReading());
       Long lastMonthReading =  Long.parseLong(connectionTreeMapDto.getMeterDtos().lastEntry().getValue().getMeterReading());
       Long totalConsumption = lastMonthReading - firstMonthReading;
       MeterDto[] meterDtoList = connectionTreeMapDto.getMeterDtos().values().toArray(new MeterDto[0]);
       double totalFraction = 0;
       for (int i=0; i<meterDtoList.length;i++){
           MeterDto meterDtoPrevious = null;
           MeterDto meterDtoCurrent = meterDtoList[i];
           MeterDto meterDtoNext = null;
           if(i<11) {
               meterDtoNext = meterDtoList[i + 1];
           }

           if(i!=0){
               meterDtoPrevious = meterDtoList[i - 1];
           }

           Long meterDtoPreviousReading = meterDtoPrevious!=null?Long.parseLong(meterDtoPrevious.getMeterReading()):0;
           Long meterDtoCurrentReading = Long.parseLong(meterDtoCurrent.getMeterReading());

           double consumption = meterDtoCurrentReading - meterDtoPreviousReading;
           FractionDto fractionDto = connectionTreeMapDto.getFractionDtos().get(i+1);
           double currentFraction = Double.parseDouble(fractionDto.getFraction());

           MeterComparisionWithPreviousDataRule meterComparisionWithPreviousDataRule = new MeterComparisionWithPreviousDataRule(meterDtoCurrent, meterDtoPrevious);
           meterComparisionWithPreviousDataRule.validate();
           validationDtos.addAll(meterComparisionWithPreviousDataRule.getValidationDtos());
           MeterComparisionWithNextDataRule meterComparisionWithNextDataRule = new MeterComparisionWithNextDataRule(meterDtoCurrent, meterDtoNext);
           meterComparisionWithNextDataRule.validate();
           validationDtos.addAll(meterComparisionWithNextDataRule.getValidationDtos());
           ConsumptionWithToleranceRule consumptionWithToleranceRule = new ConsumptionWithToleranceRule(meterDtoCurrent, meterDtoPreviousReading, totalConsumption, currentFraction);
           consumptionWithToleranceRule.validate();
           validationDtos.addAll(consumptionWithToleranceRule.getValidationDtos());

           totalFraction+= Double.parseDouble(fractionDto.getFraction());



           //validateNextMeterBiggerThanPrevious(profile, connectionTreeMapDto, meterDtoList, i, exceptionMessage);
           //validateConsumptionWithTolerance(totalConsumption, profile, connectionTreeMapDto, meterDtoList, i, exceptionMessage);

       }
       totalFraction = Math.round(totalFraction * 100) / 100;
       SumFractionRule sumFractionRule  = new SumFractionRule(profile, connectionTreeMapDto.getConnectionId(), totalFraction);
       sumFractionRule.validate();
       validationDtos.addAll(sumFractionRule.getValidationDtos());

       return validationDtos.size()>0?false:true;
    }

}
