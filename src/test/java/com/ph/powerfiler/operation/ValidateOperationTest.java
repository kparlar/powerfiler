package com.ph.powerfiler.operation;

import com.ph.powerfiler.model.dto.ConnectionTreeMapDto;
import com.ph.powerfiler.model.dto.ValidationDto;
import com.ph.powerfiler.util.provider.object.ProfileConnectionMapProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ValidateOperationTest {

    @InjectMocks
    private ValidateOperation validateOperation;

    private ProfileConnectionMapProvider profileConnectionMapProvider;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        profileConnectionMapProvider = new ProfileConnectionMapProvider();
    }

    @Test
    public void validationGivenNotValidDataWhenNoDataValidatedThenEmptyValidProfile(){
        List<String[]> validProfileConnections = new ArrayList<>();
        List<ValidationDto> validationDtos = new ArrayList<>();
        HashMap<String, HashMap<String, ConnectionTreeMapDto>> profileConnectionsMap = profileConnectionMapProvider.createNotValidProfileConnectionMap();
        validateOperation.validation(profileConnectionsMap, validProfileConnections, validationDtos);
        Assert.assertTrue("ValidProfileConnections' size has to be zero", validProfileConnections.size() == 0);
    }

    @Test
    public void validationGivenValidDataWhenOneDataValidatedThenOneValidProfile(){
        List<String[]> validProfileConnections = new ArrayList<>();
        List<ValidationDto> validationDtos = new ArrayList<>();
        HashMap<String, HashMap<String, ConnectionTreeMapDto>> profileConnectionsMap = profileConnectionMapProvider.createValidProfileConnectionMap();
        validateOperation.validation(profileConnectionsMap, validProfileConnections, validationDtos);
        Assert.assertTrue("ValidProfileConnections' size has to be zero", validProfileConnections.size() == 1);
    }
}
