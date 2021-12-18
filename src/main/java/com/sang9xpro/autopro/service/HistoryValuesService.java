package com.sang9xpro.autopro.service;

import com.sang9xpro.autopro.domain.HistoryValues;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link HistoryValues}.
 */
public interface HistoryValuesService {
    /**
     * Save a historyValues.
     *
     * @param historyValues the entity to save.
     * @return the persisted entity.
     */
    HistoryValues save(HistoryValues historyValues);

    /**
     * Partially updates a historyValues.
     *
     * @param historyValues the entity to update partially.
     * @return the persisted entity.
     */
    Optional<HistoryValues> partialUpdate(HistoryValues historyValues);

    /**
     * Get all the historyValues.
     *
     * @return the list of entities.
     */
    List<HistoryValues> findAll();

    /**
     * Get the "id" historyValues.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HistoryValues> findOne(Long id);

    /**
     * Delete the "id" historyValues.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
