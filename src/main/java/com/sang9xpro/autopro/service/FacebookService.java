package com.sang9xpro.autopro.service;

import com.sang9xpro.autopro.domain.Facebook;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Facebook}.
 */
public interface FacebookService {
    /**
     * Save a facebook.
     *
     * @param facebook the entity to save.
     * @return the persisted entity.
     */
    Facebook save(Facebook facebook);

    /**
     * Partially updates a facebook.
     *
     * @param facebook the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Facebook> partialUpdate(Facebook facebook);

    /**
     * Get all the facebooks.
     *
     * @return the list of entities.
     */
    List<Facebook> findAll();

    /**
     * Get the "id" facebook.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Facebook> findOne(Long id);

    /**
     * Delete the "id" facebook.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
