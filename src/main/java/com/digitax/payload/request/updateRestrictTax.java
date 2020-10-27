package com.digitax.payload.request;

import java.util.Date;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class updateRestrictTax {
	@NotNull(message = "restrictTaxId required.")
    private long restrictTaxId;
	
    private long year;
    private Date endDate;
   
    private Date startDate;
    
}