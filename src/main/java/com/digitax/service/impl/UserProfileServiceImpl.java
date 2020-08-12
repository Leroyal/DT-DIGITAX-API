package com.digitax.service.impl;

import com.digitax.model.UserProfile;
import com.digitax.repository.UserProfileRepository;
import com.digitax.service.UserProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("UserProfileService")
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

  

    @Override
    public void saveUserProfile(UserProfile userProfile) {
    	userProfileRepository.save(userProfile);
    }
    
    @Override
    public UserProfile updateDetails(UserProfile userProfile) {
    	userProfileRepository.updateDetails(userProfile.getFirstName(),
    			                            userProfile.getMiddleInitial(),
    			                            userProfile.getDateofbirth(),
    			                            userProfile.getLastName(),
    			                            userProfile.getCreatedAt(),
    			                            userProfile.getUserId()
    			                            );
    	
    	return userProfile;
    }

    @Override
    public List<UserProfile> getUserProfile() {
        List<UserProfile> userDtails = userProfileRepository.findAll();
        return userDtails;

    }
}