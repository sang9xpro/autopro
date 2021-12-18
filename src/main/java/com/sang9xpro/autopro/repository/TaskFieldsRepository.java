package com.sang9xpro.autopro.repository;

import com.sang9xpro.autopro.domain.TaskFields;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TaskFields entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskFieldsRepository extends JpaRepository<TaskFields, Long> {}
