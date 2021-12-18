package com.sang9xpro.autopro.repository;

import com.sang9xpro.autopro.domain.ApplicationsFields;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ApplicationsFields entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationsFieldsRepository extends JpaRepository<ApplicationsFields, Long> {}
