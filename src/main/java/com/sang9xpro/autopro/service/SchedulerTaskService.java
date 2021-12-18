package com.sang9xpro.autopro.service;

import com.sang9xpro.autopro.domain.SchedulerTask;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link SchedulerTask}.
 */
public interface SchedulerTaskService {
    /**
     * Save a schedulerTask.
     *
     * @param schedulerTask the entity to save.
     * @return the persisted entity.
     */
    SchedulerTask save(SchedulerTask schedulerTask);

    /**
     * Partially updates a schedulerTask.
     *
     * @param schedulerTask the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SchedulerTask> partialUpdate(SchedulerTask schedulerTask);

    /**
     * Get all the schedulerTasks.
     *
     * @return the list of entities.
     */
    List<SchedulerTask> findAll();

    /**
     * Get the "id" schedulerTask.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SchedulerTask> findOne(Long id);

    /**
     * Delete the "id" schedulerTask.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
