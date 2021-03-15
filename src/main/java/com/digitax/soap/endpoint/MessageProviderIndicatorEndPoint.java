package com.digitax.soap.endpoint;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


import com.digitax.soap.service.MessageProviderService;

import com.digitax.soapschema.LoginRequestType;
import com.digitax.soapschema.LoginResponseType;



@Endpoint
public class MessageProviderIndicatorEndPoint {
	
private static final String NAMESPACE="http://www.irs.gov/a2a/mef/MeFMSIServices";
@Autowired
private MessageProviderService messageProviderService;


@PayloadRoot(namespace=NAMESPACE,localPart="LoginRequestType")
@ResponsePayload
public LoginResponseType  getAuthenticateUser(@RequestPayload LoginRequestType request)
{
	System.out.println(">>>>>>>>>>>>>>>> Endpoint reached");
	LoginResponseType loginResponseType=new LoginResponseType();
	loginResponseType.setStatusTxt(messageProviderService.findUsername(request));
	return loginResponseType;
	 
	 
}

}
