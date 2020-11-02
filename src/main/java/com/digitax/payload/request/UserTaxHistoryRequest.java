package com.digitax.payload.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.json.simple.JSONObject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTaxHistoryRequest {
	@NotNull(message = "personalInfo required.")
    private JSONObject personalInfo;
    @NotNull(message = "income required.")
    private JSONObject income;
    @NotNull(message = "TaxBreaks required.")
    private JSONObject taxBreaks;
    @NotNull(message = "PreviousYearSummary required.")
    private JSONObject previousYearSummary;
    @NotNull(message = "consentToShareInformation required.")
    @Pattern(regexp = "^Android$|^iOS$|^Web$", message = "allowed input: Android, iOS, Web")
    private String consentToShareInformation;
   
    
}