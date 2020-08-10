package com.digitax.service;

import com.digitax.model.UserProfile;
import java.util.List;

public interface UserProfileService {


    public void saveUserProfile(UserProfile relation);

    public List<UserProfile> getUserProfile();
    
    

	
	
}