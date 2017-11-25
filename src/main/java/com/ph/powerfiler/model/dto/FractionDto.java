package com.ph.powerfiler.model.dto;

public class FractionDto extends FractionValueDto{
    private String profile;

    public FractionDto(String profile, String month, String fraction){
        this.profile = profile;
        this.setFraction(fraction);
        this.setMonth(month);
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

}
