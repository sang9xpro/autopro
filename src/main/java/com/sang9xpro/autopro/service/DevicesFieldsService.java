package com.sang9xpro.autopro.service;

import com.sang9xpro.autopro.domain.DevicesFields;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link DevicesFields}.
 */
public interface DevicesFieldsService {
    /**
     * Save a devicesFields.
     *
     * @param devicesFields the entity to save.
     * @return the persisted entity.
     */
    DevicesFields save(DevicesFields devicesFields);

    /**
     * Partially updates a devicesFields.
     *
     * @param devicesFields the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DevicesFields> partialUpdate(DevicesFields devicesFields);

    /**
     * Get all the devicesFields.
     *
     * @return the list of entities.
     */
    List<DevicesFields> findAll();

    /**
     * Get the "id" devicesFields.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DevicesFields> findOne(Long id);

    /**
     * Delete the "id" devicesFields.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
