package com.sang9xpro.autopro.service;

import com.sang9xpro.autopro.domain.TaskFields;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TaskFields}.
 */
public interface TaskFieldsService {
    /**
     * Save a taskFields.
     *
     * @param taskFields the entity to save.
     * @return the persisted entity.
     */
    TaskFields save(TaskFields taskFields);

    /**
     * Partially updates a taskFields.
     *
     * @param taskFields the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TaskFields> partialUpdate(TaskFields taskFields);

    /**
     * Get all the taskFields.
     *
     * @return the list of entities.
     */
    List<TaskFields> findAll();

    /**
     * Get the "id" taskFields.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TaskFields> findOne(Long id);

    /**
     * Delete the "id" taskFields.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
