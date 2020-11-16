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
import com.digitax.model.TaxTips;
import com.digitax.payload.ApiRes;
import com.digitax.payload.request.CreateTaxTipsRequest;
import com.digitax.payload.request.UpdateTaxTipRequest;
import com.digitax.repository.TaxTipsRepository;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class TaxTipsController {
	
	@Autowired
	TaxTipsRepository taxTipsRepository;
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("/tax-tips")
    public ResponseEntity<?> getTaxTips() {
		try {
		List<TaxTips> QstnObj = taxTipsRepository.findAll();
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
	@PostMapping("/tax-tips")
    public ResponseEntity<?> createTaxTips(@Valid @RequestBody CreateTaxTipsRequest TaxTipsRequest,BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			ArrayList<?> errors = (ArrayList<?>) bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
			 JSONObject statusObj = new JSONObject();
		        statusObj.put("status_code", ResponseConstants.VALIDATION_ERROR);
		        statusObj.put("message", errors);
		       
		        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));
        }
		try {
		TaxTips createObj = new TaxTips();
		createObj.setTitle(TaxTipsRequest.getTitle());
		createObj.setTaxTipLabel(TaxTipsRequest.getTaxTipLabel());
		createObj.setImage(TaxTipsRequest.getImage());
		createObj.setNumberOfQuestions(TaxTipsRequest.getNumberOfQuestions());
		createObj.setCreatedAt(System.currentTimeMillis());
		createObj.setIsVisible(true);
		
		TaxTips createdObj = taxTipsRepository.save(createObj);
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
	@PutMapping("/tax-tips")
    public ResponseEntity<?> updateTaxTips(@Valid @RequestBody UpdateTaxTipRequest TaxTipsRequest,BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			ArrayList<?> errors = (ArrayList<?>) bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
			 JSONObject statusObj = new JSONObject();
		        statusObj.put("status_code", ResponseConstants.VALIDATION_ERROR);
		        statusObj.put("message", errors);
		       
		        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));
        }
		try {
		Optional<TaxTips> findObj = taxTipsRepository.findById(TaxTipsRequest.getTaxId());
		if(!findObj.isPresent()) {
			JSONObject statusObj = new JSONObject();
	        statusObj.put("status_code", ResponseConstants.VALIDATION_ERROR);
	        statusObj.put("message", "Tax Tip not found");
	        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));
		}
		else
		{
		TaxTips upadateObj = findObj.get();
		
		if(TaxTipsRequest.getTitle()!= null)
		{
			upadateObj.setTitle(TaxTipsRequest.getTitle());
		}
		if(TaxTipsRequest.getTaxTipLabel()!= null)
		{
			upadateObj.setTaxTipLabel(TaxTipsRequest.getTaxTipLabel());
		}
		if(TaxTipsRequest.getImage()!= null)
		{
			upadateObj.setImage(TaxTipsRequest.getImage());
		}
		if(TaxTipsRequest.getNumberOfQuestions()!= null)
		{
			upadateObj.setNumberOfQuestions(TaxTipsRequest.getNumberOfQuestions());
		}
		upadateObj.setUpdatedAt(System.currentTimeMillis());
		System.out.println(upadateObj);
		taxTipsRepository.save(upadateObj);
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
	@DeleteMapping("/tax-tips/{id}")
	public ResponseEntity<?> deleteTaxTips(@PathVariable long id) {
		try {
		taxTipsRepository.deleteById(id);
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
