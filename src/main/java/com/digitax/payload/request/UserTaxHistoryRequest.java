package com.digitax.payload.request;

import javax.validation.constraints.NotNull;

import org.json.simple.JSONObject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTaxHistoryRequest {
	@NotNull(message = "personalInfo required.")
    private JSONObject ersonalInfo;
    @NotNull(message = "income required.")
    private Object income;
    @NotNull(message = "TaxBreaks required.")
    private Object taxBreaks;
    @NotNull(message = "PreviousYearSummary required.")
    private Object previousYearSummary;
   
    
}