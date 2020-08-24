package com.digitax.service;

import com.digitax.model.UserProfile;
import java.util.List;

public interface UserProfileService {


    public void saveUserProfile(UserProfile userProfile);
    
    public UserProfile updateDetails(UserProfile userProfile);

    public List<UserProfile> getUserProfile();
    
    

	
	
}