package com.digitax.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;

@Entity
@Table(name = "address", uniqueConstraints = {
        @UniqueConstraint(columnNames = "user_id"),
})
@Getter
@Setter
public class UserAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
   
    
    @Column(name = "user_id")
    private long userId;

    @Column(name = "addressLine1")
    private String addressLine1;

    @Column(name = "addressLine2")
    private String addressLine2;
    
    @Column(name = "city")
    private String city;
    
    @Column(name = "state")
    private String state;
    
    @Column(name = "stateCode")
    private Long stateCode;

    
    @Column(name = "postalCode")
    private Long postalCode;

    
    @Column(name = "country")
    private String country;
    
    @Column(name = "countryCode")
    private Long countryCode;


    @CreatedDate
    @Column(name = "created_at")
    private Long createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Long updatedAt;
    
    public UserAddress() {
    }
    public UserAddress(String addressLine1, String addressLine2, String city, String state,Long stateCode,Long postalCode,String country,Long countryCode,Long createdAt) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.stateCode = stateCode;
        this.postalCode = postalCode;
        this.country = country;
        this.countryCode = countryCode;
        this.createdAt = createdAt;
    }

}
