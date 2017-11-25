package com.ph.powerfiler.model.dto;

public class MeterDto extends MeterValueDto{

    private String connectionId;
    private String profile;

    public MeterDto(String connectionId, String profile, String month, String meterReading){
        super(month, meterReading);
        this.connectionId = connectionId;
        this.profile = profile;
    }


    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

}
