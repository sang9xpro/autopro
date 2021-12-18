package com.sang9xpro.autopro.repository;

import com.sang9xpro.autopro.domain.MostOfContent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MostOfContent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MostOfContentRepository extends JpaRepository<MostOfContent, Long> {}
