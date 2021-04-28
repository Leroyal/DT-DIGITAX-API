package com.digitax.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class BiometricSignInRequest {
	@NotBlank(message = "biometricDeviceId can not be emppty.")
	@NotNull(message = "biometricDeviceId required.")
    private String biometricDeviceId;
    
	
    @NotBlank(message = "deviceType can not be emppty.")
    @NotNull(message = "deviceType required.")
    @Pattern(regexp = "^Android$|^iOS$|^Web$", message = "allowed input: Android, iOS, Web")
    private String deviceType; 
    
    private String email;

}