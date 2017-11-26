package com.ph.powerfiler.operation;

import com.ph.powerfiler.exception.ExceptionMessage;
import com.ph.powerfiler.util.MessageCodeConstants;
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

import javax.validation.Valid;
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
            exceptionMessage.getErrors().add(String.format(MessageCodeConstants.DATA_SENT_EMPTY_ERROR_MESSAGE, "Profile"));
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
                ValidationDto validationDto = new ValidationDto(String.format(MessageCodeConstants.DATA_SENT_EMPTY_ERROR_MESSAGE, "Connection"), MessageCodeConstants.DATA_SENT_EMPTY_ERROR_CODE);
                validationDtos.add(validationDto);
                return null;
            }
            for(ConnectionDto connectionDto : connectionDtos){
                ConnectionTreeMapDto connectionTreeMapDto;
                if(connectionTreeMap==null){
                    connectionTreeMap = new HashMap<>();
                    connectionTreeMapDto = new ConnectionTreeMapDto();
                    if(connectionDto.getConnectionId() == null){
                        ValidationDto validationDto = new ValidationDto(String.format(MessageCodeConstants.DATA_SENT_EMPTY_ERROR_MESSAGE, "ConnectionId"), MessageCodeConstants.DATA_SENT_EMPTY_ERROR_CODE);
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
                    ValidationDto validationDto = new ValidationDto(String.format(MessageCodeConstants.DATA_SENT_EMPTY_ERROR_MESSAGE, "ConnectionData"), MessageCodeConstants.DATA_SENT_EMPTY_ERROR_CODE);
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
            exceptionMessage.getErrors().add(String.format(MessageCodeConstants.DATA_SENT_EMPTY_ERROR_MESSAGE, "CSV"));
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
            ValidationDto validationDto = new ValidationDto(String.format(MessageCodeConstants.DATA_SENT_EMPTY_ERROR_MESSAGE, "Meter"), MessageCodeConstants.DATA_SENT_EMPTY_ERROR_CODE);
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
            ValidationDto validationDto = new ValidationDto(String.format(MessageCodeConstants.DATA_SENT_EMPTY_ERROR_MESSAGE, "Fraction"), MessageCodeConstants.DATA_SENT_EMPTY_ERROR_CODE);
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
            throw new PowerfilerException(String.format(MessageCodeConstants.DATA_SENT_EMPTY_ERROR_MESSAGE, "ConnectionId or StartMonth or EndMonth"), MessageCodeConstants.DATA_SENT_EMPTY_ERROR_CODE, true);
        }
        String startDBMonth = powerfilerUtil.convertCalendarMonthToDBMonth(startMonth);
        String endDBMonth = powerfilerUtil.convertCalendarMonthToDBMonth(endMonth);
        if(startDBMonth==null || endDBMonth==null){
            throw new PowerfilerException(MessageCodeConstants.NOT_VALID_MONTHS_EXCEPTION_MESSAGE, MessageCodeConstants.NOT_VALID_MONTHS_EXCEPTION_CODE, true);
        }

        Meter startMeter = meterOperation.getMeter(connectionId, startDBMonth);
        Meter endMeter = meterOperation.getMeter(connectionId, endDBMonth);
        long consumption = endMeter.getReading()  - startMeter.getReading();
        ConsumptionDto consumptionDto = new ConsumptionDto(connectionId, startMonth, endMonth, String.valueOf(consumption));
        return consumptionDto;
    }

    public ExceptionMessage addMeter(String connectionId, MeterValueDto meterValueDto) throws PowerfilerException {
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        if(connectionId == null || meterValueDto == null){
            throw new PowerfilerException(String.format(MessageCodeConstants.DATA_SENT_EMPTY_ERROR_MESSAGE, "ConnectionId or Data"),
                    MessageCodeConstants.DATA_SENT_EMPTY_ERROR_CODE, HttpStatus.BAD_REQUEST, true);
        }
        Connection connection = getConnection(connectionId);
        Profile profile = getProfile(connectionId);
        isValidMonth(meterValueDto.getMonth());
        Meter meter = meterOperation.getMeter(connectionId, meterValueDto.getMonth().toUpperCase());
        if(meter!=null){
            throw new PowerfilerException(String.format(MessageCodeConstants.DELETE_FIRST_DATA_EXCEPTION_MESSAGE,"Meter","Meter", meter.getId()),
                    MessageCodeConstants.DELETE_FIRST_DATA_EXCEPTION_CODE,HttpStatus.BAD_REQUEST, true);
        }
        MeterDto meterDto = new MeterDto(connection.getConnectionId(), profile.getName(), meterValueDto.getMonth(), meterValueDto.getMeterReading());
        List<ValidationDto> validationDtos= meterOperation.validateMeter(meterDto);
        exceptionMessage.setValidationDtos(validationDtos);
        if(validationDtos.size() == 0){
            meterOperation.saveAndRelateWithConnection(connection, new MeterDto[]{meterDto});
        }
        return exceptionMessage;
    }

    public ExceptionMessage deleteMeter(String connectionId, String month) throws PowerfilerException {
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        if (connectionId == null || month == null) {
            throw new PowerfilerException(String.format(MessageCodeConstants.DATA_SENT_EMPTY_ERROR_MESSAGE, "ConnectionId or Month"), MessageCodeConstants.DATA_SENT_EMPTY_ERROR_CODE, HttpStatus.BAD_REQUEST, true);
        }
        Connection connection = getConnection(connectionId);
        Meter meter = getMeter(connectionId, month);
        boolean isDeleted = meterOperation.delete(meter.getId());
        List<ValidationDto> validationDtos = new ArrayList<>();
        ValidationDto validationDto;
        if (!isDeleted) {
            validationDtos.add(new ValidationDto(String.format(MessageCodeConstants.DELETE_IS_NOT_SUCCESSFUL_EXCEPTION_MESSAGE, "Meter", month), MessageCodeConstants.DELETE_IS_NOT_SUCCESSFUL_EXCEPTION_CODE));
        } else {
            validationDtos.add(new ValidationDto(String.format(MessageCodeConstants.SUCCESSFULLY_DELETED_SUCCESS_MESSAGE, "Meter", month), MessageCodeConstants.SUCCESSFULLY_DELETED_SUCCESS_CODE));
        }
        exceptionMessage.setValidationDtos(validationDtos);
        return exceptionMessage;
    }

    private Meter getMeter(String connectionId, String month) throws PowerfilerException {
        Meter meter = meterOperation.getMeter(connectionId, month.toUpperCase());
        if(meter==null){
            throw new PowerfilerException(String.format(MessageCodeConstants.NOT_VALID_ENTITY_EXCEPTION_MESSAGE, "Meter", month), MessageCodeConstants.NOT_VALID_ENTITY_EXCEPTION_CODE,
                    HttpStatus.NOT_FOUND, true);
        }
        return meter;
    }

    public ExceptionMessage updateMeter(String connectionId, String month, String meterReading) throws PowerfilerException {
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        if(connectionId == null || month == null || meterReading == null){
            throw new PowerfilerException(String.format(MessageCodeConstants.DATA_SENT_EMPTY_ERROR_MESSAGE, "ConnectionId or Month or MeterReading"), MessageCodeConstants.DATA_SENT_EMPTY_ERROR_CODE, true);
        }

        Connection connection = getConnection(connectionId);
        isValidMonth(month);
        Meter meter = getMeter(connection.getConnectionId(), month);
        Profile profile = getProfile(connectionId);
        MeterDto meterDto = new MeterDto(connection.getConnectionId(), profile.getName(), month, meterReading);
        List<ValidationDto> validationDtos= meterOperation.validateMeter(meterDto);
        exceptionMessage.setValidationDtos(validationDtos);
        if(validationDtos.size() == 0){
            meter.setReading(Long.parseLong(meterReading));
            meterOperation.update(meter);
        }
        return exceptionMessage;
    }

    private void isValidMonth(String month) throws PowerfilerException {
        boolean isValidMonth = powerfilerUtil.isValidMonth(month);
        if(!isValidMonth){
            throw new PowerfilerException(MessageCodeConstants.NOT_VALID_MONTH_EXCEPTION_MESSAGE, MessageCodeConstants.NOT_VALID_MONTH_EXCEPTION_CODE,
                    HttpStatus.BAD_REQUEST, true);
        }
    }

    public ExceptionMessage addFraction(String connectionId, FractionValueDto fractionValueDto) throws PowerfilerException {
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        if(connectionId == null || fractionValueDto == null){
            throw new PowerfilerException(String.format(MessageCodeConstants.DATA_SENT_EMPTY_ERROR_MESSAGE, "ConnectionId or Body"), MessageCodeConstants.DATA_SENT_EMPTY_ERROR_CODE, true);
        }
        Connection connection = getConnection(connectionId);
        isValidMonth(fractionValueDto.getMonth());
        Profile profile = getProfile(connectionId);
        Fraction fraction = fractionOperation.getFraction(connectionId, fractionValueDto.getMonth().toUpperCase());
        if(fraction!=null){
            throw new PowerfilerException(String.format(MessageCodeConstants.DELETE_FIRST_DATA_EXCEPTION_MESSAGE,"Fraction","Fraction", fraction.getId()), MessageCodeConstants.DELETE_FIRST_DATA_EXCEPTION_CODE,
                    HttpStatus.BAD_REQUEST, true);
        }


        List<ValidationDto> validationDtos = fractionOperation.validateFraction(connectionId, Double.parseDouble(fractionValueDto.getFraction()), connection.getFractions(), profile.getName());
        exceptionMessage.setValidationDtos(validationDtos);

        if(validationDtos.size() == 0){
            fractionOperation.saveAndRelateWithConnection(connection, new FractionDto[]{new FractionDto(null, fractionValueDto.getMonth(), fractionValueDto.getFraction())});
        }
        return exceptionMessage;
    }



    private Connection getConnection(String connectionId) throws PowerfilerException {
        Connection connection = connectionRepository.getByConnectionId(connectionId);
        if(connection==null)
            throw new PowerfilerException(String.format(MessageCodeConstants.NOT_VALID_ENTITY_EXCEPTION_MESSAGE,"Connection", connectionId),
                    MessageCodeConstants.NOT_VALID_ENTITY_EXCEPTION_CODE,
                    HttpStatus.NOT_FOUND, true);
        return connection;
    }

    public ExceptionMessage deleteFraction(String connectionId, String month) throws PowerfilerException {
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        if(connectionId == null || month == null){
            throw new PowerfilerException(String.format(MessageCodeConstants.DATA_SENT_EMPTY_ERROR_MESSAGE, "ConnectionId or Month"), MessageCodeConstants.DATA_SENT_EMPTY_ERROR_CODE, true);
        }
        Connection connection = getConnection(connectionId);
        Fraction fraction = getFraction(connectionId, month);
        boolean isDeleted = fractionOperation.delete(fraction.getId());
        List<ValidationDto> validationDtos = new ArrayList<>();
        exceptionMessage.setValidationDtos(validationDtos);
        if (!isDeleted) {
            validationDtos.add(new ValidationDto(String.format(MessageCodeConstants.DELETE_IS_NOT_SUCCESSFUL_EXCEPTION_MESSAGE, "Meter", month), MessageCodeConstants.DELETE_IS_NOT_SUCCESSFUL_EXCEPTION_CODE));
        } else {
            validationDtos.add(new ValidationDto(String.format(MessageCodeConstants.SUCCESSFULLY_DELETED_SUCCESS_MESSAGE, "Meter", month), MessageCodeConstants.SUCCESSFULLY_DELETED_SUCCESS_CODE));
        }
        return exceptionMessage;
    }


    public ExceptionMessage updateFraction(String connectionId, String month, String fractionStr) throws PowerfilerException {
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        if(connectionId == null || month == null || fractionStr == null){
            throw new PowerfilerException(String.format(MessageCodeConstants.DATA_SENT_EMPTY_ERROR_MESSAGE, "ConnectionId or Month or MeterReading"), MessageCodeConstants.DATA_SENT_EMPTY_ERROR_CODE, true);
        }
        Connection connection = getConnection(connectionId);
        isValidMonth(month);
        Fraction fraction = getFraction(connectionId, month);
        Profile profile = getProfile(connectionId);

        Double fractionDiff = fraction.getFraction() - Double.parseDouble(fractionStr);
        List<ValidationDto> validationDtos = fractionOperation.validateFraction(connectionId, fractionDiff, connection.getFractions(), profile.getName());
        exceptionMessage.setValidationDtos(validationDtos);
        if(validationDtos.size() == 0){
            fraction.setFraction(Double.parseDouble(fractionStr));
            fractionOperation.update(fraction);
        }
        return exceptionMessage;
    }

    private Profile getProfile(String connectionId) throws PowerfilerException {
        Profile profile = profileOperation.findOne(connectionId);
        if(profile == null){
            throw new PowerfilerException(String.format(MessageCodeConstants.NOT_VALID_ENTITY_EXCEPTION_MESSAGE,"Profile", connectionId),
                    MessageCodeConstants.NOT_VALID_ENTITY_EXCEPTION_CODE,
                    HttpStatus.NOT_FOUND, true);
        }
        return profile;
    }

    private Fraction getFraction(String connectionId, String month) throws PowerfilerException {
        Fraction fraction = fractionOperation.getFraction(connectionId, month.toUpperCase());
        if(fraction==null){
            throw new PowerfilerException(String.format(MessageCodeConstants.NOT_VALID_ENTITY_EXCEPTION_MESSAGE,"Fraction", month),
                    MessageCodeConstants.NOT_VALID_ENTITY_EXCEPTION_CODE,
                    HttpStatus.NOT_FOUND, true);
        }
        return fraction;
    }
}
