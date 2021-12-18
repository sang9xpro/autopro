package com.sang9xpro.autopro.service;

import com.sang9xpro.autopro.domain.FacebookFields;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link FacebookFields}.
 */
public interface FacebookFieldsService {
    /**
     * Save a facebookFields.
     *
     * @param facebookFields the entity to save.
     * @return the persisted entity.
     */
    FacebookFields save(FacebookFields facebookFields);

    /**
     * Partially updates a facebookFields.
     *
     * @param facebookFields the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FacebookFields> partialUpdate(FacebookFields facebookFields);

    /**
     * Get all the facebookFields.
     *
     * @return the list of entities.
     */
    List<FacebookFields> findAll();

    /**
     * Get the "id" facebookFields.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FacebookFields> findOne(Long id);

    /**
     * Delete the "id" facebookFields.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
