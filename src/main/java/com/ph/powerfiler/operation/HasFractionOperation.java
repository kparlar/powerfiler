package com.ph.powerfiler.operation;

import com.ph.powerfiler.model.entity.Connection;
import com.ph.powerfiler.model.entity.Fraction;
import com.ph.powerfiler.model.entity.HasFraction;
import com.ph.powerfiler.repository.HasFractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HasFractionOperation {
    @Autowired
    private HasFractionRepository hasFractionRepository;

    public HasFraction save(Connection connection, Fraction fraction){
        HasFraction hasFraction = new HasFraction();
        hasFraction.setFromConnection(connection);
        hasFraction.setToFraction(fraction);
        return hasFractionRepository.save(hasFraction);
    }
    public void deleteAll(Fraction fraction){
        hasFractionRepository.deleteAllByToFraction(fraction);
    }
}
