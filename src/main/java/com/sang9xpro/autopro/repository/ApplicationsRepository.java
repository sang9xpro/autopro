package com.sang9xpro.autopro.repository;

import com.sang9xpro.autopro.domain.Applications;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Applications entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationsRepository extends JpaRepository<Applications, Long> {}
