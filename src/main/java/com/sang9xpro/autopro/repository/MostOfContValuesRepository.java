package com.sang9xpro.autopro.repository;

import com.sang9xpro.autopro.domain.MostOfContValues;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MostOfContValues entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MostOfContValuesRepository extends JpaRepository<MostOfContValues, Long> {}
