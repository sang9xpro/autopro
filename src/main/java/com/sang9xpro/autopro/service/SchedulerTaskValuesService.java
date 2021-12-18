package com.sang9xpro.autopro.service;

import com.sang9xpro.autopro.domain.SchedulerTaskValues;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link SchedulerTaskValues}.
 */
public interface SchedulerTaskValuesService {
    /**
     * Save a schedulerTaskValues.
     *
     * @param schedulerTaskValues the entity to save.
     * @return the persisted entity.
     */
    SchedulerTaskValues save(SchedulerTaskValues schedulerTaskValues);

    /**
     * Partially updates a schedulerTaskValues.
     *
     * @param schedulerTaskValues the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SchedulerTaskValues> partialUpdate(SchedulerTaskValues schedulerTaskValues);

    /**
     * Get all the schedulerTaskValues.
     *
     * @return the list of entities.
     */
    List<SchedulerTaskValues> findAll();

    /**
     * Get the "id" schedulerTaskValues.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SchedulerTaskValues> findOne(Long id);

    /**
     * Delete the "id" schedulerTaskValues.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
