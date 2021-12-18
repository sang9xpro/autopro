package com.sang9xpro.autopro.service;

import com.sang9xpro.autopro.domain.DeviceValues;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link DeviceValues}.
 */
public interface DeviceValuesService {
    /**
     * Save a deviceValues.
     *
     * @param deviceValues the entity to save.
     * @return the persisted entity.
     */
    DeviceValues save(DeviceValues deviceValues);

    /**
     * Partially updates a deviceValues.
     *
     * @param deviceValues the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DeviceValues> partialUpdate(DeviceValues deviceValues);

    /**
     * Get all the deviceValues.
     *
     * @return the list of entities.
     */
    List<DeviceValues> findAll();

    /**
     * Get the "id" deviceValues.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DeviceValues> findOne(Long id);

    /**
     * Delete the "id" deviceValues.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
