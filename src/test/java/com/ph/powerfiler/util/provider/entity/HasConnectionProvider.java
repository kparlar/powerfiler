package com.ph.powerfiler.util.provider.entity;

import com.ph.powerfiler.model.entity.Connection;
import com.ph.powerfiler.model.entity.HasConnection;
import com.ph.powerfiler.model.entity.Profile;
import com.ph.powerfiler.util.PowerfilerTestConstants;

public class HasConnectionProvider {

    public HasConnection createHasConnection(){
        HasConnection hasConnection = new HasConnection();
        ConnectionProvider connectionProvider = new ConnectionProvider();
        Connection connection = connectionProvider.createConnection();
        ProfileProvider profileProvider = new ProfileProvider();
        Profile profile = profileProvider.createProfileWithoutConnections();
        hasConnection.setToConnection(connection);
        hasConnection.setFromProfile(profile);
        hasConnection.setId(PowerfilerTestConstants.ID);
        return hasConnection;
    }
}
