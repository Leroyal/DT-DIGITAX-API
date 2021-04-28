package com.digitax.service.impl;

import com.digitax.model.User;
import com.digitax.model.UserAddress;
import com.digitax.model.UserProfile;
import com.digitax.payload.request.UpdateProfileRequest;
import com.digitax.repository.UserAddressRepository;
import com.digitax.repository.UserProfileRepository;
import com.digitax.repository.UserRepository;
import com.digitax.security.jwt.AuthEntryPointJwt;
import com.digitax.security.jwt.UserSession;
import com.digitax.service.UserProfileService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@Service("UserProfileService")
public class UserProfileServiceImpl implements UserProfileService {
    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);


    @Autowired
    private UserProfileRepository userProfileRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserAddressRepository userAddressRepository;
  

    @Override
    @Transactional 
    public void saveUserProfile(UserProfile userProfile) {
    	userProfileRepository.save(userProfile);
    }
    
    
    
    @Override
    public UserProfile updateDetails(UserProfile userProfile) {
    	userProfileRepository.updateDetails(userProfile.getUserFirstName(),
    			                            userProfile.getUserMiddleInitial(),
    			                            userProfile.getUserDateofbirth(),
    			                            userProfile.getUserFirstName(),
    			                            userProfile.getCreatedAt(),
    			                            userProfile.isConsentToShareInformation(),
    			                            userProfile.getUserId()
    			                            );
    	
    	return userProfile;
    }

    @Override
    public List<UserProfile> getUserProfile() {
        List<UserProfile> userDtails = userProfileRepository.findAll();
        return userDtails;

    }
    
  
	public void updateUserPassword(User userDetails , String encode) {
    	userDetails.setPassword(encode);
    	userDetails.setId(UserSession.getUserId());
    	System.out.println(userDetails);
    	userRepository.save(userDetails);
	}
	
	
	public void updateUserProfile(UserProfile userProf ,@Valid UpdateProfileRequest updateProfile) {
		    if(updateProfile.getFirstName() != null)
		    {
		    	userProf.setUserFirstName(updateProfile.getFirstName());
		    }
		    if(updateProfile.getLastName()!= null)
		    {
		    	userProf.setUserLastName(updateProfile.getLastName());
		    }
		    if(updateProfile.getOcupation()!= null)
		    {
		    	userProf.setUserOcupation(updateProfile.getOcupation());
		    }
		    if(updateProfile.getDateofbirth() != null)
		    {
		    	userProf.setUserDateofbirth(updateProfile.getDateofbirth());
		    }
		    {
		    	userProf.setUserMiddleInitial(updateProfile.getMiddleInitial());
		    }
    	userProfileRepository.save(userProf);
	}
    
	
	public void createUserProfile(UserProfile userDetailsObj2, @Valid UpdateProfileRequest updateProfile) {
		try { 
	    UserProfile userDetailsObj = new UserProfile();
    	userDetailsObj.setUserId(UserSession.getUserId());
    	userDetailsObj.setUserFirstName(updateProfile.getFirstName());
    	userDetailsObj.setUserLastName(updateProfile.getLastName());
    	userDetailsObj.setUserMiddleInitial(updateProfile.getMiddleInitial());
    	userDetailsObj.setUserOcupation(updateProfile.getOcupation());
    	userDetailsObj.setUserDateofbirth(updateProfile.getDateofbirth());
    	userDetailsObj.setUpdatedAt(System.currentTimeMillis());
    	userDetailsObj.setCreatedAt(System.currentTimeMillis());
        System.out.println(userDetailsObj);
    	userProfileRepository.save(userDetailsObj);
	} catch (Exception e) {
        System.out.println(e);
        System.out.println(TransactionAspectSupport.currentTransactionStatus().isRollbackOnly());
        
    	logger.debug("Is rollbackOnly: " + TransactionAspectSupport.currentTransactionStatus().isRollbackOnly());
	}

	}
	
	public void createUpdateUserProfile(@Valid UpdateProfileRequest updateProfile)
	{
		Optional<UserAddress> addressObj = userAddressRepository.findByUserId(UserSession.getUserId());
		if(!addressObj.isPresent()) {
			 UserAddress userAddressObj = new UserAddress();
		     userAddressObj.setUserId(UserSession.getUserId());
		     userAddressObj.setAddressLine1(updateProfile.getAddressLine1());
		     userAddressObj.setAddressLine2(updateProfile.getAddressLine2());
		     userAddressObj.setCity(updateProfile.getCity());
		     userAddressObj.setState(updateProfile.getState());
		     userAddressObj.setStateCode(updateProfile.getStateCode());
		     userAddressObj.setPostalCode(updateProfile.getPostalCode());
		     userAddressObj.setCountry(updateProfile.getCountry());
		     userAddressObj.setCountryCode(updateProfile.getCountryCode());
		     userAddressObj.setCreatedAt(System.currentTimeMillis());
		     userAddressRepository.save(userAddressObj);
		}
		else
		{   
			UserAddress updateAddressObj = addressObj.get();
			if(updateProfile.getAddressLine1()!= null)
		    {
				updateAddressObj.setAddressLine1(updateProfile.getAddressLine1());
		    }
			if(updateProfile.getAddressLine2()!= null)
		    {
				updateAddressObj.setAddressLine2(updateProfile.getAddressLine2());
		    }
			if(updateProfile.getCity()!= null)
		    {
				updateAddressObj.setCity(updateProfile.getCity());
		    }
			if(updateProfile.getState()!= null)
		    {
				updateAddressObj.setState(updateProfile.getState());
		    }
			if(updateProfile.getStateCode()!= null)
		    {
				updateAddressObj.setStateCode(updateProfile.getStateCode());
		    }
			if(updateProfile.getPostalCode() != null)
		    {
				updateAddressObj.setPostalCode(updateProfile.getPostalCode());
		    }
			if(updateProfile.getCountry()!= null)
		    {
				updateAddressObj.setCountry(updateProfile.getCountry());
		    }
			if(updateProfile.getCountryCode()!= null)
		    {
				updateAddressObj.setCountryCode(updateProfile.getCountryCode());
		    }
			updateAddressObj.setUpdatedAt(System.currentTimeMillis());
            
			userAddressRepository.save(updateAddressObj);
			
			
		}
	    
	}
}