package com.digitax.repository;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.json.simple.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.digitax.model.TaxHistory;

import springfox.documentation.spring.web.json.Json;



@Repository
public interface TaxHistoryRepository extends JpaRepository<TaxHistory, Long> {
	public Optional<TaxHistory> findByUserId(long userId);
	
	@Modifying
    @Transactional 
	@Query(value = "UPDATE user_tax_history SET personal_info = ?1, income = ?2, tax_breaks= ?3, previous_year_summary = ?4, updated_at= ?5, consent_to_share_information=?6 WHERE user_id = ?7", nativeQuery = true)
	public void updateTaxHistory(String personalInfo,String income, String taxBreaks, String previousYearSummary,Long updatedAt, String string, Long user_id);


	
}
