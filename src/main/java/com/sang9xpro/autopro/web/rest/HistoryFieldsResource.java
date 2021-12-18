package com.sang9xpro.autopro.web.rest;

import com.sang9xpro.autopro.domain.HistoryFields;
import com.sang9xpro.autopro.repository.HistoryFieldsRepository;
import com.sang9xpro.autopro.service.HistoryFieldsService;
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
 * REST controller for managing {@link com.sang9xpro.autopro.domain.HistoryFields}.
 */
@RestController
@RequestMapping("/api")
public class HistoryFieldsResource {

    private final Logger log = LoggerFactory.getLogger(HistoryFieldsResource.class);

    private static final String ENTITY_NAME = "historyFields";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HistoryFieldsService historyFieldsService;

    private final HistoryFieldsRepository historyFieldsRepository;

    public HistoryFieldsResource(HistoryFieldsService historyFieldsService, HistoryFieldsRepository historyFieldsRepository) {
        this.historyFieldsService = historyFieldsService;
        this.historyFieldsRepository = historyFieldsRepository;
    }

    /**
     * {@code POST  /history-fields} : Create a new historyFields.
     *
     * @param historyFields the historyFields to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new historyFields, or with status {@code 400 (Bad Request)} if the historyFields has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/history-fields")
    public ResponseEntity<HistoryFields> createHistoryFields(@RequestBody HistoryFields historyFields) throws URISyntaxException {
        log.debug("REST request to save HistoryFields : {}", historyFields);
        if (historyFields.getId() != null) {
            throw new BadRequestAlertException("A new historyFields cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HistoryFields result = historyFieldsService.save(historyFields);
        return ResponseEntity
            .created(new URI("/api/history-fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /history-fields/:id} : Updates an existing historyFields.
     *
     * @param id the id of the historyFields to save.
     * @param historyFields the historyFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated historyFields,
     * or with status {@code 400 (Bad Request)} if the historyFields is not valid,
     * or with status {@code 500 (Internal Server Error)} if the historyFields couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/history-fields/{id}")
    public ResponseEntity<HistoryFields> updateHistoryFields(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HistoryFields historyFields
    ) throws URISyntaxException {
        log.debug("REST request to update HistoryFields : {}, {}", id, historyFields);
        if (historyFields.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, historyFields.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!historyFieldsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HistoryFields result = historyFieldsService.save(historyFields);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, historyFields.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /history-fields/:id} : Partial updates given fields of an existing historyFields, field will ignore if it is null
     *
     * @param id the id of the historyFields to save.
     * @param historyFields the historyFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated historyFields,
     * or with status {@code 400 (Bad Request)} if the historyFields is not valid,
     * or with status {@code 404 (Not Found)} if the historyFields is not found,
     * or with status {@code 500 (Internal Server Error)} if the historyFields couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/history-fields/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HistoryFields> partialUpdateHistoryFields(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HistoryFields historyFields
    ) throws URISyntaxException {
        log.debug("REST request to partial update HistoryFields partially : {}, {}", id, historyFields);
        if (historyFields.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, historyFields.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!historyFieldsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HistoryFields> result = historyFieldsService.partialUpdate(historyFields);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, historyFields.getId().toString())
        );
    }

    /**
     * {@code GET  /history-fields} : get all the historyFields.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of historyFields in body.
     */
    @GetMapping("/history-fields")
    public List<HistoryFields> getAllHistoryFields() {
        log.debug("REST request to get all HistoryFields");
        return historyFieldsService.findAll();
    }

    /**
     * {@code GET  /history-fields/:id} : get the "id" historyFields.
     *
     * @param id the id of the historyFields to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the historyFields, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/history-fields/{id}")
    public ResponseEntity<HistoryFields> getHistoryFields(@PathVariable Long id) {
        log.debug("REST request to get HistoryFields : {}", id);
        Optional<HistoryFields> historyFields = historyFieldsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(historyFields);
    }

    /**
     * {@code DELETE  /history-fields/:id} : delete the "id" historyFields.
     *
     * @param id the id of the historyFields to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/history-fields/{id}")
    public ResponseEntity<Void> deleteHistoryFields(@PathVariable Long id) {
        log.debug("REST request to delete HistoryFields : {}", id);
        historyFieldsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
