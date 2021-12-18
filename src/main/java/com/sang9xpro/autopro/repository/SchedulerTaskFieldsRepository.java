package com.sang9xpro.autopro.repository;

import com.sang9xpro.autopro.domain.SchedulerTaskFields;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SchedulerTaskFields entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SchedulerTaskFieldsRepository extends JpaRepository<SchedulerTaskFields, Long> {}
