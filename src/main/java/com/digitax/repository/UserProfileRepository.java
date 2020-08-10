
package com.digitax.repository;

import com.digitax.model.UserProfile;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * String provided framework to build a JPA based data access layer.
 */
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
	public Optional<UserProfile> findByUserId(long userId);
}
