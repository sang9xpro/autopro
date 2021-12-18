package com.sang9xpro.autopro.repository;

import com.sang9xpro.autopro.domain.LoggersValues;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LoggersValues entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoggersValuesRepository extends JpaRepository<LoggersValues, Long> {}
