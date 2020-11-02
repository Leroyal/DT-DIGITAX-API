package com.digitax.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class SigninRequest {
	@NotBlank(message = "username can not be emppty.")
	@NotNull(message = "username required.")
    private String username;

    @NotBlank(message = "password can not be emppty.")
    @NotNull(message = "password required.")
    private String password; 
    
    @NotBlank(message = "deviceType can not be emppty.")
    @NotNull(message = "deviceType required.")
    @Pattern(regexp = "^Android$|^iOS$|^Web$", message = "allowed input: Android, iOS, Web")
    private String deviceType; 
}