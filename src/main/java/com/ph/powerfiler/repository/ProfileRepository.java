package com.ph.powerfiler.repository;

import com.ph.powerfiler.model.entity.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile,String> {
    @Query("Select profile from Profile profile, HasConnection hc, Connection con where " +
            " profile.id = hc.fromProfile.id and hc.toConnection.id = con.id and " +
            " con.connectionId = ?1 ")
    public Profile getProfile(String connectionId);
}
