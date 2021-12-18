package com.sang9xpro.autopro.service;

import com.sang9xpro.autopro.domain.HistoryFields;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link HistoryFields}.
 */
public interface HistoryFieldsService {
    /**
     * Save a historyFields.
     *
     * @param historyFields the entity to save.
     * @return the persisted entity.
     */
    HistoryFields save(HistoryFields historyFields);

    /**
     * Partially updates a historyFields.
     *
     * @param historyFields the entity to update partially.
     * @return the persisted entity.
     */
    Optional<HistoryFields> partialUpdate(HistoryFields historyFields);

    /**
     * Get all the historyFields.
     *
     * @return the list of entities.
     */
    List<HistoryFields> findAll();

    /**
     * Get the "id" historyFields.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HistoryFields> findOne(Long id);

    /**
     * Delete the "id" historyFields.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
