package com.sang9xpro.autopro.web.rest;

import com.sang9xpro.autopro.domain.MostOfContValues;
import com.sang9xpro.autopro.repository.MostOfContValuesRepository;
import com.sang9xpro.autopro.service.MostOfContValuesService;
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
 * REST controller for managing {@link com.sang9xpro.autopro.domain.MostOfContValues}.
 */
@RestController
@RequestMapping("/api")
public class MostOfContValuesResource {

    private final Logger log = LoggerFactory.getLogger(MostOfContValuesResource.class);

    private static final String ENTITY_NAME = "mostOfContValues";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MostOfContValuesService mostOfContValuesService;

    private final MostOfContValuesRepository mostOfContValuesRepository;

    public MostOfContValuesResource(
        MostOfContValuesService mostOfContValuesService,
        MostOfContValuesRepository mostOfContValuesRepository
    ) {
        this.mostOfContValuesService = mostOfContValuesService;
        this.mostOfContValuesRepository = mostOfContValuesRepository;
    }

    /**
     * {@code POST  /most-of-cont-values} : Create a new mostOfContValues.
     *
     * @param mostOfContValues the mostOfContValues to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mostOfContValues, or with status {@code 400 (Bad Request)} if the mostOfContValues has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/most-of-cont-values")
    public ResponseEntity<MostOfContValues> createMostOfContValues(@RequestBody MostOfContValues mostOfContValues)
        throws URISyntaxException {
        log.debug("REST request to save MostOfContValues : {}", mostOfContValues);
        if (mostOfContValues.getId() != null) {
            throw new BadRequestAlertException("A new mostOfContValues cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MostOfContValues result = mostOfContValuesService.save(mostOfContValues);
        return ResponseEntity
            .created(new URI("/api/most-of-cont-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /most-of-cont-values/:id} : Updates an existing mostOfContValues.
     *
     * @param id the id of the mostOfContValues to save.
     * @param mostOfContValues the mostOfContValues to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mostOfContValues,
     * or with status {@code 400 (Bad Request)} if the mostOfContValues is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mostOfContValues couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/most-of-cont-values/{id}")
    public ResponseEntity<MostOfContValues> updateMostOfContValues(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MostOfContValues mostOfContValues
    ) throws URISyntaxException {
        log.debug("REST request to update MostOfContValues : {}, {}", id, mostOfContValues);
        if (mostOfContValues.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mostOfContValues.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mostOfContValuesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MostOfContValues result = mostOfContValuesService.save(mostOfContValues);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mostOfContValues.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /most-of-cont-values/:id} : Partial updates given fields of an existing mostOfContValues, field will ignore if it is null
     *
     * @param id the id of the mostOfContValues to save.
     * @param mostOfContValues the mostOfContValues to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mostOfContValues,
     * or with status {@code 400 (Bad Request)} if the mostOfContValues is not valid,
     * or with status {@code 404 (Not Found)} if the mostOfContValues is not found,
     * or with status {@code 500 (Internal Server Error)} if the mostOfContValues couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/most-of-cont-values/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MostOfContValues> partialUpdateMostOfContValues(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MostOfContValues mostOfContValues
    ) throws URISyntaxException {
        log.debug("REST request to partial update MostOfContValues partially : {}, {}", id, mostOfContValues);
        if (mostOfContValues.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mostOfContValues.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mostOfContValuesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MostOfContValues> result = mostOfContValuesService.partialUpdate(mostOfContValues);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mostOfContValues.getId().toString())
        );
    }

    /**
     * {@code GET  /most-of-cont-values} : get all the mostOfContValues.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mostOfContValues in body.
     */
    @GetMapping("/most-of-cont-values")
    public List<MostOfContValues> getAllMostOfContValues() {
        log.debug("REST request to get all MostOfContValues");
        return mostOfContValuesService.findAll();
    }

    /**
     * {@code GET  /most-of-cont-values/:id} : get the "id" mostOfContValues.
     *
     * @param id the id of the mostOfContValues to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mostOfContValues, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/most-of-cont-values/{id}")
    public ResponseEntity<MostOfContValues> getMostOfContValues(@PathVariable Long id) {
        log.debug("REST request to get MostOfContValues : {}", id);
        Optional<MostOfContValues> mostOfContValues = mostOfContValuesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mostOfContValues);
    }

    /**
     * {@code DELETE  /most-of-cont-values/:id} : delete the "id" mostOfContValues.
     *
     * @param id the id of the mostOfContValues to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/most-of-cont-values/{id}")
    public ResponseEntity<Void> deleteMostOfContValues(@PathVariable Long id) {
        log.debug("REST request to delete MostOfContValues : {}", id);
        mostOfContValuesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
