package com.sang9xpro.autopro.web.rest;

import com.sang9xpro.autopro.domain.DeviceValues;
import com.sang9xpro.autopro.repository.DeviceValuesRepository;
import com.sang9xpro.autopro.service.DeviceValuesService;
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
 * REST controller for managing {@link com.sang9xpro.autopro.domain.DeviceValues}.
 */
@RestController
@RequestMapping("/api")
public class DeviceValuesResource {

    private final Logger log = LoggerFactory.getLogger(DeviceValuesResource.class);

    private static final String ENTITY_NAME = "deviceValues";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeviceValuesService deviceValuesService;

    private final DeviceValuesRepository deviceValuesRepository;

    public DeviceValuesResource(DeviceValuesService deviceValuesService, DeviceValuesRepository deviceValuesRepository) {
        this.deviceValuesService = deviceValuesService;
        this.deviceValuesRepository = deviceValuesRepository;
    }

    /**
     * {@code POST  /device-values} : Create a new deviceValues.
     *
     * @param deviceValues the deviceValues to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deviceValues, or with status {@code 400 (Bad Request)} if the deviceValues has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/device-values")
    public ResponseEntity<DeviceValues> createDeviceValues(@RequestBody DeviceValues deviceValues) throws URISyntaxException {
        log.debug("REST request to save DeviceValues : {}", deviceValues);
        if (deviceValues.getId() != null) {
            throw new BadRequestAlertException("A new deviceValues cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeviceValues result = deviceValuesService.save(deviceValues);
        return ResponseEntity
            .created(new URI("/api/device-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /device-values/:id} : Updates an existing deviceValues.
     *
     * @param id the id of the deviceValues to save.
     * @param deviceValues the deviceValues to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deviceValues,
     * or with status {@code 400 (Bad Request)} if the deviceValues is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deviceValues couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/device-values/{id}")
    public ResponseEntity<DeviceValues> updateDeviceValues(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DeviceValues deviceValues
    ) throws URISyntaxException {
        log.debug("REST request to update DeviceValues : {}, {}", id, deviceValues);
        if (deviceValues.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deviceValues.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deviceValuesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DeviceValues result = deviceValuesService.save(deviceValues);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deviceValues.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /device-values/:id} : Partial updates given fields of an existing deviceValues, field will ignore if it is null
     *
     * @param id the id of the deviceValues to save.
     * @param deviceValues the deviceValues to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deviceValues,
     * or with status {@code 400 (Bad Request)} if the deviceValues is not valid,
     * or with status {@code 404 (Not Found)} if the deviceValues is not found,
     * or with status {@code 500 (Internal Server Error)} if the deviceValues couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/device-values/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DeviceValues> partialUpdateDeviceValues(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DeviceValues deviceValues
    ) throws URISyntaxException {
        log.debug("REST request to partial update DeviceValues partially : {}, {}", id, deviceValues);
        if (deviceValues.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deviceValues.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deviceValuesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DeviceValues> result = deviceValuesService.partialUpdate(deviceValues);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deviceValues.getId().toString())
        );
    }

    /**
     * {@code GET  /device-values} : get all the deviceValues.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deviceValues in body.
     */
    @GetMapping("/device-values")
    public List<DeviceValues> getAllDeviceValues() {
        log.debug("REST request to get all DeviceValues");
        return deviceValuesService.findAll();
    }

    /**
     * {@code GET  /device-values/:id} : get the "id" deviceValues.
     *
     * @param id the id of the deviceValues to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deviceValues, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/device-values/{id}")
    public ResponseEntity<DeviceValues> getDeviceValues(@PathVariable Long id) {
        log.debug("REST request to get DeviceValues : {}", id);
        Optional<DeviceValues> deviceValues = deviceValuesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deviceValues);
    }

    /**
     * {@code DELETE  /device-values/:id} : delete the "id" deviceValues.
     *
     * @param id the id of the deviceValues to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/device-values/{id}")
    public ResponseEntity<Void> deleteDeviceValues(@PathVariable Long id) {
        log.debug("REST request to delete DeviceValues : {}", id);
        deviceValuesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
