package com.sang9xpro.autopro.service;

import com.sang9xpro.autopro.domain.AccountValues;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link AccountValues}.
 */
public interface AccountValuesService {
    /**
     * Save a accountValues.
     *
     * @param accountValues the entity to save.
     * @return the persisted entity.
     */
    AccountValues save(AccountValues accountValues);

    /**
     * Partially updates a accountValues.
     *
     * @param accountValues the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AccountValues> partialUpdate(AccountValues accountValues);

    /**
     * Get all the accountValues.
     *
     * @return the list of entities.
     */
    List<AccountValues> findAll();

    /**
     * Get the "id" accountValues.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AccountValues> findOne(Long id);

    /**
     * Delete the "id" accountValues.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
