package com.digitax.repository;

import com.digitax.model.RestrictTax;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestrictTaxRepository extends JpaRepository<RestrictTax, Long> {
	RestrictTax findByYear(Number year);
}
