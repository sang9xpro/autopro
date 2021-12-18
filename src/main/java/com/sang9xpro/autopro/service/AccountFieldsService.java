package com.sang9xpro.autopro.service;

import com.sang9xpro.autopro.domain.AccountFields;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link AccountFields}.
 */
public interface AccountFieldsService {
    /**
     * Save a accountFields.
     *
     * @param accountFields the entity to save.
     * @return the persisted entity.
     */
    AccountFields save(AccountFields accountFields);

    /**
     * Partially updates a accountFields.
     *
     * @param accountFields the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AccountFields> partialUpdate(AccountFields accountFields);

    /**
     * Get all the accountFields.
     *
     * @return the list of entities.
     */
    List<AccountFields> findAll();

    /**
     * Get the "id" accountFields.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AccountFields> findOne(Long id);

    /**
     * Delete the "id" accountFields.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
