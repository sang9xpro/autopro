package com.sang9xpro.autopro.web.rest;

import com.sang9xpro.autopro.domain.CommentsFields;
import com.sang9xpro.autopro.repository.CommentsFieldsRepository;
import com.sang9xpro.autopro.service.CommentsFieldsService;
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
 * REST controller for managing {@link com.sang9xpro.autopro.domain.CommentsFields}.
 */
@RestController
@RequestMapping("/api")
public class CommentsFieldsResource {

    private final Logger log = LoggerFactory.getLogger(CommentsFieldsResource.class);

    private static final String ENTITY_NAME = "commentsFields";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommentsFieldsService commentsFieldsService;

    private final CommentsFieldsRepository commentsFieldsRepository;

    public CommentsFieldsResource(CommentsFieldsService commentsFieldsService, CommentsFieldsRepository commentsFieldsRepository) {
        this.commentsFieldsService = commentsFieldsService;
        this.commentsFieldsRepository = commentsFieldsRepository;
    }

    /**
     * {@code POST  /comments-fields} : Create a new commentsFields.
     *
     * @param commentsFields the commentsFields to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commentsFields, or with status {@code 400 (Bad Request)} if the commentsFields has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comments-fields")
    public ResponseEntity<CommentsFields> createCommentsFields(@RequestBody CommentsFields commentsFields) throws URISyntaxException {
        log.debug("REST request to save CommentsFields : {}", commentsFields);
        if (commentsFields.getId() != null) {
            throw new BadRequestAlertException("A new commentsFields cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommentsFields result = commentsFieldsService.save(commentsFields);
        return ResponseEntity
            .created(new URI("/api/comments-fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comments-fields/:id} : Updates an existing commentsFields.
     *
     * @param id the id of the commentsFields to save.
     * @param commentsFields the commentsFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commentsFields,
     * or with status {@code 400 (Bad Request)} if the commentsFields is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commentsFields couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comments-fields/{id}")
    public ResponseEntity<CommentsFields> updateCommentsFields(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CommentsFields commentsFields
    ) throws URISyntaxException {
        log.debug("REST request to update CommentsFields : {}, {}", id, commentsFields);
        if (commentsFields.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commentsFields.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commentsFieldsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CommentsFields result = commentsFieldsService.save(commentsFields);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commentsFields.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /comments-fields/:id} : Partial updates given fields of an existing commentsFields, field will ignore if it is null
     *
     * @param id the id of the commentsFields to save.
     * @param commentsFields the commentsFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commentsFields,
     * or with status {@code 400 (Bad Request)} if the commentsFields is not valid,
     * or with status {@code 404 (Not Found)} if the commentsFields is not found,
     * or with status {@code 500 (Internal Server Error)} if the commentsFields couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/comments-fields/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CommentsFields> partialUpdateCommentsFields(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CommentsFields commentsFields
    ) throws URISyntaxException {
        log.debug("REST request to partial update CommentsFields partially : {}, {}", id, commentsFields);
        if (commentsFields.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commentsFields.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commentsFieldsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CommentsFields> result = commentsFieldsService.partialUpdate(commentsFields);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commentsFields.getId().toString())
        );
    }

    /**
     * {@code GET  /comments-fields} : get all the commentsFields.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commentsFields in body.
     */
    @GetMapping("/comments-fields")
    public List<CommentsFields> getAllCommentsFields() {
        log.debug("REST request to get all CommentsFields");
        return commentsFieldsService.findAll();
    }

    /**
     * {@code GET  /comments-fields/:id} : get the "id" commentsFields.
     *
     * @param id the id of the commentsFields to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commentsFields, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comments-fields/{id}")
    public ResponseEntity<CommentsFields> getCommentsFields(@PathVariable Long id) {
        log.debug("REST request to get CommentsFields : {}", id);
        Optional<CommentsFields> commentsFields = commentsFieldsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commentsFields);
    }

    /**
     * {@code DELETE  /comments-fields/:id} : delete the "id" commentsFields.
     *
     * @param id the id of the commentsFields to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comments-fields/{id}")
    public ResponseEntity<Void> deleteCommentsFields(@PathVariable Long id) {
        log.debug("REST request to delete CommentsFields : {}", id);
        commentsFieldsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
