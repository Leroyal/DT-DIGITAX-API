package com.digitax.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "restrict_tax")
@Getter
@Setter

public class RestrictTax {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
   
    
	@Column(name = "year")
    private long year;
	
    @Column(name = "startDate")
    private Date startDate;
	
    @Column(name = "endDate")
    private Date endDate;
	
	@CreatedDate
    @Column(name = "created_at")
    private Long createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Long updatedAt;
	
}
