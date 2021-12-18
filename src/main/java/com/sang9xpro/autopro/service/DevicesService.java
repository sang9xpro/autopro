package com.sang9xpro.autopro.service;

import com.sang9xpro.autopro.domain.Devices;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Devices}.
 */
public interface DevicesService {
    /**
     * Save a devices.
     *
     * @param devices the entity to save.
     * @return the persisted entity.
     */
    Devices save(Devices devices);

    /**
     * Partially updates a devices.
     *
     * @param devices the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Devices> partialUpdate(Devices devices);

    /**
     * Get all the devices.
     *
     * @return the list of entities.
     */
    List<Devices> findAll();

    /**
     * Get the "id" devices.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Devices> findOne(Long id);

    /**
     * Delete the "id" devices.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
