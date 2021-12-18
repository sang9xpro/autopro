package com.sang9xpro.autopro.web.rest;

import com.sang9xpro.autopro.domain.TaskValues;
import com.sang9xpro.autopro.repository.TaskValuesRepository;
import com.sang9xpro.autopro.service.TaskValuesService;
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
 * REST controller for managing {@link com.sang9xpro.autopro.domain.TaskValues}.
 */
@RestController
@RequestMapping("/api")
public class TaskValuesResource {

    private final Logger log = LoggerFactory.getLogger(TaskValuesResource.class);

    private static final String ENTITY_NAME = "taskValues";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskValuesService taskValuesService;

    private final TaskValuesRepository taskValuesRepository;

    public TaskValuesResource(TaskValuesService taskValuesService, TaskValuesRepository taskValuesRepository) {
        this.taskValuesService = taskValuesService;
        this.taskValuesRepository = taskValuesRepository;
    }

    /**
     * {@code POST  /task-values} : Create a new taskValues.
     *
     * @param taskValues the taskValues to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskValues, or with status {@code 400 (Bad Request)} if the taskValues has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-values")
    public ResponseEntity<TaskValues> createTaskValues(@RequestBody TaskValues taskValues) throws URISyntaxException {
        log.debug("REST request to save TaskValues : {}", taskValues);
        if (taskValues.getId() != null) {
            throw new BadRequestAlertException("A new taskValues cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskValues result = taskValuesService.save(taskValues);
        return ResponseEntity
            .created(new URI("/api/task-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /task-values/:id} : Updates an existing taskValues.
     *
     * @param id the id of the taskValues to save.
     * @param taskValues the taskValues to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskValues,
     * or with status {@code 400 (Bad Request)} if the taskValues is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskValues couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-values/{id}")
    public ResponseEntity<TaskValues> updateTaskValues(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaskValues taskValues
    ) throws URISyntaxException {
        log.debug("REST request to update TaskValues : {}, {}", id, taskValues);
        if (taskValues.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taskValues.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taskValuesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TaskValues result = taskValuesService.save(taskValues);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskValues.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /task-values/:id} : Partial updates given fields of an existing taskValues, field will ignore if it is null
     *
     * @param id the id of the taskValues to save.
     * @param taskValues the taskValues to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskValues,
     * or with status {@code 400 (Bad Request)} if the taskValues is not valid,
     * or with status {@code 404 (Not Found)} if the taskValues is not found,
     * or with status {@code 500 (Internal Server Error)} if the taskValues couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/task-values/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TaskValues> partialUpdateTaskValues(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaskValues taskValues
    ) throws URISyntaxException {
        log.debug("REST request to partial update TaskValues partially : {}, {}", id, taskValues);
        if (taskValues.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taskValues.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taskValuesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TaskValues> result = taskValuesService.partialUpdate(taskValues);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskValues.getId().toString())
        );
    }

    /**
     * {@code GET  /task-values} : get all the taskValues.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskValues in body.
     */
    @GetMapping("/task-values")
    public List<TaskValues> getAllTaskValues() {
        log.debug("REST request to get all TaskValues");
        return taskValuesService.findAll();
    }

    /**
     * {@code GET  /task-values/:id} : get the "id" taskValues.
     *
     * @param id the id of the taskValues to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskValues, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-values/{id}")
    public ResponseEntity<TaskValues> getTaskValues(@PathVariable Long id) {
        log.debug("REST request to get TaskValues : {}", id);
        Optional<TaskValues> taskValues = taskValuesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskValues);
    }

    /**
     * {@code DELETE  /task-values/:id} : delete the "id" taskValues.
     *
     * @param id the id of the taskValues to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-values/{id}")
    public ResponseEntity<Void> deleteTaskValues(@PathVariable Long id) {
        log.debug("REST request to delete TaskValues : {}", id);
        taskValuesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
