package com.ph.powerfiler.model.dto;

public class FractionValueDto {
    private String month;
    private String fraction;
    public FractionValueDto(String month, String fraction){
        this.month = month;
        this.fraction = fraction;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getFraction() {
        return fraction;
    }

    public void setFraction(String fraction) {
        this.fraction = fraction;
    }
}
