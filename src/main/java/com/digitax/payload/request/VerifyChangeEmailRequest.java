package com.digitax.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class VerifyChangeEmailRequest {
	@NotBlank(message = "verifyToken can not be emppty.")
	@NotNull(message = "verifyToken required.")
    private String verifyToken;

}