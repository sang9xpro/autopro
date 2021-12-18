package com.sang9xpro.autopro.service;

import com.sang9xpro.autopro.domain.CommentsFields;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CommentsFields}.
 */
public interface CommentsFieldsService {
    /**
     * Save a commentsFields.
     *
     * @param commentsFields the entity to save.
     * @return the persisted entity.
     */
    CommentsFields save(CommentsFields commentsFields);

    /**
     * Partially updates a commentsFields.
     *
     * @param commentsFields the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CommentsFields> partialUpdate(CommentsFields commentsFields);

    /**
     * Get all the commentsFields.
     *
     * @return the list of entities.
     */
    List<CommentsFields> findAll();

    /**
     * Get the "id" commentsFields.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CommentsFields> findOne(Long id);

    /**
     * Delete the "id" commentsFields.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
