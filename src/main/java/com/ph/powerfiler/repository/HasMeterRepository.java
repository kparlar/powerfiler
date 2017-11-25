package com.ph.powerfiler.repository;

import com.ph.powerfiler.model.entity.HasConnection;
import com.ph.powerfiler.model.entity.HasMeter;
import com.ph.powerfiler.model.entity.Meter;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HasMeterRepository extends CrudRepository<HasMeter,String> {



    public void deleteAllByToMeter(Meter meter);
}
