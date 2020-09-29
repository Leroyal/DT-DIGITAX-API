package com.digitax.model;

import lombok.Getter;
import lombok.Setter;
import springfox.documentation.spring.web.json.Json;

import org.json.simple.JSONObject;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "user_tax_history", uniqueConstraints = {
        @UniqueConstraint(columnNames = "user_id"),
})
@Getter
@Setter
public class TaxHistory {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
   
    
    @Column(name = "user_id")
    private long userId;
    
    
    @Column(name = "personalInfo" , columnDefinition = "JSON")
    private String personalInfo;
    
    
    @Column(name = "income",columnDefinition = "JSON")
    private String income;
    
    @Column(name = "taxBreaks", columnDefinition = "JSON")
    private String  taxBreaks;
    
    @JsonSerialize
    @Column(name = "previousYearSummary", columnDefinition = "JSON")
    private String  previousYearSummary;
    
    
    @Column(name = "consentToShareInformation")
    @NotBlank
    private String consentToShareInformation;
    
    @CreatedDate
    @Column(name = "created_at")
    private Long createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Long updatedAt;
    
    public TaxHistory() {
    	
    }
    
    public TaxHistory(String personalInfo, String income, String taxBreaks,String previousYearSummary, Long  createdAt,@NotBlank String consentToShareInformation) {
        this.personalInfo = personalInfo;
        this.income = income;
        this.taxBreaks = taxBreaks;
        this.previousYearSummary = previousYearSummary;
        this.createdAt = createdAt;
        this.updatedAt = createdAt;
        this.consentToShareInformation = consentToShareInformation;
        
    }

	@Override
	public String toString() {
		return "TaxHistory [id=" + id + ", userId=" + userId + ", personalInfo=" + personalInfo + ", income=" + income
				+ ", taxBreaks=" + taxBreaks + ", previousYearSummary=" + previousYearSummary
				+ ", consentToShareInformation=" + consentToShareInformation + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "]";
	}

}
