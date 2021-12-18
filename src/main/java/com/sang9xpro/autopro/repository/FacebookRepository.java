package com.sang9xpro.autopro.repository;

import com.sang9xpro.autopro.domain.Facebook;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Facebook entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FacebookRepository extends JpaRepository<Facebook, Long> {}
