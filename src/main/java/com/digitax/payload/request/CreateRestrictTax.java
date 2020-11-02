package com.digitax.payload.request;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRestrictTax {
	@NotNull(message = "year required.")
    private long year;
    @NotNull(message = "endDate required.")
    private Date endDate;
    @NotNull(message = "startDate required.")
    private Date startDate;
    
}