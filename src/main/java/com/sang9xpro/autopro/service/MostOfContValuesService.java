package com.sang9xpro.autopro.service;

import com.sang9xpro.autopro.domain.MostOfContValues;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link MostOfContValues}.
 */
public interface MostOfContValuesService {
    /**
     * Save a mostOfContValues.
     *
     * @param mostOfContValues the entity to save.
     * @return the persisted entity.
     */
    MostOfContValues save(MostOfContValues mostOfContValues);

    /**
     * Partially updates a mostOfContValues.
     *
     * @param mostOfContValues the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MostOfContValues> partialUpdate(MostOfContValues mostOfContValues);

    /**
     * Get all the mostOfContValues.
     *
     * @return the list of entities.
     */
    List<MostOfContValues> findAll();

    /**
     * Get the "id" mostOfContValues.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MostOfContValues> findOne(Long id);

    /**
     * Delete the "id" mostOfContValues.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
