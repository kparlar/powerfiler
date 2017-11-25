package com.ph.powerfiler.controller.data;


import com.ph.powerfiler.model.entity.Meter;
import com.ph.powerfiler.operation.HasMeterOperation;
import com.ph.powerfiler.operation.MeterOperation;
import com.ph.powerfiler.repository.MeterRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/api/data/powerfiler/v1/meters")
public class MeterDataController extends AbstractPowerfilerDataController<Meter, String> {
    @Autowired
    private MeterOperation meterOperation;
    @Autowired
    private HasMeterOperation hasMeterOperation;
    @Autowired
    public MeterDataController(MeterRepository meterRepository){
        super(meterRepository);
    }


    /*@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete Entity",
            notes = "Deletes an entity with the given id.\n\nUses the Powerfiler App database at the backend.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Entity with id is found and deleted"),
            @ApiResponse(code = 404, message = "Entity is not found or invalid, no delete occured.")})
    public ResponseEntity<String> delete(@PathVariable String id) {
        boolean isDeleted = meterOperation.delete(id);
        if (isDeleted) {
            return new ResponseEntity<>("Successfully removed entity.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Entity is not found.", HttpStatus.NOT_FOUND);
        }
    }*/





}
