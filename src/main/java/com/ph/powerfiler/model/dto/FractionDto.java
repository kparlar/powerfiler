package com.ph.powerfiler.model.dto;

public class FractionDto extends FractionValueDto{
    private String profile;
    public FractionDto(){
        super();
    }
    public FractionDto(String profile, String month, String fraction){
        super(month, fraction);
        this.profile = profile;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

}
