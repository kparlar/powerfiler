package com.ph.powerfiler.util.provider.entity;

import com.ph.powerfiler.model.entity.Connection;
import com.ph.powerfiler.model.entity.Fraction;
import com.ph.powerfiler.model.entity.HasFraction;
import com.ph.powerfiler.util.PowerfilerTestConstants;

public class HasFractionProvider {

    public HasFraction createHasFraction(){
        HasFraction hasFraction = new HasFraction();
        FractionProvider fractionProvider = new FractionProvider();
        Fraction fraction = fractionProvider.createFractionWithMonthJAN();
        ConnectionProvider connectionProvider = new ConnectionProvider();
        Connection connection = connectionProvider.createConnection();
        hasFraction.setToFraction(fraction);
        hasFraction.setFromConnection(connection);
        hasFraction.setId(PowerfilerTestConstants.ID);
        return hasFraction;

    }
}
