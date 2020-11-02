
package com.digitax.payload.request;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UpdateProfileRequest {
    private String firstName;
	
    private String lastName;
	
    private String middleInitial;
	
    private String ocupation;
	
    private Date dateofbirth;
    
    private String addressLine1;

    private String addressLine2;
    
    private String city;
    
    private String state;
   
    private Long stateCode;
    
    private Long postalCode;
    
    private String country;
    
    private Long countryCode;
    
}
