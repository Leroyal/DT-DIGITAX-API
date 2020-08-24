package com.digitax.controller;


import com.digitax.payload.ApiRes;
import com.digitax.payload.request.UserDetailsRequest;
import com.digitax.security.jwt.JwtUtils;
import com.digitax.security.jwt.UserSession;
import com.digitax.constants.ResponseConstants;
import com.digitax.model.User;
import com.digitax.model.UserAddress;
import com.digitax.model.UserProfile;
import com.digitax.payload.response.UserDetailsResponse;
import com.digitax.repository.UserAddressRepository;
import com.digitax.repository.UserProfileRepository;
import com.digitax.repository.UserRepository;
import com.digitax.service.UserProfileService;

import antlr.collections.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class UserController {


    @Autowired
    JwtUtils jwtUtils;
    

    @Autowired
    UserProfileRepository userProfileRepository;
    
    @Autowired
    UserAddressRepository userAddressRepository;
    
    
    @Autowired
    UserProfileService userProfileService;
    
    @Autowired
    UserDetailsService userDetailsService;
    
    
    @Autowired
    UserRepository userRepository;
  


    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }


  
    @SuppressWarnings("unchecked")
	@GetMapping("/user-details")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> userProfileDetails() {
    	Optional<UserProfile> detailsObj = userProfileRepository.findByUserId(UserSession.getUserId());
    	Optional<User> user = userRepository.findById(UserSession.getUserId());
    	Optional<UserAddress> addressObj = userAddressRepository.findByUserId(UserSession.getUserId());
    	UserDetailsResponse obj = new UserDetailsResponse(user, detailsObj,addressObj);
        JSONObject statusObj = new JSONObject();
        statusObj.put("status_code", ResponseConstants.SUCCESS);
        statusObj.put("message", "SUCCESS");
        System.out.println(UserSession.getUserId()); 
        return new ResponseEntity<>(ApiRes.success(obj, statusObj), HttpStatus.OK);

    }
    
    
    
	@SuppressWarnings("unchecked")
	@PutMapping("/user-details")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateUserProfileDetails(@Valid @RequestBody UserDetailsRequest userDetails,BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			ArrayList<?> errors = (ArrayList<?>) bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
			 JSONObject statusObj = new JSONObject();
		        statusObj.put("status_code", ResponseConstants.VALIDATION_ERROR);
		        statusObj.put("message", errors);
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiRes.fail(statusObj));
        }
		Optional<UserProfile> detailsObj = userProfileRepository.findByUserId(UserSession.getUserId());
    	Optional<User> user = userRepository.findById(UserSession.getUserId());
    	Optional<UserAddress> addressObj = userAddressRepository.findByUserId(UserSession.getUserId());
    	UserAddress userAddressObj = new UserAddress(
			    userDetails.getAddressLine1(),
			    userDetails.getAddressLine2(),
			    userDetails.getCity(),
			    userDetails.getState(),
			    userDetails.getStateCode(),
			    userDetails.getPostalCode(),
			    userDetails.getCountry(),
			    userDetails.getCountryCode(),
                System.currentTimeMillis());
    	 UserProfile userDetailsObj = new UserProfile(
				    userDetails.getFirstName(),
				    userDetails.getMiddleInitial(),
				    userDetails.getLastName(),
				    userDetails.getDateofbirth(),
	                System.currentTimeMillis());
    	
    	if(addressObj.isEmpty()) {
    	     userAddressObj.setUserId(UserSession.getUserId());
		     userAddressRepository.save(userAddressObj);
    	}
    	else {
    		
		     userAddressRepository.updateAddress(userDetails.getAddressLine1(),
					    userDetails.getAddressLine2(),
					    userDetails.getCity(),
					    userDetails.getState(),
					    userDetails.getStateCode(),
					    userDetails.getPostalCode(),
					    userDetails.getCountry(),
					    userDetails.getCountryCode(),
		                System.currentTimeMillis(),
		                UserSession.getUserId());
    	}
    	
    	
    	 if(detailsObj.isEmpty()) {
    		    userDetailsObj.setUserId(UserSession.getUserId());
    		    userProfileRepository.save(userDetailsObj);
    	        JSONObject statusObj = new JSONObject();
    	        statusObj.put("status_code", ResponseConstants.SUCCESS);
    	        statusObj.put("message", "SUCCESS");
    	   }
    	 else
    	 {
    		userDetailsObj.setUserId(UserSession.getUserId());
    		userProfileService.updateDetails(userDetailsObj);
 	        JSONObject statusObj = new JSONObject();
 	        statusObj.put("status_code", ResponseConstants.SUCCESS);
 	        statusObj.put("message", "SUCCESS");
    	 }
    	 
    	 Optional<UserProfile> userDetailsResp = userProfileRepository.findByUserId(UserSession.getUserId());
     	 Optional<UserAddress> addressResp = userAddressRepository.findByUserId(UserSession.getUserId());
    	 UserDetailsResponse obj = new UserDetailsResponse(user, userDetailsResp,addressResp);
    	 JSONObject statusObj = new JSONObject();
    	 statusObj.put("status_code", ResponseConstants.SUCCESS);
         statusObj.put("message", "SUCCESS");
         System.out.println(UserSession.getUserId()); 
         return new ResponseEntity<>(ApiRes.success(obj, statusObj), HttpStatus.OK);
    	 
    	
    	

    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/signout")
    public ResponseEntity<?> logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JSONObject statusObj = new JSONObject();
        statusObj.put("status_code", 200);
        statusObj.put("message", "SUCCESS");
        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.fail(statusObj));
    }
}
