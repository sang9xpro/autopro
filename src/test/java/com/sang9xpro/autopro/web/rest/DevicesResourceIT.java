package com.sang9xpro.autopro.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sang9xpro.autopro.IntegrationTest;
import com.sang9xpro.autopro.domain.Devices;
import com.sang9xpro.autopro.domain.enumeration.DeviceStatus;
import com.sang9xpro.autopro.domain.enumeration.DeviceType;
import com.sang9xpro.autopro.repository.DevicesRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DevicesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DevicesResourceIT {

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_MAC_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_MAC_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_OS = "AAAAAAAAAA";
    private static final String UPDATED_OS = "BBBBBBBBBB";

    private static final DeviceType DEFAULT_DEVICE_TYPE = DeviceType.MOBILE;
    private static final DeviceType UPDATED_DEVICE_TYPE = DeviceType.COMPUTER;

    private static final DeviceStatus DEFAULT_STATUS = DeviceStatus.Online;
    private static final DeviceStatus UPDATED_STATUS = DeviceStatus.Offline;

    private static final Instant DEFAULT_LAST_UPDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_OWNER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/devices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DevicesRepository devicesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDevicesMockMvc;

    private Devices devices;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Devices createEntity(EntityManager em) {
        Devices devices = new Devices()
            .ipAddress(DEFAULT_IP_ADDRESS)
            .macAddress(DEFAULT_MAC_ADDRESS)
            .os(DEFAULT_OS)
            .deviceType(DEFAULT_DEVICE_TYPE)
            .status(DEFAULT_STATUS)
            .lastUpdate(DEFAULT_LAST_UPDATE)
            .owner(DEFAULT_OWNER);
        return devices;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Devices createUpdatedEntity(EntityManager em) {
        Devices devices = new Devices()
            .ipAddress(UPDATED_IP_ADDRESS)
            .macAddress(UPDATED_MAC_ADDRESS)
            .os(UPDATED_OS)
            .deviceType(UPDATED_DEVICE_TYPE)
            .status(UPDATED_STATUS)
            .lastUpdate(UPDATED_LAST_UPDATE)
            .owner(UPDATED_OWNER);
        return devices;
    }

    @BeforeEach
    public void initTest() {
        devices = createEntity(em);
    }

    @Test
    @Transactional
    void createDevices() throws Exception {
        int databaseSizeBeforeCreate = devicesRepository.findAll().size();
        // Create the Devices
        restDevicesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(devices)))
            .andExpect(status().isCreated());

        // Validate the Devices in the database
        List<Devices> devicesList = devicesRepository.findAll();
        assertThat(devicesList).hasSize(databaseSizeBeforeCreate + 1);
        Devices testDevices = devicesList.get(devicesList.size() - 1);
        assertThat(testDevices.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
        assertThat(testDevices.getMacAddress()).isEqualTo(DEFAULT_MAC_ADDRESS);
        assertThat(testDevices.getOs()).isEqualTo(DEFAULT_OS);
        assertThat(testDevices.getDeviceType()).isEqualTo(DEFAULT_DEVICE_TYPE);
        assertThat(testDevices.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDevices.getLastUpdate()).isEqualTo(DEFAULT_LAST_UPDATE);
        assertThat(testDevices.getOwner()).isEqualTo(DEFAULT_OWNER);
    }

    @Test
    @Transactional
    void createDevicesWithExistingId() throws Exception {
        // Create the Devices with an existing ID
        devices.setId(1L);

        int databaseSizeBeforeCreate = devicesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDevicesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(devices)))
            .andExpect(status().isBadRequest());

        // Validate the Devices in the database
        List<Devices> devicesList = devicesRepository.findAll();
        assertThat(devicesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDevices() throws Exception {
        // Initialize the database
        devicesRepository.saveAndFlush(devices);

        // Get all the devicesList
        restDevicesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(devices.getId().intValue())))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS)))
            .andExpect(jsonPath("$.[*].macAddress").value(hasItem(DEFAULT_MAC_ADDRESS)))
            .andExpect(jsonPath("$.[*].os").value(hasItem(DEFAULT_OS)))
            .andExpect(jsonPath("$.[*].deviceType").value(hasItem(DEFAULT_DEVICE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].lastUpdate").value(hasItem(DEFAULT_LAST_UPDATE.toString())))
            .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER)));
    }

    @Test
    @Transactional
    void getDevices() throws Exception {
        // Initialize the database
        devicesRepository.saveAndFlush(devices);

        // Get the devices
        restDevicesMockMvc
            .perform(get(ENTITY_API_URL_ID, devices.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(devices.getId().intValue()))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS))
            .andExpect(jsonPath("$.macAddress").value(DEFAULT_MAC_ADDRESS))
            .andExpect(jsonPath("$.os").value(DEFAULT_OS))
            .andExpect(jsonPath("$.deviceType").value(DEFAULT_DEVICE_TYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.lastUpdate").value(DEFAULT_LAST_UPDATE.toString()))
            .andExpect(jsonPath("$.owner").value(DEFAULT_OWNER));
    }

    @Test
    @Transactional
    void getNonExistingDevices() throws Exception {
        // Get the devices
        restDevicesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDevices() throws Exception {
        // Initialize the database
        devicesRepository.saveAndFlush(devices);

        int databaseSizeBeforeUpdate = devicesRepository.findAll().size();

        // Update the devices
        Devices updatedDevices = devicesRepository.findById(devices.getId()).get();
        // Disconnect from session so that the updates on updatedDevices are not directly saved in db
        em.detach(updatedDevices);
        updatedDevices
            .ipAddress(UPDATED_IP_ADDRESS)
            .macAddress(UPDATED_MAC_ADDRESS)
            .os(UPDATED_OS)
            .deviceType(UPDATED_DEVICE_TYPE)
            .status(UPDATED_STATUS)
            .lastUpdate(UPDATED_LAST_UPDATE)
            .owner(UPDATED_OWNER);

        restDevicesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDevices.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDevices))
            )
            .andExpect(status().isOk());

        // Validate the Devices in the database
        List<Devices> devicesList = devicesRepository.findAll();
        assertThat(devicesList).hasSize(databaseSizeBeforeUpdate);
        Devices testDevices = devicesList.get(devicesList.size() - 1);
        assertThat(testDevices.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
        assertThat(testDevices.getMacAddress()).isEqualTo(UPDATED_MAC_ADDRESS);
        assertThat(testDevices.getOs()).isEqualTo(UPDATED_OS);
        assertThat(testDevices.getDeviceType()).isEqualTo(UPDATED_DEVICE_TYPE);
        assertThat(testDevices.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDevices.getLastUpdate()).isEqualTo(UPDATED_LAST_UPDATE);
        assertThat(testDevices.getOwner()).isEqualTo(UPDATED_OWNER);
    }

    @Test
    @Transactional
    void putNonExistingDevices() throws Exception {
        int databaseSizeBeforeUpdate = devicesRepository.findAll().size();
        devices.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDevicesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, devices.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(devices))
            )
            .andExpect(status().isBadRequest());

        // Validate the Devices in the database
        List<Devices> devicesList = devicesRepository.findAll();
        assertThat(devicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDevices() throws Exception {
        int databaseSizeBeforeUpdate = devicesRepository.findAll().size();
        devices.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDevicesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(devices))
            )
            .andExpect(status().isBadRequest());

        // Validate the Devices in the database
        List<Devices> devicesList = devicesRepository.findAll();
        assertThat(devicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDevices() throws Exception {
        int databaseSizeBeforeUpdate = devicesRepository.findAll().size();
        devices.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDevicesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(devices)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Devices in the database
        List<Devices> devicesList = devicesRepository.findAll();
        assertThat(devicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDevicesWithPatch() throws Exception {
        // Initialize the database
        devicesRepository.saveAndFlush(devices);

        int databaseSizeBeforeUpdate = devicesRepository.findAll().size();

        // Update the devices using partial update
        Devices partialUpdatedDevices = new Devices();
        partialUpdatedDevices.setId(devices.getId());

        partialUpdatedDevices.macAddress(UPDATED_MAC_ADDRESS).status(UPDATED_STATUS).owner(UPDATED_OWNER);

        restDevicesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDevices.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDevices))
            )
            .andExpect(status().isOk());

        // Validate the Devices in the database
        List<Devices> devicesList = devicesRepository.findAll();
        assertThat(devicesList).hasSize(databaseSizeBeforeUpdate);
        Devices testDevices = devicesList.get(devicesList.size() - 1);
        assertThat(testDevices.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
        assertThat(testDevices.getMacAddress()).isEqualTo(UPDATED_MAC_ADDRESS);
        assertThat(testDevices.getOs()).isEqualTo(DEFAULT_OS);
        assertThat(testDevices.getDeviceType()).isEqualTo(DEFAULT_DEVICE_TYPE);
        assertThat(testDevices.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDevices.getLastUpdate()).isEqualTo(DEFAULT_LAST_UPDATE);
        assertThat(testDevices.getOwner()).isEqualTo(UPDATED_OWNER);
    }

    @Test
    @Transactional
    void fullUpdateDevicesWithPatch() throws Exception {
        // Initialize the database
        devicesRepository.saveAndFlush(devices);

        int databaseSizeBeforeUpdate = devicesRepository.findAll().size();

        // Update the devices using partial update
        Devices partialUpdatedDevices = new Devices();
        partialUpdatedDevices.setId(devices.getId());

        partialUpdatedDevices
            .ipAddress(UPDATED_IP_ADDRESS)
            .macAddress(UPDATED_MAC_ADDRESS)
            .os(UPDATED_OS)
            .deviceType(UPDATED_DEVICE_TYPE)
            .status(UPDATED_STATUS)
            .lastUpdate(UPDATED_LAST_UPDATE)
            .owner(UPDATED_OWNER);

        restDevicesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDevices.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDevices))
            )
            .andExpect(status().isOk());

        // Validate the Devices in the database
        List<Devices> devicesList = devicesRepository.findAll();
        assertThat(devicesList).hasSize(databaseSizeBeforeUpdate);
        Devices testDevices = devicesList.get(devicesList.size() - 1);
        assertThat(testDevices.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
        assertThat(testDevices.getMacAddress()).isEqualTo(UPDATED_MAC_ADDRESS);
        assertThat(testDevices.getOs()).isEqualTo(UPDATED_OS);
        assertThat(testDevices.getDeviceType()).isEqualTo(UPDATED_DEVICE_TYPE);
        assertThat(testDevices.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDevices.getLastUpdate()).isEqualTo(UPDATED_LAST_UPDATE);
        assertThat(testDevices.getOwner()).isEqualTo(UPDATED_OWNER);
    }

    @Test
    @Transactional
    void patchNonExistingDevices() throws Exception {
        int databaseSizeBeforeUpdate = devicesRepository.findAll().size();
        devices.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDevicesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, devices.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(devices))
            )
            .andExpect(status().isBadRequest());

        // Validate the Devices in the database
        List<Devices> devicesList = devicesRepository.findAll();
        assertThat(devicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDevices() throws Exception {
        int databaseSizeBeforeUpdate = devicesRepository.findAll().size();
        devices.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDevicesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(devices))
            )
            .andExpect(status().isBadRequest());

        // Validate the Devices in the database
        List<Devices> devicesList = devicesRepository.findAll();
        assertThat(devicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDevices() throws Exception {
        int databaseSizeBeforeUpdate = devicesRepository.findAll().size();
        devices.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDevicesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(devices)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Devices in the database
        List<Devices> devicesList = devicesRepository.findAll();
        assertThat(devicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDevices() throws Exception {
        // Initialize the database
        devicesRepository.saveAndFlush(devices);

        int databaseSizeBeforeDelete = devicesRepository.findAll().size();

        // Delete the devices
        restDevicesMockMvc
            .perform(delete(ENTITY_API_URL_ID, devices.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Devices> devicesList = devicesRepository.findAll();
        assertThat(devicesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
