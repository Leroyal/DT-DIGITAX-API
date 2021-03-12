package com.digitax.soap.endpoint;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


import com.digitax.soap.service.MessageProviderService;
import com.digitax.soapschema.GetMessageResponse;
import com.digitax.soapschema.Message;
import com.digitax.soapschema.MessageRequest;
import com.digitax.soapschema.ObjectFactory;
@Endpoint
public class MessageProviderIndicatorEndPoint {
	
private static final String NAMESPACE="htttp://www.digitax.com/spring/soap/api/mefService";
@Autowired
private MessageProviderService messageProviderService;


@PayloadRoot(namespace=NAMESPACE,localPart="messageRequest")
@ResponsePayload
public GetMessageResponse getEtinStatus(@RequestPayload MessageRequest request)
{
	
	
	GetMessageResponse response = new GetMessageResponse();
	response.setMessage(messageProviderService.findEtin(request.getETIN()));
    
	 return response;
}

}
