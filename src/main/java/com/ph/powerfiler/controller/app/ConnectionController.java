package com.ph.powerfiler.controller.app;

import com.ph.powerfiler.exception.PowerfilerException;
import com.ph.powerfiler.model.dto.ConsumptionDto;
import com.ph.powerfiler.operation.ConnectionOperation;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/app/powerfiler/v1/connections")
public class ConnectionController {

    @Autowired
    private ConnectionOperation connectionOperation;

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
