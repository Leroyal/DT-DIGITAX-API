package com.digitax.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "phone")
        })
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @NotBlank
    @Size(max = 10)
    private String phone;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Column(name = "is_deleted")
    private int is_deleted;

    @Column(name = "is_active")
    private int is_active;
    
    @Column(name = "isVerifiedEmail")
    private int isVerifiedEmail;
    
    @Column(name = "isVerifiedPhone")
    private int isVerifiedPhone;
    

    @CreatedDate
    @Column(name = "created_at")
    private Long createdAt;
    
    
    @CreatedDate
    @Column(name = "deleted_at")
    private Long deletedAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Long updatedAt;

    public User() {
    }

    /**
     * User object
     *
     * @param username The username of the user
     * @param email    The email of the user
     * @param password The password of the user
     * @param phone    The phone number of the user
     */
    public User(String username, String email, String password, String phone, Long createdAt) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.createdAt = createdAt;
    }

	

}
