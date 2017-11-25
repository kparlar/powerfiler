package com.ph.powerfiler.model.dto;

public class FractionWithConnectionDto extends FractionDto{
    String connectionId;

    public FractionWithConnectionDto(String profile, String connectionId, String month, String fraction) {
        super(profile, month, fraction);
        this.connectionId = connectionId;
    }

    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }
}
