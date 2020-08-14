package com.digitax.repository;

import com.digitax.model.UserAddress;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * String provided framework to build a JPA based data access layer.
 */
@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
	public Optional<UserAddress> findByUserId(long userId);
	
	@Modifying
    @Transactional 
	@Query(value = "UPDATE address SET address_line1 = ?1, address_line2 = ?2, city= ?3, state = ?4, state_code= ?5, postal_code = ?6, country = ?7, country_code = ?8, updated_at = ?9 WHERE user_id = ?10", nativeQuery = true)
	public void updateAddress(String addressLine1,String addressLine2 ,String city,String state,Long stateCode,Long postalCode, String country,Long countryCode, Long updatedAt, Long user_id);


}
