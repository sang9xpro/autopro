package com.sang9xpro.autopro.web.rest;

import com.sang9xpro.autopro.domain.Devices;
import com.sang9xpro.autopro.repository.DevicesRepository;
import com.sang9xpro.autopro.service.DevicesService;
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
 * REST controller for managing {@link com.sang9xpro.autopro.domain.Devices}.
 */
@RestController
@RequestMapping("/api")
public class DevicesResource {

    private final Logger log = LoggerFactory.getLogger(DevicesResource.class);

    private static final String ENTITY_NAME = "devices";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DevicesService devicesService;

    private final DevicesRepository devicesRepository;

    public DevicesResource(DevicesService devicesService, DevicesRepository devicesRepository) {
        this.devicesService = devicesService;
        this.devicesRepository = devicesRepository;
    }

    /**
     * {@code POST  /devices} : Create a new devices.
     *
     * @param devices the devices to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new devices, or with status {@code 400 (Bad Request)} if the devices has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/devices")
    public ResponseEntity<Devices> createDevices(@RequestBody Devices devices) throws URISyntaxException {
        log.debug("REST request to save Devices : {}", devices);
        if (devices.getId() != null) {
            throw new BadRequestAlertException("A new devices cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Devices result = devicesService.save(devices);
        return ResponseEntity
            .created(new URI("/api/devices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /devices/:id} : Updates an existing devices.
     *
     * @param id the id of the devices to save.
     * @param devices the devices to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated devices,
     * or with status {@code 400 (Bad Request)} if the devices is not valid,
     * or with status {@code 500 (Internal Server Error)} if the devices couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/devices/{id}")
    public ResponseEntity<Devices> updateDevices(@PathVariable(value = "id", required = false) final Long id, @RequestBody Devices devices)
        throws URISyntaxException {
        log.debug("REST request to update Devices : {}, {}", id, devices);
        if (devices.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, devices.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!devicesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Devices result = devicesService.save(devices);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, devices.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /devices/:id} : Partial updates given fields of an existing devices, field will ignore if it is null
     *
     * @param id the id of the devices to save.
     * @param devices the devices to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated devices,
     * or with status {@code 400 (Bad Request)} if the devices is not valid,
     * or with status {@code 404 (Not Found)} if the devices is not found,
     * or with status {@code 500 (Internal Server Error)} if the devices couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/devices/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Devices> partialUpdateDevices(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Devices devices
    ) throws URISyntaxException {
        log.debug("REST request to partial update Devices partially : {}, {}", id, devices);
        if (devices.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, devices.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!devicesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Devices> result = devicesService.partialUpdate(devices);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, devices.getId().toString())
        );
    }

    /**
     * {@code GET  /devices} : get all the devices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of devices in body.
     */
    @GetMapping("/devices")
    public List<Devices> getAllDevices() {
        log.debug("REST request to get all Devices");
        return devicesService.findAll();
    }

    /**
     * {@code GET  /devices/:id} : get the "id" devices.
     *
     * @param id the id of the devices to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the devices, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/devices/{id}")
    public ResponseEntity<Devices> getDevices(@PathVariable Long id) {
        log.debug("REST request to get Devices : {}", id);
        Optional<Devices> devices = devicesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(devices);
    }

    /**
     * {@code DELETE  /devices/:id} : delete the "id" devices.
     *
     * @param id the id of the devices to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/devices/{id}")
    public ResponseEntity<Void> deleteDevices(@PathVariable Long id) {
        log.debug("REST request to delete Devices : {}", id);
        devicesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
