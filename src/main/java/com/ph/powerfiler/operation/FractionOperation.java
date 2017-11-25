package com.ph.powerfiler.operation;

import com.ph.powerfiler.model.dto.FractionDto;
import com.ph.powerfiler.model.entity.Connection;
import com.ph.powerfiler.model.entity.Fraction;
import com.ph.powerfiler.repository.FractionRepository;
import com.ph.powerfiler.util.PowerfilerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        Fraction fraction  = get(id);
        if(fraction!=null){
            hasFractionOperation.deleteAll(fraction);
            fractionRepository.delete(fraction);
            return true;
        }else{
            return false;
        }
    }

    public Fraction get(String id){
        return fractionRepository.findOne(id);
    }
    public Fraction update(Fraction fraction) {
        return fractionRepository.save(fraction);
    }
}
