package com.sang9xpro.autopro.repository;

import com.sang9xpro.autopro.domain.CommentsValues;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CommentsValues entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommentsValuesRepository extends JpaRepository<CommentsValues, Long> {}
