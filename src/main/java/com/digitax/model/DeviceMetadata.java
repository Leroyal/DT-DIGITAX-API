package com.digitax.model;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.data.annotation.CreatedDate;
import javax.persistence.Id;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "device_metadata", uniqueConstraints = {
        @UniqueConstraint(columnNames = "hardwareAddress"),
})
@Getter
@Setter
public class DeviceMetadata {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	@Column(name = "user_id")
    private long userId;
	@Column(name = "userAgent")
    private String userAgent;
	@Column(name = "clientOS")
    private String clientOS;
	@Column(name = "clientBrowser")
    private String clientIpAddr;
	@Column(name = "clientIpAddr")
    private String clientBrowser;
	@Column(name = "hardwareAddress")
    private String hardwareAddress;
	@Column(name = "isLoggedIn")
    private Boolean isLoggedIn;
    @CreatedDate
    @Column(name = "created_at")
    private Long createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Long updatedAt;



}
