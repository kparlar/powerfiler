package com.ph.powerfiler.repository;

import com.ph.powerfiler.model.entity.HasMeter;
import com.ph.powerfiler.model.entity.Meter;
import org.springframework.data.repository.CrudRepository;

public interface HasMeterRepository extends CrudRepository<HasMeter,String> {



    public void deleteAllByToMeter(Meter meter);
}
