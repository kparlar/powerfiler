package com.ph.powerfiler.controller.data;

import com.ph.powerfiler.exception.ExceptionMessage;
import com.ph.powerfiler.exception.PowerfilerException;
import com.ph.powerfiler.model.dto.FractionValueDto;
import com.ph.powerfiler.model.dto.MeterValueDto;
import com.ph.powerfiler.model.dto.ValidationDto;
import com.ph.powerfiler.model.entity.Connection;
import com.ph.powerfiler.operation.ConnectionOperation;
import com.ph.powerfiler.operation.MeterOperation;
import com.ph.powerfiler.repository.ConnectionRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/api/data/powerfiler/v1/connections")
public class ConnectionDataController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ConnectionOperation connectionOperation;


    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {PowerfilerException.class})
    @RequestMapping(value = "/{connectionId}/meters",method = RequestMethod.PUT)
    @ApiOperation(value = "Insert Connection Details", notes = "Saves Meter Readings\n " +
            "If any internal error occured, GlobalControllerException " +
            "handler return 500 error with unique id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public ResponseEntity<ExceptionMessage> addMeter(@PathVariable(value = "connectionId") String connectionId, @RequestBody MeterValueDto meterValueDto) throws PowerfilerException {
        ExceptionMessage exceptionMessage = connectionOperation.addMeter(connectionId, meterValueDto);
        if(exceptionMessage.getErrors().size()>0){
            return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(exceptionMessage, HttpStatus.OK);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {PowerfilerException.class})
    @RequestMapping(value = "/{connectionId}/meters/{month}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete Meter and delete HasMeter relation",
            notes = "Deletes Meter according to given ConnectionId and Month. Also delete HasMeter relation\n " +
                    "If any internal error occured, GlobalControllerException " +
                    "handler return 500 error with unique id")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Entity with id is found and deleted"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<ExceptionMessage> deleteMeter(@PathVariable(value = "connectionId") String connectionId, @PathVariable(value = "month") String month) throws PowerfilerException {
        ExceptionMessage exceptionMessage =connectionOperation.deleteMeter(connectionId, month);
        if(exceptionMessage.getErrors().size()>0){
            return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(exceptionMessage, HttpStatus.OK);
        }


    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {PowerfilerException.class})
    @RequestMapping(value = "/{connectionId}/meters/{month}",method = RequestMethod.POST)
    @ApiOperation(value = "Insert Connection Details", notes = "Update Meter Readings\n " +
            "If any internal error occured, GlobalControllerException " +
            "handler return 500 error with unique id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public ResponseEntity<ExceptionMessage> updateMeter(@PathVariable(value = "connectionId") String connectionId, @PathVariable(value = "month") String month, @RequestBody String meterReading) throws PowerfilerException {
        ExceptionMessage exceptionMessage = connectionOperation.updateMeter(connectionId, month, meterReading);
        if(exceptionMessage.getErrors().size()>0){
            return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(exceptionMessage, HttpStatus.OK);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {PowerfilerException.class})
    @RequestMapping(value = "/{connectionId}/fractions",method = RequestMethod.PUT)
    @ApiOperation(value = "Insert Connection Details", notes = "Saves Fraction to H2\n " +
            "If any internal error occured, GlobalControllerException " +
            "handler return 500 error with unique id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public ResponseEntity<ExceptionMessage> addFraction(@PathVariable(value = "connectionId") String connectionId, @RequestBody FractionValueDto fractionValueDto) throws PowerfilerException {
        ExceptionMessage exceptionMessage = connectionOperation.addFraction(connectionId, fractionValueDto);
        if(exceptionMessage.getErrors().size()>0){
            return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(exceptionMessage, HttpStatus.OK);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {PowerfilerException.class})
    @RequestMapping(value = "/{connectionId}/fractions/{month}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete Fraction and delete HasFraction relation",
            notes = "Deletes Fraction according to given ConnectionId and Month. Also delete HasFraction relation\n " +
                    "If any internal error occured, GlobalControllerException " +
                    "handler return 500 error with unique id")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Entity with id is found and deleted"),
            @ApiResponse(code = 404, message = "Entity is not found or invalid, no delete occured.")})
    public ResponseEntity<ExceptionMessage> deleteFraction(@PathVariable(value = "connectionId") String connectionId, @PathVariable(value = "month") String month) throws PowerfilerException {
        ExceptionMessage exceptionMessage = connectionOperation.deleteFraction(connectionId, month);
        if(exceptionMessage.getErrors().size()>0){
            return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(exceptionMessage, HttpStatus.OK);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {PowerfilerException.class})
    @RequestMapping(value = "/{connectionId}/fractions/{month}",method = RequestMethod.POST)
    @ApiOperation(value = "Insert Connection Details", notes = "Update Fraction Readings\n " +
            "If any internal error occured, GlobalControllerException " +
            "handler return 500 error with unique id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public ResponseEntity<ExceptionMessage> updateFraction(@PathVariable(value = "connectionId") String connectionId, @PathVariable(value = "month") String month, @RequestBody String fraction) throws PowerfilerException {
        ExceptionMessage exceptionMessage = connectionOperation.updateFraction(connectionId, month, fraction);
        if(exceptionMessage.getErrors().size()>0){
            return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(exceptionMessage, HttpStatus.OK);
        }
    }
}
