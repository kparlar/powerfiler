package com.ph.powerfiler.operation;

import com.ph.powerfiler.model.entity.HasConnection;
import com.ph.powerfiler.repository.HasConnectionRepository;
import com.ph.powerfiler.util.provider.entity.HasConnectionProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

public class HasConnectionOperationTest {

    @InjectMocks
    private HasConnectionOperation hasConnectionOperation;

    @Mock
    private HasConnectionRepository hasConnectionRepository;

    private HasConnectionProvider hasConnectionProvider;


    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        hasConnectionProvider = new HasConnectionProvider();
    }

    @Test
    public void saveGivenValidDataWhenSavingRelationThenHasConection(){
        HasConnection hasConnection = hasConnectionProvider.createHasConnection();
        when(hasConnectionRepository.save(any(HasConnection.class))).thenReturn(hasConnection);
        HasConnection result = hasConnectionOperation.save(hasConnection.getFromProfile(), hasConnection.getToConnection());
        Assert.assertTrue("Not same HasConnection is returned", hasConnection.getId().equalsIgnoreCase(result.getId()));
    }



}
