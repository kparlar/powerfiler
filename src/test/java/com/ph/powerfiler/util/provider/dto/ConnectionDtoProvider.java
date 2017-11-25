package com.ph.powerfiler.util.provider.dto;

import com.ph.powerfiler.model.dto.ConnectionDataDto;
import com.ph.powerfiler.model.dto.ConnectionDto;
import com.ph.powerfiler.util.PowerfilerTestConstants;

import java.util.ArrayList;
import java.util.List;

public class ConnectionDtoProvider {

    public ConnectionDto createConnectionDtoWithNoConnectionDataDtos(){
        return new ConnectionDto(PowerfilerTestConstants.CONNECTION_ID_0001, null);
    }
    public ConnectionDto createConnectionDtoWithConnectionDataDtos(){
        ConnectionDataDtoProvider connectionDataDtoProvider = new ConnectionDataDtoProvider();
        List<ConnectionDataDto> connectionDataDtos = connectionDataDtoProvider.createConnectionDataDtos();
        return new ConnectionDto(PowerfilerTestConstants.CONNECTION_ID_0001, connectionDataDtos);
    }

    public ConnectionDto createConnectionDtoWithTwoConnectionDataDtos(){
        ConnectionDataDtoProvider connectionDataDtoProvider = new ConnectionDataDtoProvider();
        List<ConnectionDataDto> connectionDataDtos = connectionDataDtoProvider.createConnectionDataDtosWithTwoData();
        return new ConnectionDto(PowerfilerTestConstants.CONNECTION_ID_0001, connectionDataDtos);
    }


    public List<ConnectionDto> createConnectionDtosWithNoConnectionDataDtos(){
        List<ConnectionDto> connectionDtos = new ArrayList<>();
        ConnectionDto connectionDto = createConnectionDtoWithNoConnectionDataDtos();
        connectionDtos.add(connectionDto);
        return connectionDtos;
    }

    public List<ConnectionDto> createConnectionDtosWithConnectionDataDtos(){
        List<ConnectionDto> connectionDtos = new ArrayList<>();
        ConnectionDto connectionDto = createConnectionDtoWithConnectionDataDtos();
        connectionDtos.add(connectionDto);
        return connectionDtos;
    }
    public List<ConnectionDto> createConnectionDtosWithTwoConnectionDataDtos(){
        List<ConnectionDto> connectionDtos = new ArrayList<>();
        ConnectionDto connectionDto = createConnectionDtoWithTwoConnectionDataDtos();
        connectionDtos.add(connectionDto);
        return connectionDtos;
    }

}
