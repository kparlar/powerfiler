package com.ph.powerfiler.model.dto;

import com.ph.powerfiler.model.entity.Profile;

import java.util.List;

public class ProfileDto {

    String profile;
    List<ConnectionDto> connectionDtos;
    public ProfileDto(){

    }
    public ProfileDto(String profile, List<ConnectionDto> connectionDtos){
        this.profile = profile;
        this.connectionDtos = connectionDtos;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public List<ConnectionDto> getConnectionDtos() {
        return connectionDtos;
    }

    public void setConnectionDtos(List<ConnectionDto> connectionDtos) {
        this.connectionDtos = connectionDtos;
    }
}
