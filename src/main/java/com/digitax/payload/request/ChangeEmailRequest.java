package com.digitax.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ChangeEmailRequest {
	@NotBlank(message = "newEmail can not be emppty.")
	@NotNull(message = "newEmail required.")
    private String newEmail;

}