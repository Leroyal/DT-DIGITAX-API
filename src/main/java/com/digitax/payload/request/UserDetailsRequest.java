package com.digitax.payload.request;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsRequest {
	
    private String firstName;
    private String middleInitial;
    private Date dateofbirth;
    private String lastName;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private Number stateCode;
    private Number postalCode;
    private String country;
    private Number countryCode;
    

}
