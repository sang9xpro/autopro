package com.sang9xpro.autopro.service;

import com.sang9xpro.autopro.domain.History;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link History}.
 */
public interface HistoryService {
    /**
     * Save a history.
     *
     * @param history the entity to save.
     * @return the persisted entity.
     */
    History save(History history);

    /**
     * Partially updates a history.
     *
     * @param history the entity to update partially.
     * @return the persisted entity.
     */
    Optional<History> partialUpdate(History history);

    /**
     * Get all the histories.
     *
     * @return the list of entities.
     */
    List<History> findAll();

    /**
     * Get the "id" history.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<History> findOne(Long id);

    /**
     * Delete the "id" history.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
