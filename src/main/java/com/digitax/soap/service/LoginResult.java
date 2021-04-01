package com.digitax.soap.service;

import gov.irs.a2a.mef.mefheader.MeFHeaderType;
import gov.irs.a2a.mef.mefmsiservices.LoginResponseType;

public class LoginResult extends BaseResult<LoginResult>{
private LoginResponseType loginResponse;
	
	private LoginResult(TBuilder myBuilder) {
		super(myBuilder);
		this.loginResponse = myBuilder.loginResponse;
	}

	/**
	 * @return String status message from Login response.
	 */
	public String getStatusTxt() {
		return this.loginResponse.getStatusTxt();
	}
	
	static class TBuilder extends BaseResultBuilder<LoginResult> {
		
		private LoginResponseType loginResponse;
		
		TBuilder(MeFHeaderType responseHeader, LoginResponseType aLoginResponse) {
			super(responseHeader);
			this.loginResponse = aLoginResponse;
		}

		public LoginResult build () {
			return new LoginResult(this);
		}
	}
}
