package com.sang9xpro.autopro.repository;

import com.sang9xpro.autopro.domain.SchedulerTaskValues;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SchedulerTaskValues entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SchedulerTaskValuesRepository extends JpaRepository<SchedulerTaskValues, Long> {}
