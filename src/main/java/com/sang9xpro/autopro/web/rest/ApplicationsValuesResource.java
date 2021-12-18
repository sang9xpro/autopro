package com.sang9xpro.autopro.web.rest;

import com.sang9xpro.autopro.domain.ApplicationsValues;
import com.sang9xpro.autopro.repository.ApplicationsValuesRepository;
import com.sang9xpro.autopro.service.ApplicationsValuesService;
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
 * REST controller for managing {@link com.sang9xpro.autopro.domain.ApplicationsValues}.
 */
@RestController
@RequestMapping("/api")
public class ApplicationsValuesResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationsValuesResource.class);

    private static final String ENTITY_NAME = "applicationsValues";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicationsValuesService applicationsValuesService;

    private final ApplicationsValuesRepository applicationsValuesRepository;

    public ApplicationsValuesResource(
        ApplicationsValuesService applicationsValuesService,
        ApplicationsValuesRepository applicationsValuesRepository
    ) {
        this.applicationsValuesService = applicationsValuesService;
        this.applicationsValuesRepository = applicationsValuesRepository;
    }

    /**
     * {@code POST  /applications-values} : Create a new applicationsValues.
     *
     * @param applicationsValues the applicationsValues to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicationsValues, or with status {@code 400 (Bad Request)} if the applicationsValues has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/applications-values")
    public ResponseEntity<ApplicationsValues> createApplicationsValues(@RequestBody ApplicationsValues applicationsValues)
        throws URISyntaxException {
        log.debug("REST request to save ApplicationsValues : {}", applicationsValues);
        if (applicationsValues.getId() != null) {
            throw new BadRequestAlertException("A new applicationsValues cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationsValues result = applicationsValuesService.save(applicationsValues);
        return ResponseEntity
            .created(new URI("/api/applications-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /applications-values/:id} : Updates an existing applicationsValues.
     *
     * @param id the id of the applicationsValues to save.
     * @param applicationsValues the applicationsValues to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationsValues,
     * or with status {@code 400 (Bad Request)} if the applicationsValues is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicationsValues couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/applications-values/{id}")
    public ResponseEntity<ApplicationsValues> updateApplicationsValues(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ApplicationsValues applicationsValues
    ) throws URISyntaxException {
        log.debug("REST request to update ApplicationsValues : {}, {}", id, applicationsValues);
        if (applicationsValues.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applicationsValues.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicationsValuesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ApplicationsValues result = applicationsValuesService.save(applicationsValues);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationsValues.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /applications-values/:id} : Partial updates given fields of an existing applicationsValues, field will ignore if it is null
     *
     * @param id the id of the applicationsValues to save.
     * @param applicationsValues the applicationsValues to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationsValues,
     * or with status {@code 400 (Bad Request)} if the applicationsValues is not valid,
     * or with status {@code 404 (Not Found)} if the applicationsValues is not found,
     * or with status {@code 500 (Internal Server Error)} if the applicationsValues couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/applications-values/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ApplicationsValues> partialUpdateApplicationsValues(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ApplicationsValues applicationsValues
    ) throws URISyntaxException {
        log.debug("REST request to partial update ApplicationsValues partially : {}, {}", id, applicationsValues);
        if (applicationsValues.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applicationsValues.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicationsValuesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApplicationsValues> result = applicationsValuesService.partialUpdate(applicationsValues);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationsValues.getId().toString())
        );
    }

    /**
     * {@code GET  /applications-values} : get all the applicationsValues.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicationsValues in body.
     */
    @GetMapping("/applications-values")
    public List<ApplicationsValues> getAllApplicationsValues() {
        log.debug("REST request to get all ApplicationsValues");
        return applicationsValuesService.findAll();
    }

    /**
     * {@code GET  /applications-values/:id} : get the "id" applicationsValues.
     *
     * @param id the id of the applicationsValues to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicationsValues, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/applications-values/{id}")
    public ResponseEntity<ApplicationsValues> getApplicationsValues(@PathVariable Long id) {
        log.debug("REST request to get ApplicationsValues : {}", id);
        Optional<ApplicationsValues> applicationsValues = applicationsValuesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicationsValues);
    }

    /**
     * {@code DELETE  /applications-values/:id} : delete the "id" applicationsValues.
     *
     * @param id the id of the applicationsValues to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/applications-values/{id}")
    public ResponseEntity<Void> deleteApplicationsValues(@PathVariable Long id) {
        log.debug("REST request to delete ApplicationsValues : {}", id);
        applicationsValuesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
