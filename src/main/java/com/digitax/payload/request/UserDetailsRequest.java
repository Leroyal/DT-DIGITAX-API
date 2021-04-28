package com.digitax.payload.request;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsRequest {
	@NotNull(message = "firstName required.")
    private String firstName;
    @NotNull(message = "middleInitial required.")
    private String middleInitial;
    @NotNull(message = "dateofbirth required.")
    private Date dateofbirth;
    @NotNull(message = "lastName required.")
    private String lastName;
    @NotNull(message = "addressLine1 required.")
    private String addressLine1;
    @NotNull(message = "addressLine2 required.")
    private String addressLine2;
    @NotNull(message = "city required.")
    private String city;
    @NotNull(message = "state required.")
    private String state;
    @NotNull(message = "stateCode required.")
    private Long stateCode;
    @NotNull(message = "postalCode required.")
    private Long postalCode;
    @NotNull(message = "country required.")
    private String country;
    @NotNull(message = "countryCode required.")
    private Long countryCode;
    private boolean consentToShareInformation;
}
