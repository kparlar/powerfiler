package com.ph.powerfiler.util.provider.entity;

import com.ph.powerfiler.model.entity.Connection;
import com.ph.powerfiler.model.entity.HasMeter;
import com.ph.powerfiler.model.entity.Meter;
import com.ph.powerfiler.util.PowerfilerTestConstants;

public class HasMeterProvider {

    public HasMeter createHasMeter(){
        HasMeter hasMeter = new HasMeter();
        MeterProvider meterProvider = new MeterProvider();
        Meter meter = meterProvider.createMeterWithMonthJAN();
        ConnectionProvider connectionProvider = new ConnectionProvider();
        Connection connection = connectionProvider.createConnection();
        hasMeter.setToMeter(meter);
        hasMeter.setFromConnection(connection);
        hasMeter.setId(PowerfilerTestConstants.ID);
        return hasMeter;
    }
}
