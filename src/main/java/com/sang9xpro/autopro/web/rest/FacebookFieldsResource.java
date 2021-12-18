package com.sang9xpro.autopro.web.rest;

import com.sang9xpro.autopro.domain.FacebookFields;
import com.sang9xpro.autopro.repository.FacebookFieldsRepository;
import com.sang9xpro.autopro.service.FacebookFieldsService;
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
 * REST controller for managing {@link com.sang9xpro.autopro.domain.FacebookFields}.
 */
@RestController
@RequestMapping("/api")
public class FacebookFieldsResource {

    private final Logger log = LoggerFactory.getLogger(FacebookFieldsResource.class);

    private static final String ENTITY_NAME = "facebookFields";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FacebookFieldsService facebookFieldsService;

    private final FacebookFieldsRepository facebookFieldsRepository;

    public FacebookFieldsResource(FacebookFieldsService facebookFieldsService, FacebookFieldsRepository facebookFieldsRepository) {
        this.facebookFieldsService = facebookFieldsService;
        this.facebookFieldsRepository = facebookFieldsRepository;
    }

    /**
     * {@code POST  /facebook-fields} : Create a new facebookFields.
     *
     * @param facebookFields the facebookFields to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new facebookFields, or with status {@code 400 (Bad Request)} if the facebookFields has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/facebook-fields")
    public ResponseEntity<FacebookFields> createFacebookFields(@RequestBody FacebookFields facebookFields) throws URISyntaxException {
        log.debug("REST request to save FacebookFields : {}", facebookFields);
        if (facebookFields.getId() != null) {
            throw new BadRequestAlertException("A new facebookFields cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FacebookFields result = facebookFieldsService.save(facebookFields);
        return ResponseEntity
            .created(new URI("/api/facebook-fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /facebook-fields/:id} : Updates an existing facebookFields.
     *
     * @param id the id of the facebookFields to save.
     * @param facebookFields the facebookFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated facebookFields,
     * or with status {@code 400 (Bad Request)} if the facebookFields is not valid,
     * or with status {@code 500 (Internal Server Error)} if the facebookFields couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/facebook-fields/{id}")
    public ResponseEntity<FacebookFields> updateFacebookFields(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FacebookFields facebookFields
    ) throws URISyntaxException {
        log.debug("REST request to update FacebookFields : {}, {}", id, facebookFields);
        if (facebookFields.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, facebookFields.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!facebookFieldsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FacebookFields result = facebookFieldsService.save(facebookFields);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, facebookFields.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /facebook-fields/:id} : Partial updates given fields of an existing facebookFields, field will ignore if it is null
     *
     * @param id the id of the facebookFields to save.
     * @param facebookFields the facebookFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated facebookFields,
     * or with status {@code 400 (Bad Request)} if the facebookFields is not valid,
     * or with status {@code 404 (Not Found)} if the facebookFields is not found,
     * or with status {@code 500 (Internal Server Error)} if the facebookFields couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/facebook-fields/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FacebookFields> partialUpdateFacebookFields(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FacebookFields facebookFields
    ) throws URISyntaxException {
        log.debug("REST request to partial update FacebookFields partially : {}, {}", id, facebookFields);
        if (facebookFields.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, facebookFields.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!facebookFieldsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FacebookFields> result = facebookFieldsService.partialUpdate(facebookFields);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, facebookFields.getId().toString())
        );
    }

    /**
     * {@code GET  /facebook-fields} : get all the facebookFields.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of facebookFields in body.
     */
    @GetMapping("/facebook-fields")
    public List<FacebookFields> getAllFacebookFields() {
        log.debug("REST request to get all FacebookFields");
        return facebookFieldsService.findAll();
    }

    /**
     * {@code GET  /facebook-fields/:id} : get the "id" facebookFields.
     *
     * @param id the id of the facebookFields to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the facebookFields, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/facebook-fields/{id}")
    public ResponseEntity<FacebookFields> getFacebookFields(@PathVariable Long id) {
        log.debug("REST request to get FacebookFields : {}", id);
        Optional<FacebookFields> facebookFields = facebookFieldsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(facebookFields);
    }

    /**
     * {@code DELETE  /facebook-fields/:id} : delete the "id" facebookFields.
     *
     * @param id the id of the facebookFields to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/facebook-fields/{id}")
    public ResponseEntity<Void> deleteFacebookFields(@PathVariable Long id) {
        log.debug("REST request to delete FacebookFields : {}", id);
        facebookFieldsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
