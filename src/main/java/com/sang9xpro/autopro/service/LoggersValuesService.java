package com.sang9xpro.autopro.service;

import com.sang9xpro.autopro.domain.LoggersValues;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link LoggersValues}.
 */
public interface LoggersValuesService {
    /**
     * Save a loggersValues.
     *
     * @param loggersValues the entity to save.
     * @return the persisted entity.
     */
    LoggersValues save(LoggersValues loggersValues);

    /**
     * Partially updates a loggersValues.
     *
     * @param loggersValues the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LoggersValues> partialUpdate(LoggersValues loggersValues);

    /**
     * Get all the loggersValues.
     *
     * @return the list of entities.
     */
    List<LoggersValues> findAll();

    /**
     * Get the "id" loggersValues.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LoggersValues> findOne(Long id);

    /**
     * Delete the "id" loggersValues.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
