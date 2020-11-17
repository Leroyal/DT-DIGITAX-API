package com.digitax.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.digitax.model.DeviceMetadata;

@Repository
public interface DeviceMetadataRepository extends JpaRepository<DeviceMetadata, Long> {
	List<DeviceMetadata> findByUserId(Long userId);

	DeviceMetadata findByUniqueSessionKey(String string);

	List<DeviceMetadata> findAllByUniqueId(String join);

	void removeByUserName(String userName);

	void removeByUniqueId(String uniqueId);
	
	
	@Modifying
    @Transactional 
	@Query(value = "delete from device_metadata WHERE unique_id = ?1 and user_name = ?2", nativeQuery = true)
	void deleteByUniqueIdandUserName(String uniqueId, String userName);

}