package com.digitax.payload.request;



import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarketingPreferenceRequest {
	
    @Pattern(regexp = "^true$|^false$", message = "allowed input: true or false")
    private String isContactViaMailDisabled;
    
    @Pattern(regexp = "^true$|^false$", message = "allowed input: true or false")
    private String isContactViaEmailDisabled;
    
    @Pattern(regexp = "^true$|^false$", message = "allowed input: true or false")
    private String isContactViaPhoneDisabled;
    
}