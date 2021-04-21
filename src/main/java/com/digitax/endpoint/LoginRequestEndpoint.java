package com.digitax.endpoint;

import java.io.File;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.client.core.SoapActionCallback;


import com.digitax.soap.service.LoginService;


import gov.irs.a2a.mef.mefheader.TestCdType;
import gov.irs.a2a.mef.mefmsiservices.LoginRequestType;
import gov.irs.a2a.mef.mefmsiservices.LoginResponseType;
import gov.irs.mef.ApplicationContext;
import gov.irs.mef.exception.ServiceException;
import gov.irs.mef.exception.ToolkitException;
import gov.irs.mef.inputcomposition.SubmissionXML;
import gov.irs.mef.services.ServiceContext;
import gov.irs.mef.services.ServiceEndpoints;
import gov.irs.mef.services.data.ETIN;
import gov.irs.mef.services.msi.LoginClient;
import gov.irs.mef.services.msi.LoginResult;
import gov.irs.mef.services.util.KeyStoreUtil;








@Endpoint
public class LoginRequestEndpoint  {
	private static final Logger logger = LoggerFactory.getLogger(LoginRequestEndpoint.class);
	
	// private static final String NAMESPACE_URI ="";
		
		  private ETIN etin; private String appSysID="47762"; private TestCdType
		  testCd;
		 
	  
	 
	LoginService loginService;

	
	  @Autowired 
	  public LoginRequestEndpoint(LoginService loginService) { 
		  
	  this.loginService = loginService; 
	  }
	 

	@PayloadRoot(namespace = "NAMESPACE_URI", localPart = "getLoginRequest")
	
	public  @ResponsePayload LoginResult  getLogin() throws ToolkitException, ServiceException  
	{
		
	
		
		
		  String keystorePassword="!digit@lt@xus@" ; 
		  String keyalias="digitaltaxusa";
		  String keyPassword="!digit@lt@xus@"; 
		  String keyStoreType="PKCS12 "; File file
		  = new File("DT-DIGITAX-API/clientkeystore/trd.jks"); 
		  KeyStoreUtil key=new		  KeyStoreUtil(); 
		  char[] chars = new char[keyPassword.length()];
		  key.loadKeyStore(file, chars); 
		  System.out.println(key);
		  
		  ServiceContext serviceContext= new ServiceContext(etin, appSysID, testCd);
		  
		  
		  String s2=serviceContext.getAppSysID();
		  
		  etin=serviceContext.getEtin(); logger.
		  info("in getLogin------------------------------------------------------------------------------------------"
		  ); TestCdType v=serviceContext.getTestCdType(); 
		  LoginClient loginClient =new	  LoginClient(); 
		  LoginResult l=loginClient.invoke(serviceContext,file ,keystorePassword, keyalias, null, java.security.Security.getProperty(keyStoreType)); 
		  return l;
		 
		
	

	}
}

