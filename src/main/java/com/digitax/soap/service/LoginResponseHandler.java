package com.digitax.soap.service;

import gov.irs.a2a.mef.mefheader.MeFHeaderType;
import gov.irs.a2a.mef.mefmsiservices.LoginResponseType;

 class LoginResponseHandler  extends BaseResponseHandler<LoginResult>{
private LoginResponseType loginResponse;
	
	LoginResponseHandler(MeFHeaderType responseHeader, LoginResponseType responseObj) {
		super(responseHeader);
		this.loginResponse = responseObj;
	}

	public Builder<LoginResult> handleResponse(ServiceContext cont) {
		
		LoginResult.TBuilder containerBuilder =
			new LoginResult.TBuilder(this.responseHeader, this.loginResponse);
		//Do some stuff with the builder object here to set the objects state etc...
		return containerBuilder;
	}
	

}
