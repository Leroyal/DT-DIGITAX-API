package com.digitax.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.digitax.app.exception.ServiceException;
import com.digitax.endpoint.LoginRequestEndPoint;

import gov.irs.mef.ApplicationContext;
import gov.irs.mef.exception.ToolkitException;
import gov.irs.mef.services.msi.LoginResult;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class LoginController {
	@Autowired
	LoginRequestEndPoint loginRequestEndPoint;
	ApplicationContext applicationContext;
	@RequestMapping(value = "/login", method = RequestMethod.POST)
			
	
	    public LoginResult logUser() throws ToolkitException, gov.irs.mef.exception.ServiceException {
		
	        // represents the token for an authentication request or for an authenticated principal
	        // once the request has been processed.
		
		
		
		
		//boolean b=file.canExecute();
	LoginResult  result =loginRequestEndPoint.getLogin();
	        return result;        // return response entity
	        
	    }

}
