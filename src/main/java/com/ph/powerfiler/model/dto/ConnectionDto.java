package com.ph.powerfiler.model.dto;

import java.util.List;

public class ConnectionDto {

    String connectionId;
    List<ConnectionDataDto> connectionDataDtos;

    public ConnectionDto(String connectionId, List<ConnectionDataDto> connectionDataDtos){
        this.connectionId = connectionId;
        this.connectionDataDtos = connectionDataDtos;
    }

    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public List<ConnectionDataDto> getConnectionDataDtos() {
        return connectionDataDtos;
    }

    public void setConnectionDataDtos(List<ConnectionDataDto> connectionDataDtos) {
        this.connectionDataDtos = connectionDataDtos;
    }
}
