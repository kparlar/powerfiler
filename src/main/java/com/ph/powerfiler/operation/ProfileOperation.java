package com.ph.powerfiler.operation;

import com.ph.powerfiler.model.dto.ConnectionTreeMapDto;
import com.ph.powerfiler.model.dto.FractionDto;
import com.ph.powerfiler.model.dto.MeterDto;
import com.ph.powerfiler.model.entity.Connection;
import com.ph.powerfiler.model.entity.HasConnection;
import com.ph.powerfiler.model.entity.Profile;
import com.ph.powerfiler.repository.ProfileRepository;
import com.ph.powerfiler.util.PowerfilerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class ProfileOperation {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ConnectionOperation connectionOperation;
    @Autowired
    private HasConnectionOperation hasConnectionOperation;
    @Autowired
    private MeterOperation meterOperation;
    @Autowired
    private FractionOperation fractionOperation;

    public void saveValidProfiles(HashMap<String, HashMap<String, ConnectionTreeMapDto>> profileConnectionsMap, List<String[]> validProfileConnections){

        for(String[] profileConnectionArr: validProfileConnections){
            HashMap<String, ConnectionTreeMapDto> connectionTreeMapDtoHashMap = profileConnectionsMap.get(profileConnectionArr[PowerfilerConstants.INDEX_PROFILE]);
            ConnectionTreeMapDto connectionTreeMapDto = connectionTreeMapDtoHashMap.get(profileConnectionArr[PowerfilerConstants.INDEX_CONNECTION]);
            Profile profile = save(profileConnectionArr[PowerfilerConstants.INDEX_PROFILE]);
            Connection connection = connectionOperation.save(connectionTreeMapDto.getConnectionId());
            HasConnection hasConnection = hasConnectionOperation.save(profile, connection);
            meterOperation.saveAndRelateWithConnection(connection, connectionTreeMapDto.getMeterDtos().values().toArray(new MeterDto[0]));
            fractionOperation.saveAndRelateWithConnection(connection, connectionTreeMapDto.getFractionDtos().values().toArray(new FractionDto[0]));
        }
    }

    private Profile save(String profileStr) {
        Profile profile = new Profile();
        profile.setName(profileStr);
        return profileRepository.save(profile);
    }

    public Profile findOne(String connectionId){
        return profileRepository.getProfile(connectionId);
    }
}
