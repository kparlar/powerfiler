package com.ph.powerfiler.repository;

import com.ph.powerfiler.model.entity.Fraction;
import com.ph.powerfiler.model.entity.Meter;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface FractionRepository extends CrudRepository<Fraction,String> {

    @Query("Select fraction from Fraction fraction, HasFraction hf where " +
            " fraction.id = hf.toFraction.id and hf.fromConnection.connectionId = ?1 and " +
            " fraction.month = ?2 ")
    public Fraction getFraction(String connectionId, String month);
}
