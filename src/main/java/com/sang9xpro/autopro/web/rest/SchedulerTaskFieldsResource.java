package com.sang9xpro.autopro.web.rest;

import com.sang9xpro.autopro.domain.SchedulerTaskFields;
import com.sang9xpro.autopro.repository.SchedulerTaskFieldsRepository;
import com.sang9xpro.autopro.service.SchedulerTaskFieldsService;
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
 * REST controller for managing {@link com.sang9xpro.autopro.domain.SchedulerTaskFields}.
 */
@RestController
@RequestMapping("/api")
public class SchedulerTaskFieldsResource {

    private final Logger log = LoggerFactory.getLogger(SchedulerTaskFieldsResource.class);

    private static final String ENTITY_NAME = "schedulerTaskFields";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SchedulerTaskFieldsService schedulerTaskFieldsService;

    private final SchedulerTaskFieldsRepository schedulerTaskFieldsRepository;

    public SchedulerTaskFieldsResource(
        SchedulerTaskFieldsService schedulerTaskFieldsService,
        SchedulerTaskFieldsRepository schedulerTaskFieldsRepository
    ) {
        this.schedulerTaskFieldsService = schedulerTaskFieldsService;
        this.schedulerTaskFieldsRepository = schedulerTaskFieldsRepository;
    }

    /**
     * {@code POST  /scheduler-task-fields} : Create a new schedulerTaskFields.
     *
     * @param schedulerTaskFields the schedulerTaskFields to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new schedulerTaskFields, or with status {@code 400 (Bad Request)} if the schedulerTaskFields has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/scheduler-task-fields")
    public ResponseEntity<SchedulerTaskFields> createSchedulerTaskFields(@RequestBody SchedulerTaskFields schedulerTaskFields)
        throws URISyntaxException {
        log.debug("REST request to save SchedulerTaskFields : {}", schedulerTaskFields);
        if (schedulerTaskFields.getId() != null) {
            throw new BadRequestAlertException("A new schedulerTaskFields cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SchedulerTaskFields result = schedulerTaskFieldsService.save(schedulerTaskFields);
        return ResponseEntity
            .created(new URI("/api/scheduler-task-fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /scheduler-task-fields/:id} : Updates an existing schedulerTaskFields.
     *
     * @param id the id of the schedulerTaskFields to save.
     * @param schedulerTaskFields the schedulerTaskFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated schedulerTaskFields,
     * or with status {@code 400 (Bad Request)} if the schedulerTaskFields is not valid,
     * or with status {@code 500 (Internal Server Error)} if the schedulerTaskFields couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/scheduler-task-fields/{id}")
    public ResponseEntity<SchedulerTaskFields> updateSchedulerTaskFields(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SchedulerTaskFields schedulerTaskFields
    ) throws URISyntaxException {
        log.debug("REST request to update SchedulerTaskFields : {}, {}", id, schedulerTaskFields);
        if (schedulerTaskFields.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, schedulerTaskFields.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!schedulerTaskFieldsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SchedulerTaskFields result = schedulerTaskFieldsService.save(schedulerTaskFields);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, schedulerTaskFields.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /scheduler-task-fields/:id} : Partial updates given fields of an existing schedulerTaskFields, field will ignore if it is null
     *
     * @param id the id of the schedulerTaskFields to save.
     * @param schedulerTaskFields the schedulerTaskFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated schedulerTaskFields,
     * or with status {@code 400 (Bad Request)} if the schedulerTaskFields is not valid,
     * or with status {@code 404 (Not Found)} if the schedulerTaskFields is not found,
     * or with status {@code 500 (Internal Server Error)} if the schedulerTaskFields couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/scheduler-task-fields/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SchedulerTaskFields> partialUpdateSchedulerTaskFields(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SchedulerTaskFields schedulerTaskFields
    ) throws URISyntaxException {
        log.debug("REST request to partial update SchedulerTaskFields partially : {}, {}", id, schedulerTaskFields);
        if (schedulerTaskFields.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, schedulerTaskFields.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!schedulerTaskFieldsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SchedulerTaskFields> result = schedulerTaskFieldsService.partialUpdate(schedulerTaskFields);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, schedulerTaskFields.getId().toString())
        );
    }

    /**
     * {@code GET  /scheduler-task-fields} : get all the schedulerTaskFields.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of schedulerTaskFields in body.
     */
    @GetMapping("/scheduler-task-fields")
    public List<SchedulerTaskFields> getAllSchedulerTaskFields() {
        log.debug("REST request to get all SchedulerTaskFields");
        return schedulerTaskFieldsService.findAll();
    }

    /**
     * {@code GET  /scheduler-task-fields/:id} : get the "id" schedulerTaskFields.
     *
     * @param id the id of the schedulerTaskFields to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the schedulerTaskFields, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/scheduler-task-fields/{id}")
    public ResponseEntity<SchedulerTaskFields> getSchedulerTaskFields(@PathVariable Long id) {
        log.debug("REST request to get SchedulerTaskFields : {}", id);
        Optional<SchedulerTaskFields> schedulerTaskFields = schedulerTaskFieldsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(schedulerTaskFields);
    }

    /**
     * {@code DELETE  /scheduler-task-fields/:id} : delete the "id" schedulerTaskFields.
     *
     * @param id the id of the schedulerTaskFields to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/scheduler-task-fields/{id}")
    public ResponseEntity<Void> deleteSchedulerTaskFields(@PathVariable Long id) {
        log.debug("REST request to delete SchedulerTaskFields : {}", id);
        schedulerTaskFieldsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
