package com.ph.powerfiler.util.provider.object;

import com.ph.powerfiler.model.dto.ConnectionTreeMapDto;
import com.ph.powerfiler.util.PowerfilerTestConstants;
import com.ph.powerfiler.util.provider.dto.ConnectionTreeMapDtoProvider;

import java.util.HashMap;

public class ProfileConnectionMapProvider{

    public HashMap<String, HashMap<String, ConnectionTreeMapDto>> createValidProfileConnectionMap(){
        HashMap<String, HashMap<String, ConnectionTreeMapDto>>  profileConnectionMap = new HashMap<>();
        HashMap<String, ConnectionTreeMapDto> connectionTreeMap = new HashMap<>();
        ConnectionTreeMapDtoProvider connectionTreeMapDtoProvider = new ConnectionTreeMapDtoProvider();
        ConnectionTreeMapDto connectionTreeMapDto = connectionTreeMapDtoProvider.createValidConnectionTreeMapDto();
        connectionTreeMap.put(PowerfilerTestConstants.CONNECTION_ID_0001, connectionTreeMapDto);
        profileConnectionMap.put(PowerfilerTestConstants.PROFILE_A, connectionTreeMap);
        return profileConnectionMap;
    }
    public HashMap<String, HashMap<String, ConnectionTreeMapDto>> createNotValidProfileConnectionMap(){
        HashMap<String, HashMap<String, ConnectionTreeMapDto>>  profileConnectionMap = new HashMap<>();
        HashMap<String, ConnectionTreeMapDto> connectionTreeMap = new HashMap<>();
        ConnectionTreeMapDtoProvider connectionTreeMapDtoProvider = new ConnectionTreeMapDtoProvider();
        ConnectionTreeMapDto connectionTreeMapDto = connectionTreeMapDtoProvider.createNotValidConnectionTreeMapDto();
        connectionTreeMap.put(PowerfilerTestConstants.CONNECTION_ID_0001, connectionTreeMapDto);
        profileConnectionMap.put(PowerfilerTestConstants.PROFILE_A, connectionTreeMap);
        return profileConnectionMap;
    }
}
