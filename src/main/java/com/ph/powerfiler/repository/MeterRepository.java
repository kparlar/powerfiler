package com.ph.powerfiler.repository;

import com.ph.powerfiler.model.entity.Meter;
import com.ph.powerfiler.model.entity.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MeterRepository extends CrudRepository<Meter,String> {

    @Query("Select meter from Meter meter, HasMeter hm where " +
            " meter.id = hm.toMeter.id and hm.fromConnection.connectionId = ?1 and " +
            " meter.month = ?2 ")
    public Meter getMeter(String connectionId, String month);


}
