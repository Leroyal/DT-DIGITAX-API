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
    private long id;
   
    
    @Column(name = "user_id")
    private long userId;

    @Column(name = "userFirstName")
    private String userFirstName;

    @Column(name = "userMiddleInitial")
    private String userMiddleInitial;
    
    @Column(name = "userOcupation")
    private String userOcupation;
    
    @Column(name = "userDateofbirth")
    private Date userDateofbirth;
    
    @Column(name = "consentToShareInformation")
    private boolean consentToShareInformation;
    
    @Column(name = "userLastName")
    private String userLastName;

    @CreatedDate
    @Column(name = "created_at")
    private Long createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Long updatedAt;

	@Override
	public String toString() {
		return "UserProfile [id=" + id + ", userId=" + userId + ", userFirstName=" + userFirstName
				+ ", userMiddleInitial=" + userMiddleInitial + ", userOcupation=" + userOcupation + ", userDateofbirth="
				+ userDateofbirth + ", consentToShareInformation=" + consentToShareInformation + ", userLastName="
				+ userLastName + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
    
	

}
