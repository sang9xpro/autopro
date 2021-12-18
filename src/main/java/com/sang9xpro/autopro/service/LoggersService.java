package com.sang9xpro.autopro.service;

import com.sang9xpro.autopro.domain.Loggers;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Loggers}.
 */
public interface LoggersService {
    /**
     * Save a loggers.
     *
     * @param loggers the entity to save.
     * @return the persisted entity.
     */
    Loggers save(Loggers loggers);

    /**
     * Partially updates a loggers.
     *
     * @param loggers the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Loggers> partialUpdate(Loggers loggers);

    /**
     * Get all the loggers.
     *
     * @return the list of entities.
     */
    List<Loggers> findAll();

    /**
     * Get the "id" loggers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Loggers> findOne(Long id);

    /**
     * Delete the "id" loggers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
