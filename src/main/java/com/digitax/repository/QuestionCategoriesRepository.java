package com.digitax.repository;


import com.digitax.model.QuestionCategories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionCategoriesRepository extends JpaRepository<QuestionCategories, Long> {
	QuestionCategories findByTitle(String title);
}

