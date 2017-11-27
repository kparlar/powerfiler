package com.ph.powerfiler.model.dto;

public class MeterValueDto {

    private String month;
    private String meterReading;
    public MeterValueDto(){

    }
    public MeterValueDto(String month, String meterReading){
        this.month = month;
        this.meterReading = meterReading;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getMeterReading() {
        return meterReading;
    }

    public void setMeterReading(String meterReading) {
        this.meterReading = meterReading;
    }
}
