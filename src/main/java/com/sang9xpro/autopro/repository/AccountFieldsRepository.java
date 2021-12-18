package com.sang9xpro.autopro.repository;

import com.sang9xpro.autopro.domain.AccountFields;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AccountFields entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountFieldsRepository extends JpaRepository<AccountFields, Long> {}
