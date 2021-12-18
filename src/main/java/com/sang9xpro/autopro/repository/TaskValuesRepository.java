package com.sang9xpro.autopro.repository;

import com.sang9xpro.autopro.domain.TaskValues;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TaskValues entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskValuesRepository extends JpaRepository<TaskValues, Long> {}
