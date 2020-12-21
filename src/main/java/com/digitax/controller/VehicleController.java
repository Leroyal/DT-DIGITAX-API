package com.digitax.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitax.constants.ResponseConstants;
import com.digitax.model.Vehicle;
import com.digitax.payload.ApiRes;
import com.digitax.payload.request.AddVehicleRequest;
import com.digitax.repository.VehicleRepository;
import com.digitax.security.jwt.UserSession;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class VehicleController {
	@Autowired
	VehicleRepository vehicleRepository;
	
	
	/**##
     * 
     * @param no paramaeter required
     * @return
     * This is a simple get functionality of all vehicle stored in DB
     * Model name is vehicle
     * Used VehicleRepository Repository for data access
     */
	@SuppressWarnings("unchecked")
	@GetMapping("/get-all-vehicle")
    public ResponseEntity<?> getVehicles() {
		try {
		List<Vehicle> VehicleObj = vehicleRepository.findAll();
		JSONObject statusObj = new JSONObject();
        statusObj.put("status_code", ResponseConstants.SUCCESS);
        statusObj.put("message", "SUCCESS");
        return new ResponseEntity<>(ApiRes.success(VehicleObj, statusObj), HttpStatus.OK);	
		} 
		catch (Exception e) {
		  	   JSONObject statusObj = new JSONObject();
		         statusObj.put("status_code",ResponseConstants.INTERNAL_SERVER_ERROR);
		         statusObj.put("message", "FAILURE");
		         return new ResponseEntity<>(ApiRes.success(e.getMessage(), statusObj), HttpStatus.OK);	
		         }
    }
	
	
	
	/**##
     * 
     * @param no paramaeter required
     * @return
     * This is a simple get functionality of all vehicle stored in DB by userId
     * Checking the session by user session snd has role functionality
     * Model name is vehicle
     * Used VehicleRepository Repository for data access
     */
	@SuppressWarnings("unchecked")
	@GetMapping("/get-vehicle-by-user")
	@PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getVehiclesByUser() {
		try {
	    List<Vehicle> VehicleObj = vehicleRepository.findByUserId(UserSession.getUserId());
		JSONObject statusObj = new JSONObject();
        statusObj.put("status_code", ResponseConstants.SUCCESS);
        statusObj.put("message", "SUCCESS");
        return new ResponseEntity<>(ApiRes.success(VehicleObj, statusObj), HttpStatus.OK);	
		} 
		catch (Exception e) {
		  	   JSONObject statusObj = new JSONObject();
		         statusObj.put("status_code",ResponseConstants.INTERNAL_SERVER_ERROR);
		         statusObj.put("message", "FAILURE");
		         return new ResponseEntity<>(ApiRes.success(e.getMessage(), statusObj), HttpStatus.OK);	
		         }
    }
	
	/**##
     * 
     * @param AddVehicleRequest
     * @return
     * This is a simple Save functionality vehicle 
     * Method Post, Getting userId from session
     * Checking the session by user session snd has role functionality
     * Model name is vehicle
     * Used VehicleRepository Repository for data access
     */
	
	@PreAuthorize("hasRole('USER')")
	@SuppressWarnings("unchecked")
	@PostMapping("/add-vehicle")
    public ResponseEntity<?> createTaxTips(@Valid @RequestBody AddVehicleRequest vehicleRequest,BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			ArrayList<?> errors = (ArrayList<?>) bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
			 JSONObject statusObj = new JSONObject();
		        statusObj.put("status_code", ResponseConstants.VALIDATION_ERROR);
		        statusObj.put("message", errors);
		       
		        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));
        }
		try {
		Vehicle createObj = new Vehicle();
		createObj.setUserId(UserSession.getUserId());
		createObj.setYear(vehicleRequest.getYear());
		createObj.setMake(vehicleRequest.getMake());
		createObj.setModel(vehicleRequest.getModel());
		createObj.setNickName(vehicleRequest.getNickName());
		createObj.setOdoMeter(vehicleRequest.getOdoMeter());
		createObj.setNotes(vehicleRequest.getNotes());
		createObj.setCreatedAt(System.currentTimeMillis());
		if(vehicleRequest.getPrimaryVehicle().equals("true")){
			createObj.setPrimaryVehicle(true);
		}
		else
		{
			createObj.setPrimaryVehicle(false);
		}
		
		Vehicle createdObj = vehicleRepository.save(createObj);
		JSONObject statusObj = new JSONObject();
        statusObj.put("status_code", ResponseConstants.SUCCESS);
        statusObj.put("message", "SUCCESS");
        return new ResponseEntity<>(ApiRes.success(createdObj, statusObj), HttpStatus.OK);	
		} 
		catch (Exception e) {
		  	   JSONObject statusObj = new JSONObject();
		         statusObj.put("status_code",ResponseConstants.INTERNAL_SERVER_ERROR);
		         statusObj.put("message", "FAILURE");
		         return new ResponseEntity<>(ApiRes.success(e.getMessage(), statusObj), HttpStatus.OK);	
		         }
    }
	
	

}
