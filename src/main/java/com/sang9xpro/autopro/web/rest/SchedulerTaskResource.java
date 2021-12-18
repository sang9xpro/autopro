package com.sang9xpro.autopro.web.rest;

import com.sang9xpro.autopro.domain.SchedulerTask;
import com.sang9xpro.autopro.repository.SchedulerTaskRepository;
import com.sang9xpro.autopro.service.SchedulerTaskService;
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
 * REST controller for managing {@link com.sang9xpro.autopro.domain.SchedulerTask}.
 */
@RestController
@RequestMapping("/api")
public class SchedulerTaskResource {

    private final Logger log = LoggerFactory.getLogger(SchedulerTaskResource.class);

    private static final String ENTITY_NAME = "schedulerTask";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SchedulerTaskService schedulerTaskService;

    private final SchedulerTaskRepository schedulerTaskRepository;

    public SchedulerTaskResource(SchedulerTaskService schedulerTaskService, SchedulerTaskRepository schedulerTaskRepository) {
        this.schedulerTaskService = schedulerTaskService;
        this.schedulerTaskRepository = schedulerTaskRepository;
    }

    /**
     * {@code POST  /scheduler-tasks} : Create a new schedulerTask.
     *
     * @param schedulerTask the schedulerTask to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new schedulerTask, or with status {@code 400 (Bad Request)} if the schedulerTask has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/scheduler-tasks")
    public ResponseEntity<SchedulerTask> createSchedulerTask(@RequestBody SchedulerTask schedulerTask) throws URISyntaxException {
        log.debug("REST request to save SchedulerTask : {}", schedulerTask);
        if (schedulerTask.getId() != null) {
            throw new BadRequestAlertException("A new schedulerTask cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SchedulerTask result = schedulerTaskService.save(schedulerTask);
        return ResponseEntity
            .created(new URI("/api/scheduler-tasks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /scheduler-tasks/:id} : Updates an existing schedulerTask.
     *
     * @param id the id of the schedulerTask to save.
     * @param schedulerTask the schedulerTask to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated schedulerTask,
     * or with status {@code 400 (Bad Request)} if the schedulerTask is not valid,
     * or with status {@code 500 (Internal Server Error)} if the schedulerTask couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/scheduler-tasks/{id}")
    public ResponseEntity<SchedulerTask> updateSchedulerTask(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SchedulerTask schedulerTask
    ) throws URISyntaxException {
        log.debug("REST request to update SchedulerTask : {}, {}", id, schedulerTask);
        if (schedulerTask.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, schedulerTask.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!schedulerTaskRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SchedulerTask result = schedulerTaskService.save(schedulerTask);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, schedulerTask.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /scheduler-tasks/:id} : Partial updates given fields of an existing schedulerTask, field will ignore if it is null
     *
     * @param id the id of the schedulerTask to save.
     * @param schedulerTask the schedulerTask to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated schedulerTask,
     * or with status {@code 400 (Bad Request)} if the schedulerTask is not valid,
     * or with status {@code 404 (Not Found)} if the schedulerTask is not found,
     * or with status {@code 500 (Internal Server Error)} if the schedulerTask couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/scheduler-tasks/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SchedulerTask> partialUpdateSchedulerTask(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SchedulerTask schedulerTask
    ) throws URISyntaxException {
        log.debug("REST request to partial update SchedulerTask partially : {}, {}", id, schedulerTask);
        if (schedulerTask.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, schedulerTask.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!schedulerTaskRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SchedulerTask> result = schedulerTaskService.partialUpdate(schedulerTask);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, schedulerTask.getId().toString())
        );
    }

    /**
     * {@code GET  /scheduler-tasks} : get all the schedulerTasks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of schedulerTasks in body.
     */
    @GetMapping("/scheduler-tasks")
    public List<SchedulerTask> getAllSchedulerTasks() {
        log.debug("REST request to get all SchedulerTasks");
        return schedulerTaskService.findAll();
    }

    /**
     * {@code GET  /scheduler-tasks/:id} : get the "id" schedulerTask.
     *
     * @param id the id of the schedulerTask to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the schedulerTask, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/scheduler-tasks/{id}")
    public ResponseEntity<SchedulerTask> getSchedulerTask(@PathVariable Long id) {
        log.debug("REST request to get SchedulerTask : {}", id);
        Optional<SchedulerTask> schedulerTask = schedulerTaskService.findOne(id);
        return ResponseUtil.wrapOrNotFound(schedulerTask);
    }

    /**
     * {@code DELETE  /scheduler-tasks/:id} : delete the "id" schedulerTask.
     *
     * @param id the id of the schedulerTask to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/scheduler-tasks/{id}")
    public ResponseEntity<Void> deleteSchedulerTask(@PathVariable Long id) {
        log.debug("REST request to delete SchedulerTask : {}", id);
        schedulerTaskService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
