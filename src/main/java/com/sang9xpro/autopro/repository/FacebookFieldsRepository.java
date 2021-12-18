package com.sang9xpro.autopro.repository;

import com.sang9xpro.autopro.domain.FacebookFields;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FacebookFields entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FacebookFieldsRepository extends JpaRepository<FacebookFields, Long> {}
