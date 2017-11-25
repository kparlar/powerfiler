package com.ph.powerfiler.util.provider.entity;

import com.ph.powerfiler.exception.PowerfilerException;
import com.ph.powerfiler.model.entity.Fraction;
import com.ph.powerfiler.util.PowerfilerTestConstants;
import com.ph.powerfiler.util.provider.dto.ProfileDtoProvider;

import java.util.ArrayList;
import java.util.List;

public class FractionProvider {

    public Fraction createFractionWithMonthJAN(){
        Fraction fraction = new Fraction();
        fraction.setFraction(Double.parseDouble(PowerfilerTestConstants.FRACTION_JAN));
        fraction.setMonth(PowerfilerTestConstants.MONTH_JAN);
        return fraction;
    }
    public List<Fraction> createFractions(){
        List<Fraction> fractions = new ArrayList<>();
        Fraction fraction = createFractionWithMonthJAN();
        fractions.add(fraction);
        return fractions;

    }
    public List<Fraction> createFractionsWithValidFractionTotal(){
        List<Fraction> fractions = new ArrayList<>();

        for(int i = 0;i<12;i++) {
            Fraction fraction = createFractionWithMonthJAN();
            fractions.add(fraction);
        }
        return fractions;

    }
}
