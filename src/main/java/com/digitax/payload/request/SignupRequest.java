package com.digitax.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Set;

@Getter
@Setter
public class SignupRequest {
    @NotBlank(message = "username can not be emppty.")
    @NotNull(message = "username required.")
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank(message = "email can not be emppty.")
    @NotNull(message = "email required.")
    @Size(max = 50)
    @Email
    private String email;
    
 
    private Set<String> role;

    @NotBlank(message = "password can not be emppty.")
    @NotNull(message = "password required.")
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank(message = "phone can not be emppty.")
    @NotNull(message = "phone required.")
    @Size(min = 10, max = 10)
    private String phone;
    
    @NotBlank(message = "deviceType can not be emppty.")
    @NotNull(message = "deviceType required.")
    private String deviceType;
}