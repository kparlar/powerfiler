package com.ph.powerfiler.model.dto;

import java.util.HashMap;

public class ProfilerDto {

    private String profile;
    private HashMap<String, ConnectionTreeMapDto> connectionDtos;

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public HashMap<String, ConnectionTreeMapDto> getConnectionDtos() {
        return connectionDtos;
    }

    public void setConnectionDtos(HashMap<String, ConnectionTreeMapDto> connectionDtos) {
        this.connectionDtos = connectionDtos;
    }
}
