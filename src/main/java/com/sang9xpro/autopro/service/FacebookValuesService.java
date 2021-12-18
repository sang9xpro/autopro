package com.sang9xpro.autopro.service;

import com.sang9xpro.autopro.domain.FacebookValues;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link FacebookValues}.
 */
public interface FacebookValuesService {
    /**
     * Save a facebookValues.
     *
     * @param facebookValues the entity to save.
     * @return the persisted entity.
     */
    FacebookValues save(FacebookValues facebookValues);

    /**
     * Partially updates a facebookValues.
     *
     * @param facebookValues the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FacebookValues> partialUpdate(FacebookValues facebookValues);

    /**
     * Get all the facebookValues.
     *
     * @return the list of entities.
     */
    List<FacebookValues> findAll();

    /**
     * Get the "id" facebookValues.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FacebookValues> findOne(Long id);

    /**
     * Delete the "id" facebookValues.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
