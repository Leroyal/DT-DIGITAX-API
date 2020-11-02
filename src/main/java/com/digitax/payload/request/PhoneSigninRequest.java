package com.digitax.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PhoneSigninRequest {
	@NotBlank(message = "phone can not be emppty.")
	@NotNull(message = "phone required.")
    private String phone;
    
	@NotBlank(message = "countryCode can not be emppty.")
    @NotNull(message = "countryCode required.")
    private String countryCode; 
	
    @NotBlank(message = "otp can not be emppty.")
    @NotNull(message = "otp required.")
    private String otp; 
    
    @NotBlank(message = "deviceType can not be emppty.")
    @NotNull(message = "deviceType required.")
    @Pattern(regexp = "^Android$|^iOS$|^Web$", message = "allowed input: Android, iOS, Web")
    private String deviceType; 
}