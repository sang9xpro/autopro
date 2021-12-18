package com.sang9xpro.autopro.web.rest;

import com.sang9xpro.autopro.domain.SchedulerTaskValues;
import com.sang9xpro.autopro.repository.SchedulerTaskValuesRepository;
import com.sang9xpro.autopro.service.SchedulerTaskValuesService;
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
 * REST controller for managing {@link com.sang9xpro.autopro.domain.SchedulerTaskValues}.
 */
@RestController
@RequestMapping("/api")
public class SchedulerTaskValuesResource {

    private final Logger log = LoggerFactory.getLogger(SchedulerTaskValuesResource.class);

    private static final String ENTITY_NAME = "schedulerTaskValues";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SchedulerTaskValuesService schedulerTaskValuesService;

    private final SchedulerTaskValuesRepository schedulerTaskValuesRepository;

    public SchedulerTaskValuesResource(
        SchedulerTaskValuesService schedulerTaskValuesService,
        SchedulerTaskValuesRepository schedulerTaskValuesRepository
    ) {
        this.schedulerTaskValuesService = schedulerTaskValuesService;
        this.schedulerTaskValuesRepository = schedulerTaskValuesRepository;
    }

    /**
     * {@code POST  /scheduler-task-values} : Create a new schedulerTaskValues.
     *
     * @param schedulerTaskValues the schedulerTaskValues to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new schedulerTaskValues, or with status {@code 400 (Bad Request)} if the schedulerTaskValues has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/scheduler-task-values")
    public ResponseEntity<SchedulerTaskValues> createSchedulerTaskValues(@RequestBody SchedulerTaskValues schedulerTaskValues)
        throws URISyntaxException {
        log.debug("REST request to save SchedulerTaskValues : {}", schedulerTaskValues);
        if (schedulerTaskValues.getId() != null) {
            throw new BadRequestAlertException("A new schedulerTaskValues cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SchedulerTaskValues result = schedulerTaskValuesService.save(schedulerTaskValues);
        return ResponseEntity
            .created(new URI("/api/scheduler-task-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /scheduler-task-values/:id} : Updates an existing schedulerTaskValues.
     *
     * @param id the id of the schedulerTaskValues to save.
     * @param schedulerTaskValues the schedulerTaskValues to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated schedulerTaskValues,
     * or with status {@code 400 (Bad Request)} if the schedulerTaskValues is not valid,
     * or with status {@code 500 (Internal Server Error)} if the schedulerTaskValues couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/scheduler-task-values/{id}")
    public ResponseEntity<SchedulerTaskValues> updateSchedulerTaskValues(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SchedulerTaskValues schedulerTaskValues
    ) throws URISyntaxException {
        log.debug("REST request to update SchedulerTaskValues : {}, {}", id, schedulerTaskValues);
        if (schedulerTaskValues.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, schedulerTaskValues.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!schedulerTaskValuesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SchedulerTaskValues result = schedulerTaskValuesService.save(schedulerTaskValues);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, schedulerTaskValues.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /scheduler-task-values/:id} : Partial updates given fields of an existing schedulerTaskValues, field will ignore if it is null
     *
     * @param id the id of the schedulerTaskValues to save.
     * @param schedulerTaskValues the schedulerTaskValues to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated schedulerTaskValues,
     * or with status {@code 400 (Bad Request)} if the schedulerTaskValues is not valid,
     * or with status {@code 404 (Not Found)} if the schedulerTaskValues is not found,
     * or with status {@code 500 (Internal Server Error)} if the schedulerTaskValues couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/scheduler-task-values/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SchedulerTaskValues> partialUpdateSchedulerTaskValues(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SchedulerTaskValues schedulerTaskValues
    ) throws URISyntaxException {
        log.debug("REST request to partial update SchedulerTaskValues partially : {}, {}", id, schedulerTaskValues);
        if (schedulerTaskValues.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, schedulerTaskValues.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!schedulerTaskValuesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SchedulerTaskValues> result = schedulerTaskValuesService.partialUpdate(schedulerTaskValues);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, schedulerTaskValues.getId().toString())
        );
    }

    /**
     * {@code GET  /scheduler-task-values} : get all the schedulerTaskValues.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of schedulerTaskValues in body.
     */
    @GetMapping("/scheduler-task-values")
    public List<SchedulerTaskValues> getAllSchedulerTaskValues() {
        log.debug("REST request to get all SchedulerTaskValues");
        return schedulerTaskValuesService.findAll();
    }

    /**
     * {@code GET  /scheduler-task-values/:id} : get the "id" schedulerTaskValues.
     *
     * @param id the id of the schedulerTaskValues to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the schedulerTaskValues, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/scheduler-task-values/{id}")
    public ResponseEntity<SchedulerTaskValues> getSchedulerTaskValues(@PathVariable Long id) {
        log.debug("REST request to get SchedulerTaskValues : {}", id);
        Optional<SchedulerTaskValues> schedulerTaskValues = schedulerTaskValuesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(schedulerTaskValues);
    }

    /**
     * {@code DELETE  /scheduler-task-values/:id} : delete the "id" schedulerTaskValues.
     *
     * @param id the id of the schedulerTaskValues to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/scheduler-task-values/{id}")
    public ResponseEntity<Void> deleteSchedulerTaskValues(@PathVariable Long id) {
        log.debug("REST request to delete SchedulerTaskValues : {}", id);
        schedulerTaskValuesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
