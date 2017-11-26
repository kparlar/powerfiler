package com.ph.powerfiler.util.provider.object;

import com.ph.powerfiler.util.PowerfilerTestConstants;

import java.util.ArrayList;
import java.util.List;

public class ListProvider {

    public List<String[]> createValidProfileConnections(){
        List<String[]> validProfileConnections = new ArrayList<>();
        String[] validProfileConnection = {PowerfilerTestConstants.PROFILE_A, PowerfilerTestConstants.CONNECTION_ID_0001};
        validProfileConnections.add(validProfileConnection);
        return validProfileConnections;
    }
}
