package com.digitax.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.digitax.model.TrueFalse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserConsentRequest {
	@NotNull(message = "firstName required.")
    private String firstName;
    @NotNull(message = "lastName required.")
    private String lastName;
    @NotNull(message = "Spouse firstName required.")
    private String spouseFirstName;
    @NotNull(message = "Spouse lastName required.")
    private String spouseLastName;
    @NotNull(message = "consentToShareInformation required.")
    @Pattern(regexp = "^true$|^false$", message = "allowed input: true or false")
    private String consentToShareInformation;
    
}