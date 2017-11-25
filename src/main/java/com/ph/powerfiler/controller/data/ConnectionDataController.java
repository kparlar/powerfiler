package com.ph.powerfiler.controller.data;

import com.ph.powerfiler.exception.PowerfilerException;
import com.ph.powerfiler.model.dto.FractionValueDto;
import com.ph.powerfiler.model.dto.MeterValueDto;
import com.ph.powerfiler.model.dto.ValidationDto;
import com.ph.powerfiler.model.entity.Connection;
import com.ph.powerfiler.operation.ConnectionOperation;
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
public class ConnectionDataController extends AbstractPowerfilerDataController<Connection, String> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ConnectionOperation connectionOperation;


    @Autowired
    public ConnectionDataController(ConnectionRepository connectionRepository){
        super(connectionRepository);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {PowerfilerException.class})
    @RequestMapping(value = "/{connectionId}/meters",method = RequestMethod.PUT)
    @ApiOperation(value = "Insert Connection Details", notes = "Saves Meter Readings")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public ResponseEntity<List<ValidationDto>> addMeter(@PathVariable(value = "connectionId") String connectionId, @RequestBody MeterValueDto meterValueDto) throws PowerfilerException {
        logger.debug("create() with body {} of type {}", meterValueDto, meterValueDto.getClass());
        return new ResponseEntity<>(connectionOperation.addMeter(connectionId, meterValueDto), HttpStatus.OK);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {PowerfilerException.class})
    @RequestMapping(value = "/{connectionId}/meters/{month}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete Meter and delete HasMeter relation",
            notes = "Deletes Meter according to given ConnectionId and Month. Also delete HasMeter relation")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Entity with id is found and deleted"),
            @ApiResponse(code = 404, message = "Entity is not found or invalid, no delete occured.")})
    public ResponseEntity<ValidationDto> deleteMeter(@PathVariable(value = "connectionId") String connectionId, @PathVariable(value = "month") String month) throws PowerfilerException {

        return new ResponseEntity<>(connectionOperation.deleteMeter(connectionId, month), HttpStatus.OK);

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {PowerfilerException.class})
    @RequestMapping(value = "/{connectionId}/meters/{month}",method = RequestMethod.POST)
    @ApiOperation(value = "Insert Connection Details", notes = "Update Meter Readings")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public ResponseEntity<List<ValidationDto>> updateMeter(@PathVariable(value = "connectionId") String connectionId, @PathVariable(value = "month") String month, @RequestBody String meterReading) throws PowerfilerException {
        return new ResponseEntity<>(connectionOperation.updateMeter(connectionId, month, meterReading), HttpStatus.OK);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {PowerfilerException.class})
    @RequestMapping(value = "/{connectionId}/fractions",method = RequestMethod.PUT)
    @ApiOperation(value = "Insert Connection Details", notes = "Saves Fraction to H2")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public ResponseEntity<List<ValidationDto>> addFraction(@PathVariable(value = "connectionId") String connectionId, @RequestBody FractionValueDto fractionValueDto) throws PowerfilerException {
        logger.debug("create() with body {} of type {}", fractionValueDto, fractionValueDto.getClass());
        return new ResponseEntity<>(connectionOperation.addFraction(connectionId, fractionValueDto), HttpStatus.OK);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {PowerfilerException.class})
    @RequestMapping(value = "/{connectionId}/fractions/{month}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete Fraction and delete HasFraction relation",
            notes = "Deletes Fraction according to given ConnectionId and Month. Also delete HasFraction relation")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Entity with id is found and deleted"),
            @ApiResponse(code = 404, message = "Entity is not found or invalid, no delete occured.")})
    public ResponseEntity<ValidationDto> deleteFraction(@PathVariable(value = "connectionId") String connectionId, @PathVariable(value = "month") String month) throws PowerfilerException {
        return new ResponseEntity<>(connectionOperation.deleteFraction(connectionId, month), HttpStatus.OK);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {PowerfilerException.class})
    @RequestMapping(value = "/{connectionId}/fractions/{month}",method = RequestMethod.POST)
    @ApiOperation(value = "Insert Connection Details", notes = "Update Fraction Readings")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public ResponseEntity<List<ValidationDto>> updateFraction(@PathVariable(value = "connectionId") String connectionId, @PathVariable(value = "month") String month, @RequestBody String fraction) throws PowerfilerException {
        return new ResponseEntity<>(connectionOperation.updateFraction(connectionId, month, fraction), HttpStatus.OK);
    }
}
