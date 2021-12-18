package com.sang9xpro.autopro.service;

import com.sang9xpro.autopro.domain.CommentsValues;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CommentsValues}.
 */
public interface CommentsValuesService {
    /**
     * Save a commentsValues.
     *
     * @param commentsValues the entity to save.
     * @return the persisted entity.
     */
    CommentsValues save(CommentsValues commentsValues);

    /**
     * Partially updates a commentsValues.
     *
     * @param commentsValues the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CommentsValues> partialUpdate(CommentsValues commentsValues);

    /**
     * Get all the commentsValues.
     *
     * @return the list of entities.
     */
    List<CommentsValues> findAll();

    /**
     * Get the "id" commentsValues.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CommentsValues> findOne(Long id);

    /**
     * Delete the "id" commentsValues.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
