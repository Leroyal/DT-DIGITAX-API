package com.digitax.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ChangePasswordRequest {

    @NotBlank(message = "password can not be emppty.")
    @NotNull(message = "password required.")
    private String password; 
    
    @NotBlank(message = "confirmPassword can not be emppty.")
    @NotNull(message = "confirmPassword required.")
    private String confirmPassword; 
    
    @NotBlank(message = "oldPassword can not be emppty.")
    @NotNull(message = "oldPassword required.")
    private String oldPassword; 
}
