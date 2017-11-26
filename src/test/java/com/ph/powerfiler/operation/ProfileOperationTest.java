package com.ph.powerfiler.operation;



import com.ph.powerfiler.model.dto.ConnectionTreeMapDto;
import com.ph.powerfiler.model.dto.FractionDto;
import com.ph.powerfiler.model.dto.MeterDto;
import com.ph.powerfiler.model.entity.Connection;
import com.ph.powerfiler.model.entity.Fraction;
import com.ph.powerfiler.model.entity.HasConnection;
import com.ph.powerfiler.model.entity.Profile;
import com.ph.powerfiler.repository.ProfileRepository;
import com.ph.powerfiler.util.PowerfilerTestConstants;
import com.ph.powerfiler.util.provider.entity.ConnectionProvider;
import com.ph.powerfiler.util.provider.entity.HasConnectionProvider;
import com.ph.powerfiler.util.provider.entity.ProfileProvider;
import com.ph.powerfiler.util.provider.object.ListProvider;
import com.ph.powerfiler.util.provider.object.ProfileConnectionMapProvider;
import javafx.beans.binding.Bindings;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.List;

public class ProfileOperationTest {

    @InjectMocks
    private ProfileOperation profileOperation;

    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private ConnectionOperation connectionOperation;
    @Mock
    private HasConnectionOperation hasConnectionOperation;
    @Mock
    private MeterOperation meterOperation;
    @Mock
    private FractionOperation fractionOperation;

    private ProfileConnectionMapProvider profileConnectionMapProvider;
    private ConnectionProvider connectionProvider;
    private ProfileProvider profileProvider;
    private HasConnectionProvider hasConnectionProvider;
    private ListProvider listProvider;


    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        profileConnectionMapProvider = new ProfileConnectionMapProvider();
        connectionProvider = new ConnectionProvider();
        hasConnectionProvider = new HasConnectionProvider();
        listProvider = new ListProvider();
        profileProvider = new ProfileProvider();
    }

    @Test
    public void saveProfileGivenValidDataWhenSavingDatasWithRelationThenVoid(){
        List<String[]> validProfileConnections = listProvider.createValidProfileConnections();
        HashMap<String, HashMap<String, ConnectionTreeMapDto>> profileConnectionsMap = profileConnectionMapProvider.createValidProfileConnectionMap();
        Connection connection = connectionProvider.createConnection();
        Profile profile = profileProvider.createProfileWithoutConnections();
        HasConnection hasConnection = hasConnectionProvider.createHasConnection();
        when(profileRepository.save(any(Profile.class))).thenReturn(profile);
        when(connectionOperation.save(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(connection);
        when(hasConnectionOperation.save(any(Profile.class), any(Connection.class))).thenReturn(hasConnection);
        doNothing().when(meterOperation).saveAndRelateWithConnection(any(Connection.class), any(MeterDto[].class));
        doNothing().when(fractionOperation).saveAndRelateWithConnection(any(Connection.class), any(FractionDto[].class));
        profileOperation.saveValidProfiles(profileConnectionsMap, validProfileConnections);
        verify(fractionOperation, atLeastOnce()).saveAndRelateWithConnection(any(Connection.class), any(FractionDto[].class));
    }

    @Test
    public void findOneGivenValidDataWhenFindingProfileThenProfile(){
        Profile profile = profileProvider.createProfileWithoutConnections();
        when(profileRepository.getProfile(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(profile);
        Profile result = profileOperation.findOne(PowerfilerTestConstants.CONNECTION_ID_0001);
        Assert.assertTrue("Not expected Profile is returned", profile.getId().equalsIgnoreCase(result.getId()));
    }
}
