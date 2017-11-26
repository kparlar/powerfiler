package com.ph.powerfiler.util.provider.dto;

import com.ph.powerfiler.model.dto.ConnectionTreeMapDto;
import com.ph.powerfiler.model.dto.FractionDto;
import com.ph.powerfiler.model.dto.MeterDto;
import com.ph.powerfiler.util.PowerfilerTestConstants;

import java.util.TreeMap;


public class ConnectionTreeMapDtoProvider {

    public ConnectionTreeMapDto createValidConnectionTreeMapDto(){
        ConnectionTreeMapDto connectionTreeMapDto = new ConnectionTreeMapDto();
        connectionTreeMapDto.setConnectionId(PowerfilerTestConstants.CONNECTION_ID_0001);
        MeterDtoProvider meterDtoProvider = new MeterDtoProvider();
        TreeMap<Integer, MeterDto> treeMapMeterDto = meterDtoProvider.createValidTreeMapMeterDto();
        connectionTreeMapDto.setMeterDtos(treeMapMeterDto);
        FractionDtoProvider fractionDtoProvider = new FractionDtoProvider();
        TreeMap<Integer, FractionDto> treeMapFractionDto = fractionDtoProvider.createValidTreeMapFractionDto();
        connectionTreeMapDto.setFractionDtos(treeMapFractionDto);
        return connectionTreeMapDto;
    }

    public ConnectionTreeMapDto createNotValidConnectionTreeMapDto(){
        ConnectionTreeMapDto connectionTreeMapDto = new ConnectionTreeMapDto();
        connectionTreeMapDto.setConnectionId(PowerfilerTestConstants.CONNECTION_ID_0001);
        MeterDtoProvider meterDtoProvider = new MeterDtoProvider();
        TreeMap<Integer, MeterDto> treeMapMeterDto = meterDtoProvider.createNotValidTreeMapMeterDto();
        connectionTreeMapDto.setMeterDtos(treeMapMeterDto);
        FractionDtoProvider fractionDtoProvider = new FractionDtoProvider();
        TreeMap<Integer, FractionDto> treeMapFractionDto = fractionDtoProvider.createNotValidTreeMapFractionDto();
        connectionTreeMapDto.setFractionDtos(treeMapFractionDto);
        return connectionTreeMapDto;
    }
}
