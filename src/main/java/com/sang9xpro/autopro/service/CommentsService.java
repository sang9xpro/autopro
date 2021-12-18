package com.sang9xpro.autopro.service;

import com.sang9xpro.autopro.domain.Comments;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Comments}.
 */
public interface CommentsService {
    /**
     * Save a comments.
     *
     * @param comments the entity to save.
     * @return the persisted entity.
     */
    Comments save(Comments comments);

    /**
     * Partially updates a comments.
     *
     * @param comments the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Comments> partialUpdate(Comments comments);

    /**
     * Get all the comments.
     *
     * @return the list of entities.
     */
    List<Comments> findAll();

    /**
     * Get the "id" comments.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Comments> findOne(Long id);

    /**
     * Delete the "id" comments.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
