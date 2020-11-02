package com.digitax.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyOtpRequest {
	    @NotBlank(message = "phone can not be emppty.")
	    @NotNull(message = "phone required.")
	    private String phone; 
	    
	    @NotBlank(message = "otp can not be emppty.")
	    @NotNull(message = "otp required.")
	    private String otp; 

}
