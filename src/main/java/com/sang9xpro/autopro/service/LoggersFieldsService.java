package com.sang9xpro.autopro.service;

import com.sang9xpro.autopro.domain.LoggersFields;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link LoggersFields}.
 */
public interface LoggersFieldsService {
    /**
     * Save a loggersFields.
     *
     * @param loggersFields the entity to save.
     * @return the persisted entity.
     */
    LoggersFields save(LoggersFields loggersFields);

    /**
     * Partially updates a loggersFields.
     *
     * @param loggersFields the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LoggersFields> partialUpdate(LoggersFields loggersFields);

    /**
     * Get all the loggersFields.
     *
     * @return the list of entities.
     */
    List<LoggersFields> findAll();

    /**
     * Get the "id" loggersFields.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LoggersFields> findOne(Long id);

    /**
     * Delete the "id" loggersFields.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
