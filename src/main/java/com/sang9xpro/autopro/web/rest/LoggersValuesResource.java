package com.sang9xpro.autopro.web.rest;

import com.sang9xpro.autopro.domain.LoggersValues;
import com.sang9xpro.autopro.repository.LoggersValuesRepository;
import com.sang9xpro.autopro.service.LoggersValuesService;
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
 * REST controller for managing {@link com.sang9xpro.autopro.domain.LoggersValues}.
 */
@RestController
@RequestMapping("/api")
public class LoggersValuesResource {

    private final Logger log = LoggerFactory.getLogger(LoggersValuesResource.class);

    private static final String ENTITY_NAME = "loggersValues";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LoggersValuesService loggersValuesService;

    private final LoggersValuesRepository loggersValuesRepository;

    public LoggersValuesResource(LoggersValuesService loggersValuesService, LoggersValuesRepository loggersValuesRepository) {
        this.loggersValuesService = loggersValuesService;
        this.loggersValuesRepository = loggersValuesRepository;
    }

    /**
     * {@code POST  /loggers-values} : Create a new loggersValues.
     *
     * @param loggersValues the loggersValues to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new loggersValues, or with status {@code 400 (Bad Request)} if the loggersValues has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/loggers-values")
    public ResponseEntity<LoggersValues> createLoggersValues(@RequestBody LoggersValues loggersValues) throws URISyntaxException {
        log.debug("REST request to save LoggersValues : {}", loggersValues);
        if (loggersValues.getId() != null) {
            throw new BadRequestAlertException("A new loggersValues cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LoggersValues result = loggersValuesService.save(loggersValues);
        return ResponseEntity
            .created(new URI("/api/loggers-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /loggers-values/:id} : Updates an existing loggersValues.
     *
     * @param id the id of the loggersValues to save.
     * @param loggersValues the loggersValues to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loggersValues,
     * or with status {@code 400 (Bad Request)} if the loggersValues is not valid,
     * or with status {@code 500 (Internal Server Error)} if the loggersValues couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/loggers-values/{id}")
    public ResponseEntity<LoggersValues> updateLoggersValues(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LoggersValues loggersValues
    ) throws URISyntaxException {
        log.debug("REST request to update LoggersValues : {}, {}", id, loggersValues);
        if (loggersValues.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loggersValues.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loggersValuesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LoggersValues result = loggersValuesService.save(loggersValues);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loggersValues.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /loggers-values/:id} : Partial updates given fields of an existing loggersValues, field will ignore if it is null
     *
     * @param id the id of the loggersValues to save.
     * @param loggersValues the loggersValues to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loggersValues,
     * or with status {@code 400 (Bad Request)} if the loggersValues is not valid,
     * or with status {@code 404 (Not Found)} if the loggersValues is not found,
     * or with status {@code 500 (Internal Server Error)} if the loggersValues couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/loggers-values/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LoggersValues> partialUpdateLoggersValues(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LoggersValues loggersValues
    ) throws URISyntaxException {
        log.debug("REST request to partial update LoggersValues partially : {}, {}", id, loggersValues);
        if (loggersValues.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loggersValues.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loggersValuesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LoggersValues> result = loggersValuesService.partialUpdate(loggersValues);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loggersValues.getId().toString())
        );
    }

    /**
     * {@code GET  /loggers-values} : get all the loggersValues.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of loggersValues in body.
     */
    @GetMapping("/loggers-values")
    public List<LoggersValues> getAllLoggersValues() {
        log.debug("REST request to get all LoggersValues");
        return loggersValuesService.findAll();
    }

    /**
     * {@code GET  /loggers-values/:id} : get the "id" loggersValues.
     *
     * @param id the id of the loggersValues to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the loggersValues, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/loggers-values/{id}")
    public ResponseEntity<LoggersValues> getLoggersValues(@PathVariable Long id) {
        log.debug("REST request to get LoggersValues : {}", id);
        Optional<LoggersValues> loggersValues = loggersValuesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(loggersValues);
    }

    /**
     * {@code DELETE  /loggers-values/:id} : delete the "id" loggersValues.
     *
     * @param id the id of the loggersValues to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/loggers-values/{id}")
    public ResponseEntity<Void> deleteLoggersValues(@PathVariable Long id) {
        log.debug("REST request to delete LoggersValues : {}", id);
        loggersValuesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
