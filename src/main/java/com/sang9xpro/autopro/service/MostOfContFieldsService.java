package com.sang9xpro.autopro.service;

import com.sang9xpro.autopro.domain.MostOfContFields;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link MostOfContFields}.
 */
public interface MostOfContFieldsService {
    /**
     * Save a mostOfContFields.
     *
     * @param mostOfContFields the entity to save.
     * @return the persisted entity.
     */
    MostOfContFields save(MostOfContFields mostOfContFields);

    /**
     * Partially updates a mostOfContFields.
     *
     * @param mostOfContFields the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MostOfContFields> partialUpdate(MostOfContFields mostOfContFields);

    /**
     * Get all the mostOfContFields.
     *
     * @return the list of entities.
     */
    List<MostOfContFields> findAll();

    /**
     * Get the "id" mostOfContFields.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MostOfContFields> findOne(Long id);

    /**
     * Delete the "id" mostOfContFields.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
