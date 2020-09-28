package com.digitax.payload.response;

import org.json.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.digitax.model.TaxHistory;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class TaxHistoryResponse {
	private long userId;
    private JSONObject personalInfo;
    private JSONObject income;
    private JSONObject taxBreaks;
    private JSONObject previousYearSummary;
    private long updatedAt;
    private long createdAt;

    public TaxHistoryResponse(TaxHistory taxObj) throws ParseException {
    	JSONParser parser = new JSONParser();
        this.userId = taxObj.getUserId();
        this.personalInfo =  (JSONObject) parser.parse(taxObj.getPersonalInfo());
        this.income =  (JSONObject) parser.parse(taxObj.getIncome());
        this.taxBreaks =  (JSONObject) parser.parse(taxObj.getTaxBreaks());
        this.previousYearSummary =  (JSONObject) parser.parse(taxObj.getPreviousYearSummary());
        this.updatedAt = taxObj.getUpdatedAt();
        this.createdAt = taxObj.getCreatedAt();
    }

}