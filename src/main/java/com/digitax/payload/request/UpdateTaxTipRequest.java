package com.digitax.payload.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTaxTipRequest {

	@NotNull(message = "taxId required.")
    private long taxId;
    private String title;
    private String taxTipLabel;
    private String image;
    private String numberOfQuestions;
    
}