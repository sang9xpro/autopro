package com.sang9xpro.autopro.repository;

import com.sang9xpro.autopro.domain.FacebookValues;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FacebookValues entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FacebookValuesRepository extends JpaRepository<FacebookValues, Long> {}
