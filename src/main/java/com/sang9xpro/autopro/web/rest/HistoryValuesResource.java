package com.sang9xpro.autopro.web.rest;

import com.sang9xpro.autopro.domain.HistoryValues;
import com.sang9xpro.autopro.repository.HistoryValuesRepository;
import com.sang9xpro.autopro.service.HistoryValuesService;
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
 * REST controller for managing {@link com.sang9xpro.autopro.domain.HistoryValues}.
 */
@RestController
@RequestMapping("/api")
public class HistoryValuesResource {

    private final Logger log = LoggerFactory.getLogger(HistoryValuesResource.class);

    private static final String ENTITY_NAME = "historyValues";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HistoryValuesService historyValuesService;

    private final HistoryValuesRepository historyValuesRepository;

    public HistoryValuesResource(HistoryValuesService historyValuesService, HistoryValuesRepository historyValuesRepository) {
        this.historyValuesService = historyValuesService;
        this.historyValuesRepository = historyValuesRepository;
    }

    /**
     * {@code POST  /history-values} : Create a new historyValues.
     *
     * @param historyValues the historyValues to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new historyValues, or with status {@code 400 (Bad Request)} if the historyValues has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/history-values")
    public ResponseEntity<HistoryValues> createHistoryValues(@RequestBody HistoryValues historyValues) throws URISyntaxException {
        log.debug("REST request to save HistoryValues : {}", historyValues);
        if (historyValues.getId() != null) {
            throw new BadRequestAlertException("A new historyValues cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HistoryValues result = historyValuesService.save(historyValues);
        return ResponseEntity
            .created(new URI("/api/history-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /history-values/:id} : Updates an existing historyValues.
     *
     * @param id the id of the historyValues to save.
     * @param historyValues the historyValues to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated historyValues,
     * or with status {@code 400 (Bad Request)} if the historyValues is not valid,
     * or with status {@code 500 (Internal Server Error)} if the historyValues couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/history-values/{id}")
    public ResponseEntity<HistoryValues> updateHistoryValues(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HistoryValues historyValues
    ) throws URISyntaxException {
        log.debug("REST request to update HistoryValues : {}, {}", id, historyValues);
        if (historyValues.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, historyValues.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!historyValuesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HistoryValues result = historyValuesService.save(historyValues);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, historyValues.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /history-values/:id} : Partial updates given fields of an existing historyValues, field will ignore if it is null
     *
     * @param id the id of the historyValues to save.
     * @param historyValues the historyValues to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated historyValues,
     * or with status {@code 400 (Bad Request)} if the historyValues is not valid,
     * or with status {@code 404 (Not Found)} if the historyValues is not found,
     * or with status {@code 500 (Internal Server Error)} if the historyValues couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/history-values/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HistoryValues> partialUpdateHistoryValues(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HistoryValues historyValues
    ) throws URISyntaxException {
        log.debug("REST request to partial update HistoryValues partially : {}, {}", id, historyValues);
        if (historyValues.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, historyValues.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!historyValuesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HistoryValues> result = historyValuesService.partialUpdate(historyValues);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, historyValues.getId().toString())
        );
    }

    /**
     * {@code GET  /history-values} : get all the historyValues.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of historyValues in body.
     */
    @GetMapping("/history-values")
    public List<HistoryValues> getAllHistoryValues() {
        log.debug("REST request to get all HistoryValues");
        return historyValuesService.findAll();
    }

    /**
     * {@code GET  /history-values/:id} : get the "id" historyValues.
     *
     * @param id the id of the historyValues to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the historyValues, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/history-values/{id}")
    public ResponseEntity<HistoryValues> getHistoryValues(@PathVariable Long id) {
        log.debug("REST request to get HistoryValues : {}", id);
        Optional<HistoryValues> historyValues = historyValuesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(historyValues);
    }

    /**
     * {@code DELETE  /history-values/:id} : delete the "id" historyValues.
     *
     * @param id the id of the historyValues to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/history-values/{id}")
    public ResponseEntity<Void> deleteHistoryValues(@PathVariable Long id) {
        log.debug("REST request to delete HistoryValues : {}", id);
        historyValuesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
