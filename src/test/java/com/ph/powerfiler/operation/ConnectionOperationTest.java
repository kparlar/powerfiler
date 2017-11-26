package com.ph.powerfiler.operation;

import com.ph.powerfiler.exception.ExceptionMessage;
import com.ph.powerfiler.util.MessageCodeConstants;
import com.ph.powerfiler.exception.PowerfilerException;
import com.ph.powerfiler.model.dto.*;
import com.ph.powerfiler.model.entity.Connection;
import com.ph.powerfiler.model.entity.Fraction;
import com.ph.powerfiler.model.entity.Meter;
import com.ph.powerfiler.model.entity.Profile;
import com.ph.powerfiler.repository.ConnectionRepository;
import com.ph.powerfiler.util.PowerfilerTestConstants;
import com.ph.powerfiler.util.PowerfilerUtil;
import com.ph.powerfiler.util.provider.dto.*;
import com.ph.powerfiler.util.provider.entity.ConnectionProvider;
import com.ph.powerfiler.util.provider.entity.FractionProvider;
import com.ph.powerfiler.util.provider.entity.MeterProvider;
import com.ph.powerfiler.util.provider.entity.ProfileProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

public class ConnectionOperationTest {

    @InjectMocks
    private ConnectionOperation connectionOperation;

    @Mock
    private ValidateOperation validateOperation;
    @Mock
    private ProfileOperation profileOperation;
    @Mock
    private PowerfilerUtil powerfilerUtil;
    @Mock
    private ConnectionRepository connectionRepository;
    @Mock
    private MeterOperation meterOperation;
    @Mock
    private FractionOperation fractionOperation;

    private ProfileDtoProvider profileDtoProvider;
    private ConnectionsDtoProvider connectionsDtoProvider;
    private ConnectionProvider connectionProvider;
    private MeterProvider meterProvider;
    private MeterValueDtoProvider meterValueDtoProvider;
    private ValidationDtoProvider validationDtoProvider;
    private ProfileProvider profileProvider;
    private FractionValueDtoProvider fractionValueDtoProvider;
    private FractionProvider fractionProvider;


    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        profileDtoProvider = new ProfileDtoProvider();
        connectionsDtoProvider = new ConnectionsDtoProvider();
        connectionProvider = new ConnectionProvider();
        meterProvider = new MeterProvider();
        meterValueDtoProvider = new MeterValueDtoProvider();
        validationDtoProvider = new ValidationDtoProvider();
        profileProvider = new ProfileProvider();
        fractionValueDtoProvider = new FractionValueDtoProvider();
        fractionProvider = new FractionProvider();
    }

    @Test
    public void saveConnectionsGivenNullListWhenSaveConnectionsThenExceptionMessage(){
        ExceptionMessage exceptionMessage = connectionOperation.saveConnections(null);
        Assert.assertTrue("Exception message has to be equal", exceptionMessage.getErrors().get(0).equalsIgnoreCase(String.format(MessageCodeConstants.DATA_SENT_EMPTY_ERROR_MESSAGE,"Profile")));
    }
    @Test
    public void saveConnectionsGivenEmptyProfileListWhenSaveConnectionsThenExceptionMessage(){
        ExceptionMessage exceptionMessage = connectionOperation.saveConnections(new ArrayList<>());
        Assert.assertTrue("Exception message has to be equal", exceptionMessage.getErrors().get(0).equalsIgnoreCase(String.format(MessageCodeConstants.DATA_SENT_EMPTY_ERROR_MESSAGE,"Profile")));
    }

    @Test
    public void saveConnectionsGivenProfileListWithEmptyConnectionsWhenSaveConnectionsThenEmptyExceptionMessage(){
        List<ProfileDto> profileDtos = profileDtoProvider.createProfileDtoWithEmptyConnection();
        doNothing().when(validateOperation).validation(Matchers.<HashMap<String, HashMap<String,ConnectionTreeMapDto>>> any(), Matchers.<List<String[]>>any(), Matchers.<List<ValidationDto>>any());
        doNothing().when(profileOperation).saveValidProfiles(Matchers.<HashMap<String, HashMap<String,ConnectionTreeMapDto>>> any(), Matchers.<List<String[]>>any());
        ExceptionMessage exceptionMessage = connectionOperation.saveConnections(profileDtos);
        String exceptionMessageStr = String.format("ErrorMessage [errors=Code:%s;Message:%s]", MessageCodeConstants.DATA_SENT_EMPTY_ERROR_CODE, String.format(MessageCodeConstants.DATA_SENT_EMPTY_ERROR_MESSAGE, "Connection"));
        Assert.assertTrue("Exception message has to be equal", exceptionMessage.toString().equalsIgnoreCase(exceptionMessageStr));
    }

    @Test
    public void saveConnectionsGivenProfileListWithEmptyConnectionDataDtosWhenSaveConnectionsThenEmptyExceptionMessage(){
        List<ProfileDto> profileDtos = profileDtoProvider.createProfileDtoWithConnectionWithoutConnectionData();
        doNothing().when(validateOperation).validation(Matchers.<HashMap<String, HashMap<String,ConnectionTreeMapDto>>> any(), Matchers.<List<String[]>>any(), Matchers.<List<ValidationDto>>any());
        doNothing().when(profileOperation).saveValidProfiles(Matchers.<HashMap<String, HashMap<String,ConnectionTreeMapDto>>> any(), Matchers.<List<String[]>>any());
        ExceptionMessage exceptionMessage = connectionOperation.saveConnections(profileDtos);
        String exceptionMessageStr = String.format("ErrorMessage [errors=Code:%s;Message:%s]", MessageCodeConstants.DATA_SENT_EMPTY_ERROR_CODE, String.format(MessageCodeConstants.DATA_SENT_EMPTY_ERROR_MESSAGE, "ConnectionData"));
        Assert.assertTrue("Exception message has to be equal", exceptionMessage.toString().equalsIgnoreCase(exceptionMessageStr));
    }

    @Test
    public void saveConnectionsGivenProfileListWithConnectionDataDtosWhenSSaveConnectionsThenSuccess(){
        List<ProfileDto> profileDtos = profileDtoProvider.createProfileDtoWithConnectionWithConnectionData();
        doNothing().when(validateOperation).validation(Matchers.<HashMap<String, HashMap<String,ConnectionTreeMapDto>>> any(), Matchers.<List<String[]>>any(), Matchers.<List<ValidationDto>>any());
        doNothing().when(profileOperation).saveValidProfiles(Matchers.<HashMap<String, HashMap<String,ConnectionTreeMapDto>>> any(), Matchers.<List<String[]>>any());
        when(powerfilerUtil.convertMonthToInteger(eq(PowerfilerTestConstants.MONTH_JAN))).thenReturn(1);
        ExceptionMessage exceptionMessage = connectionOperation.saveConnections(profileDtos);
        Assert.assertTrue("Exception message has to be zero", exceptionMessage.getErrors().size() == 0);
    }
    @Test
    public void saveConnectionsGivenProfileListWithTwoConnectionDataDtosWhenSaveConnectionsThenSuccess(){
        List<ProfileDto> profileDtos = profileDtoProvider.createProfileDtoWithConnectionWithTwoConnectionData();
        doNothing().when(validateOperation).validation(Matchers.<HashMap<String, HashMap<String,ConnectionTreeMapDto>>> any(), Matchers.<List<String[]>>any(), Matchers.<List<ValidationDto>>any());
        doNothing().when(profileOperation).saveValidProfiles(Matchers.<HashMap<String, HashMap<String,ConnectionTreeMapDto>>> any(), Matchers.<List<String[]>>any());
        when(powerfilerUtil.convertMonthToInteger(eq(PowerfilerTestConstants.MONTH_JAN))).thenReturn(1);
        ExceptionMessage exceptionMessage = connectionOperation.saveConnections(profileDtos);
        Assert.assertTrue("Exception message has to be zero", exceptionMessage.getErrors().size() == 0);
    }

    @Test
    public void saveCsvConnectionsGivenNullConnectionsDtoWhenSaveConnectionsThenExceptionMessage(){
        ExceptionMessage exceptionMessage = connectionOperation.saveCsvConnections(null);
        Assert.assertTrue("Exception message has to be equal", exceptionMessage.getErrors().get(0).equalsIgnoreCase(String.format(MessageCodeConstants.DATA_SENT_EMPTY_ERROR_MESSAGE,"CSV")));
    }

    @Test
    public void saveCsvConnectionsGivenConnectionsDtoWithNullValuesWhenSaveConnectionsThenExceptionMessage(){
        ConnectionsDto connectionsDto = connectionsDtoProvider.createConnectionsDtoWithNullValues();
        ExceptionMessage exceptionMessage = connectionOperation.saveCsvConnections(connectionsDto);
        String exceptionMessageStr = String.format("ErrorMessage [errors=Code:%s;Message:%s]", MessageCodeConstants.DATA_SENT_EMPTY_ERROR_CODE, String.format(MessageCodeConstants.DATA_SENT_EMPTY_ERROR_MESSAGE, "Meter"));
        Assert.assertTrue("Exception message has to be equal", exceptionMessage.toString().equalsIgnoreCase(exceptionMessageStr));
    }
    @Test
    public void saveCsvConnectionsGivenConnectionsDtoWithEmptyMeterDtosAndNullFractionDtosWhenSaveConnectionsThenExceptionMessage(){
        ConnectionsDto connectionsDto = connectionsDtoProvider.createConnectionsDtoWithEmptyMeterDtosAndNullFractionDtos();
        ExceptionMessage exceptionMessage = connectionOperation.saveCsvConnections(connectionsDto);
        String exceptionMessageStr = String.format("ErrorMessage [errors=Code:%s;Message:%s]", MessageCodeConstants.DATA_SENT_EMPTY_ERROR_CODE, String.format(MessageCodeConstants.DATA_SENT_EMPTY_ERROR_MESSAGE, "Meter"));
        Assert.assertTrue("Exception message has to be equal", exceptionMessage.toString().equalsIgnoreCase(exceptionMessageStr));
    }

    @Test
    public void saveCsvConnectionsGivenConnectionsDtoWithMeterDtosAndNullFractionDtosWhenSaveConnectionsThenExceptionMessage(){
        ConnectionsDto connectionsDto = connectionsDtoProvider.createConnectionsDtoWithMeterDtosAndNullFractionDtos();
        when(powerfilerUtil.convertMonthToInteger(eq(PowerfilerTestConstants.MONTH_JAN))).thenReturn(1);
        ExceptionMessage exceptionMessage = connectionOperation.saveCsvConnections(connectionsDto);
        String exceptionMessageStr = String.format("ErrorMessage [errors=Code:%s;Message:%s]", MessageCodeConstants.DATA_SENT_EMPTY_ERROR_CODE, String.format(MessageCodeConstants.DATA_SENT_EMPTY_ERROR_MESSAGE, "Fraction"));
        Assert.assertTrue("Exception message has to be equal", exceptionMessage.toString().equalsIgnoreCase(exceptionMessageStr));
    }

    @Test
    public void saveCsvConnectionsGivenConnectionsDtoWithMeterDtosAndractionDtosWhenSaveConnectionsThenSuccess(){
        ConnectionsDto connectionsDto = connectionsDtoProvider.createConnectionsDtoWithMeterDtosAndFractionDtos();
        when(powerfilerUtil.convertMonthToInteger(eq(PowerfilerTestConstants.MONTH_JAN))).thenReturn(1);
        doNothing().when(validateOperation).validation(Matchers.<HashMap<String, HashMap<String,ConnectionTreeMapDto>>> any(), Matchers.<List<String[]>>any(), Matchers.<List<ValidationDto>>any());
        doNothing().when(profileOperation).saveValidProfiles(Matchers.<HashMap<String, HashMap<String,ConnectionTreeMapDto>>> any(), Matchers.<List<String[]>>any());

        ExceptionMessage exceptionMessage = connectionOperation.saveCsvConnections(connectionsDto);
        String exceptionMessageStr = String.format("ErrorMessage [errors=Code:%s;Message:%s]", MessageCodeConstants.DATA_SENT_EMPTY_ERROR_CODE, String.format(MessageCodeConstants.DATA_SENT_EMPTY_ERROR_MESSAGE, "Fraction"));
        Assert.assertTrue("Exception message has to be zero", exceptionMessage.getErrors().size() == 0);
    }

    @Test
    public void saveGivenConnectionIdWhenConnectionRepositorySaveThenConnection(){
        Connection connection = connectionProvider.createConnection();
        when(connectionRepository.save(any(Connection.class))).thenReturn(connection);
        Connection result = connectionOperation.save(PowerfilerTestConstants.CONNECTION_ID_0001);
        Assert.assertTrue("Connection id is not same", connection.getConnectionId().equalsIgnoreCase(result.getConnectionId()));
    }
    @Test
    public void getMetersGivenNullConnectionIdWhenGettingMetersThenEmptyList(){
        List<Meter> meters = connectionOperation.getMeters(null);
        Assert.assertTrue("List has to be empty", meters.size() == 0);
    }
    @Test
    public void getMetersGivenNotValidConnectionIdWhenGettingMetersThenEmptyList(){
        when(connectionRepository.getByConnectionId(eq(PowerfilerTestConstants.CONNECTION_ID_NOT_VALID))).thenReturn(null);
        List<Meter> meters = connectionOperation.getMeters(PowerfilerTestConstants.CONNECTION_ID_NOT_VALID);
        Assert.assertTrue("List has to be empty", meters.size() == 0);
    }
    @Test
    public void getMetersGivenValidConnectionIdWhenGettingMetersThenList(){
        Connection connection = connectionProvider.createConnectionWithMeters();
        when(connectionRepository.getByConnectionId(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(connection);
        List<Meter> meters = connectionOperation.getMeters(PowerfilerTestConstants.CONNECTION_ID_0001);
        Assert.assertTrue("List size has to be 1", meters.size() == 1);
    }

    @Test
    public void getFractionsGivenNullConnectionIdWhenGettingFractionsThenEmptyList(){
        List<Fraction> fractions = connectionOperation.getFractions(null);
        Assert.assertTrue("List has to be empty", fractions.size() == 0);
    }

    @Test
    public void getFractionsGivenNotValidConnectionIdWhenGettingFractionsThenEmptyList(){
        when(connectionRepository.getByConnectionId(eq(PowerfilerTestConstants.CONNECTION_ID_NOT_VALID))).thenReturn(null);
        List<Fraction> fractions = connectionOperation.getFractions(PowerfilerTestConstants.CONNECTION_ID_NOT_VALID);
        Assert.assertTrue("List has to be empty", fractions.size() == 0);
    }
    @Test
    public void getFractionsGivenValidConnectionIdWhenGettingFractionsThenList(){
        Connection connection = connectionProvider.createConnectionWithFractions();
        when(connectionRepository.getByConnectionId(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(connection);
        List<Fraction> fractions = connectionOperation.getFractions(PowerfilerTestConstants.CONNECTION_ID_0001);
        Assert.assertTrue("List size has to be 1", fractions.size() == 1);
    }
    @Test(expected = PowerfilerException.class)
    public void getConsumptionGivenNotValidDataWhenGettingConsumptionThenError() throws PowerfilerException {
        connectionOperation.getConsumption(null, null, null);
    }
    @Test(expected = PowerfilerException.class)
    public void getConsumptionGivenNotValidStartMonthWhenGettingConsumptionThenError() throws PowerfilerException {
        when(powerfilerUtil.convertCalendarMonthToDBMonth(eq(PowerfilerTestConstants.MONTH_NOT_VALID))).thenReturn(null);
        when(powerfilerUtil.convertCalendarMonthToDBMonth(eq(PowerfilerTestConstants.MONTH_FEB_CALENDAR))).thenReturn(PowerfilerTestConstants.MONTH_FEB);
        connectionOperation.getConsumption(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.MONTH_NOT_VALID, PowerfilerTestConstants.MONTH_FEB_CALENDAR);
    }
    @Test(expected = PowerfilerException.class)
    public void getConsumptionGivenNotValidEndMonthWhenGettingConsumptionThenError() throws PowerfilerException {
        when(powerfilerUtil.convertCalendarMonthToDBMonth(eq(PowerfilerTestConstants.MONTH_JAN_CALENDAR))).thenReturn(PowerfilerTestConstants.MONTH_JAN);
        when(powerfilerUtil.convertCalendarMonthToDBMonth(eq(PowerfilerTestConstants.MONTH_NOT_VALID))).thenReturn(null);
        connectionOperation.getConsumption(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.MONTH_JAN_CALENDAR, PowerfilerTestConstants.MONTH_NOT_VALID);
    }
    @Test
    public void getConsumptionGivenValidDataWhenGettingConsumptionThenResult() throws PowerfilerException {

        Meter meterJAN = meterProvider.createMeterWithMonthJAN();
        Meter meterFEB = meterProvider.createMeterWithMonthFEB();
        when(powerfilerUtil.convertCalendarMonthToDBMonth(eq(PowerfilerTestConstants.MONTH_JAN_CALENDAR))).thenReturn(PowerfilerTestConstants.MONTH_JAN);
        when(powerfilerUtil.convertCalendarMonthToDBMonth(eq(PowerfilerTestConstants.MONTH_FEB_CALENDAR))).thenReturn(PowerfilerTestConstants.MONTH_FEB);
        when(meterOperation.getMeter(eq(PowerfilerTestConstants.CONNECTION_ID_0001), eq(PowerfilerTestConstants.MONTH_JAN))).thenReturn(meterJAN);
        when(meterOperation.getMeter(eq(PowerfilerTestConstants.CONNECTION_ID_0001), eq(PowerfilerTestConstants.MONTH_FEB))).thenReturn(meterFEB);
        Long result = meterFEB.getReading() - meterJAN.getReading();
        ConsumptionDto consumptionDto = connectionOperation.getConsumption(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.MONTH_JAN_CALENDAR, PowerfilerTestConstants.MONTH_FEB_CALENDAR);
        Assert.assertTrue("Consumption has to be 1", result == Long.parseLong(consumptionDto.getResult()));
    }

    @Test(expected = PowerfilerException.class)
    public void addMeterGivenNotValidDataWhenAddingMeterThenError() throws PowerfilerException {
        connectionOperation.addMeter(null, null);
    }
    @Test(expected = PowerfilerException.class)
    public void addMeterGivenNotValidConnectionWhenErrorInConnectionThenError() throws PowerfilerException {
        connectionOperation.addMeter(PowerfilerTestConstants.CONNECTION_ID_NOT_VALID, new MeterValueDto(null, null));
    }
    @Test(expected = PowerfilerException.class)
    public void addMeterGivenConnectionWithoutProfileWhenErrorInProfileThenError() throws PowerfilerException {
        Connection connection = connectionProvider.createConnection();
        when(connectionRepository.getByConnectionId(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(connection);
        when(profileOperation.findOne(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(null);
        connectionOperation.addMeter(PowerfilerTestConstants.CONNECTION_ID_0001, new MeterValueDto(PowerfilerTestConstants.MONTH_NOT_VALID, null));
    }
    @Test(expected = PowerfilerException.class)
    public void addMeterGivenNotValidMonthInMeterValueDtoWhenErrorInMeterThenError() throws PowerfilerException {
        Connection connection = connectionProvider.createConnection();
        Profile profile = profileProvider.createProfileWithoutConnections();
        when(connectionRepository.getByConnectionId(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(connection);
        when(profileOperation.findOne(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(profile);
        when(powerfilerUtil.isValidMonth(eq(PowerfilerTestConstants.MONTH_NOT_VALID))).thenReturn(false);
        connectionOperation.addMeter(PowerfilerTestConstants.CONNECTION_ID_0001, new MeterValueDto(PowerfilerTestConstants.MONTH_NOT_VALID, null));
    }
    @Test(expected = PowerfilerException.class)
    public void addMeterGivenValidDataWhenMeterHasAlreadyInsertedThenError() throws PowerfilerException {
        MeterValueDto meterValueDto = meterValueDtoProvider.createMeterValueDtoWithMonthJAN();
        Meter meterJAN = meterProvider.createMeterWithMonthJAN();
        Connection connection = connectionProvider.createConnection();
        Profile profile = profileProvider.createProfileWithoutConnections();
        when(connectionRepository.getByConnectionId(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(connection);
        when(profileOperation.findOne(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(profile);
        when(powerfilerUtil.isValidMonth(eq(PowerfilerTestConstants.MONTH_JAN))).thenReturn(true);
        when(meterOperation.getMeter(eq(PowerfilerTestConstants.CONNECTION_ID_0001), eq(PowerfilerTestConstants.MONTH_JAN))).thenReturn(meterJAN);
        connectionOperation.addMeter(PowerfilerTestConstants.CONNECTION_ID_0001, meterValueDto);
    }
    @Test
    public void addMeterGivenValidDataWhenValidationFailThenValidationError() throws PowerfilerException {
        MeterValueDto meterValueDto = meterValueDtoProvider.createMeterValueDtoWithMonthJAN();
        List<ValidationDto> validationDtos = validationDtoProvider.createValidationDtos();
        MeterDto meterDto = new MeterDto(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.PROFILE_A, meterValueDto.getMonth(), meterValueDto.getMeterReading());
        Connection connection = connectionProvider.createConnection();
        Profile profile = profileProvider.createProfileWithoutConnections();
        when(connectionRepository.getByConnectionId(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(connection);
        when(profileOperation.findOne(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(profile);
        when(powerfilerUtil.isValidMonth(eq(PowerfilerTestConstants.MONTH_JAN))).thenReturn(true);
        when(meterOperation.getMeter(eq(PowerfilerTestConstants.CONNECTION_ID_0001), eq(PowerfilerTestConstants.MONTH_JAN))).thenReturn(null);
        when(meterOperation.validateMeter(any(MeterDto.class))).thenReturn(validationDtos);
        ExceptionMessage exceptionMessage = connectionOperation.addMeter(PowerfilerTestConstants.CONNECTION_ID_0001, meterValueDto);
    }
    @Test
    public void addMeterGivenValidDataWhenPassValidationThenSuccess() throws PowerfilerException {
        MeterValueDto meterValueDto = meterValueDtoProvider.createMeterValueDtoWithMonthJAN();
        Connection connection = connectionProvider.createConnection();
        Profile profile = profileProvider.createProfileWithoutConnections();
        when(connectionRepository.getByConnectionId(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(connection);
        when(profileOperation.findOne(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(profile);
        when(powerfilerUtil.isValidMonth(eq(PowerfilerTestConstants.MONTH_JAN))).thenReturn(true);
        when(meterOperation.getMeter(eq(PowerfilerTestConstants.CONNECTION_ID_0001), eq(PowerfilerTestConstants.MONTH_JAN))).thenReturn(null);
        when(meterOperation.validateMeter(any(MeterDto.class))).thenReturn(new ArrayList<>());
        doNothing().when(meterOperation).saveAndRelateWithConnection(any(Connection.class), any(MeterDto[].class));
        ExceptionMessage exceptionMessage = connectionOperation.addMeter(PowerfilerTestConstants.CONNECTION_ID_0001, meterValueDto);
        Assert.assertTrue("ValidationDtos size has to be 0 ", exceptionMessage.getErrors().size() == 0);
    }
    @Test(expected = PowerfilerException.class)
    public void deleteMeterGivenNotValidDataWhenDeletingMeterThenException() throws PowerfilerException {
        connectionOperation.deleteMeter(null,  null);
    }

    @Test(expected = PowerfilerException.class)
    public void deleteMeterGivenNotValidConnectionIdWhenErrorInConnectionThenException() throws PowerfilerException {
        connectionOperation.deleteMeter(PowerfilerTestConstants.CONNECTION_ID_NOT_VALID,  null);
    }

    @Test(expected = PowerfilerException.class)
    public void deleteMeterGivenNotValidMonthWhenErrorInMeterThenException() throws PowerfilerException {
        Connection connection = connectionProvider.createConnection();
        when(connectionRepository.getByConnectionId(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(connection);
        when(meterOperation.getMeter(eq(PowerfilerTestConstants.CONNECTION_ID_0001), eq(PowerfilerTestConstants.MONTH_NOT_VALID))).thenReturn(null);
        connectionOperation.deleteMeter(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.MONTH_NOT_VALID);
    }
    @Test
    public void deleteMeterGivenValidDataWhenErrorInDeletingThenException() throws PowerfilerException {
        Connection connection = connectionProvider.createConnection();
        Meter meterJAN = meterProvider.createMeterWithMonthJAN();
        meterJAN.setId(PowerfilerTestConstants.ID);
        when(connectionRepository.getByConnectionId(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(connection);
        when(meterOperation.getMeter(eq(PowerfilerTestConstants.CONNECTION_ID_0001), eq(PowerfilerTestConstants.MONTH_JAN))).thenReturn(meterJAN);
        when(meterOperation.delete(eq(meterJAN.getId()))).thenReturn(false);
        ExceptionMessage exceptionMessage = connectionOperation.deleteMeter(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.MONTH_JAN);
        Assert.assertTrue("Error message code expecting", exceptionMessage.getValidationDtos().get(0).getCode().equalsIgnoreCase(MessageCodeConstants.DELETE_IS_NOT_SUCCESSFUL_EXCEPTION_CODE));
    }
    @Test()
    public void deleteMeterGivenValidDataWhenDeletingMeterThenSuccess() throws PowerfilerException {
        Connection connection = connectionProvider.createConnection();
        Meter meterJAN = meterProvider.createMeterWithMonthJAN();
        meterJAN.setId(PowerfilerTestConstants.ID);
        when(connectionRepository.getByConnectionId(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(connection);
        when(meterOperation.getMeter(eq(PowerfilerTestConstants.CONNECTION_ID_0001), eq(PowerfilerTestConstants.MONTH_JAN))).thenReturn(meterJAN);
        when(meterOperation.delete(eq(meterJAN.getId()))).thenReturn(true);
        ExceptionMessage exceptionMessage = connectionOperation.deleteMeter(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.MONTH_JAN);
        Assert.assertTrue("Success message code expecting", exceptionMessage.getValidationDtos().get(0).getCode().equalsIgnoreCase(MessageCodeConstants.SUCCESSFULLY_DELETED_SUCCESS_CODE));
    }

    @Test(expected = PowerfilerException.class)
    public void updateMeterGivenNotValidDataWhenUpdatingMeterThanException() throws PowerfilerException {
        connectionOperation.updateMeter(null ,null, null);
    }

    @Test(expected = PowerfilerException.class)
    public void updateMeterGivenNotValidConnectionIdThenErrorInConnectionThanException() throws PowerfilerException {
        connectionOperation.updateMeter(PowerfilerTestConstants.CONNECTION_ID_NOT_VALID ,PowerfilerTestConstants.MONTH_JAN, PowerfilerTestConstants.METER_READING_JAN);
    }
    @Test(expected = PowerfilerException.class)
    public void updateMeterGivenNotValidMonthWhenErrorInMeterThenError() throws PowerfilerException {
        Connection connection = connectionProvider.createConnection();
        when(connectionRepository.getByConnectionId(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(connection);
        when(powerfilerUtil.isValidMonth(eq(PowerfilerTestConstants.MONTH_NOT_VALID))).thenReturn(false);
        connectionOperation.updateMeter(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.MONTH_NOT_VALID, PowerfilerTestConstants.METER_READING_JAN);
    }

    @Test(expected = PowerfilerException.class)
    public void updateMeterGivenValidDataWhenMeterNotFoundThenError() throws PowerfilerException {
        Connection connection = connectionProvider.createConnection();
        when(connectionRepository.getByConnectionId(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(connection);
        when(powerfilerUtil.isValidMonth(eq(PowerfilerTestConstants.MONTH_JAN))).thenReturn(true);
        when(meterOperation.getMeter(eq(PowerfilerTestConstants.CONNECTION_ID_0001), eq(PowerfilerTestConstants.MONTH_JAN))).thenReturn(null);
        connectionOperation.updateMeter(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.MONTH_JAN, PowerfilerTestConstants.METER_READING_JAN);
    }

    @Test
    public void updateMeterGivenValidDataWhenValidationErrorThenValidaionDtoWithSuccess() throws PowerfilerException {
        Connection connection = connectionProvider.createConnection();
        Meter meterJAN = meterProvider.createMeterWithMonthJAN();
        Profile profile = profileProvider.createProfileWithoutConnections();
        List<ValidationDto> validationDtos = validationDtoProvider.createValidationDtos();
        when(connectionRepository.getByConnectionId(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(connection);
        when(powerfilerUtil.isValidMonth(eq(PowerfilerTestConstants.MONTH_JAN))).thenReturn(true);
        when(meterOperation.getMeter(eq(PowerfilerTestConstants.CONNECTION_ID_0001), eq(PowerfilerTestConstants.MONTH_JAN))).thenReturn(meterJAN);
        when(profileOperation.findOne(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(profile);
        when(meterOperation.validateMeter(any(MeterDto.class))).thenReturn(validationDtos);
        ExceptionMessage result = connectionOperation.updateMeter(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.MONTH_JAN, PowerfilerTestConstants.METER_READING_JAN);
        Assert.assertTrue("ValidationDtos size has to be 1 and has to be same message", result.getErrors().size() == validationDtos.size() &&
                validationDtos.get(0).getMessage().equalsIgnoreCase(result.getValidationDtos().get(0).getMessage()));
        Mockito.verify(meterOperation, never()).update(any(Meter.class));
    }

    @Test
    public void updateMeterGivenValidDataWhenValidationErrorThenValidaionDtoWithError() throws PowerfilerException {
        Connection connection = connectionProvider.createConnection();
        Meter meterJAN = meterProvider.createMeterWithMonthJAN();
        Profile profile = profileProvider.createProfileWithoutConnections();
        when(connectionRepository.getByConnectionId(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(connection);
        when(powerfilerUtil.isValidMonth(eq(PowerfilerTestConstants.MONTH_JAN))).thenReturn(true);
        when(meterOperation.getMeter(eq(PowerfilerTestConstants.CONNECTION_ID_0001), eq(PowerfilerTestConstants.MONTH_JAN))).thenReturn(meterJAN);
        when(profileOperation.findOne(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(profile);
        when(meterOperation.validateMeter(any(MeterDto.class))).thenReturn(new ArrayList<>());
        when(meterOperation.update(any(Meter.class))).thenReturn(meterJAN);
        ExceptionMessage result = connectionOperation.updateMeter(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.MONTH_JAN, PowerfilerTestConstants.METER_READING_JAN);
        Assert.assertTrue("ValidationDtos size has to be 0", result.getErrors().size() == 0);
        Mockito.verify(meterOperation, atMost(1)).update(any(Meter.class));
    }

    @Test(expected = PowerfilerException.class)
    public void addFractionGivenNotValidDataWhenAddingFractionThenError() throws PowerfilerException {
        connectionOperation.addFraction(null, null);
    }
    @Test(expected = PowerfilerException.class)
    public void addFractionGivenNotValidConnectionWhenErrorInConnectionThenError() throws PowerfilerException {
        connectionOperation.addFraction(PowerfilerTestConstants.CONNECTION_ID_NOT_VALID, new FractionValueDto(null, null));
    }
    @Test(expected = PowerfilerException.class)
    public void addFractionGivenNotValidMonthValueDtoWhenErrorInMonthValidaionThenError() throws PowerfilerException {
        Connection connection = connectionProvider.createConnection();
        when(connectionRepository.getByConnectionId(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(connection);
        when(powerfilerUtil.isValidMonth(eq(PowerfilerTestConstants.MONTH_NOT_VALID))).thenReturn(false);
        connectionOperation.addFraction(PowerfilerTestConstants.CONNECTION_ID_0001, new FractionValueDto(PowerfilerTestConstants.MONTH_NOT_VALID, null));
    }
    @Test(expected = PowerfilerException.class)
    public void addFractionGivenConnectionWithoutProfileWhenErrorInProfileThenError() throws PowerfilerException {
        Connection connection = connectionProvider.createConnection();
        when(connectionRepository.getByConnectionId(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(connection);
        when(profileOperation.findOne(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(null);
        connectionOperation.addFraction(PowerfilerTestConstants.CONNECTION_ID_0001, new FractionValueDto(PowerfilerTestConstants.MONTH_NOT_VALID, null));
    }
    @Test(expected = PowerfilerException.class)
    public void addFractionGivenValidDataWhenFractionHasAlreadyInsertedThenError() throws PowerfilerException {
        FractionValueDto fractionValueDto = fractionValueDtoProvider.createFractionValueDto();
        Fraction fractionJAN = fractionProvider.createFractionWithMonthJAN();
        Connection connection = connectionProvider.createConnection();
        Profile profile = profileProvider.createProfileWithoutConnections();
        when(connectionRepository.getByConnectionId(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(connection);
        when(powerfilerUtil.isValidMonth(eq(PowerfilerTestConstants.MONTH_JAN))).thenReturn(true);
        when(profileOperation.findOne(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(profile);
        when(fractionOperation.getFraction(eq(PowerfilerTestConstants.CONNECTION_ID_0001), eq(PowerfilerTestConstants.MONTH_JAN))).thenReturn(fractionJAN);
        connectionOperation.addFraction(PowerfilerTestConstants.CONNECTION_ID_0001, fractionValueDto);
    }

    @Test
    public void addFractionGivenValidDataWhenValidationFailThenValidationError() throws PowerfilerException {
        FractionValueDto fractionValueDto = fractionValueDtoProvider.createFractionValueDto();
        List<ValidationDto> validationDtos = validationDtoProvider.createValidationDtos();
        Connection connection = connectionProvider.createConnection();
        Profile profile = profileProvider.createProfileWithoutConnections();
        when(connectionRepository.getByConnectionId(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(connection);
        when(powerfilerUtil.isValidMonth(eq(PowerfilerTestConstants.MONTH_JAN))).thenReturn(true);
        when(profileOperation.findOne(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(profile);
        when(fractionOperation.getFraction(eq(PowerfilerTestConstants.CONNECTION_ID_0001), eq(PowerfilerTestConstants.MONTH_JAN))).thenReturn(null);
        when(fractionOperation.validateFraction(eq(PowerfilerTestConstants.CONNECTION_ID_0001), eq(Double.parseDouble(fractionValueDto.getFraction())), anyListOf(Fraction.class), eq(PowerfilerTestConstants.PROFILE_A))).thenReturn(validationDtos);
        ExceptionMessage result = connectionOperation.addFraction(PowerfilerTestConstants.CONNECTION_ID_0001, fractionValueDto);
        Assert.assertTrue("ValidationDtos size has to be 1 and has to be same message", result.getErrors().size() == validationDtos.size() &&
                validationDtos.get(0).getMessage().equalsIgnoreCase(result.getValidationDtos().get(0).getMessage()));
    }

    @Test
    public void addFractionGivenValidDataWhenValidationPassThenSuccess() throws PowerfilerException {
        FractionValueDto fractionValueDto = fractionValueDtoProvider.createFractionValueDto();
        Connection connection = connectionProvider.createConnection();
        Profile profile = profileProvider.createProfileWithoutConnections();
        when(connectionRepository.getByConnectionId(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(connection);
        when(powerfilerUtil.isValidMonth(eq(PowerfilerTestConstants.MONTH_JAN))).thenReturn(true);
        when(profileOperation.findOne(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(profile);
        when(fractionOperation.getFraction(eq(PowerfilerTestConstants.CONNECTION_ID_0001), eq(PowerfilerTestConstants.MONTH_JAN))).thenReturn(null);
        when(fractionOperation.validateFraction(eq(PowerfilerTestConstants.CONNECTION_ID_0001), eq(Double.parseDouble(fractionValueDto.getFraction())), anyListOf(Fraction.class), eq(PowerfilerTestConstants.PROFILE_A))).thenReturn(new ArrayList<>());
        ExceptionMessage result = connectionOperation.addFraction(PowerfilerTestConstants.CONNECTION_ID_0001, fractionValueDto);
        Assert.assertTrue("ValidationDtos size has to be 0", result.getErrors().size() == 0);
        Mockito.verify(fractionOperation, atMost(1)).saveAndRelateWithConnection(any(Connection.class), any(FractionDto[].class));
    }

    @Test(expected = PowerfilerException.class)
    public void deleteFractionGivenNotValidDataWhenDeletingFractionThenException() throws PowerfilerException {
        connectionOperation.deleteFraction(null,  null);
    }

    @Test(expected = PowerfilerException.class)
    public void deleteFractionGivenNotValidConnectionIdWhenErrorInConnectionThenException() throws PowerfilerException {
        connectionOperation.deleteFraction(PowerfilerTestConstants.CONNECTION_ID_NOT_VALID,  null);
    }
    @Test(expected = PowerfilerException.class)
    public void deleteFractionGivenNotValidMonthWhenErrorInFractionThenException() throws PowerfilerException {
        Connection connection = connectionProvider.createConnection();
        when(connectionRepository.getByConnectionId(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(connection);
        when(fractionOperation.getFraction(eq(PowerfilerTestConstants.CONNECTION_ID_0001), eq(PowerfilerTestConstants.MONTH_NOT_VALID))).thenReturn(null);
        connectionOperation.deleteFraction(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.MONTH_NOT_VALID);
    }
    @Test
    public void deleteFractionGivenValidDataWhenErrorInDeletingThenException() throws PowerfilerException {
        Connection connection = connectionProvider.createConnection();
        Fraction fractionJAN = fractionProvider.createFractionWithMonthJAN();
        fractionJAN.setId(PowerfilerTestConstants.ID);
        when(connectionRepository.getByConnectionId(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(connection);
        when(fractionOperation.getFraction(eq(PowerfilerTestConstants.CONNECTION_ID_0001), eq(PowerfilerTestConstants.MONTH_JAN))).thenReturn(fractionJAN);
        when(fractionOperation.delete(eq(fractionJAN.getId()))).thenReturn(false);
        ExceptionMessage exceptionMessage = connectionOperation.deleteFraction(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.MONTH_JAN);
        Assert.assertTrue("Error message code expecting", exceptionMessage.getValidationDtos().get(0).getCode().equalsIgnoreCase(MessageCodeConstants.DELETE_IS_NOT_SUCCESSFUL_EXCEPTION_CODE));
    }
    @Test
    public void deleteFractionGivenValidDataWhenDeletingFractionThenSuccess() throws PowerfilerException {
        Connection connection = connectionProvider.createConnection();
        Fraction fractionJAN = fractionProvider.createFractionWithMonthJAN();
        fractionJAN.setId(PowerfilerTestConstants.ID);
        when(connectionRepository.getByConnectionId(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(connection);
        when(fractionOperation.getFraction(eq(PowerfilerTestConstants.CONNECTION_ID_0001), eq(PowerfilerTestConstants.MONTH_JAN))).thenReturn(fractionJAN);
        when(fractionOperation.delete(eq(fractionJAN.getId()))).thenReturn(true);
        ExceptionMessage exceptionMessage = connectionOperation.deleteFraction(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.MONTH_JAN);
        Assert.assertTrue("Success message code expecting", exceptionMessage.getValidationDtos().get(0).getCode().equalsIgnoreCase(MessageCodeConstants.SUCCESSFULLY_DELETED_SUCCESS_CODE));
    }
    @Test(expected = PowerfilerException.class)
    public void updateFractionGivenNotValidDataWhenUpdatingFractionThanException() throws PowerfilerException {
        connectionOperation.updateFraction(null ,null, null);
    }
    @Test(expected = PowerfilerException.class)
    public void updateFractionGivenNotValidConnectionIdThenErrorInConnectionThanException() throws PowerfilerException {
        connectionOperation.updateFraction(PowerfilerTestConstants.CONNECTION_ID_NOT_VALID ,PowerfilerTestConstants.MONTH_JAN, PowerfilerTestConstants.METER_READING_JAN);
    }
    @Test(expected = PowerfilerException.class)
    public void updateFractionGivenNotValidMonthWhenErrorInFractionThenError() throws PowerfilerException {
        Connection connection = connectionProvider.createConnection();
        when(connectionRepository.getByConnectionId(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(connection);
        when(powerfilerUtil.isValidMonth(eq(PowerfilerTestConstants.MONTH_NOT_VALID))).thenReturn(false);
        connectionOperation.updateFraction(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.MONTH_NOT_VALID, PowerfilerTestConstants.METER_READING_JAN);
    }
    @Test(expected = PowerfilerException.class)
    public void updateFractionGivenValidDataWhenFractionNotFoundThenError() throws PowerfilerException {
        Connection connection = connectionProvider.createConnection();
        when(connectionRepository.getByConnectionId(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(connection);
        when(powerfilerUtil.isValidMonth(eq(PowerfilerTestConstants.MONTH_JAN))).thenReturn(true);
        when(fractionOperation.getFraction(eq(PowerfilerTestConstants.CONNECTION_ID_0001), eq(PowerfilerTestConstants.MONTH_JAN))).thenReturn(null);
        connectionOperation.updateFraction(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.MONTH_JAN, PowerfilerTestConstants.METER_READING_JAN);
    }
    @Test
    public void updateFractionGivenValidDataWhenValidationErrorThenValidaionDtoWithSuccess() throws PowerfilerException {
        FractionValueDto fractionValueDto = fractionValueDtoProvider.createFractionValueDto();
        Connection connection = connectionProvider.createConnection();
        Fraction fractionJAN = fractionProvider.createFractionWithMonthJAN();
        Profile profile = profileProvider.createProfileWithoutConnections();
        List<ValidationDto> validationDtos = validationDtoProvider.createValidationDtos();
        when(connectionRepository.getByConnectionId(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(connection);
        when(powerfilerUtil.isValidMonth(eq(PowerfilerTestConstants.MONTH_JAN))).thenReturn(true);
        when(fractionOperation.getFraction(eq(PowerfilerTestConstants.CONNECTION_ID_0001), eq(PowerfilerTestConstants.MONTH_JAN))).thenReturn(fractionJAN);
        when(profileOperation.findOne(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(profile);
        Double fractionDiff = fractionJAN.getFraction() - Double.parseDouble(fractionValueDto.getFraction());
        when(fractionOperation.validateFraction(eq(PowerfilerTestConstants.CONNECTION_ID_0001), eq(fractionDiff), anyListOf(Fraction.class), eq(PowerfilerTestConstants.PROFILE_A))).thenReturn(validationDtos);
        ExceptionMessage result = connectionOperation.updateFraction(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.MONTH_JAN, PowerfilerTestConstants.FRACTION_JAN);
        Assert.assertTrue("ValidationDtos size has to be 1 and has to be same message", result.getErrors().size() == validationDtos.size() &&
                validationDtos.get(0).getMessage().equalsIgnoreCase(result.getValidationDtos().get(0).getMessage()));
        Mockito.verify(fractionOperation, never()).update(any(Fraction.class));
    }

    @Test
    public void updateFractionGivenValidDataWhenValidationErrorThenValidaionDtoWithError() throws PowerfilerException {
        FractionValueDto fractionValueDto = fractionValueDtoProvider.createFractionValueDto();
        Connection connection = connectionProvider.createConnection();
        Fraction fractionJAN = fractionProvider.createFractionWithMonthJAN();
        Profile profile = profileProvider.createProfileWithoutConnections();
        when(connectionRepository.getByConnectionId(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(connection);
        when(powerfilerUtil.isValidMonth(eq(PowerfilerTestConstants.MONTH_JAN))).thenReturn(true);
        when(fractionOperation.getFraction(eq(PowerfilerTestConstants.CONNECTION_ID_0001), eq(PowerfilerTestConstants.MONTH_JAN))).thenReturn(fractionJAN);
        when(profileOperation.findOne(eq(PowerfilerTestConstants.CONNECTION_ID_0001))).thenReturn(profile);
        Double fractionDiff = fractionJAN.getFraction() - Double.parseDouble(fractionValueDto.getFraction());
        when(fractionOperation.validateFraction(eq(PowerfilerTestConstants.CONNECTION_ID_0001), eq(fractionDiff), anyListOf(Fraction.class), eq(PowerfilerTestConstants.PROFILE_A))).thenReturn(new ArrayList<>());
        when(fractionOperation.update(any(Fraction.class))).thenReturn(fractionJAN);
        ExceptionMessage result = connectionOperation.updateFraction(PowerfilerTestConstants.CONNECTION_ID_0001, PowerfilerTestConstants.MONTH_JAN, PowerfilerTestConstants.FRACTION_JAN);
        Assert.assertTrue("ValidationDtos size has to be 0", result.getErrors().size() == 0);
        Mockito.verify(meterOperation, atMost(1)).update(any(Meter.class));
    }






}
