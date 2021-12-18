package com.sang9xpro.autopro.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sang9xpro.autopro.IntegrationTest;
import com.sang9xpro.autopro.domain.DeviceValues;
import com.sang9xpro.autopro.repository.DeviceValuesRepository;
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
 * Integration tests for the {@link DeviceValuesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DeviceValuesResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/device-values";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DeviceValuesRepository deviceValuesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeviceValuesMockMvc;

    private DeviceValues deviceValues;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeviceValues createEntity(EntityManager em) {
        DeviceValues deviceValues = new DeviceValues().value(DEFAULT_VALUE);
        return deviceValues;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeviceValues createUpdatedEntity(EntityManager em) {
        DeviceValues deviceValues = new DeviceValues().value(UPDATED_VALUE);
        return deviceValues;
    }

    @BeforeEach
    public void initTest() {
        deviceValues = createEntity(em);
    }

    @Test
    @Transactional
    void createDeviceValues() throws Exception {
        int databaseSizeBeforeCreate = deviceValuesRepository.findAll().size();
        // Create the DeviceValues
        restDeviceValuesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deviceValues)))
            .andExpect(status().isCreated());

        // Validate the DeviceValues in the database
        List<DeviceValues> deviceValuesList = deviceValuesRepository.findAll();
        assertThat(deviceValuesList).hasSize(databaseSizeBeforeCreate + 1);
        DeviceValues testDeviceValues = deviceValuesList.get(deviceValuesList.size() - 1);
        assertThat(testDeviceValues.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void createDeviceValuesWithExistingId() throws Exception {
        // Create the DeviceValues with an existing ID
        deviceValues.setId(1L);

        int databaseSizeBeforeCreate = deviceValuesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeviceValuesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deviceValues)))
            .andExpect(status().isBadRequest());

        // Validate the DeviceValues in the database
        List<DeviceValues> deviceValuesList = deviceValuesRepository.findAll();
        assertThat(deviceValuesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDeviceValues() throws Exception {
        // Initialize the database
        deviceValuesRepository.saveAndFlush(deviceValues);

        // Get all the deviceValuesList
        restDeviceValuesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deviceValues.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    void getDeviceValues() throws Exception {
        // Initialize the database
        deviceValuesRepository.saveAndFlush(deviceValues);

        // Get the deviceValues
        restDeviceValuesMockMvc
            .perform(get(ENTITY_API_URL_ID, deviceValues.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deviceValues.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingDeviceValues() throws Exception {
        // Get the deviceValues
        restDeviceValuesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDeviceValues() throws Exception {
        // Initialize the database
        deviceValuesRepository.saveAndFlush(deviceValues);

        int databaseSizeBeforeUpdate = deviceValuesRepository.findAll().size();

        // Update the deviceValues
        DeviceValues updatedDeviceValues = deviceValuesRepository.findById(deviceValues.getId()).get();
        // Disconnect from session so that the updates on updatedDeviceValues are not directly saved in db
        em.detach(updatedDeviceValues);
        updatedDeviceValues.value(UPDATED_VALUE);

        restDeviceValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDeviceValues.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDeviceValues))
            )
            .andExpect(status().isOk());

        // Validate the DeviceValues in the database
        List<DeviceValues> deviceValuesList = deviceValuesRepository.findAll();
        assertThat(deviceValuesList).hasSize(databaseSizeBeforeUpdate);
        DeviceValues testDeviceValues = deviceValuesList.get(deviceValuesList.size() - 1);
        assertThat(testDeviceValues.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingDeviceValues() throws Exception {
        int databaseSizeBeforeUpdate = deviceValuesRepository.findAll().size();
        deviceValues.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeviceValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, deviceValues.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deviceValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeviceValues in the database
        List<DeviceValues> deviceValuesList = deviceValuesRepository.findAll();
        assertThat(deviceValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDeviceValues() throws Exception {
        int databaseSizeBeforeUpdate = deviceValuesRepository.findAll().size();
        deviceValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviceValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deviceValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeviceValues in the database
        List<DeviceValues> deviceValuesList = deviceValuesRepository.findAll();
        assertThat(deviceValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDeviceValues() throws Exception {
        int databaseSizeBeforeUpdate = deviceValuesRepository.findAll().size();
        deviceValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviceValuesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deviceValues)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DeviceValues in the database
        List<DeviceValues> deviceValuesList = deviceValuesRepository.findAll();
        assertThat(deviceValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDeviceValuesWithPatch() throws Exception {
        // Initialize the database
        deviceValuesRepository.saveAndFlush(deviceValues);

        int databaseSizeBeforeUpdate = deviceValuesRepository.findAll().size();

        // Update the deviceValues using partial update
        DeviceValues partialUpdatedDeviceValues = new DeviceValues();
        partialUpdatedDeviceValues.setId(deviceValues.getId());

        restDeviceValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeviceValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeviceValues))
            )
            .andExpect(status().isOk());

        // Validate the DeviceValues in the database
        List<DeviceValues> deviceValuesList = deviceValuesRepository.findAll();
        assertThat(deviceValuesList).hasSize(databaseSizeBeforeUpdate);
        DeviceValues testDeviceValues = deviceValuesList.get(deviceValuesList.size() - 1);
        assertThat(testDeviceValues.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateDeviceValuesWithPatch() throws Exception {
        // Initialize the database
        deviceValuesRepository.saveAndFlush(deviceValues);

        int databaseSizeBeforeUpdate = deviceValuesRepository.findAll().size();

        // Update the deviceValues using partial update
        DeviceValues partialUpdatedDeviceValues = new DeviceValues();
        partialUpdatedDeviceValues.setId(deviceValues.getId());

        partialUpdatedDeviceValues.value(UPDATED_VALUE);

        restDeviceValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeviceValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeviceValues))
            )
            .andExpect(status().isOk());

        // Validate the DeviceValues in the database
        List<DeviceValues> deviceValuesList = deviceValuesRepository.findAll();
        assertThat(deviceValuesList).hasSize(databaseSizeBeforeUpdate);
        DeviceValues testDeviceValues = deviceValuesList.get(deviceValuesList.size() - 1);
        assertThat(testDeviceValues.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingDeviceValues() throws Exception {
        int databaseSizeBeforeUpdate = deviceValuesRepository.findAll().size();
        deviceValues.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeviceValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, deviceValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deviceValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeviceValues in the database
        List<DeviceValues> deviceValuesList = deviceValuesRepository.findAll();
        assertThat(deviceValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDeviceValues() throws Exception {
        int databaseSizeBeforeUpdate = deviceValuesRepository.findAll().size();
        deviceValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviceValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deviceValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeviceValues in the database
        List<DeviceValues> deviceValuesList = deviceValuesRepository.findAll();
        assertThat(deviceValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDeviceValues() throws Exception {
        int databaseSizeBeforeUpdate = deviceValuesRepository.findAll().size();
        deviceValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviceValuesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(deviceValues))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DeviceValues in the database
        List<DeviceValues> deviceValuesList = deviceValuesRepository.findAll();
        assertThat(deviceValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDeviceValues() throws Exception {
        // Initialize the database
        deviceValuesRepository.saveAndFlush(deviceValues);

        int databaseSizeBeforeDelete = deviceValuesRepository.findAll().size();

        // Delete the deviceValues
        restDeviceValuesMockMvc
            .perform(delete(ENTITY_API_URL_ID, deviceValues.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DeviceValues> deviceValuesList = deviceValuesRepository.findAll();
        assertThat(deviceValuesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
