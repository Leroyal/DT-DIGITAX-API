
package com.digitax.repository;

import com.digitax.model.UserProfile;

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
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
	public Optional<UserProfile> findByUserId(long userId);
	
	@Modifying
    @Transactional 
	@Query(value = "UPDATE user_profile SET first_name = ?1, middle_initial = ?2, dateofbirth= ?3, last_name = ?4, updated_at= ?5, consent_to_share_information=?6 WHERE user_id = ?7", nativeQuery = true)
	public void updateDetails(String firstname, String middleInitial,Date dateofbirth,String lastname,Long updatedAt, Boolean b, Long user_id);

}
