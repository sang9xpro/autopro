package com.sang9xpro.autopro.repository;

import com.sang9xpro.autopro.domain.AccountValues;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AccountValues entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountValuesRepository extends JpaRepository<AccountValues, Long> {}
