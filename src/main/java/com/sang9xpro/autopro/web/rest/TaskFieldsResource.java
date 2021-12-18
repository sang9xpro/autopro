package com.sang9xpro.autopro.web.rest;

import com.sang9xpro.autopro.domain.TaskFields;
import com.sang9xpro.autopro.repository.TaskFieldsRepository;
import com.sang9xpro.autopro.service.TaskFieldsService;
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
 * REST controller for managing {@link com.sang9xpro.autopro.domain.TaskFields}.
 */
@RestController
@RequestMapping("/api")
public class TaskFieldsResource {

    private final Logger log = LoggerFactory.getLogger(TaskFieldsResource.class);

    private static final String ENTITY_NAME = "taskFields";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskFieldsService taskFieldsService;

    private final TaskFieldsRepository taskFieldsRepository;

    public TaskFieldsResource(TaskFieldsService taskFieldsService, TaskFieldsRepository taskFieldsRepository) {
        this.taskFieldsService = taskFieldsService;
        this.taskFieldsRepository = taskFieldsRepository;
    }

    /**
     * {@code POST  /task-fields} : Create a new taskFields.
     *
     * @param taskFields the taskFields to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskFields, or with status {@code 400 (Bad Request)} if the taskFields has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-fields")
    public ResponseEntity<TaskFields> createTaskFields(@RequestBody TaskFields taskFields) throws URISyntaxException {
        log.debug("REST request to save TaskFields : {}", taskFields);
        if (taskFields.getId() != null) {
            throw new BadRequestAlertException("A new taskFields cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskFields result = taskFieldsService.save(taskFields);
        return ResponseEntity
            .created(new URI("/api/task-fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /task-fields/:id} : Updates an existing taskFields.
     *
     * @param id the id of the taskFields to save.
     * @param taskFields the taskFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskFields,
     * or with status {@code 400 (Bad Request)} if the taskFields is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskFields couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-fields/{id}")
    public ResponseEntity<TaskFields> updateTaskFields(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaskFields taskFields
    ) throws URISyntaxException {
        log.debug("REST request to update TaskFields : {}, {}", id, taskFields);
        if (taskFields.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taskFields.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taskFieldsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TaskFields result = taskFieldsService.save(taskFields);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskFields.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /task-fields/:id} : Partial updates given fields of an existing taskFields, field will ignore if it is null
     *
     * @param id the id of the taskFields to save.
     * @param taskFields the taskFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskFields,
     * or with status {@code 400 (Bad Request)} if the taskFields is not valid,
     * or with status {@code 404 (Not Found)} if the taskFields is not found,
     * or with status {@code 500 (Internal Server Error)} if the taskFields couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/task-fields/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TaskFields> partialUpdateTaskFields(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaskFields taskFields
    ) throws URISyntaxException {
        log.debug("REST request to partial update TaskFields partially : {}, {}", id, taskFields);
        if (taskFields.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taskFields.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taskFieldsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TaskFields> result = taskFieldsService.partialUpdate(taskFields);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskFields.getId().toString())
        );
    }

    /**
     * {@code GET  /task-fields} : get all the taskFields.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskFields in body.
     */
    @GetMapping("/task-fields")
    public List<TaskFields> getAllTaskFields() {
        log.debug("REST request to get all TaskFields");
        return taskFieldsService.findAll();
    }

    /**
     * {@code GET  /task-fields/:id} : get the "id" taskFields.
     *
     * @param id the id of the taskFields to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskFields, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-fields/{id}")
    public ResponseEntity<TaskFields> getTaskFields(@PathVariable Long id) {
        log.debug("REST request to get TaskFields : {}", id);
        Optional<TaskFields> taskFields = taskFieldsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskFields);
    }

    /**
     * {@code DELETE  /task-fields/:id} : delete the "id" taskFields.
     *
     * @param id the id of the taskFields to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-fields/{id}")
    public ResponseEntity<Void> deleteTaskFields(@PathVariable Long id) {
        log.debug("REST request to delete TaskFields : {}", id);
        taskFieldsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
