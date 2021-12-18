package com.sang9xpro.autopro.web.rest;

import com.sang9xpro.autopro.domain.MostOfContent;
import com.sang9xpro.autopro.repository.MostOfContentRepository;
import com.sang9xpro.autopro.service.MostOfContentService;
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
 * REST controller for managing {@link com.sang9xpro.autopro.domain.MostOfContent}.
 */
@RestController
@RequestMapping("/api")
public class MostOfContentResource {

    private final Logger log = LoggerFactory.getLogger(MostOfContentResource.class);

    private static final String ENTITY_NAME = "mostOfContent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MostOfContentService mostOfContentService;

    private final MostOfContentRepository mostOfContentRepository;

    public MostOfContentResource(MostOfContentService mostOfContentService, MostOfContentRepository mostOfContentRepository) {
        this.mostOfContentService = mostOfContentService;
        this.mostOfContentRepository = mostOfContentRepository;
    }

    /**
     * {@code POST  /most-of-contents} : Create a new mostOfContent.
     *
     * @param mostOfContent the mostOfContent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mostOfContent, or with status {@code 400 (Bad Request)} if the mostOfContent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/most-of-contents")
    public ResponseEntity<MostOfContent> createMostOfContent(@RequestBody MostOfContent mostOfContent) throws URISyntaxException {
        log.debug("REST request to save MostOfContent : {}", mostOfContent);
        if (mostOfContent.getId() != null) {
            throw new BadRequestAlertException("A new mostOfContent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MostOfContent result = mostOfContentService.save(mostOfContent);
        return ResponseEntity
            .created(new URI("/api/most-of-contents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /most-of-contents/:id} : Updates an existing mostOfContent.
     *
     * @param id the id of the mostOfContent to save.
     * @param mostOfContent the mostOfContent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mostOfContent,
     * or with status {@code 400 (Bad Request)} if the mostOfContent is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mostOfContent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/most-of-contents/{id}")
    public ResponseEntity<MostOfContent> updateMostOfContent(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MostOfContent mostOfContent
    ) throws URISyntaxException {
        log.debug("REST request to update MostOfContent : {}, {}", id, mostOfContent);
        if (mostOfContent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mostOfContent.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mostOfContentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MostOfContent result = mostOfContentService.save(mostOfContent);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mostOfContent.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /most-of-contents/:id} : Partial updates given fields of an existing mostOfContent, field will ignore if it is null
     *
     * @param id the id of the mostOfContent to save.
     * @param mostOfContent the mostOfContent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mostOfContent,
     * or with status {@code 400 (Bad Request)} if the mostOfContent is not valid,
     * or with status {@code 404 (Not Found)} if the mostOfContent is not found,
     * or with status {@code 500 (Internal Server Error)} if the mostOfContent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/most-of-contents/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MostOfContent> partialUpdateMostOfContent(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MostOfContent mostOfContent
    ) throws URISyntaxException {
        log.debug("REST request to partial update MostOfContent partially : {}, {}", id, mostOfContent);
        if (mostOfContent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mostOfContent.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mostOfContentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MostOfContent> result = mostOfContentService.partialUpdate(mostOfContent);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mostOfContent.getId().toString())
        );
    }

    /**
     * {@code GET  /most-of-contents} : get all the mostOfContents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mostOfContents in body.
     */
    @GetMapping("/most-of-contents")
    public List<MostOfContent> getAllMostOfContents() {
        log.debug("REST request to get all MostOfContents");
        return mostOfContentService.findAll();
    }

    /**
     * {@code GET  /most-of-contents/:id} : get the "id" mostOfContent.
     *
     * @param id the id of the mostOfContent to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mostOfContent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/most-of-contents/{id}")
    public ResponseEntity<MostOfContent> getMostOfContent(@PathVariable Long id) {
        log.debug("REST request to get MostOfContent : {}", id);
        Optional<MostOfContent> mostOfContent = mostOfContentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mostOfContent);
    }

    /**
     * {@code DELETE  /most-of-contents/:id} : delete the "id" mostOfContent.
     *
     * @param id the id of the mostOfContent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/most-of-contents/{id}")
    public ResponseEntity<Void> deleteMostOfContent(@PathVariable Long id) {
        log.debug("REST request to delete MostOfContent : {}", id);
        mostOfContentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
