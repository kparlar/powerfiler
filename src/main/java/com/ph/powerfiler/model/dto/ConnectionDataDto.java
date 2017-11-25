package com.ph.powerfiler.model.dto;

public class ConnectionDataDto extends MeterValueDto{


    String fraction;

    public ConnectionDataDto(String month, String meterReading, String fraction) {
        super(month, meterReading);
        this.fraction = fraction;
    }

    public String getFraction() {
        return fraction;
    }

    public void setFraction(String fraction) {
        this.fraction = fraction;
    }
}
