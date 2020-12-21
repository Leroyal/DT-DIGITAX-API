package com.digitax.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddVehicleRequest {
	
	@NotBlank(message = "make can not be emppty.")
	@NotNull(message = "make required.")
    private String make;
	
	@NotBlank(message = "model can not be emppty.")
	@NotNull(message = "model required.")
    private String model;
	
	@NotNull(message = "year required.")
    private Long year;
	
	@NotBlank(message = "nickName can not be emppty.")
	@NotNull(message = "nickName required.")
    private String nickName;
	
	@NotBlank(message = "notes can not be emppty.")
	@NotNull(message = "notes required.")
    private String notes;
	
	@Pattern(regexp = "^true$|^false$", message = "allowed input: true or false")
    private String primaryVehicle;
	
	@NotNull(message = "odoMeter required.")
	private Long odoMeter;

}
