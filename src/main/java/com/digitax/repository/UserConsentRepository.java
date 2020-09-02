package com.digitax.repository;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.digitax.model.TrueFalse;
import com.digitax.model.UserConsent;



@Repository
public interface UserConsentRepository extends JpaRepository<UserConsent, Long> {
	public Optional<UserConsent> findByUserId(long userId);
	
	@Modifying
    @Transactional 
	@Query(value = "UPDATE user_consent SET first_name = ?1, last_name = ?2, spouse_first_name= ?3, spouse_last_name = ?4, updated_at= ?5,consent_to_share_information=?6 WHERE user_id = ?7", nativeQuery = true)
	public void updateConsent(String firstname, String last_name, String spouse_first_name, String spouse_last_name,Long updatedAt, String string,Long user_id);

	
}
