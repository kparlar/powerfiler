package com.ph.powerfiler.util.provider.dto;

import com.ph.powerfiler.model.dto.ConnectionDataDto;
import com.ph.powerfiler.util.PowerfilerTestConstants;

import java.util.ArrayList;
import java.util.List;

public class ConnectionDataDtoProvider {


    public ConnectionDataDto createConnectionDataDtoJAN(){
        return new ConnectionDataDto(PowerfilerTestConstants.MONTH_JAN, PowerfilerTestConstants.METER_READING_JAN,  PowerfilerTestConstants.FRACTION_JAN);
    }
    public ConnectionDataDto createConnectionDataDtoFEB(){
        return new ConnectionDataDto(PowerfilerTestConstants.MONTH_FEB, PowerfilerTestConstants.METER_READING_FEB,  PowerfilerTestConstants.FRACTION_FEB);
    }
    public List<ConnectionDataDto> createConnectionDataDtos(){
        List<ConnectionDataDto> connectionDataDtos = new ArrayList<>();
        ConnectionDataDto connectionDataDto = createConnectionDataDtoJAN();
        connectionDataDtos.add(connectionDataDto);
        return connectionDataDtos;
    }
    public List<ConnectionDataDto> createConnectionDataDtosWithTwoData(){
        List<ConnectionDataDto> connectionDataDtos = new ArrayList<>();
        ConnectionDataDto connectionDataDtoJAN = createConnectionDataDtoJAN();
        ConnectionDataDto connectionDataDtoFEB = createConnectionDataDtoFEB();
        connectionDataDtos.add(connectionDataDtoJAN);
        connectionDataDtos.add(connectionDataDtoFEB);
        return connectionDataDtos;
    }

}
