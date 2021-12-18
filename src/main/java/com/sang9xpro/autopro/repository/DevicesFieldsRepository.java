package com.sang9xpro.autopro.repository;

import com.sang9xpro.autopro.domain.DevicesFields;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DevicesFields entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DevicesFieldsRepository extends JpaRepository<DevicesFields, Long> {}
