
package com.digitax.payload.request;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UpdateProfileRequest {
    private String firstName;
	
    private String lastName;
	
	
    private String ocupation;
	
    private Date dateofbirth;

}
