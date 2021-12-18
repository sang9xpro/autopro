package com.sang9xpro.autopro.service;

import com.sang9xpro.autopro.domain.ApplicationsFields;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ApplicationsFields}.
 */
public interface ApplicationsFieldsService {
    /**
     * Save a applicationsFields.
     *
     * @param applicationsFields the entity to save.
     * @return the persisted entity.
     */
    ApplicationsFields save(ApplicationsFields applicationsFields);

    /**
     * Partially updates a applicationsFields.
     *
     * @param applicationsFields the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ApplicationsFields> partialUpdate(ApplicationsFields applicationsFields);

    /**
     * Get all the applicationsFields.
     *
     * @return the list of entities.
     */
    List<ApplicationsFields> findAll();

    /**
     * Get the "id" applicationsFields.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApplicationsFields> findOne(Long id);

    /**
     * Delete the "id" applicationsFields.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
