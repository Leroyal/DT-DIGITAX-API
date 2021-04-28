package com.digitax.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user_consent", uniqueConstraints = {
        @UniqueConstraint(columnNames = "user_id"),
})
@Getter
@Setter
public class UserConsent {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
   
    
    @Column(name = "user_id")
    private long userId;
    
    @Column(name = "firstName")
    private String firstName;
    
    
    @Column(name = "spouseFirstName")
    private String spouseFirstName;
    
    @Column(name = "lastName")
    private String lastName;
    
    @Column(name = "spouseLastName")
    private String spouseLastName;
    
    @Column(name = "dateForUser")
    private Date dateForUser;
    
    @Column(name = "dateForSpouse")
    private Date dateForSpouse;
    
    @Column(name = "consentToShareInformation")
    @NotBlank
    private String consentToShareInformation;
    
    @CreatedDate
    @Column(name = "created_at")
    private Long createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Long updatedAt;
    
    public UserConsent() {
    	
    }
    
    public UserConsent(String firstName, String lastName, String spouseFirstName, String spouseLastName, Date dateForUser,Date dateForSpouse,Long  createdAt,@NotBlank String consentToShareInformation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.spouseFirstName = spouseFirstName;
        this.spouseLastName = spouseLastName;
        this.dateForUser = dateForUser;
        this.dateForSpouse = dateForSpouse;
        this.createdAt = createdAt;
        this.consentToShareInformation = consentToShareInformation;
        
    }

}
