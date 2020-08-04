package com.digitax.payload.response;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SessionResponse {
	
	private String accessToken;
	
	private int expirationMiliSecond;
	
	private Date utcExpirationTime;
	
	private Date ptExpirationTime;
	
	
	public SessionResponse(String accessToken, int jwtExpirationMs) {
		this.accessToken = accessToken;
		this.expirationMiliSecond = jwtExpirationMs;
		this.utcExpirationTime = new Date((new Date()).getTime() + jwtExpirationMs);
		this.ptExpirationTime = new Date((new Date()).getTime() + jwtExpirationMs);
	}
	

}
