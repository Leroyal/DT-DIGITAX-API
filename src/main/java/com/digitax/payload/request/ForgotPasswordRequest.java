package com.digitax.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ForgotPasswordRequest {
	@NotBlank(message = "email can not be emppty.")
	@NotNull(message = "email required.")
    private String email;
}