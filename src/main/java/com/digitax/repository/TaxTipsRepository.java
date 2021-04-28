package com.digitax.repository;


import com.digitax.model.TaxTips;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxTipsRepository extends JpaRepository<TaxTips, Long> {
	TaxTips findByTitle(String title);
}

