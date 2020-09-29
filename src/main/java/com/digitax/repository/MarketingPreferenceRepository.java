package com.digitax.repository;

import com.digitax.model.MarketingPreference;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketingPreferenceRepository extends JpaRepository<MarketingPreference, Long> {

	Optional<MarketingPreference> findByUserId(long userId);
	
	@Modifying
    @Transactional 
	@Query(value = "UPDATE marketing_preference SET is_contact_via_mail_disabled = ?1, is_contact_via_email_disabled = ?2, is_contact_via_phone_disabled= ?3, updated_at= ?4 WHERE user_id = ?5", nativeQuery = true)
	public void updateMarketingPref(String isContactViaMailDisabled,String isContactViaEmailDisabled, String isContactViaPhoneDisabled,Long updatedAt, Long user_id);
}
