package com.ph.powerfiler.repository;

import com.ph.powerfiler.model.entity.Fraction;
import com.ph.powerfiler.model.entity.HasFraction;
import org.springframework.data.repository.CrudRepository;

public interface HasFractionRepository extends CrudRepository<HasFraction,String> {


    void deleteAllByToFraction(Fraction fraction);
}
