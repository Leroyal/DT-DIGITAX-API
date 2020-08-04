package com.digitax.repository;

import com.digitax.model.Relation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * String provided framework to build a JPA based data access layer.
 */
@Repository
public interface RelationRepository extends JpaRepository<Relation, Long> {
    Relation findByName(String name);
}


