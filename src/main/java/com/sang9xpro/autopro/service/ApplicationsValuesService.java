package com.sang9xpro.autopro.service;

import com.sang9xpro.autopro.domain.ApplicationsValues;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ApplicationsValues}.
 */
public interface ApplicationsValuesService {
    /**
     * Save a applicationsValues.
     *
     * @param applicationsValues the entity to save.
     * @return the persisted entity.
     */
    ApplicationsValues save(ApplicationsValues applicationsValues);

    /**
     * Partially updates a applicationsValues.
     *
     * @param applicationsValues the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ApplicationsValues> partialUpdate(ApplicationsValues applicationsValues);

    /**
     * Get all the applicationsValues.
     *
     * @return the list of entities.
     */
    List<ApplicationsValues> findAll();

    /**
     * Get the "id" applicationsValues.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApplicationsValues> findOne(Long id);

    /**
     * Delete the "id" applicationsValues.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
