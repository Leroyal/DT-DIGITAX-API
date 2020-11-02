package com.digitax.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitax.constants.ResponseConstants;
import com.digitax.model.RestrictTax;
import com.digitax.payload.ApiRes;
import com.digitax.payload.request.CreateRestrictTax;
import com.digitax.payload.request.updateRestrictTax;
import com.digitax.repository.RestrictTaxRepository;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class TaxRestrictController {
	
	@Autowired
	RestrictTaxRepository restrictTaxRepository;
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("/restrict-tax")
    public ResponseEntity<?> getTaxTips() {
		try {
		List<RestrictTax> QstnObj = restrictTaxRepository.findAll();
		JSONObject statusObj = new JSONObject();
        statusObj.put("status_code", ResponseConstants.SUCCESS);
        statusObj.put("message", "SUCCESS");
        return new ResponseEntity<>(ApiRes.success(QstnObj, statusObj), HttpStatus.OK);	
		} 
		catch (Exception e) {
		  	   JSONObject statusObj = new JSONObject();
		         statusObj.put("status_code",ResponseConstants.INTERNAL_SERVER_ERROR);
		         statusObj.put("message", "FAILURE");
		         return new ResponseEntity<>(ApiRes.success(e.getMessage(), statusObj), HttpStatus.OK);	
		         }
    }
	
	
	@SuppressWarnings("unchecked")
	@PostMapping("/restrict-tax")
    public ResponseEntity<?> createTaxTips(@Valid @RequestBody CreateRestrictTax createRestrictTax,BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			ArrayList<?> errors = (ArrayList<?>) bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
			 JSONObject statusObj = new JSONObject();
		        statusObj.put("status_code", ResponseConstants.VALIDATION_ERROR);
		        statusObj.put("message", errors);
		       
		        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));
        }
		try {
		RestrictTax createObj = new RestrictTax();
		createObj.setYear(createRestrictTax.getYear());
		createObj.setStartDate(createRestrictTax.getStartDate());
		createObj.setEndDate(createRestrictTax.getEndDate());
		createObj.setCreatedAt(System.currentTimeMillis());
		
		RestrictTax createdObj = restrictTaxRepository.save(createObj);
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
	
	@SuppressWarnings("unchecked")
	@PutMapping("/restrict-tax")
    public ResponseEntity<?> updateTaxTips(@Valid @RequestBody updateRestrictTax updateRestrictTax,BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			ArrayList<?> errors = (ArrayList<?>) bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
			 JSONObject statusObj = new JSONObject();
		        statusObj.put("status_code", ResponseConstants.VALIDATION_ERROR);
		        statusObj.put("message", errors);
		       
		        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));
        }
		try {
		Optional<RestrictTax> findObj = restrictTaxRepository.findById(updateRestrictTax.getRestrictTaxId());
		if(!findObj.isPresent()) {
			JSONObject statusObj = new JSONObject();
	        statusObj.put("status_code", ResponseConstants.VALIDATION_ERROR);
	        statusObj.put("message", "Restrict Tax not found");
	        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));
		}
		else
		{
			RestrictTax upadateObj = findObj.get();
		
		if(updateRestrictTax.getYear() > 0)
		{
			upadateObj.setYear(updateRestrictTax.getYear());
		}
		if(updateRestrictTax.getEndDate()!= null)
		{
			upadateObj.setEndDate(updateRestrictTax.getEndDate());
		}
		if(updateRestrictTax.getStartDate()!= null)
		{
			upadateObj.setStartDate(updateRestrictTax.getStartDate());
		}
		
		upadateObj.setUpdatedAt(System.currentTimeMillis());
		System.out.println(upadateObj);
		restrictTaxRepository.save(upadateObj);
		JSONObject statusObj = new JSONObject();
        statusObj.put("status_code", ResponseConstants.SUCCESS);
        statusObj.put("message", "SUCCESS");
        return new ResponseEntity<>(ApiRes.success(upadateObj, statusObj), HttpStatus.OK);	
		 }
		} 
		catch (Exception e) {
			   System.out.println(e);
		  	   JSONObject statusObj = new JSONObject();
		         statusObj.put("status_code",ResponseConstants.INTERNAL_SERVER_ERROR);
		         statusObj.put("message", "FAILURE");
		         return new ResponseEntity<>(ApiRes.success(e.getMessage(), statusObj), HttpStatus.OK);	
		         }
    }
	
	@SuppressWarnings("unchecked")
	@DeleteMapping("/restrict-tax/{id}")
	public ResponseEntity<?> deleteTaxTips(@PathVariable long id) {
		try {
	    restrictTaxRepository.deleteById(id);
	    JSONObject statusObj = new JSONObject();
        statusObj.put("status_code", ResponseConstants.SUCCESS);
        statusObj.put("message", "SUCCESS");
        return new ResponseEntity<>(ApiRes.success(null, statusObj), HttpStatus.OK);
		}
	    catch (Exception e) {
		  	   JSONObject statusObj = new JSONObject();
		         statusObj.put("status_code",ResponseConstants.INTERNAL_SERVER_ERROR);
		         statusObj.put("message", "FAILURE");
		         return new ResponseEntity<>(ApiRes.success(e.getMessage(), statusObj), HttpStatus.OK);	
		         }
	}

}
