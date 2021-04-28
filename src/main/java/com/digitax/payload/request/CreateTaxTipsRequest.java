package com.digitax.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTaxTipsRequest {
	@NotBlank(message = "title can not be emppty.")
	@NotNull(message = "title required.")
    private String title;
	@NotBlank(message = "taxTipLabel can not be emppty.")
    @NotNull(message = "taxTipLabel required.")
    private String taxTipLabel;
    @NotBlank(message = "image can not be emppty.")
    @NotNull(message = "image required.")
    private String image;
    @NotBlank(message = "numberOfQuestions can not be emppty.")
	@NotNull(message = "numberOfQuestions required.")
    private String numberOfQuestions;
    
}