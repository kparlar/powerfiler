package com.ph.powerfiler.repository;

import com.ph.powerfiler.model.entity.Connection;
import org.springframework.data.repository.CrudRepository;

public interface ConnectionRepository extends CrudRepository<Connection,String> {

    public Connection getByConnectionId(String connectionId);
}
