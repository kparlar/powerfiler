package com.ph.powerfiler.util.provider.entity;

import com.ph.powerfiler.model.entity.Connection;
import com.ph.powerfiler.model.entity.Fraction;
import com.ph.powerfiler.model.entity.Meter;
import com.ph.powerfiler.util.PowerfilerTestConstants;

import java.util.List;

public class ConnectionProvider {

    public Connection createConnection(){
        Connection connection = new Connection();
        connection.setConnectionId(PowerfilerTestConstants.CONNECTION_ID_0001);
        return connection;
    }
    public Connection createConnectionWithMeters(){
        Connection connection = new Connection();
        connection.setConnectionId(PowerfilerTestConstants.CONNECTION_ID_0001);
        MeterProvider meterProvider = new MeterProvider();
        List<Meter> meters = meterProvider.createMeters();
        connection.setMeters(meters);
        return connection;
    }
    public Connection createConnectionWithFractions(){
        Connection connection = new Connection();
        connection.setConnectionId(PowerfilerTestConstants.CONNECTION_ID_0001);
        FractionProvider fractionProvider = new FractionProvider();
        List<Fraction> fractions =fractionProvider.createFractions();
        connection.setFractions(fractions);
        return connection;

    }
}
