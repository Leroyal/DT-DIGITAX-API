package com.digitax.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "vehicle")
@Getter
@Setter
public class Vehicle {

	@Override
	public String toString() {
		return "Vehicle [id=" + id + ", make=" + make + ", model=" + model + ", nickName=" + nickName
				+ ", primaryVehicle=" + primaryVehicle + ", odoMeter=" + odoMeter + ", notes=" + notes + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + ", getId()=" + getId() + ", getMake()=" + getMake()
				+ ", getModel()=" + getModel() + ", getNickName()=" + getNickName() + ", getPrimaryVehicle()="
				+ getPrimaryVehicle() + ", getOdoMeter()=" + getOdoMeter() + ", getNotes()=" + getNotes()
				+ ", getCreatedAt()=" + getCreatedAt() + ", getUpdatedAt()=" + getUpdatedAt() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	
	@Column(name = "user_id")
    private long userId;
	
	@Column(name = "make")
    private String make;
	
	@Column(name = "model")
    private String model;
	
	@Column(name = "year")
    private Long year;
	
	@Column(name = "nickName")
    private String nickName;
	
	@Column(name = "primaryVehicle")
    private Boolean primaryVehicle;
	
	@Column(name = "odoMeter")
    private Long odoMeter;
	
	@Column(name = "notes")
    private String notes;
	
	@CreatedDate
    @Column(name = "created_at")
    private Long createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Long updatedAt;



}
