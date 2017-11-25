package com.ph.powerfiler.operation;

import com.ph.powerfiler.model.entity.Fraction;
import com.ph.powerfiler.model.entity.HasConnection;
import com.ph.powerfiler.model.entity.HasFraction;
import com.ph.powerfiler.repository.HasFractionRepository;
import com.ph.powerfiler.util.provider.entity.FractionProvider;
import com.ph.powerfiler.util.provider.entity.HasFractionProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class HasFractionOperationTest {

    @InjectMocks
    private HasFractionOperation hasFractionOperation;
    @Mock
    private HasFractionRepository hasFractionRepository;

    private HasFractionProvider hasFractionProvider;
    private FractionProvider fractionProvider;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        hasFractionProvider = new HasFractionProvider();
        fractionProvider = new FractionProvider();
    }

    @Test
    public void saveGivenValidDataWhenSavingThenHasFraction(){
        HasFraction hasFraction = hasFractionProvider.createHasFraction();
        when(hasFractionRepository.save(any(HasFraction.class))).thenReturn(hasFraction);
        HasFraction result = hasFractionOperation.save(hasFraction.getFromConnection(), hasFraction.getToFraction());
        Assert.assertTrue("Not same HasFraction is returned", hasFraction.getId().equalsIgnoreCase(result.getId()));
    }
    @Test
    public void deleteAllGivenValidFractionWhenDeletingThenVoid(){
        Fraction fraction = fractionProvider.createFractionWithMonthJAN();
        doNothing().when(hasFractionRepository).deleteAllByToFraction(any(Fraction.class));
        hasFractionOperation.deleteAll(fraction);
        verify(hasFractionRepository, atMost(1)).deleteAllByToFraction(any(Fraction.class));
    }
}
