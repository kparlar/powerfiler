package com.ph.powerfiler.controller.app;

import com.ph.powerfiler.exception.ExceptionMessage;
import com.ph.powerfiler.exception.PowerfilerException;
import com.ph.powerfiler.model.dto.ConsumptionDto;
import com.ph.powerfiler.model.dto.ProfileDto;
import com.ph.powerfiler.model.entity.Fraction;
import com.ph.powerfiler.model.entity.Meter;
import com.ph.powerfiler.operation.ConnectionOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


import java.util.List;

@RestController
@RequestMapping("/api/app/powerfiler/v1/connections")
public class ConnectionController {

    @Autowired
    private ConnectionOperation connectionOperation;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @RequestMapping(method= RequestMethod.PUT)
    @ApiOperation(value = "Insert Connection Details", notes = "Saves Meter Readings and Fractions to H2 \n " +
            "If any internal error occured, GlobalControllerException " +
            "handler return 500 error with unique id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public ResponseEntity<ExceptionMessage> saveConnections(@RequestBody(required=true) List<ProfileDto> profileDtos){
        ExceptionMessage exceptionMessage = connectionOperation.saveConnections(profileDtos);
        if(exceptionMessage.getErrors().size() > 0){
            return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(exceptionMessage, HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/{connectionId}/meters",method = RequestMethod.GET)
    @ApiOperation(value = "Get Meters of Connection", notes = "If any internal error occured, GlobalControllerException " +
            "handler return 500 error with unique id")
    @ApiResponses(value =
            {@ApiResponse(code = 200, message = "Successfully get Meters of Connections"),
                    @ApiResponse(code = 500, message = "Internal Server Error") })
    public ResponseEntity<List<Meter>> getMeters(@PathVariable(value = "connectionId") String connectionId){

        return new ResponseEntity<>(connectionOperation.getMeters(connectionId), HttpStatus.OK);
    }

    @RequestMapping(value = "/{connectionId}/fractions",method = RequestMethod.GET)
    @ApiOperation(value = "Get Fractions of Connection", notes = "If any internal error occured, GlobalControllerException " +
            "handler return 500 error with unique id")
    @ApiResponses(value =
            {@ApiResponse(code = 200, message = "Successfully get Fractions of Connections"),
                    @ApiResponse(code = 500, message = "Internal Server Error") })
    public ResponseEntity<List<Fraction>> getFractions(@PathVariable(value = "connectionId") String connectionId){

        return new ResponseEntity<>(connectionOperation.getFractions(connectionId), HttpStatus.OK);
    }

    @RequestMapping(value = "/{connectionId}/consumption",method = RequestMethod.GET)
    @ApiOperation(value = "Get Consumption between given start and end month period", notes = "If any internal error occured, GlobalControllerException " +
            "handler return 500 error with unique id")
    @ApiResponses(value =
            {@ApiResponse(code = 200, message = "Successfully get Consumption between given start and end month period"),
                    @ApiResponse(code = 500, message = "Internal Server Error") })
    public ResponseEntity<ConsumptionDto> getConsumption(@PathVariable(value = "connectionId") String connectionId, @RequestParam(value="startMonth")String startMonth, @RequestParam(value = "endMonth")String endMonth) throws PowerfilerException {

        return new ResponseEntity<>(connectionOperation.getConsumption(connectionId, startMonth, endMonth), HttpStatus.OK);
    }







}
