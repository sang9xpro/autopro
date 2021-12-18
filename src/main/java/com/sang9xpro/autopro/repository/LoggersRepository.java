package com.sang9xpro.autopro.repository;

import com.sang9xpro.autopro.domain.Loggers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Loggers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoggersRepository extends JpaRepository<Loggers, Long> {}
