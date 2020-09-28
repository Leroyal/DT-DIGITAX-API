package com.digitax.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;

@Entity
@Table(name = "marketing_preference",uniqueConstraints = {
        @UniqueConstraint(columnNames = "user_id"),
})
@Getter
@Setter
public class MarketingPreference {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
   
	 @Column(name = "user_id")
	 private long userId;
    
	@Column(name = "isContactViaMailDisabled")
    private String isContactViaMailDisabled;
    
	@Column(name = "isContactViaEmailDisabled")
    private String isContactViaEmailDisabled;
    
	@Column(name = "isContactViaPhoneDisabled")
    private String isContactViaPhoneDisabled;

    
    @CreatedDate
    @Column(name = "created_at")
    private Long createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Long updatedAt;
    
    public MarketingPreference() {
    	
    }
    public MarketingPreference(String isContactViaMailDisabled, String isContactViaEmailDisabled, String isContactViaPhoneDisabled,Long createdAt) {
        this.isContactViaMailDisabled = isContactViaMailDisabled;
        this.isContactViaEmailDisabled = isContactViaEmailDisabled;
        this.isContactViaPhoneDisabled = isContactViaPhoneDisabled;
        this.createdAt = createdAt;
    }

}

