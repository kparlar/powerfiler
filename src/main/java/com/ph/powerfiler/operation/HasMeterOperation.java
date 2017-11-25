package com.ph.powerfiler.operation;

import com.ph.powerfiler.model.entity.Connection;
import com.ph.powerfiler.model.entity.HasMeter;
import com.ph.powerfiler.model.entity.Meter;
import com.ph.powerfiler.repository.HasMeterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HasMeterOperation {
    @Autowired
    private HasMeterRepository hasMeterRepository;

    public HasMeter save(Connection connection, Meter meter){
        HasMeter hasMeter = new HasMeter();
        hasMeter.setFromConnection(connection);
        hasMeter.setToMeter(meter);
        return hasMeterRepository.save(hasMeter);
    }
    public void deleteAll(Meter meter){
        hasMeterRepository.deleteAllByToMeter(meter);
    }
}
