package com.digitax.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendOtpRequest {
	 @NotBlank(message = "phone can not be emppty.")
	    @NotNull(message = "phone required.")
	    private String phone; 

}
