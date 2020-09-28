package com.digitax.model;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "userProfile", uniqueConstraints = {
        @UniqueConstraint(columnNames = "user_id"),
})
@Getter
@Setter
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
   
    
    @Column(name = "user_id")
    private long userId;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "middleInitial")
    private String middleInitial;
    
    @Column(name = "ocupation")
    private String ocupation;
    
    @Column(name = "dateofbirth")
    private Date dateofbirth;
    
    @Column(name = "consentToShareInformation")
    @Size(max = 10)
    private boolean consentToShareInformation;
    
    @Column(name = "lastName")
    private String lastName;

    @CreatedDate
    @Column(name = "created_at")
    private Long createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Long updatedAt;
    
    public UserProfile() {
    }
    public UserProfile(String firstName, String middleInitial, String lastName, Date dateofbirth,Long createdAt,boolean consentToShareInformation) {
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.lastName = lastName;
        this.dateofbirth = dateofbirth;
        this.createdAt = createdAt;
        this.consentToShareInformation = consentToShareInformation;
    }
	@Override
	public String toString() {
		return "UserProfile [id=" + id + ", userId=" + userId + ", firstName=" + firstName + ", middleInitial="
				+ middleInitial + ", dateofbirth=" + dateofbirth + ", lastName=" + lastName + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "]";
	}

}
