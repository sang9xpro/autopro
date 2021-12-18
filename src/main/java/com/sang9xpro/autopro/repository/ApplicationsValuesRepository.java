package com.sang9xpro.autopro.repository;

import com.sang9xpro.autopro.domain.ApplicationsValues;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ApplicationsValues entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationsValuesRepository extends JpaRepository<ApplicationsValues, Long> {}
