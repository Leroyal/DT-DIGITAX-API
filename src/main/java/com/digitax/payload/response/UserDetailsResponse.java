package com.digitax.payload.response;

import java.util.Optional;

import com.digitax.model.User;
import com.digitax.model.UserProfile;

public class UserDetailsResponse {
	private Optional<User> user;
    private Optional<UserProfile> userDetails;

    public UserDetailsResponse(Optional<User> user, Optional<UserProfile> userDetails) {
        this.setUser(user);
        this.setUserDetails(userDetails);
    }

	public Optional<User> getUser() {
		return user;
	}

	public void setUser(Optional<User> user) {
		this.user = user;
	}

	public Optional<UserProfile> getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(Optional<UserProfile> userDetails) {
		this.userDetails = userDetails;
	}

	
}
