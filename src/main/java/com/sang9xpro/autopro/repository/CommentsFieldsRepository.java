package com.sang9xpro.autopro.repository;

import com.sang9xpro.autopro.domain.CommentsFields;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CommentsFields entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommentsFieldsRepository extends JpaRepository<CommentsFields, Long> {}
