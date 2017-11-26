package com.ph.powerfiler.operation;

import com.ph.powerfiler.model.dto.FractionDto;
import com.ph.powerfiler.model.dto.ValidationDto;
import com.ph.powerfiler.model.entity.Connection;
import com.ph.powerfiler.model.entity.Fraction;
import com.ph.powerfiler.repository.FractionRepository;
import com.ph.powerfiler.util.MessageCodeConstants;
import com.ph.powerfiler.util.PowerfilerTestConstants;
import com.ph.powerfiler.util.provider.dto.FractionDtoProvider;
import com.ph.powerfiler.util.provider.entity.ConnectionProvider;
import com.ph.powerfiler.util.provider.entity.FractionProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;


public class FractionOperationTest {

    @InjectMocks
    private FractionOperation fractionOperation;

    @Mock
    private FractionRepository fractionRepository;
    @Mock
    private HasFractionOperation hasFractionOperation;

    private ConnectionProvider connectionProvider;
    private FractionDtoProvider fractionDtoProvider;
    private FractionProvider fractionProvider;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        connectionProvider = new ConnectionProvider();
        fractionDtoProvider = new FractionDtoProvider();
        fractionProvider = new FractionProvider();
    }

    @Test
    public void saveAndRelateWithConnectionGivenValidDataWhenSavingThenVoid() {
        Connection connection = connectionProvider.createConnection();
        FractionDto[] fractionDtos = fractionDtoProvider.createFractionDtoArray();
        when(fractionRepository.save(any(Fraction.class))).thenReturn(null);
        when(hasFractionOperation.save(any(Connection.class), any(Fraction.class))).thenReturn(null);
        fractionOperation.saveAndRelateWithConnection(connection, fractionDtos);
        verify(hasFractionOperation, atLeastOnce()).save(any(Connection.class), any(Fraction.class));
    }

    @Test
    public void getFractionGivenValidDataWhenGettingFractionThenFraction() {
        Fraction fraction = fractionProvider.createFractionWithMonthJAN();
        when(fractionRepository.getFraction(eq(PowerfilerTestConstants.CONNECTION_ID_0001), eq(PowerfilerTestConstants.MONTH_JAN))).thenReturn(fraction);
        Fraction result = fractionOperation.getFraction(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.MONTH_JAN);
        Assert.assertTrue("Fraction is not expected", fraction.getMonth().equalsIgnoreCase(result.getMonth()) &&
                fraction.getFraction().equals(result.getFraction()));
    }

    @Test
    public void deleteGivenFractionIdWhenNoDataFoundThenFalse() {
        when(fractionRepository.findOne(eq(PowerfilerTestConstants.ID))).thenReturn(null);
        boolean isDeleted = fractionOperation.delete(PowerfilerTestConstants.ID);
        Assert.assertTrue("False is Expected", !isDeleted);
    }

    @Test
    public void deleteGivenFractionIdWhenFractionFoundThenTrue() {
        Fraction fraction = fractionProvider.createFractionWithMonthJAN();
        when(fractionRepository.findOne(eq(PowerfilerTestConstants.ID))).thenReturn(fraction);
        doNothing().when(hasFractionOperation).deleteAll(any(Fraction.class));
        doNothing().when(fractionRepository).delete(any(Fraction.class));
        boolean isDeleted = fractionOperation.delete(PowerfilerTestConstants.ID);
        Assert.assertTrue("True is Expected", isDeleted);
    }

    @Test
    public void validateFractionGivenNotValidDataWhenValidatingThenValidationListWithError() {
        List<ValidationDto> validationDtos = fractionOperation.validateFraction(PowerfilerTestConstants.CONNECTION_ID_0001, Double.parseDouble(PowerfilerTestConstants.FRACTION_JAN), new ArrayList<>(), PowerfilerTestConstants.PROFILE_A);
        Assert.assertTrue("An Error is Expected", validationDtos.get(0).getCode().equalsIgnoreCase(MessageCodeConstants.TOTAL_FRACTION_NOT_ONE_EXCEPTION_CODE));
    }

    @Test
    public void validateFractionGivenValidDataWhenValidatingThenEmptyValidationList() {
        List<Fraction> fractions = fractionProvider.createFractionsWithValidFractionTotal();
        List<ValidationDto> validationDtos = fractionOperation.validateFraction(PowerfilerTestConstants.CONNECTION_ID_0001, Double.parseDouble(PowerfilerTestConstants.FRACTION_JAN), fractions, PowerfilerTestConstants.PROFILE_A);
        Assert.assertTrue("No Error is Expected", validationDtos.size() == 0);
    }

    @Test
    public void getFractionGivenValidIdWhenGettingFractionThenFraction() {
        Fraction fraction = fractionProvider.createFractionWithMonthJAN();
        fraction.setId(PowerfilerTestConstants.ID);
        when(fractionRepository.findOne(eq(PowerfilerTestConstants.ID))).thenReturn(fraction);
        Fraction result = fractionOperation.getFraction(PowerfilerTestConstants.ID);
        Assert.assertTrue("Fraction is not same", fraction.getId().equalsIgnoreCase(result.getId()));
    }

    @Test
    public void updateGivenFractionWhenUpdatingThenUpdatedFraction(){
        Fraction fraction = fractionProvider.createFractionWithMonthJAN();
        fraction.setId(PowerfilerTestConstants.ID);
        when(fractionRepository.save(any(Fraction.class))).thenReturn(fraction);
        Fraction result = fractionOperation.update(fraction);
        Assert.assertTrue("Fraction is not same", fraction.getId().equalsIgnoreCase(result.getId()));
    }

}