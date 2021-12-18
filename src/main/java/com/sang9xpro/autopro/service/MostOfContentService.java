package com.sang9xpro.autopro.service;

import com.sang9xpro.autopro.domain.MostOfContent;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link MostOfContent}.
 */
public interface MostOfContentService {
    /**
     * Save a mostOfContent.
     *
     * @param mostOfContent the entity to save.
     * @return the persisted entity.
     */
    MostOfContent save(MostOfContent mostOfContent);

    /**
     * Partially updates a mostOfContent.
     *
     * @param mostOfContent the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MostOfContent> partialUpdate(MostOfContent mostOfContent);

    /**
     * Get all the mostOfContents.
     *
     * @return the list of entities.
     */
    List<MostOfContent> findAll();

    /**
     * Get the "id" mostOfContent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MostOfContent> findOne(Long id);

    /**
     * Delete the "id" mostOfContent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
