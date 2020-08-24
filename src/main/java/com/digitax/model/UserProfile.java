package com.digitax.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
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
    
    @Column(name = "dateofbirth")
    private Date dateofbirth;
    
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
    public UserProfile(String firstName, String middleInitial, String lastName, Date dateofbirth,Long createdAt) {
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.lastName = lastName;
        this.dateofbirth = dateofbirth;
        this.createdAt = createdAt;
    }

}
