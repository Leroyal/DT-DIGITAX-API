package com.digitax.payload.response;

import java.util.List;

import com.digitax.security.services.UserDetailsImpl;

public class JwtResponse {
	private UserDetailsImpl user;
	private Object session;
	
	public JwtResponse(Object sessionobj,UserDetailsImpl userDetails) {
		 this.user = userDetails;
		 this.session = sessionobj;
	}

	

	public UserDetailsImpl getUser() {
		return user;
	}

	public void setUser(List<String> user) {
		this.user = (UserDetailsImpl) user;
	}

	public Object getSession() {
		return session;
	}

	public void setSession(Object session) {
		this.session = session;
	}
}