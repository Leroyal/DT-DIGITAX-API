package com.digitax.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ChangeEmailRequest {
	@NotBlank(message = "email can not be emppty.")
	@NotNull(message = "email required.")
    private String email;
	@NotBlank(message = "password can not be emppty.")
	@NotNull(message = "password required.")
    private String password;
	@NotBlank(message = "newEmail can not be emppty.")
	@NotNull(message = "newEmail required.")
    private String newEmail;

}