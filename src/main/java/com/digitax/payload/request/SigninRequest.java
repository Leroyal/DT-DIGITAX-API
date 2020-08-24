package com.digitax.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class SigninRequest {
    private String username;
    
   
    private String email;
    
 
    private String phone;

    @NotBlank(message = "password can not be emppty.")
    @NotNull(message = "password required.")
    private String password; 
    
    @NotBlank(message = "deviceType can not be emppty.")
    @NotNull(message = "deviceType required.")
    private String deviceType; 
}