package com.ph.powerfiler.controller.data;



import com.ph.powerfiler.exception.PowerfilerException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;


@RestController
public abstract class AbstractPowerfilerDataController<T, I extends Serializable> {
  private Logger logger = LoggerFactory.getLogger(AbstractPowerfilerDataController.class);
  private CrudRepository<T, I> repo;

  public AbstractPowerfilerDataController(CrudRepository<T, I> repo) {
    this.repo = repo;
  }


  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ApiOperation(value = "Get Entity",
      notes = "Gets an entity with the given id.\n\nUses the Powerfiler App database at the backend.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Entity with id is found and returned"),
      @ApiResponse(code = 404, message = "Entity is not found or invalid")})
  public ResponseEntity<T> get(@PathVariable I id) {
    T entity = this.repo.findOne(id);
    if (entity != null) {
      return new ResponseEntity<>(entity, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  @ApiOperation(value = "Delete Entity",
          notes = "Deletes an entity with the given id.\n\nUses the PowerfilerApp database at the backend.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Entity with id is found and deleted"),
          @ApiResponse(code = 404, message = "Entity is not found or invalid, no delete occured.")})
  public ResponseEntity<String> delete(@PathVariable I id) {
    if (this.repo.exists(id)) {
      this.repo.delete(id);
      return new ResponseEntity<>("Successfully removed entity.", HttpStatus.OK);
    } else {
      return new ResponseEntity<>("Entity is not found.", HttpStatus.NOT_FOUND);
    }
  }

  @RequestMapping(method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(value = "Creates Entity",
          notes = "Creates an Entity.\n\nUses the PowerfilerApp database at the backend.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully Created Entity"),
          @ApiResponse(code = 400, message = "Payload is malformed or not proper")})
  public ResponseEntity<T> create(@RequestBody T json) {
    logger.debug("create() with body {} of type {}", json, json.getClass());
    try {
      T created = this.repo.save(json);
      return new ResponseEntity<>(created, HttpStatus.OK);
    } catch (Exception e) {
      logger.error("exception occured in entity create", e);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @ApiOperation(value = "Updates Entity",
          notes = "Updates an Entity.\n\nUses the PowerfilerApp database at the backend.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully Updated Entity"),
          @ApiResponse(code = 404, message = "No entity is found to update")})
  @RequestMapping(value = "/{id}", method = RequestMethod.POST,
          consumes = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<T> update(@PathVariable I id, @RequestBody T json) {

    logger.debug("update() of id#{} with body {}", id, json);
    logger.debug("T json is of type {}", json.getClass());

    T entity = this.repo.findOne(id);
    BeanUtils.copyProperties(json, entity);

    logger.debug("merged entity: {}", entity);

    T updated = this.repo.save(entity);
    logger.debug("updated enitity: {}", updated);
    if (updated != null) {
      return new ResponseEntity<>(updated, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  public CrudRepository<T, I> getRepo() {
    return repo;
  }


}
