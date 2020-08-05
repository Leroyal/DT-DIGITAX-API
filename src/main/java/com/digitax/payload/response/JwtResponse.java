package com.digitax.payload.response;

import java.util.List;

import org.json.simple.JSONObject;

public class JwtResponse {
	private JSONObject user;
	private Object session;
	
	public JwtResponse(Object sessionobj,JSONObject userDetailsObj) {
		 this.user = userDetailsObj;
		 this.session = sessionobj;
	}

	

	public JSONObject getUser() {
		return user;
	}

	public void setUser(List<String> user) {
		this.user = (JSONObject) user;
	}

	public Object getSession() {
		return session;
	}

	public void setSession(Object session) {
		this.session = session;
	}
}