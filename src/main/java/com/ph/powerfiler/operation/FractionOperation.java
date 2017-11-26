package com.ph.powerfiler.operation;

import com.ph.powerfiler.model.dto.FractionDto;
import com.ph.powerfiler.model.dto.ValidationDto;
import com.ph.powerfiler.model.entity.Connection;
import com.ph.powerfiler.model.entity.Fraction;
import com.ph.powerfiler.operation.rule.SumFractionRule;
import com.ph.powerfiler.repository.FractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FractionOperation {
    @Autowired
    private FractionRepository fractionRepository;
    @Autowired
    private HasFractionOperation hasFractionOperation;

    public void saveAndRelateWithConnection(Connection connection, FractionDto[] fractionDtos){
        for(FractionDto fractionDto: fractionDtos){
            Fraction fraction = save(fractionDto.getMonth(), Double.parseDouble(fractionDto.getFraction()));
            hasFractionOperation.save(connection, fraction);
        }

    }

    private Fraction save(String month, double fractionValue) {
        Fraction fraction = new Fraction();
        fraction.setMonth(month);
        fraction.setFraction(fractionValue);
        return fractionRepository.save(fraction);
    }

    public Fraction getFraction(String connectionId, String month){
        return fractionRepository.getFraction(connectionId, month);
    }


    public boolean delete(String id) {
        Fraction fraction  = getFraction(id);
        if(fraction!=null){
            hasFractionOperation.deleteAll(fraction);
            fractionRepository.delete(fraction);
            return true;
        }else{
            return false;
        }
    }

    public List<ValidationDto> validateFraction(String connectionId, Double fractionValue, List<Fraction> fractions, String profileName) {
        double totalFraction = 0;
        for(Fraction fractionInner: fractions){
            totalFraction+=fractionInner.getFraction();
        }
        totalFraction+=fractionValue;
        totalFraction = Math.round(totalFraction * 100) / 100;

        List<ValidationDto> validationDtos = new ArrayList<>();
        SumFractionRule sumFractionRule = new SumFractionRule(profileName, connectionId, totalFraction);
        sumFractionRule.validate();
        validationDtos.addAll(sumFractionRule.getValidationDtos());
        return validationDtos;
    }

    public Fraction getFraction(String id){
        return fractionRepository.findOne(id);
    }

    public Fraction update(Fraction fraction) {
        return fractionRepository.save(fraction);
    }

}
