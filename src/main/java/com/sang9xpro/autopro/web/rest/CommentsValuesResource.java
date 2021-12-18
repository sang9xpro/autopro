package com.sang9xpro.autopro.web.rest;

import com.sang9xpro.autopro.domain.CommentsValues;
import com.sang9xpro.autopro.repository.CommentsValuesRepository;
import com.sang9xpro.autopro.service.CommentsValuesService;
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
 * REST controller for managing {@link com.sang9xpro.autopro.domain.CommentsValues}.
 */
@RestController
@RequestMapping("/api")
public class CommentsValuesResource {

    private final Logger log = LoggerFactory.getLogger(CommentsValuesResource.class);

    private static final String ENTITY_NAME = "commentsValues";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommentsValuesService commentsValuesService;

    private final CommentsValuesRepository commentsValuesRepository;

    public CommentsValuesResource(CommentsValuesService commentsValuesService, CommentsValuesRepository commentsValuesRepository) {
        this.commentsValuesService = commentsValuesService;
        this.commentsValuesRepository = commentsValuesRepository;
    }

    /**
     * {@code POST  /comments-values} : Create a new commentsValues.
     *
     * @param commentsValues the commentsValues to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commentsValues, or with status {@code 400 (Bad Request)} if the commentsValues has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comments-values")
    public ResponseEntity<CommentsValues> createCommentsValues(@RequestBody CommentsValues commentsValues) throws URISyntaxException {
        log.debug("REST request to save CommentsValues : {}", commentsValues);
        if (commentsValues.getId() != null) {
            throw new BadRequestAlertException("A new commentsValues cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommentsValues result = commentsValuesService.save(commentsValues);
        return ResponseEntity
            .created(new URI("/api/comments-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comments-values/:id} : Updates an existing commentsValues.
     *
     * @param id the id of the commentsValues to save.
     * @param commentsValues the commentsValues to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commentsValues,
     * or with status {@code 400 (Bad Request)} if the commentsValues is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commentsValues couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comments-values/{id}")
    public ResponseEntity<CommentsValues> updateCommentsValues(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CommentsValues commentsValues
    ) throws URISyntaxException {
        log.debug("REST request to update CommentsValues : {}, {}", id, commentsValues);
        if (commentsValues.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commentsValues.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commentsValuesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CommentsValues result = commentsValuesService.save(commentsValues);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commentsValues.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /comments-values/:id} : Partial updates given fields of an existing commentsValues, field will ignore if it is null
     *
     * @param id the id of the commentsValues to save.
     * @param commentsValues the commentsValues to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commentsValues,
     * or with status {@code 400 (Bad Request)} if the commentsValues is not valid,
     * or with status {@code 404 (Not Found)} if the commentsValues is not found,
     * or with status {@code 500 (Internal Server Error)} if the commentsValues couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/comments-values/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CommentsValues> partialUpdateCommentsValues(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CommentsValues commentsValues
    ) throws URISyntaxException {
        log.debug("REST request to partial update CommentsValues partially : {}, {}", id, commentsValues);
        if (commentsValues.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commentsValues.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commentsValuesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CommentsValues> result = commentsValuesService.partialUpdate(commentsValues);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commentsValues.getId().toString())
        );
    }

    /**
     * {@code GET  /comments-values} : get all the commentsValues.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commentsValues in body.
     */
    @GetMapping("/comments-values")
    public List<CommentsValues> getAllCommentsValues() {
        log.debug("REST request to get all CommentsValues");
        return commentsValuesService.findAll();
    }

    /**
     * {@code GET  /comments-values/:id} : get the "id" commentsValues.
     *
     * @param id the id of the commentsValues to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commentsValues, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comments-values/{id}")
    public ResponseEntity<CommentsValues> getCommentsValues(@PathVariable Long id) {
        log.debug("REST request to get CommentsValues : {}", id);
        Optional<CommentsValues> commentsValues = commentsValuesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commentsValues);
    }

    /**
     * {@code DELETE  /comments-values/:id} : delete the "id" commentsValues.
     *
     * @param id the id of the commentsValues to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comments-values/{id}")
    public ResponseEntity<Void> deleteCommentsValues(@PathVariable Long id) {
        log.debug("REST request to delete CommentsValues : {}", id);
        commentsValuesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
