package com.digitax.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.digitax.model.DeviceMetadata;

@Repository
public interface DeviceMetadataRepository extends JpaRepository<DeviceMetadata, Long> {
	List<DeviceMetadata> findByUserId(Long userId);

	List<DeviceMetadata> findAllByHardwareAddress(String join);

	DeviceMetadata findByHardwareAddress(String join);


	DeviceMetadata findByUniqueSessionKey(String string);
}