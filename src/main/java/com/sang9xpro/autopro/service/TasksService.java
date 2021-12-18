package com.sang9xpro.autopro.service;

import com.sang9xpro.autopro.domain.Tasks;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Tasks}.
 */
public interface TasksService {
    /**
     * Save a tasks.
     *
     * @param tasks the entity to save.
     * @return the persisted entity.
     */
    Tasks save(Tasks tasks);

    /**
     * Partially updates a tasks.
     *
     * @param tasks the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Tasks> partialUpdate(Tasks tasks);

    /**
     * Get all the tasks.
     *
     * @return the list of entities.
     */
    List<Tasks> findAll();

    /**
     * Get the "id" tasks.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Tasks> findOne(Long id);

    /**
     * Delete the "id" tasks.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
