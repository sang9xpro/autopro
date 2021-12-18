package com.sang9xpro.autopro.web.rest;

import com.sang9xpro.autopro.domain.ApplicationsFields;
import com.sang9xpro.autopro.repository.ApplicationsFieldsRepository;
import com.sang9xpro.autopro.service.ApplicationsFieldsService;
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
 * REST controller for managing {@link com.sang9xpro.autopro.domain.ApplicationsFields}.
 */
@RestController
@RequestMapping("/api")
public class ApplicationsFieldsResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationsFieldsResource.class);

    private static final String ENTITY_NAME = "applicationsFields";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicationsFieldsService applicationsFieldsService;

    private final ApplicationsFieldsRepository applicationsFieldsRepository;

    public ApplicationsFieldsResource(
        ApplicationsFieldsService applicationsFieldsService,
        ApplicationsFieldsRepository applicationsFieldsRepository
    ) {
        this.applicationsFieldsService = applicationsFieldsService;
        this.applicationsFieldsRepository = applicationsFieldsRepository;
    }

    /**
     * {@code POST  /applications-fields} : Create a new applicationsFields.
     *
     * @param applicationsFields the applicationsFields to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicationsFields, or with status {@code 400 (Bad Request)} if the applicationsFields has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/applications-fields")
    public ResponseEntity<ApplicationsFields> createApplicationsFields(@RequestBody ApplicationsFields applicationsFields)
        throws URISyntaxException {
        log.debug("REST request to save ApplicationsFields : {}", applicationsFields);
        if (applicationsFields.getId() != null) {
            throw new BadRequestAlertException("A new applicationsFields cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationsFields result = applicationsFieldsService.save(applicationsFields);
        return ResponseEntity
            .created(new URI("/api/applications-fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /applications-fields/:id} : Updates an existing applicationsFields.
     *
     * @param id the id of the applicationsFields to save.
     * @param applicationsFields the applicationsFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationsFields,
     * or with status {@code 400 (Bad Request)} if the applicationsFields is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicationsFields couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/applications-fields/{id}")
    public ResponseEntity<ApplicationsFields> updateApplicationsFields(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ApplicationsFields applicationsFields
    ) throws URISyntaxException {
        log.debug("REST request to update ApplicationsFields : {}, {}", id, applicationsFields);
        if (applicationsFields.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applicationsFields.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicationsFieldsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ApplicationsFields result = applicationsFieldsService.save(applicationsFields);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationsFields.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /applications-fields/:id} : Partial updates given fields of an existing applicationsFields, field will ignore if it is null
     *
     * @param id the id of the applicationsFields to save.
     * @param applicationsFields the applicationsFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationsFields,
     * or with status {@code 400 (Bad Request)} if the applicationsFields is not valid,
     * or with status {@code 404 (Not Found)} if the applicationsFields is not found,
     * or with status {@code 500 (Internal Server Error)} if the applicationsFields couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/applications-fields/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ApplicationsFields> partialUpdateApplicationsFields(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ApplicationsFields applicationsFields
    ) throws URISyntaxException {
        log.debug("REST request to partial update ApplicationsFields partially : {}, {}", id, applicationsFields);
        if (applicationsFields.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applicationsFields.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicationsFieldsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApplicationsFields> result = applicationsFieldsService.partialUpdate(applicationsFields);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationsFields.getId().toString())
        );
    }

    /**
     * {@code GET  /applications-fields} : get all the applicationsFields.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicationsFields in body.
     */
    @GetMapping("/applications-fields")
    public List<ApplicationsFields> getAllApplicationsFields() {
        log.debug("REST request to get all ApplicationsFields");
        return applicationsFieldsService.findAll();
    }

    /**
     * {@code GET  /applications-fields/:id} : get the "id" applicationsFields.
     *
     * @param id the id of the applicationsFields to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicationsFields, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/applications-fields/{id}")
    public ResponseEntity<ApplicationsFields> getApplicationsFields(@PathVariable Long id) {
        log.debug("REST request to get ApplicationsFields : {}", id);
        Optional<ApplicationsFields> applicationsFields = applicationsFieldsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicationsFields);
    }

    /**
     * {@code DELETE  /applications-fields/:id} : delete the "id" applicationsFields.
     *
     * @param id the id of the applicationsFields to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/applications-fields/{id}")
    public ResponseEntity<Void> deleteApplicationsFields(@PathVariable Long id) {
        log.debug("REST request to delete ApplicationsFields : {}", id);
        applicationsFieldsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
