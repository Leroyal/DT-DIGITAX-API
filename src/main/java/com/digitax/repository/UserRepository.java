package com.digitax.repository;

import com.digitax.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;


/**
 * String provided framework to build a JPA based data access layer.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByPhone(String phone);

	User findByEmail(String email);

	User findByPhone(String phone);
	
	@Modifying
    @Transactional 
	@Query(value = "UPDATE users SET email = ?1, updated_at= ?2 WHERE id = ?3", nativeQuery = true)
	public void updateUserEmail(String email,Long updatedAt, Long user_id);

}
