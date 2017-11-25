package com.ph.powerfiler.util.provider.dto;

import com.ph.powerfiler.model.dto.ConnectionDto;
import com.ph.powerfiler.model.dto.ProfileDto;
import com.ph.powerfiler.util.PowerfilerTestConstants;

import java.util.ArrayList;
import java.util.List;

public class ProfileDtoProvider {
    public ProfileDto createProfileDto(){

        return  new ProfileDto(PowerfilerTestConstants.PROFILE_A, null);

    }
    public ProfileDto createProfileDtoWithConnectionDtoWithoutConnectionData(){
        ConnectionDtoProvider connectionDtoProvider = new ConnectionDtoProvider();
        List<ConnectionDto>  connectionDtos = connectionDtoProvider.createConnectionDtosWithNoConnectionDataDtos();
        return  new ProfileDto(PowerfilerTestConstants.PROFILE_A, connectionDtos);
    }

    public ProfileDto createProfileDtoWithConnectionDtoWithConnectionData(){
        ConnectionDtoProvider connectionDtoProvider = new ConnectionDtoProvider();
        List<ConnectionDto>  connectionDtos = connectionDtoProvider.createConnectionDtosWithConnectionDataDtos();
        return  new ProfileDto(PowerfilerTestConstants.PROFILE_A, connectionDtos);
    }
    public ProfileDto createProfileDtoWithConnectionDtoWithTwoConnectionData(){
        ConnectionDtoProvider connectionDtoProvider = new ConnectionDtoProvider();
        List<ConnectionDto>  connectionDtos = connectionDtoProvider.createConnectionDtosWithTwoConnectionDataDtos();
        return  new ProfileDto(PowerfilerTestConstants.PROFILE_A, connectionDtos);
    }
    public List<ProfileDto> createProfileDtoWithEmptyConnection(){
        List<ProfileDto> profileDtos = new ArrayList<>();
        ProfileDto profileDto = createProfileDto();
        profileDtos.add(profileDto);
        return profileDtos;
    }
    public List<ProfileDto> createProfileDtoWithConnectionWithoutConnectionData(){
        List<ProfileDto> profileDtos = new ArrayList<>();
        ProfileDto profileDto = createProfileDtoWithConnectionDtoWithoutConnectionData();
        profileDtos.add(profileDto);
        return profileDtos;
    }

    public List<ProfileDto> createProfileDtoWithConnectionWithConnectionData(){
        List<ProfileDto> profileDtos = new ArrayList<>();
        ProfileDto profileDto = createProfileDtoWithConnectionDtoWithConnectionData();
        profileDtos.add(profileDto);
        return profileDtos;
    }
    public List<ProfileDto> createProfileDtoWithConnectionWithTwoConnectionData(){
        List<ProfileDto> profileDtos = new ArrayList<>();
        ProfileDto profileDto = createProfileDtoWithConnectionDtoWithTwoConnectionData();
        profileDtos.add(profileDto);
        return profileDtos;
    }



}
