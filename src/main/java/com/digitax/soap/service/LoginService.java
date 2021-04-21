package com.digitax.soap.service;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.jws.WebMethod;
import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.digitax.endpoint.LoginRequestEndpoint;


import gov.irs.a2a.mef.mefheader.TestCdType;
import gov.irs.a2a.mef.mefmsiservices.EtinStatus;
import gov.irs.a2a.mef.mefmsiservices.EtinStatus_Service;
import gov.irs.a2a.mef.mefmsiservices.Login;
import gov.irs.a2a.mef.mefmsiservices.LoginRequestType;
import gov.irs.mef.services.ServiceContext;
import gov.irs.mef.services.ServiceEndpoints;
import gov.irs.mef.services.data.ETIN;





@Component
public class LoginService {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
	  
		
		  private ETIN etin; private String appSysID="47762"; private TestCdType
		  testCd;
		 
	 
	  ServiceContext serviceContext;
	
	  public  void initData() { 
		
			
			
			  testCd=TestCdType.T;
			  
			  serviceContext.setEtin(etin); serviceContext.setAppSysID(appSysID);
			  serviceContext.setTestCdType(testCd);
			  
			 
	  }
	 
	
		
		  
		 
	  }


