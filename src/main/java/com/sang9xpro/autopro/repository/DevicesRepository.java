package com.sang9xpro.autopro.repository;

import com.sang9xpro.autopro.domain.Devices;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Devices entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DevicesRepository extends JpaRepository<Devices, Long> {}
