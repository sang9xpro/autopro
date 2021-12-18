package com.sang9xpro.autopro.service;

import com.sang9xpro.autopro.domain.SchedulerTaskFields;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link SchedulerTaskFields}.
 */
public interface SchedulerTaskFieldsService {
    /**
     * Save a schedulerTaskFields.
     *
     * @param schedulerTaskFields the entity to save.
     * @return the persisted entity.
     */
    SchedulerTaskFields save(SchedulerTaskFields schedulerTaskFields);

    /**
     * Partially updates a schedulerTaskFields.
     *
     * @param schedulerTaskFields the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SchedulerTaskFields> partialUpdate(SchedulerTaskFields schedulerTaskFields);

    /**
     * Get all the schedulerTaskFields.
     *
     * @return the list of entities.
     */
    List<SchedulerTaskFields> findAll();

    /**
     * Get the "id" schedulerTaskFields.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SchedulerTaskFields> findOne(Long id);

    /**
     * Delete the "id" schedulerTaskFields.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
