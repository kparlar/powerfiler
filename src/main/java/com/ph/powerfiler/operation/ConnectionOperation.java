package com.ph.powerfiler.operation;

import com.ph.powerfiler.exception.ExceptionMessage;
import com.ph.powerfiler.exception.ExceptionMessageCodeConstants;
import com.ph.powerfiler.exception.PowerfilerException;
import com.ph.powerfiler.model.dto.*;
import com.ph.powerfiler.model.entity.Connection;
import com.ph.powerfiler.model.entity.Fraction;
import com.ph.powerfiler.model.entity.Meter;
import com.ph.powerfiler.model.entity.Profile;
import com.ph.powerfiler.operation.rule.SumFractionRule;
import com.ph.powerfiler.repository.ConnectionRepository;
import com.ph.powerfiler.util.PowerfilerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class ConnectionOperation {

    @Autowired
    private ValidateOperation validateOperation;
    @Autowired
    private PowerfilerUtil powerfilerUtil;
    @Autowired
    private ConnectionRepository connectionRepository;
    @Autowired
    private ProfileOperation profileOperation;
    @Autowired
    private MeterOperation meterOperation;
    @Autowired
    private FractionOperation fractionOperation;



    public ExceptionMessage saveConnections(List<ProfileDto> profileDtos){
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        if(CollectionUtils.isEmpty(profileDtos)){
            exceptionMessage.getErrors().add(String.format(ExceptionMessageCodeConstants.DATA_SENT_EMPTY_ERROR_MESSAGE, "Profile"));
            return exceptionMessage;
        }
        List<ValidationDto> validationDtos = new ArrayList<>();
        exceptionMessage.setValidationDtos(validationDtos);
        HashMap<String, HashMap<String, ConnectionTreeMapDto>> profileConnectionsMap = arrangeProfileAndConnections(profileDtos, validationDtos);
        if(validationDtos.size() == 0){
            List<String[]> validProfileConnections = new ArrayList<>();
            validateOperation.validation(profileConnectionsMap, validProfileConnections, validationDtos);
            profileOperation.saveValidProfiles(profileConnectionsMap, validProfileConnections);
        }
        return exceptionMessage;




    }

    private HashMap<String, HashMap<String, ConnectionTreeMapDto>> arrangeProfileAndConnections(List<ProfileDto> profileDtos, List<ValidationDto> validationDtos) {
        HashMap<String, HashMap<String, ConnectionTreeMapDto>> profileConnectionsMap = new HashMap<>();
        for(ProfileDto profileDto: profileDtos){
            HashMap<String, ConnectionTreeMapDto> connectionTreeMap =  profileConnectionsMap.get(profileDto.getProfile());
            List<ConnectionDto> connectionDtos =  profileDto.getConnectionDtos();
            if(CollectionUtils.isEmpty(connectionDtos)){
                ValidationDto validationDto = new ValidationDto(String.format(ExceptionMessageCodeConstants.DATA_SENT_EMPTY_ERROR_MESSAGE, "Connection"), ExceptionMessageCodeConstants.DATA_SENT_EMPTY_ERROR_CODE);
                validationDtos.add(validationDto);
                return null;
            }
            for(ConnectionDto connectionDto : connectionDtos){
                ConnectionTreeMapDto connectionTreeMapDto;
                if(connectionTreeMap==null){
                    connectionTreeMap = new HashMap<>();
                    connectionTreeMapDto = new ConnectionTreeMapDto();
                    if(connectionDto.getConnectionId() == null){
                        ValidationDto validationDto = new ValidationDto(String.format(ExceptionMessageCodeConstants.DATA_SENT_EMPTY_ERROR_MESSAGE, "ConnectionId"), ExceptionMessageCodeConstants.DATA_SENT_EMPTY_ERROR_CODE);
                        validationDtos.add(validationDto);
                        break;
                    }
                    connectionTreeMapDto.setConnectionId(connectionDto.getConnectionId());
                    connectionTreeMap.put(connectionTreeMapDto.getConnectionId(), connectionTreeMapDto);
                    profileConnectionsMap.put(profileDto.getProfile(), connectionTreeMap);
                }else{
                    connectionTreeMapDto = connectionTreeMap.get(connectionDto.getConnectionId());
                }

                List<ConnectionDataDto> connectionDataDtos = connectionDto.getConnectionDataDtos();
                if(CollectionUtils.isEmpty(connectionDataDtos)){
                    ValidationDto validationDto = new ValidationDto(String.format(ExceptionMessageCodeConstants.DATA_SENT_EMPTY_ERROR_MESSAGE, "ConnectionData"), ExceptionMessageCodeConstants.DATA_SENT_EMPTY_ERROR_CODE);
                    validationDtos.add(validationDto);
                    return null;
                }
                for (ConnectionDataDto connectionDataDto : connectionDataDtos){
                    int month = powerfilerUtil.convertMonthToInteger(connectionDataDto.getMonth());
                    MeterDto meterDto = new MeterDto(connectionDto.getConnectionId(),profileDto.getProfile(), connectionDataDto.getMonth(), connectionDataDto.getMeterReading());
                    connectionTreeMapDto.getMeterDtos().put(month, meterDto);
                    FractionDto fractionDto = new FractionDto(profileDto.getProfile(), connectionDataDto.getMonth(), connectionDataDto.getFraction());
                    connectionTreeMapDto.getFractionDtos().put(month, fractionDto );
                }
            }

        }
        return profileConnectionsMap;
    }


    public ExceptionMessage saveCsvConnections(ConnectionsDto connectionsDto){
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        if(connectionsDto == null){
            exceptionMessage.getErrors().add(String.format(ExceptionMessageCodeConstants.DATA_SENT_EMPTY_ERROR_MESSAGE, "CSV"));
            return exceptionMessage;
        }
        List<ValidationDto> validationDtos = new ArrayList<>();
        exceptionMessage.setValidationDtos(validationDtos);
        HashMap<String, HashMap<String, ConnectionTreeMapDto>> profileConnectionsMap = arrangeCsvProfileAndConnections(connectionsDto, validationDtos);
        if(validationDtos.size() == 0){
            List<String[]> validProfileConnections = new ArrayList<>();
            validateOperation.validation(profileConnectionsMap, validProfileConnections, validationDtos);
            profileOperation.saveValidProfiles(profileConnectionsMap, validProfileConnections);
        }
        return exceptionMessage;
    }

    private HashMap<String, HashMap<String, ConnectionTreeMapDto>> arrangeCsvProfileAndConnections(ConnectionsDto connectionsDto, List<ValidationDto> validationDtos) {
        List<MeterDto> meterDtos = connectionsDto.getMeterDtos();
        HashMap<String, HashMap<String, ConnectionTreeMapDto>> profileConnectionsMap = new HashMap<>();
        HashMap<String, String> profileConnectionMap = new HashMap<>();
        if(CollectionUtils.isEmpty(meterDtos)){
            ValidationDto validationDto = new ValidationDto(String.format(ExceptionMessageCodeConstants.DATA_SENT_EMPTY_ERROR_MESSAGE, "Meter"), ExceptionMessageCodeConstants.DATA_SENT_EMPTY_ERROR_CODE);
            validationDtos.add(validationDto);
            return null;
        }
        for(MeterDto meterDto: meterDtos){
            String profile = meterDto.getProfile();
            HashMap<String, ConnectionTreeMapDto> connectionDtos = profileConnectionsMap.get(profile);
            int month =powerfilerUtil.convertMonthToInteger(meterDto.getMonth());
            if(connectionDtos == null){
                connectionDtos = new HashMap<>();
                ConnectionTreeMapDto connectionTreeMapDto = new ConnectionTreeMapDto();
                connectionTreeMapDto.setConnectionId(meterDto.getConnectionId());
                connectionDtos.put(meterDto.getConnectionId(), connectionTreeMapDto);
                profileConnectionsMap.put(profile, connectionDtos);
                profileConnectionMap.put(profile, meterDto.getConnectionId());
            }
            connectionDtos.get(meterDto.getConnectionId()).getMeterDtos().put(month, meterDto);
        }
        List<FractionDto> fractionDtos = connectionsDto.getFractionDtos();
        if(CollectionUtils.isEmpty(fractionDtos)){
            ValidationDto validationDto = new ValidationDto(String.format(ExceptionMessageCodeConstants.DATA_SENT_EMPTY_ERROR_MESSAGE, "Fraction"), ExceptionMessageCodeConstants.DATA_SENT_EMPTY_ERROR_CODE);
            validationDtos.add(validationDto);
            return null;
        }
        for(FractionDto fractionDto : fractionDtos){
            String profile = fractionDto.getProfile();
            HashMap<String, ConnectionTreeMapDto> connectionDtos = profileConnectionsMap.get(profile);
            String connectionId = profileConnectionMap.get(profile);
            int month =powerfilerUtil.convertMonthToInteger(fractionDto.getMonth());
            connectionDtos.get(connectionId).getFractionDtos().put(month, fractionDto);
        }
        return profileConnectionsMap;

    }




    public Connection save(String connectionId){
        Connection connection = new Connection();
        connection.setConnectionId(connectionId);
        return connectionRepository.save(connection);
    }

    public List<Meter> getMeters(String connectionId){
        if(connectionId==null)
            return new ArrayList<>();

        Connection connection = connectionRepository.getByConnectionId(connectionId);

        if(connection!=null)
            return  connection.getMeters();
        return new ArrayList<>();
    }

    public List<Fraction> getFractions(String connectionId){
        if(connectionId == null)
            return new ArrayList<>();
        Connection connection = connectionRepository.getByConnectionId(connectionId);

        if(connection!=null)
            return  connection.getFractions();
        return new ArrayList<>();
    }

    public ConsumptionDto getConsumption(String connectionId, String startMonth, String endMonth) throws PowerfilerException {
        if(connectionId == null || startMonth == null || endMonth == null){
            throw new PowerfilerException(String.format(ExceptionMessageCodeConstants.DATA_SENT_EMPTY_ERROR_MESSAGE, "ConnectionId or StartMonth or EndMonth"), ExceptionMessageCodeConstants.DATA_SENT_EMPTY_ERROR_CODE, true);
        }
        String startDBMonth = powerfilerUtil.convertCalendarMonthToDBMonth(startMonth);
        String endDBMonth = powerfilerUtil.convertCalendarMonthToDBMonth(endMonth);
        if(startDBMonth==null || endDBMonth==null){
            throw new PowerfilerException(ExceptionMessageCodeConstants.NOT_VALID_MONTHS_EXCEPTION_MESSAGE, ExceptionMessageCodeConstants.NOT_VALID_MONTHS_EXCEPTION_CODE, true);
        }

        Meter startMeter = meterOperation.getMeter(connectionId, startDBMonth);
        Meter endMeter = meterOperation.getMeter(connectionId, endDBMonth);
        long consumption = endMeter.getReading()  - startMeter.getReading();
        ConsumptionDto consumptionDto = new ConsumptionDto(connectionId, startMonth, endMonth, String.valueOf(consumption));
        return consumptionDto;
    }

    public List<ValidationDto> addMeter(String connectionId, MeterValueDto meterValueDto) throws PowerfilerException {
        if(connectionId == null || meterValueDto == null){
            throw new PowerfilerException(String.format(ExceptionMessageCodeConstants.DATA_SENT_EMPTY_ERROR_MESSAGE, "ConnectionId or Data"), ExceptionMessageCodeConstants.DATA_SENT_EMPTY_ERROR_CODE, true);
        }
        Connection connection = getConnection(connectionId);
        Profile profile = getProfile(connectionId);
        isValidMonth(meterValueDto.getMonth());
        Meter meter = meterOperation.getMeter(connectionId, meterValueDto.getMonth().toUpperCase());
        if(meter!=null){
            throw new PowerfilerException(String.format(ExceptionMessageCodeConstants.DELETE_FIRST_DATA_EXCEPTION_MESSAGE,"Meter","Meter", meter.getId()), ExceptionMessageCodeConstants.DELETE_FIRST_DATA_EXCEPTION_CODE,
                    HttpStatus.BAD_REQUEST, true);
        }
        MeterDto meterDto = new MeterDto(connection.getConnectionId(), profile.getName(), meterValueDto.getMonth(), meterValueDto.getMeterReading());
        List<ValidationDto> validationDtos= meterOperation.validateMeter(meterDto);
        if(validationDtos.size() == 0){
            meterOperation.saveAndRelateWithConnection(connection, new MeterDto[]{meterDto});
            return new ArrayList<>();
        }
        return validationDtos;
    }

    public void deleteMeter(String connectionId, String month) throws PowerfilerException {
        Connection connection = getConnection(connectionId);
        Meter meter = getMeter(connectionId, month);
        boolean isDeleted =  meterOperation.delete(meter.getId());
        if(!isDeleted)
            throw new PowerfilerException(String.format(ExceptionMessageCodeConstants.DELETE_IS_NOT_SUCCESSFUL_EXCEPTION_MESSAGE, "Meter", month), ExceptionMessageCodeConstants.DELETE_IS_NOT_SUCCESSFUL_EXCEPTION_CODE,
                    HttpStatus.BAD_REQUEST, true);

    }

    private Meter getMeter(String connectionId, String month) throws PowerfilerException {
        Meter meter = meterOperation.getMeter(connectionId, month.toUpperCase());
        if(meter==null){
            throw new PowerfilerException(String.format(ExceptionMessageCodeConstants.NOT_VALID_ENTITY_EXCEPTION_MESSAGE, "Meter", month), ExceptionMessageCodeConstants.NOT_VALID_ENTITY_EXCEPTION_CODE,
                    HttpStatus.BAD_REQUEST, true);
        }
        return meter;
    }

    public List<ValidationDto> updateMeter(String connectionId, String month, String meterReading) throws PowerfilerException {

        Connection connection = getConnection(connectionId);
        isValidMonth(month);
        Meter meter = getMeter(connectionId, month);
        MeterDto meterDto = new MeterDto(null, connectionId, month, meterReading);
        List<ValidationDto> validationDtos= meterOperation.validateMeter(meterDto);
        if(validationDtos.size() == 0){
            meter.setReading(Long.parseLong(meterReading));
            meterOperation.update(meter);
            return new ArrayList<>();
        }
        return validationDtos;
    }

    private void isValidMonth(String month) throws PowerfilerException {
        boolean isValidMonth = powerfilerUtil.isValidMonth(month);
        if(!isValidMonth){
            throw new PowerfilerException(ExceptionMessageCodeConstants.NOT_VALID_MONTH_EXCEPTION_MESSAGE, ExceptionMessageCodeConstants.NOT_VALID_MONTH_EXCEPTION_CODE,
                    HttpStatus.BAD_REQUEST, true);
        }
    }

    public List<ValidationDto> addFraction(String connectionId, FractionValueDto fractionValueDto) throws PowerfilerException {
        Connection connection = getConnection(connectionId);
        isValidMonth(fractionValueDto.getMonth());
        Fraction fraction = fractionOperation.getFraction(connectionId, fractionValueDto.getMonth().toUpperCase());
        if(fraction!=null){
            throw new PowerfilerException(String.format(ExceptionMessageCodeConstants.DELETE_FIRST_DATA_EXCEPTION_MESSAGE,"Fraction","Fraction", fraction.getId()), ExceptionMessageCodeConstants.DELETE_FIRST_DATA_EXCEPTION_CODE,
                    HttpStatus.BAD_REQUEST, true);
        }
        Profile profile = getProfile(connectionId);

        List<ValidationDto> validationDtos = validateFraction(connectionId, Double.parseDouble(fractionValueDto.getFraction()), connection.getFractions(), profile.getName());


        if(validationDtos.size() == 0){
            fractionOperation.saveAndRelateWithConnection(connection, new FractionDto[]{new FractionDto(null, fractionValueDto.getMonth(), fractionValueDto.getFraction())});
            return new ArrayList<>();
        }
        return validationDtos;
    }

    private List<ValidationDto> validateFraction(String connectionId, Double fractionValue, List<Fraction> fractions, String profileName) {
        double totalFraction = 0;
        for(Fraction fractionInner: fractions){
            totalFraction+=fractionInner.getFraction();
        }
        totalFraction+=fractionValue;
        totalFraction = Math.round(totalFraction * 100) / 100;

        List<ValidationDto> validationDtos = new ArrayList<>();
        SumFractionRule sumFractionRule = new SumFractionRule(profileName, connectionId, totalFraction);
        sumFractionRule.validate();
        validationDtos.addAll(sumFractionRule.getValidationDtos());
        return validationDtos;
    }

    private Connection getConnection(String connectionId) throws PowerfilerException {
        Connection connection = connectionRepository.getByConnectionId(connectionId);
        if(connection==null)
            throw new PowerfilerException(String.format(ExceptionMessageCodeConstants.NOT_VALID_ENTITY_EXCEPTION_MESSAGE,"Connection", connectionId),
                    ExceptionMessageCodeConstants.NOT_VALID_ENTITY_EXCEPTION_CODE,
                    HttpStatus.NOT_FOUND, true);
        return connection;
    }

    public void deleteFraction(String connectionId, String month) throws PowerfilerException {
        Connection connection = getConnection(connectionId);
        Fraction fraction = getFraction(connectionId, month);
        isFractionDeleted(month, fraction);
    }

    private void isFractionDeleted(String month, Fraction fraction) throws PowerfilerException {
        boolean isDeleted =  fractionOperation.delete(fraction.getId());
        if(!isDeleted)
            throw new PowerfilerException(String.format(ExceptionMessageCodeConstants.DELETE_IS_NOT_SUCCESSFUL_EXCEPTION_MESSAGE, "Fraction", month), ExceptionMessageCodeConstants.DELETE_IS_NOT_SUCCESSFUL_EXCEPTION_CODE,
                    HttpStatus.BAD_REQUEST, true);
    }

    public List<ValidationDto> updateFraction(String connectionId, String month, String fractionStr) throws PowerfilerException {
        Connection connection = getConnection(connectionId);
        isValidMonth(month);
        Fraction fraction = getFraction(connectionId, month);
        Profile profile = getProfile(connectionId);

        Double fractionDiff = fraction.getFraction() - Double.parseDouble(fractionStr);
        List<ValidationDto> validationDtos = validateFraction(connectionId, fractionDiff, connection.getFractions(), profile.getName());

        if(validationDtos.size() == 0){
            fraction.setFraction(Double.parseDouble(fractionStr));
            fractionOperation.update(fraction);
            return new ArrayList<>();
        }
        return validationDtos;
    }

    private Profile getProfile(String connectionId) throws PowerfilerException {
        Profile profile = profileOperation.findOne(connectionId);
        if(profile == null){
            throw new PowerfilerException(String.format(ExceptionMessageCodeConstants.NOT_VALID_ENTITY_EXCEPTION_MESSAGE,"Profile", connectionId),
                    ExceptionMessageCodeConstants.NOT_VALID_ENTITY_EXCEPTION_CODE,
                    HttpStatus.NOT_FOUND, true);
        }
        return profile;
    }

    private Fraction getFraction(String connectionId, String month) throws PowerfilerException {
        Fraction fraction = fractionOperation.getFraction(connectionId, month.toUpperCase());
        if(fraction==null){
            throw new PowerfilerException(String.format(ExceptionMessageCodeConstants.NOT_VALID_ENTITY_EXCEPTION_MESSAGE,"Fraction", month),
                    ExceptionMessageCodeConstants.NOT_VALID_ENTITY_EXCEPTION_CODE,
                    HttpStatus.NOT_FOUND, true);
        }
        return fraction;
    }
}
