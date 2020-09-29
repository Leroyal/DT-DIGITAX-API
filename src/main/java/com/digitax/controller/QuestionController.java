package com.digitax.controller;

import java.util.List;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitax.constants.ResponseConstants;
import com.digitax.model.QuestionCategories;
import com.digitax.payload.ApiRes;
import com.digitax.repository.QuestionCategoriesRepository;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class QuestionController {
	
	@Autowired
	QuestionCategoriesRepository questionCategoriesRepository;
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("/question-categories")
    public ResponseEntity<?> getUserTaxHistory() {
		try {
		List<QuestionCategories> QstnObj = questionCategoriesRepository.findAll();
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

}
