package com.digitax.mef.validation;

public class ValidationError {
String errorMessage;
	
	
	public ValidationError(String anErrorMessage) {
		this.errorMessage = anErrorMessage;
	}
	
	
	public String getMessage() {
		return this.errorMessage;
	}

}
