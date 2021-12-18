package com.sang9xpro.autopro.service;

import com.sang9xpro.autopro.domain.Accounts;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Accounts}.
 */
public interface AccountsService {
    /**
     * Save a accounts.
     *
     * @param accounts the entity to save.
     * @return the persisted entity.
     */
    Accounts save(Accounts accounts);

    /**
     * Partially updates a accounts.
     *
     * @param accounts the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Accounts> partialUpdate(Accounts accounts);

    /**
     * Get all the accounts.
     *
     * @return the list of entities.
     */
    List<Accounts> findAll();

    /**
     * Get the "id" accounts.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Accounts> findOne(Long id);

    /**
     * Delete the "id" accounts.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
