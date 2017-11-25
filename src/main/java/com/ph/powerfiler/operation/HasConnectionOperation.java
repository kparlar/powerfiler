package com.ph.powerfiler.operation;

import com.ph.powerfiler.model.entity.Connection;
import com.ph.powerfiler.model.entity.HasConnection;
import com.ph.powerfiler.model.entity.Profile;
import com.ph.powerfiler.repository.HasConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HasConnectionOperation {
    @Autowired
    private HasConnectionRepository hasConnectionRepository;




    public HasConnection save(Profile profile, Connection connection){
        HasConnection hasConnection = new HasConnection();
        hasConnection.setFromProfile(profile);
        hasConnection.setToConnection(connection);
        return hasConnectionRepository.save(hasConnection);
    }
}
