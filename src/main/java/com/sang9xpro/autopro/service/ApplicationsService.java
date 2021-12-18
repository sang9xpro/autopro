package com.sang9xpro.autopro.service;

import com.sang9xpro.autopro.domain.Applications;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Applications}.
 */
public interface ApplicationsService {
    /**
     * Save a applications.
     *
     * @param applications the entity to save.
     * @return the persisted entity.
     */
    Applications save(Applications applications);

    /**
     * Partially updates a applications.
     *
     * @param applications the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Applications> partialUpdate(Applications applications);

    /**
     * Get all the applications.
     *
     * @return the list of entities.
     */
    List<Applications> findAll();

    /**
     * Get the "id" applications.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Applications> findOne(Long id);

    /**
     * Delete the "id" applications.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
