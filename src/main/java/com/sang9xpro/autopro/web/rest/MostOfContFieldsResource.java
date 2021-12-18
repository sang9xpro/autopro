package com.sang9xpro.autopro.web.rest;

import com.sang9xpro.autopro.domain.MostOfContFields;
import com.sang9xpro.autopro.repository.MostOfContFieldsRepository;
import com.sang9xpro.autopro.service.MostOfContFieldsService;
import com.sang9xpro.autopro.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.sang9xpro.autopro.domain.MostOfContFields}.
 */
@RestController
@RequestMapping("/api")
public class MostOfContFieldsResource {

    private final Logger log = LoggerFactory.getLogger(MostOfContFieldsResource.class);

    private static final String ENTITY_NAME = "mostOfContFields";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MostOfContFieldsService mostOfContFieldsService;

    private final MostOfContFieldsRepository mostOfContFieldsRepository;

    public MostOfContFieldsResource(
        MostOfContFieldsService mostOfContFieldsService,
        MostOfContFieldsRepository mostOfContFieldsRepository
    ) {
        this.mostOfContFieldsService = mostOfContFieldsService;
        this.mostOfContFieldsRepository = mostOfContFieldsRepository;
    }

    /**
     * {@code POST  /most-of-cont-fields} : Create a new mostOfContFields.
     *
     * @param mostOfContFields the mostOfContFields to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mostOfContFields, or with status {@code 400 (Bad Request)} if the mostOfContFields has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/most-of-cont-fields")
    public ResponseEntity<MostOfContFields> createMostOfContFields(@RequestBody MostOfContFields mostOfContFields)
        throws URISyntaxException {
        log.debug("REST request to save MostOfContFields : {}", mostOfContFields);
        if (mostOfContFields.getId() != null) {
            throw new BadRequestAlertException("A new mostOfContFields cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MostOfContFields result = mostOfContFieldsService.save(mostOfContFields);
        return ResponseEntity
            .created(new URI("/api/most-of-cont-fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /most-of-cont-fields/:id} : Updates an existing mostOfContFields.
     *
     * @param id the id of the mostOfContFields to save.
     * @param mostOfContFields the mostOfContFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mostOfContFields,
     * or with status {@code 400 (Bad Request)} if the mostOfContFields is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mostOfContFields couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/most-of-cont-fields/{id}")
    public ResponseEntity<MostOfContFields> updateMostOfContFields(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MostOfContFields mostOfContFields
    ) throws URISyntaxException {
        log.debug("REST request to update MostOfContFields : {}, {}", id, mostOfContFields);
        if (mostOfContFields.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mostOfContFields.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mostOfContFieldsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MostOfContFields result = mostOfContFieldsService.save(mostOfContFields);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mostOfContFields.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /most-of-cont-fields/:id} : Partial updates given fields of an existing mostOfContFields, field will ignore if it is null
     *
     * @param id the id of the mostOfContFields to save.
     * @param mostOfContFields the mostOfContFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mostOfContFields,
     * or with status {@code 400 (Bad Request)} if the mostOfContFields is not valid,
     * or with status {@code 404 (Not Found)} if the mostOfContFields is not found,
     * or with status {@code 500 (Internal Server Error)} if the mostOfContFields couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/most-of-cont-fields/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MostOfContFields> partialUpdateMostOfContFields(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MostOfContFields mostOfContFields
    ) throws URISyntaxException {
        log.debug("REST request to partial update MostOfContFields partially : {}, {}", id, mostOfContFields);
        if (mostOfContFields.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mostOfContFields.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mostOfContFieldsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MostOfContFields> result = mostOfContFieldsService.partialUpdate(mostOfContFields);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mostOfContFields.getId().toString())
        );
    }

    /**
     * {@code GET  /most-of-cont-fields} : get all the mostOfContFields.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mostOfContFields in body.
     */
    @GetMapping("/most-of-cont-fields")
    public List<MostOfContFields> getAllMostOfContFields() {
        log.debug("REST request to get all MostOfContFields");
        return mostOfContFieldsService.findAll();
    }

    /**
     * {@code GET  /most-of-cont-fields/:id} : get the "id" mostOfContFields.
     *
     * @param id the id of the mostOfContFields to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mostOfContFields, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/most-of-cont-fields/{id}")
    public ResponseEntity<MostOfContFields> getMostOfContFields(@PathVariable Long id) {
        log.debug("REST request to get MostOfContFields : {}", id);
        Optional<MostOfContFields> mostOfContFields = mostOfContFieldsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mostOfContFields);
    }

    /**
     * {@code DELETE  /most-of-cont-fields/:id} : delete the "id" mostOfContFields.
     *
     * @param id the id of the mostOfContFields to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/most-of-cont-fields/{id}")
    public ResponseEntity<Void> deleteMostOfContFields(@PathVariable Long id) {
        log.debug("REST request to delete MostOfContFields : {}", id);
        mostOfContFieldsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
