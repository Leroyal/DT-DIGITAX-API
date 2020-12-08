package com.digitax.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class VerifySiteRequest {
	@NotBlank(message = "secret can not be emppty.")
	@NotNull(message = "secret required.")
    private String secret;
	@NotBlank(message = "response can not be emppty.")
	@NotNull(message = "response required.")
    private String response;

}