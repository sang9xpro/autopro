package com.sang9xpro.autopro.web.rest;

import com.sang9xpro.autopro.domain.DevicesFields;
import com.sang9xpro.autopro.repository.DevicesFieldsRepository;
import com.sang9xpro.autopro.service.DevicesFieldsService;
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
 * REST controller for managing {@link com.sang9xpro.autopro.domain.DevicesFields}.
 */
@RestController
@RequestMapping("/api")
public class DevicesFieldsResource {

    private final Logger log = LoggerFactory.getLogger(DevicesFieldsResource.class);

    private static final String ENTITY_NAME = "devicesFields";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DevicesFieldsService devicesFieldsService;

    private final DevicesFieldsRepository devicesFieldsRepository;

    public DevicesFieldsResource(DevicesFieldsService devicesFieldsService, DevicesFieldsRepository devicesFieldsRepository) {
        this.devicesFieldsService = devicesFieldsService;
        this.devicesFieldsRepository = devicesFieldsRepository;
    }

    /**
     * {@code POST  /devices-fields} : Create a new devicesFields.
     *
     * @param devicesFields the devicesFields to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new devicesFields, or with status {@code 400 (Bad Request)} if the devicesFields has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/devices-fields")
    public ResponseEntity<DevicesFields> createDevicesFields(@RequestBody DevicesFields devicesFields) throws URISyntaxException {
        log.debug("REST request to save DevicesFields : {}", devicesFields);
        if (devicesFields.getId() != null) {
            throw new BadRequestAlertException("A new devicesFields cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DevicesFields result = devicesFieldsService.save(devicesFields);
        return ResponseEntity
            .created(new URI("/api/devices-fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /devices-fields/:id} : Updates an existing devicesFields.
     *
     * @param id the id of the devicesFields to save.
     * @param devicesFields the devicesFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated devicesFields,
     * or with status {@code 400 (Bad Request)} if the devicesFields is not valid,
     * or with status {@code 500 (Internal Server Error)} if the devicesFields couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/devices-fields/{id}")
    public ResponseEntity<DevicesFields> updateDevicesFields(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DevicesFields devicesFields
    ) throws URISyntaxException {
        log.debug("REST request to update DevicesFields : {}, {}", id, devicesFields);
        if (devicesFields.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, devicesFields.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!devicesFieldsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DevicesFields result = devicesFieldsService.save(devicesFields);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, devicesFields.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /devices-fields/:id} : Partial updates given fields of an existing devicesFields, field will ignore if it is null
     *
     * @param id the id of the devicesFields to save.
     * @param devicesFields the devicesFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated devicesFields,
     * or with status {@code 400 (Bad Request)} if the devicesFields is not valid,
     * or with status {@code 404 (Not Found)} if the devicesFields is not found,
     * or with status {@code 500 (Internal Server Error)} if the devicesFields couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/devices-fields/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DevicesFields> partialUpdateDevicesFields(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DevicesFields devicesFields
    ) throws URISyntaxException {
        log.debug("REST request to partial update DevicesFields partially : {}, {}", id, devicesFields);
        if (devicesFields.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, devicesFields.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!devicesFieldsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DevicesFields> result = devicesFieldsService.partialUpdate(devicesFields);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, devicesFields.getId().toString())
        );
    }

    /**
     * {@code GET  /devices-fields} : get all the devicesFields.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of devicesFields in body.
     */
    @GetMapping("/devices-fields")
    public List<DevicesFields> getAllDevicesFields() {
        log.debug("REST request to get all DevicesFields");
        return devicesFieldsService.findAll();
    }

    /**
     * {@code GET  /devices-fields/:id} : get the "id" devicesFields.
     *
     * @param id the id of the devicesFields to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the devicesFields, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/devices-fields/{id}")
    public ResponseEntity<DevicesFields> getDevicesFields(@PathVariable Long id) {
        log.debug("REST request to get DevicesFields : {}", id);
        Optional<DevicesFields> devicesFields = devicesFieldsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(devicesFields);
    }

    /**
     * {@code DELETE  /devices-fields/:id} : delete the "id" devicesFields.
     *
     * @param id the id of the devicesFields to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/devices-fields/{id}")
    public ResponseEntity<Void> deleteDevicesFields(@PathVariable Long id) {
        log.debug("REST request to delete DevicesFields : {}", id);
        devicesFieldsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
