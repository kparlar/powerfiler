package com.ph.powerfiler.util.provider.entity;

import com.ph.powerfiler.model.dto.ProfileDto;
import com.ph.powerfiler.model.entity.Profile;
import com.ph.powerfiler.util.PowerfilerTestConstants;

public class ProfileProvider {

    public Profile createProfileWithoutConnections(){
        Profile profile = new Profile();
        profile.setName(PowerfilerTestConstants.PROFILE_A);
        profile.setId(PowerfilerTestConstants.ID);
        return profile;
    }
}
