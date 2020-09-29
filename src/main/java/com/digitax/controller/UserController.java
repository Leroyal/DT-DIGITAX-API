package com.digitax.controller;


import com.digitax.payload.ApiRes;
import com.digitax.payload.request.ChangeEmailRequest;
import com.digitax.payload.request.MarketingPreferenceRequest;
import com.digitax.payload.request.UpdatePasswordRequest;
import com.digitax.payload.request.UpdateProfileRequest;
import com.digitax.payload.request.UserConsentRequest;
import com.digitax.payload.request.UserDetailsRequest;
import com.digitax.payload.request.UserTaxHistoryRequest;
import com.digitax.security.jwt.JwtUtils;
import com.digitax.security.jwt.UserSession;
import com.digitax.constants.ResponseConstants;
import com.digitax.model.MarketingPreference;
import com.digitax.model.TaxHistory;
import com.digitax.model.User;
import com.digitax.model.UserAddress;
import com.digitax.model.UserConsent;
import com.digitax.model.UserProfile;
import com.digitax.payload.response.TaxHistoryResponse;
import com.digitax.payload.response.UserDetailsResponse;
import com.digitax.repository.UserAddressRepository;
import com.digitax.repository.UserConsentRepository;
import com.digitax.repository.UserProfileRepository;
import com.digitax.repository.UserRepository;
import com.digitax.repository.MarketingPreferenceRepository;
import com.digitax.repository.TaxHistoryRepository;
import com.digitax.service.EmailService;
import com.digitax.service.UserProfileService;

import antlr.collections.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    
    @Autowired
    UserConsentRepository userConsentRepository;
    
    
    @Autowired
    TaxHistoryRepository taxHistoryRepository;
    
    @Autowired
    MarketingPreferenceRepository marketingPreferenceRepository;
    
    @Autowired
    EmailService emailService;


    @SuppressWarnings("unchecked")
	@GetMapping("/user-details")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> userProfileDetails() throws ParseException {
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
		        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));
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
	                System.currentTimeMillis(),
	                userDetails.isConsentToShareInformation());
    	
    	if(!addressObj.isPresent()) {
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
    	
    	
    	 if(!detailsObj.isPresent()) {
    		    userDetailsObj.setUserId(UserSession.getUserId());
    		    userProfileRepository.save(userDetailsObj);
    	        
    	   }
    	 else
    	 {
    		userDetailsObj.setUserId(UserSession.getUserId());
    		userProfileService.updateDetails(userDetailsObj);
 	        
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
	
	@SuppressWarnings("unchecked")
	@PostMapping("/user-consents")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateUserConsents(@Valid @RequestBody UserConsentRequest userConsent,BindingResult bindingResult) throws ParseException{
		if (bindingResult.hasErrors()) {
			ArrayList<?> errors = (ArrayList<?>) bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
			 JSONObject statusObj = new JSONObject();
		        statusObj.put("status_code", ResponseConstants.VALIDATION_ERROR);
		        statusObj.put("message", errors);
		       
		        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));
        }
		int year = Calendar.getInstance().get(Calendar.YEAR);
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    	Date currentDate = sdf.parse(year+"-01-01T00:00:00.235-0700");
    	Calendar calndr = Calendar.getInstance();
    	calndr.setTime(currentDate);
    	calndr.add(Calendar.YEAR, 1);
    	Date newDate = calndr.getTime();
    	System.out.println(newDate); 
		try {
		
		Optional<UserConsent> detailsObj = userConsentRepository.findByUserId(UserSession.getUserId());
		UserConsent userConsentObj = new UserConsent(
				userConsent.getFirstName(),
				userConsent.getLastName(),
				userConsent.getSpouseFirstName(),
				userConsent.getSpouseLastName(),
				newDate,
				newDate,
				System.currentTimeMillis(),
				userConsent.getConsentToShareInformation()
             );
		if(!detailsObj.isPresent())
		{
			userConsentObj.setUserId(UserSession.getUserId());
			UserConsent obj = userConsentRepository.save(userConsentObj);
	        JSONObject statusObj = new JSONObject();
	        statusObj.put("status_code", ResponseConstants.SUCCESS);
	        statusObj.put("message", "SUCCESS");
	        return new ResponseEntity<>(ApiRes.success(obj, statusObj), HttpStatus.OK);	
		}
		else
		{
		 userConsentRepository.updateConsent(
					userConsent.getFirstName(),
					userConsent.getLastName(),
					userConsent.getSpouseFirstName(),
					userConsent.getSpouseLastName(),
					System.currentTimeMillis(),
					userConsent.getConsentToShareInformation(),
					UserSession.getUserId());
		   Optional<UserConsent> obj = userConsentRepository.findByUserId(UserSession.getUserId());
			JSONObject statusObj = new JSONObject();
	        statusObj.put("status_code", ResponseConstants.SUCCESS);
	        statusObj.put("message", "SUCCESS");
	        return new ResponseEntity<>(ApiRes.success(obj, statusObj), HttpStatus.OK);	
			
		}
		
	 } catch (Exception e) {
  	   JSONObject statusObj = new JSONObject();
         statusObj.put("status_code",ResponseConstants.INTERNAL_SERVER_ERROR);
         statusObj.put("message", "FAILURE");
         return new ResponseEntity<>(ApiRes.success(e.getMessage(), statusObj), HttpStatus.OK);	
         }
  }
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("/user-consents")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserConsents() throws ParseException {
		try {
		Optional<UserConsent> obj = userConsentRepository.findByUserId(UserSession.getUserId());
		JSONObject statusObj = new JSONObject();
        statusObj.put("status_code", ResponseConstants.SUCCESS);
        statusObj.put("message", "SUCCESS");
        return new ResponseEntity<>(ApiRes.success(obj, statusObj), HttpStatus.OK);	
		} 
		catch (Exception e) {
		  	   JSONObject statusObj = new JSONObject();
		         statusObj.put("status_code",ResponseConstants.INTERNAL_SERVER_ERROR);
		         statusObj.put("message", "FAILURE");
		         return new ResponseEntity<>(ApiRes.success(e.getMessage(), statusObj), HttpStatus.OK);	
		         }
    }
	
	@SuppressWarnings("unchecked")
	@GetMapping("/user-tax-history")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserTaxHistory() {
		try {
		Optional<TaxHistory> detailsObj = taxHistoryRepository.findByUserId(UserSession.getUserId());
		TaxHistory TaxObj = detailsObj.get();
		TaxHistoryResponse RespObj =new TaxHistoryResponse(TaxObj);
		JSONObject statusObj = new JSONObject();
        statusObj.put("status_code", ResponseConstants.SUCCESS);
        statusObj.put("message", "SUCCESS");
        return new ResponseEntity<>(ApiRes.success(RespObj, statusObj), HttpStatus.OK);	
		} 
		catch (Exception e) {
		  	   JSONObject statusObj = new JSONObject();
		         statusObj.put("status_code",ResponseConstants.INTERNAL_SERVER_ERROR);
		         statusObj.put("message", "FAILURE");
		         return new ResponseEntity<>(ApiRes.success(e.getMessage(), statusObj), HttpStatus.OK);	
		         }
    }
	
	
	
	@SuppressWarnings("unchecked")
	@PostMapping("/user-tax-history")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateUserTaxHistory(@Valid @RequestBody UserTaxHistoryRequest taxHistoryBody,BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			ArrayList<?> errors = (ArrayList<?>) bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
			 JSONObject statusObj = new JSONObject();
		        statusObj.put("status_code", ResponseConstants.VALIDATION_ERROR);
		        statusObj.put("message", errors);
		        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));
        }
		try {
			Optional<TaxHistory> detailsObj = taxHistoryRepository.findByUserId(UserSession.getUserId());
			TaxHistory taxHistorytObj = new TaxHistory(
					taxHistoryBody.getPersonalInfo().toString(),
					taxHistoryBody.getIncome().toString(),
					taxHistoryBody.getTaxBreaks().toString(),
					taxHistoryBody.getPreviousYearSummary().toString(),
					System.currentTimeMillis(),
					taxHistoryBody.getConsentToShareInformation()
	             );
			if(!detailsObj.isPresent())
			{
				taxHistorytObj.setUserId(UserSession.getUserId());
				TaxHistory obj = taxHistoryRepository.save(taxHistorytObj);
				TaxHistoryResponse RespObj =new TaxHistoryResponse(obj);
		        JSONObject statusObj = new JSONObject();
		        statusObj.put("status_code", ResponseConstants.SUCCESS);
		        statusObj.put("message", "SUCCESS");
		        return new ResponseEntity<>(ApiRes.success(RespObj, statusObj), HttpStatus.OK);	
			}
			else
			{
				taxHistoryRepository.updateTaxHistory(
					  taxHistoryBody.getPersonalInfo().toString(),
						taxHistoryBody.getIncome().toString(),
						taxHistoryBody.getTaxBreaks().toString(),
						taxHistoryBody.getPreviousYearSummary().toString(),
						System.currentTimeMillis(),
						taxHistoryBody.getConsentToShareInformation(),
						UserSession.getUserId());
			    Optional<TaxHistory> obj = taxHistoryRepository.findByUserId(UserSession.getUserId());
			    TaxHistory TaxObj = obj.get();
			    TaxHistoryResponse RespObj =new TaxHistoryResponse(TaxObj);
				JSONObject statusObj = new JSONObject();
		        statusObj.put("status_code", ResponseConstants.SUCCESS);
		        statusObj.put("message", "SUCCESS");
		        return new ResponseEntity<>(ApiRes.success(RespObj, statusObj), HttpStatus.OK);	
				
			}
		}
		catch (Exception e) {
		JSONObject statusObj = new JSONObject();
        statusObj.put("status_code",ResponseConstants.INTERNAL_SERVER_ERROR);
        statusObj.put("message", "FAILURE");
        return new ResponseEntity<>(ApiRes.success(e.getMessage(), statusObj), HttpStatus.OK);	
		 }
    
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("/user-marketing-preference")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getMarketingPreference() {
		try {
		Optional<MarketingPreference> detailsObj = marketingPreferenceRepository.findByUserId(UserSession.getUserId());
		MarketingPreference TaxObj = detailsObj.get();	
		JSONObject statusObj = new JSONObject();
        statusObj.put("status_code", ResponseConstants.SUCCESS);
        statusObj.put("message", "SUCCESS");
        return new ResponseEntity<>(ApiRes.success(TaxObj, statusObj), HttpStatus.OK);	
		} 
		catch (Exception e) {
		  	   JSONObject statusObj = new JSONObject();
		         statusObj.put("status_code",ResponseConstants.INTERNAL_SERVER_ERROR);
		         statusObj.put("message", "FAILURE");
		         return new ResponseEntity<>(ApiRes.success(e.getMessage(), statusObj), HttpStatus.OK);	
		         }
    }
	
	
	
	@SuppressWarnings("unchecked")
	@PostMapping("/user-marketing-preference")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateMarketingPreference(@Valid @RequestBody MarketingPreferenceRequest request,BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			ArrayList<?> errors = (ArrayList<?>) bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
			 JSONObject statusObj = new JSONObject();
		        statusObj.put("status_code", ResponseConstants.VALIDATION_ERROR);
		        statusObj.put("message", errors);
		        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));
        }
		try {
			Optional<MarketingPreference> detailsObj = marketingPreferenceRepository.findByUserId(UserSession.getUserId());
			MarketingPreference marketingPreferenceObj = new MarketingPreference(
					request.getIsContactViaMailDisabled(),
					request.getIsContactViaEmailDisabled(),
					request.getIsContactViaPhoneDisabled(),
					System.currentTimeMillis()
	             );
			if(!detailsObj.isPresent())
			{
				marketingPreferenceObj.setUserId(UserSession.getUserId());
				MarketingPreference obj = marketingPreferenceRepository.save(marketingPreferenceObj);
		        JSONObject statusObj = new JSONObject();
		        statusObj.put("status_code", ResponseConstants.SUCCESS);
		        statusObj.put("message", "SUCCESS");
		        return new ResponseEntity<>(ApiRes.success(obj, statusObj), HttpStatus.OK);	
			}
			else
			{
				marketingPreferenceRepository.updateMarketingPref(
						request.getIsContactViaMailDisabled(),
						request.getIsContactViaEmailDisabled(),
						request.getIsContactViaPhoneDisabled(),
						System.currentTimeMillis(),
						UserSession.getUserId());
				JSONObject statusObj = new JSONObject();
		        statusObj.put("status_code", ResponseConstants.SUCCESS);
		        statusObj.put("message", "SUCCESS");
		        return new ResponseEntity<>(ApiRes.success(detailsObj, statusObj), HttpStatus.OK);	
				
			}
		}
		catch (Exception e) {
		JSONObject statusObj = new JSONObject();
        statusObj.put("status_code",ResponseConstants.INTERNAL_SERVER_ERROR);
        statusObj.put("message", "FAILURE");
        return new ResponseEntity<>(ApiRes.success(e.getMessage(), statusObj), HttpStatus.OK);	
		 }
    
	}
	
	@SuppressWarnings("unchecked")
    @PostMapping("/update-email")
    public ResponseEntity<?> updateemail(@Valid @RequestBody ChangeEmailRequest changeEmail,BindingResult bindingResult) {
		try {
    	if (userRepository.existsByEmail(changeEmail.getNewEmail())) {
    		JSONObject statusObj = new JSONObject();
	        statusObj.put("status_code", ResponseConstants.VALIDATION_ERROR);
	        statusObj.put("message", "User with this email already exists");
	        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));
    	}
    	else
    	{	
        userRepository.updateUserEmail(changeEmail.getNewEmail(),System.currentTimeMillis(),UserSession.getUserId());
       	String emailData = emailService.changeEmailSupport("jayanta@redappletech.com",changeEmail.getNewEmail(),"Change of email");
		System.out.println(emailData);
       	JSONObject statusObj = new JSONObject();	      	
        statusObj.put("status_code", ResponseConstants.SUCCESS);
        statusObj.put("message", "SUCCESS");
        return new ResponseEntity<>(ApiRes.success(null, statusObj), HttpStatus.OK);
    	}    
			}
			catch (Exception e) {
			JSONObject statusObj = new JSONObject();
	        statusObj.put("status_code",ResponseConstants.INTERNAL_SERVER_ERROR);
	        statusObj.put("message", "FAILURE");
	        return new ResponseEntity<>(ApiRes.success(e.getMessage(), statusObj), HttpStatus.OK);	
			 }
        
    }
	
	@SuppressWarnings("unchecked")
    @PostMapping("/update-profile")
	@Transactional
    public ResponseEntity<?> updateProfile(@Valid @RequestBody UpdateProfileRequest updateProfile,BindingResult bindingResult) {
		try {
	    Optional<UserProfile> UserProfile= userProfileRepository.findByUserId(UserSession.getUserId());
	    if(!UserProfile.isPresent()) {
	    	UserProfile userDetailsObj = new UserProfile();
	    	userDetailsObj.setUserId(UserSession.getUserId());
	    	userDetailsObj.setFirstName(updateProfile.getFirstName());
	    	userDetailsObj.setLastName(updateProfile.getLastName());
	    	userDetailsObj.setOcupation(updateProfile.getOcupation());
	    	userDetailsObj.setDateofbirth(updateProfile.getDateofbirth());
	    	
	    	System.out.println(userDetailsObj);
	    	
	    	
	    	userProfileService.saveUserProfile(userDetailsObj);
	    }
	    else
	    {
			    UserProfile userProfileObj = UserProfile.get();
			    if(!updateProfile.getFirstName().isBlank())
			    {
			    	userProfileObj.setFirstName(updateProfile.getFirstName());
			    }
			    if(!updateProfile.getLastName().isBlank())
			    {
			    	userProfileObj.setLastName(updateProfile.getLastName());
			    }
			    if(!updateProfile.getOcupation().isBlank())
			    {
			    	userProfileObj.setOcupation(updateProfile.getOcupation());
			    }
			    if(updateProfile.getDateofbirth() != null)
			    {
			    	userProfileObj.setDateofbirth(updateProfile.getDateofbirth());
			    }
			    
			    UserProfile author = userProfileRepository.findByUserId(UserSession.getUserId()).get(); 
		        author.setFirstName("new name"); 
		        author.setFirstName("new name"); 
		    	System.out.println(author);
			    userProfileRepository.save(userProfileObj);
	    }
       	JSONObject statusObj = new JSONObject();	      	
        statusObj.put("status_code", ResponseConstants.SUCCESS);
        statusObj.put("message", "SUCCESS");
        return new ResponseEntity<>(ApiRes.success(null, statusObj), HttpStatus.OK);   
			}
			catch (Exception e) {
				System.out.println(e);
			JSONObject statusObj = new JSONObject();
	        statusObj.put("status_code",ResponseConstants.INTERNAL_SERVER_ERROR);
	        statusObj.put("message", "FAILURE");
	        return new ResponseEntity<>(ApiRes.success(e.getMessage(), statusObj), HttpStatus.OK);	
			 }
        
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
}
