package com.ph.powerfiler.model.dto;

import java.util.TreeMap;

public class ConnectionTreeMapDto {

    private String connectionId;
    private TreeMap<Integer, MeterDto> meterDtos = new TreeMap<>();
    private TreeMap<Integer, FractionDto> fractionDtos = new TreeMap<>();

    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public TreeMap<Integer, MeterDto> getMeterDtos() {
        return meterDtos;
    }

    public void setMeterDtos(TreeMap<Integer, MeterDto> meterDtos) {
        this.meterDtos = meterDtos;
    }

    public TreeMap<Integer, FractionDto> getFractionDtos() {
        return fractionDtos;
    }

    public void setFractionDtos(TreeMap<Integer, FractionDto> fractionDtos) {
        this.fractionDtos = fractionDtos;
    }
}
