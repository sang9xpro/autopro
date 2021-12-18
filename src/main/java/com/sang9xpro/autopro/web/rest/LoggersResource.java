package com.sang9xpro.autopro.web.rest;

import com.sang9xpro.autopro.domain.Loggers;
import com.sang9xpro.autopro.repository.LoggersRepository;
import com.sang9xpro.autopro.service.LoggersService;
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
 * REST controller for managing {@link com.sang9xpro.autopro.domain.Loggers}.
 */
@RestController
@RequestMapping("/api")
public class LoggersResource {

    private final Logger log = LoggerFactory.getLogger(LoggersResource.class);

    private static final String ENTITY_NAME = "loggers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LoggersService loggersService;

    private final LoggersRepository loggersRepository;

    public LoggersResource(LoggersService loggersService, LoggersRepository loggersRepository) {
        this.loggersService = loggersService;
        this.loggersRepository = loggersRepository;
    }

    /**
     * {@code POST  /loggers} : Create a new loggers.
     *
     * @param loggers the loggers to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new loggers, or with status {@code 400 (Bad Request)} if the loggers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/loggers")
    public ResponseEntity<Loggers> createLoggers(@RequestBody Loggers loggers) throws URISyntaxException {
        log.debug("REST request to save Loggers : {}", loggers);
        if (loggers.getId() != null) {
            throw new BadRequestAlertException("A new loggers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Loggers result = loggersService.save(loggers);
        return ResponseEntity
            .created(new URI("/api/loggers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /loggers/:id} : Updates an existing loggers.
     *
     * @param id the id of the loggers to save.
     * @param loggers the loggers to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loggers,
     * or with status {@code 400 (Bad Request)} if the loggers is not valid,
     * or with status {@code 500 (Internal Server Error)} if the loggers couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/loggers/{id}")
    public ResponseEntity<Loggers> updateLoggers(@PathVariable(value = "id", required = false) final Long id, @RequestBody Loggers loggers)
        throws URISyntaxException {
        log.debug("REST request to update Loggers : {}, {}", id, loggers);
        if (loggers.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loggers.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loggersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Loggers result = loggersService.save(loggers);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loggers.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /loggers/:id} : Partial updates given fields of an existing loggers, field will ignore if it is null
     *
     * @param id the id of the loggers to save.
     * @param loggers the loggers to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loggers,
     * or with status {@code 400 (Bad Request)} if the loggers is not valid,
     * or with status {@code 404 (Not Found)} if the loggers is not found,
     * or with status {@code 500 (Internal Server Error)} if the loggers couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/loggers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Loggers> partialUpdateLoggers(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Loggers loggers
    ) throws URISyntaxException {
        log.debug("REST request to partial update Loggers partially : {}, {}", id, loggers);
        if (loggers.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loggers.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loggersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Loggers> result = loggersService.partialUpdate(loggers);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loggers.getId().toString())
        );
    }

    /**
     * {@code GET  /loggers} : get all the loggers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of loggers in body.
     */
    @GetMapping("/loggers")
    public List<Loggers> getAllLoggers() {
        log.debug("REST request to get all Loggers");
        return loggersService.findAll();
    }

    /**
     * {@code GET  /loggers/:id} : get the "id" loggers.
     *
     * @param id the id of the loggers to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the loggers, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/loggers/{id}")
    public ResponseEntity<Loggers> getLoggers(@PathVariable Long id) {
        log.debug("REST request to get Loggers : {}", id);
        Optional<Loggers> loggers = loggersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(loggers);
    }

    /**
     * {@code DELETE  /loggers/:id} : delete the "id" loggers.
     *
     * @param id the id of the loggers to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/loggers/{id}")
    public ResponseEntity<Void> deleteLoggers(@PathVariable Long id) {
        log.debug("REST request to delete Loggers : {}", id);
        loggersService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
