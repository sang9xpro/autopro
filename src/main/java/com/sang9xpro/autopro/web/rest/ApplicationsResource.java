package com.sang9xpro.autopro.web.rest;

import com.sang9xpro.autopro.domain.Applications;
import com.sang9xpro.autopro.repository.ApplicationsRepository;
import com.sang9xpro.autopro.service.ApplicationsService;
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
 * REST controller for managing {@link com.sang9xpro.autopro.domain.Applications}.
 */
@RestController
@RequestMapping("/api")
public class ApplicationsResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationsResource.class);

    private static final String ENTITY_NAME = "applications";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicationsService applicationsService;

    private final ApplicationsRepository applicationsRepository;

    public ApplicationsResource(ApplicationsService applicationsService, ApplicationsRepository applicationsRepository) {
        this.applicationsService = applicationsService;
        this.applicationsRepository = applicationsRepository;
    }

    /**
     * {@code POST  /applications} : Create a new applications.
     *
     * @param applications the applications to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applications, or with status {@code 400 (Bad Request)} if the applications has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/applications")
    public ResponseEntity<Applications> createApplications(@RequestBody Applications applications) throws URISyntaxException {
        log.debug("REST request to save Applications : {}", applications);
        if (applications.getId() != null) {
            throw new BadRequestAlertException("A new applications cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Applications result = applicationsService.save(applications);
        return ResponseEntity
            .created(new URI("/api/applications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /applications/:id} : Updates an existing applications.
     *
     * @param id the id of the applications to save.
     * @param applications the applications to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applications,
     * or with status {@code 400 (Bad Request)} if the applications is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applications couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/applications/{id}")
    public ResponseEntity<Applications> updateApplications(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Applications applications
    ) throws URISyntaxException {
        log.debug("REST request to update Applications : {}, {}", id, applications);
        if (applications.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applications.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicationsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Applications result = applicationsService.save(applications);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applications.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /applications/:id} : Partial updates given fields of an existing applications, field will ignore if it is null
     *
     * @param id the id of the applications to save.
     * @param applications the applications to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applications,
     * or with status {@code 400 (Bad Request)} if the applications is not valid,
     * or with status {@code 404 (Not Found)} if the applications is not found,
     * or with status {@code 500 (Internal Server Error)} if the applications couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/applications/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Applications> partialUpdateApplications(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Applications applications
    ) throws URISyntaxException {
        log.debug("REST request to partial update Applications partially : {}, {}", id, applications);
        if (applications.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applications.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicationsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Applications> result = applicationsService.partialUpdate(applications);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applications.getId().toString())
        );
    }

    /**
     * {@code GET  /applications} : get all the applications.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applications in body.
     */
    @GetMapping("/applications")
    public List<Applications> getAllApplications() {
        log.debug("REST request to get all Applications");
        return applicationsService.findAll();
    }

    /**
     * {@code GET  /applications/:id} : get the "id" applications.
     *
     * @param id the id of the applications to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applications, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/applications/{id}")
    public ResponseEntity<Applications> getApplications(@PathVariable Long id) {
        log.debug("REST request to get Applications : {}", id);
        Optional<Applications> applications = applicationsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applications);
    }

    /**
     * {@code DELETE  /applications/:id} : delete the "id" applications.
     *
     * @param id the id of the applications to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/applications/{id}")
    public ResponseEntity<Void> deleteApplications(@PathVariable Long id) {
        log.debug("REST request to delete Applications : {}", id);
        applicationsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
