package com.ph.powerfiler.model.dto;

import java.util.List;

public class ConnectionsDto {

    private List<MeterDto> meterDtos;
    private List<FractionDto> fractionDtos;
    public ConnectionsDto(){

    }
    public ConnectionsDto(List<MeterDto> meterDtos, List<FractionDto> fractionDtos){
        this.meterDtos = meterDtos;
        this.fractionDtos = fractionDtos;
    }

    public List<MeterDto> getMeterDtos() {
        return meterDtos;
    }

    public void setMeterDtos(List<MeterDto> meterDtos) {
        this.meterDtos = meterDtos;
    }

    public List<FractionDto> getFractionDtos() {
        return fractionDtos;
    }

    public void setFractionDtos(List<FractionDto> fractionDtos) {
        this.fractionDtos = fractionDtos;
    }
}
