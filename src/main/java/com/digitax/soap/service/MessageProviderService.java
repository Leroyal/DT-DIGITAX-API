package com.digitax.soap.service;

import java.util.HashMap;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.validation.Valid;


import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import com.digitax.soapschema.LoginRequestType;
import com.digitax.soapschema.LoginResponseType;

//import java.time.LocalDate;



@Service
public class MessageProviderService {
	private static final Map<String, String> login = new HashMap<>();
@PostConstruct
		public LoginResponseType  authenticateUser(@Valid @RequestBody LoginRequestType loginRequest)
	{
		LoginResponseType loginResponseType=new LoginResponseType();
		login.put(loginRequest.getUsername(),loginRequest.getPassword() );
		return loginResponseType;
	}
	public  LoginRequestType findUsername(LoginRequestType loginRequest) {
		//LoginResponseType loginResponseType=new LoginResponseType();
        Assert.notNull(loginRequest, "The username& password must not be null");
		return loginRequest;
       
    }
	
	
}
