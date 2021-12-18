package com.sang9xpro.autopro.web.rest;

import com.sang9xpro.autopro.domain.LoggersFields;
import com.sang9xpro.autopro.repository.LoggersFieldsRepository;
import com.sang9xpro.autopro.service.LoggersFieldsService;
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
 * REST controller for managing {@link com.sang9xpro.autopro.domain.LoggersFields}.
 */
@RestController
@RequestMapping("/api")
public class LoggersFieldsResource {

    private final Logger log = LoggerFactory.getLogger(LoggersFieldsResource.class);

    private static final String ENTITY_NAME = "loggersFields";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LoggersFieldsService loggersFieldsService;

    private final LoggersFieldsRepository loggersFieldsRepository;

    public LoggersFieldsResource(LoggersFieldsService loggersFieldsService, LoggersFieldsRepository loggersFieldsRepository) {
        this.loggersFieldsService = loggersFieldsService;
        this.loggersFieldsRepository = loggersFieldsRepository;
    }

    /**
     * {@code POST  /loggers-fields} : Create a new loggersFields.
     *
     * @param loggersFields the loggersFields to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new loggersFields, or with status {@code 400 (Bad Request)} if the loggersFields has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/loggers-fields")
    public ResponseEntity<LoggersFields> createLoggersFields(@RequestBody LoggersFields loggersFields) throws URISyntaxException {
        log.debug("REST request to save LoggersFields : {}", loggersFields);
        if (loggersFields.getId() != null) {
            throw new BadRequestAlertException("A new loggersFields cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LoggersFields result = loggersFieldsService.save(loggersFields);
        return ResponseEntity
            .created(new URI("/api/loggers-fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /loggers-fields/:id} : Updates an existing loggersFields.
     *
     * @param id the id of the loggersFields to save.
     * @param loggersFields the loggersFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loggersFields,
     * or with status {@code 400 (Bad Request)} if the loggersFields is not valid,
     * or with status {@code 500 (Internal Server Error)} if the loggersFields couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/loggers-fields/{id}")
    public ResponseEntity<LoggersFields> updateLoggersFields(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LoggersFields loggersFields
    ) throws URISyntaxException {
        log.debug("REST request to update LoggersFields : {}, {}", id, loggersFields);
        if (loggersFields.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loggersFields.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loggersFieldsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LoggersFields result = loggersFieldsService.save(loggersFields);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loggersFields.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /loggers-fields/:id} : Partial updates given fields of an existing loggersFields, field will ignore if it is null
     *
     * @param id the id of the loggersFields to save.
     * @param loggersFields the loggersFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loggersFields,
     * or with status {@code 400 (Bad Request)} if the loggersFields is not valid,
     * or with status {@code 404 (Not Found)} if the loggersFields is not found,
     * or with status {@code 500 (Internal Server Error)} if the loggersFields couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/loggers-fields/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LoggersFields> partialUpdateLoggersFields(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LoggersFields loggersFields
    ) throws URISyntaxException {
        log.debug("REST request to partial update LoggersFields partially : {}, {}", id, loggersFields);
        if (loggersFields.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loggersFields.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loggersFieldsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LoggersFields> result = loggersFieldsService.partialUpdate(loggersFields);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loggersFields.getId().toString())
        );
    }

    /**
     * {@code GET  /loggers-fields} : get all the loggersFields.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of loggersFields in body.
     */
    @GetMapping("/loggers-fields")
    public List<LoggersFields> getAllLoggersFields() {
        log.debug("REST request to get all LoggersFields");
        return loggersFieldsService.findAll();
    }

    /**
     * {@code GET  /loggers-fields/:id} : get the "id" loggersFields.
     *
     * @param id the id of the loggersFields to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the loggersFields, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/loggers-fields/{id}")
    public ResponseEntity<LoggersFields> getLoggersFields(@PathVariable Long id) {
        log.debug("REST request to get LoggersFields : {}", id);
        Optional<LoggersFields> loggersFields = loggersFieldsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(loggersFields);
    }

    /**
     * {@code DELETE  /loggers-fields/:id} : delete the "id" loggersFields.
     *
     * @param id the id of the loggersFields to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/loggers-fields/{id}")
    public ResponseEntity<Void> deleteLoggersFields(@PathVariable Long id) {
        log.debug("REST request to delete LoggersFields : {}", id);
        loggersFieldsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
