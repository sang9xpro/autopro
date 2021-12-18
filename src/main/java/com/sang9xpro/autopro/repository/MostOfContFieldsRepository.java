package com.sang9xpro.autopro.repository;

import com.sang9xpro.autopro.domain.MostOfContFields;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MostOfContFields entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MostOfContFieldsRepository extends JpaRepository<MostOfContFields, Long> {}
