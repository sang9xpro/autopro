package com.sang9xpro.autopro.web.rest;

import com.sang9xpro.autopro.domain.FacebookValues;
import com.sang9xpro.autopro.repository.FacebookValuesRepository;
import com.sang9xpro.autopro.service.FacebookValuesService;
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
 * REST controller for managing {@link com.sang9xpro.autopro.domain.FacebookValues}.
 */
@RestController
@RequestMapping("/api")
public class FacebookValuesResource {

    private final Logger log = LoggerFactory.getLogger(FacebookValuesResource.class);

    private static final String ENTITY_NAME = "facebookValues";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FacebookValuesService facebookValuesService;

    private final FacebookValuesRepository facebookValuesRepository;

    public FacebookValuesResource(FacebookValuesService facebookValuesService, FacebookValuesRepository facebookValuesRepository) {
        this.facebookValuesService = facebookValuesService;
        this.facebookValuesRepository = facebookValuesRepository;
    }

    /**
     * {@code POST  /facebook-values} : Create a new facebookValues.
     *
     * @param facebookValues the facebookValues to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new facebookValues, or with status {@code 400 (Bad Request)} if the facebookValues has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/facebook-values")
    public ResponseEntity<FacebookValues> createFacebookValues(@RequestBody FacebookValues facebookValues) throws URISyntaxException {
        log.debug("REST request to save FacebookValues : {}", facebookValues);
        if (facebookValues.getId() != null) {
            throw new BadRequestAlertException("A new facebookValues cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FacebookValues result = facebookValuesService.save(facebookValues);
        return ResponseEntity
            .created(new URI("/api/facebook-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /facebook-values/:id} : Updates an existing facebookValues.
     *
     * @param id the id of the facebookValues to save.
     * @param facebookValues the facebookValues to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated facebookValues,
     * or with status {@code 400 (Bad Request)} if the facebookValues is not valid,
     * or with status {@code 500 (Internal Server Error)} if the facebookValues couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/facebook-values/{id}")
    public ResponseEntity<FacebookValues> updateFacebookValues(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FacebookValues facebookValues
    ) throws URISyntaxException {
        log.debug("REST request to update FacebookValues : {}, {}", id, facebookValues);
        if (facebookValues.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, facebookValues.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!facebookValuesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FacebookValues result = facebookValuesService.save(facebookValues);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, facebookValues.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /facebook-values/:id} : Partial updates given fields of an existing facebookValues, field will ignore if it is null
     *
     * @param id the id of the facebookValues to save.
     * @param facebookValues the facebookValues to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated facebookValues,
     * or with status {@code 400 (Bad Request)} if the facebookValues is not valid,
     * or with status {@code 404 (Not Found)} if the facebookValues is not found,
     * or with status {@code 500 (Internal Server Error)} if the facebookValues couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/facebook-values/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FacebookValues> partialUpdateFacebookValues(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FacebookValues facebookValues
    ) throws URISyntaxException {
        log.debug("REST request to partial update FacebookValues partially : {}, {}", id, facebookValues);
        if (facebookValues.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, facebookValues.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!facebookValuesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FacebookValues> result = facebookValuesService.partialUpdate(facebookValues);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, facebookValues.getId().toString())
        );
    }

    /**
     * {@code GET  /facebook-values} : get all the facebookValues.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of facebookValues in body.
     */
    @GetMapping("/facebook-values")
    public List<FacebookValues> getAllFacebookValues() {
        log.debug("REST request to get all FacebookValues");
        return facebookValuesService.findAll();
    }

    /**
     * {@code GET  /facebook-values/:id} : get the "id" facebookValues.
     *
     * @param id the id of the facebookValues to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the facebookValues, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/facebook-values/{id}")
    public ResponseEntity<FacebookValues> getFacebookValues(@PathVariable Long id) {
        log.debug("REST request to get FacebookValues : {}", id);
        Optional<FacebookValues> facebookValues = facebookValuesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(facebookValues);
    }

    /**
     * {@code DELETE  /facebook-values/:id} : delete the "id" facebookValues.
     *
     * @param id the id of the facebookValues to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/facebook-values/{id}")
    public ResponseEntity<Void> deleteFacebookValues(@PathVariable Long id) {
        log.debug("REST request to delete FacebookValues : {}", id);
        facebookValuesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
