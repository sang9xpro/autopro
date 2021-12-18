package com.sang9xpro.autopro.service;

import com.sang9xpro.autopro.domain.TaskValues;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TaskValues}.
 */
public interface TaskValuesService {
    /**
     * Save a taskValues.
     *
     * @param taskValues the entity to save.
     * @return the persisted entity.
     */
    TaskValues save(TaskValues taskValues);

    /**
     * Partially updates a taskValues.
     *
     * @param taskValues the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TaskValues> partialUpdate(TaskValues taskValues);

    /**
     * Get all the taskValues.
     *
     * @return the list of entities.
     */
    List<TaskValues> findAll();

    /**
     * Get the "id" taskValues.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TaskValues> findOne(Long id);

    /**
     * Delete the "id" taskValues.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
