package com.ph.powerfiler.repository;

import com.ph.powerfiler.model.entity.HasConnection;
import com.ph.powerfiler.model.entity.Profile;
import org.springframework.data.repository.CrudRepository;

public interface HasConnectionRepository extends CrudRepository<HasConnection,String> {
}
