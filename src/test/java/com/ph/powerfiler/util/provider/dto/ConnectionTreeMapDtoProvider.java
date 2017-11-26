package com.ph.powerfiler.util.provider.dto;

import com.ph.powerfiler.model.dto.ConnectionTreeMapDto;
import com.ph.powerfiler.model.dto.FractionDto;
import com.ph.powerfiler.model.dto.MeterDto;
import com.ph.powerfiler.util.PowerfilerTestConstants;

import java.util.TreeMap;


public class ConnectionTreeMapDtoProvider {

    public ConnectionTreeMapDto createConnectionTreeMapDto(){
        ConnectionTreeMapDto connectionTreeMapDto = new ConnectionTreeMapDto();
        connectionTreeMapDto.setConnectionId(PowerfilerTestConstants.CONNECTION_ID_0001);
        MeterDtoProvider meterDtoProvider = new MeterDtoProvider();
        TreeMap<Integer, MeterDto> treeMapMeterDto = meterDtoProvider.createTreeMapMeterDto();
        connectionTreeMapDto.setMeterDtos(treeMapMeterDto);
        FractionDtoProvider fractionDtoProvider = new FractionDtoProvider();
        TreeMap<Integer, FractionDto> treeMapFractionDto = fractionDtoProvider.createTreeMapFractionDto();
        connectionTreeMapDto.setFractionDtos(treeMapFractionDto);
        return connectionTreeMapDto;
    }
}
