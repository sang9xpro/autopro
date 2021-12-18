package com.sang9xpro.autopro.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sang9xpro.autopro.IntegrationTest;
import com.sang9xpro.autopro.domain.DevicesFields;
import com.sang9xpro.autopro.repository.DevicesFieldsRepository;
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
 * Integration tests for the {@link DevicesFieldsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DevicesFieldsResourceIT {

    private static final String DEFAULT_FIELDS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIELDS_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/devices-fields";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DevicesFieldsRepository devicesFieldsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDevicesFieldsMockMvc;

    private DevicesFields devicesFields;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DevicesFields createEntity(EntityManager em) {
        DevicesFields devicesFields = new DevicesFields().fieldsName(DEFAULT_FIELDS_NAME);
        return devicesFields;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DevicesFields createUpdatedEntity(EntityManager em) {
        DevicesFields devicesFields = new DevicesFields().fieldsName(UPDATED_FIELDS_NAME);
        return devicesFields;
    }

    @BeforeEach
    public void initTest() {
        devicesFields = createEntity(em);
    }

    @Test
    @Transactional
    void createDevicesFields() throws Exception {
        int databaseSizeBeforeCreate = devicesFieldsRepository.findAll().size();
        // Create the DevicesFields
        restDevicesFieldsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(devicesFields)))
            .andExpect(status().isCreated());

        // Validate the DevicesFields in the database
        List<DevicesFields> devicesFieldsList = devicesFieldsRepository.findAll();
        assertThat(devicesFieldsList).hasSize(databaseSizeBeforeCreate + 1);
        DevicesFields testDevicesFields = devicesFieldsList.get(devicesFieldsList.size() - 1);
        assertThat(testDevicesFields.getFieldsName()).isEqualTo(DEFAULT_FIELDS_NAME);
    }

    @Test
    @Transactional
    void createDevicesFieldsWithExistingId() throws Exception {
        // Create the DevicesFields with an existing ID
        devicesFields.setId(1L);

        int databaseSizeBeforeCreate = devicesFieldsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDevicesFieldsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(devicesFields)))
            .andExpect(status().isBadRequest());

        // Validate the DevicesFields in the database
        List<DevicesFields> devicesFieldsList = devicesFieldsRepository.findAll();
        assertThat(devicesFieldsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDevicesFields() throws Exception {
        // Initialize the database
        devicesFieldsRepository.saveAndFlush(devicesFields);

        // Get all the devicesFieldsList
        restDevicesFieldsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(devicesFields.getId().intValue())))
            .andExpect(jsonPath("$.[*].fieldsName").value(hasItem(DEFAULT_FIELDS_NAME)));
    }

    @Test
    @Transactional
    void getDevicesFields() throws Exception {
        // Initialize the database
        devicesFieldsRepository.saveAndFlush(devicesFields);

        // Get the devicesFields
        restDevicesFieldsMockMvc
            .perform(get(ENTITY_API_URL_ID, devicesFields.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(devicesFields.getId().intValue()))
            .andExpect(jsonPath("$.fieldsName").value(DEFAULT_FIELDS_NAME));
    }

    @Test
    @Transactional
    void getNonExistingDevicesFields() throws Exception {
        // Get the devicesFields
        restDevicesFieldsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDevicesFields() throws Exception {
        // Initialize the database
        devicesFieldsRepository.saveAndFlush(devicesFields);

        int databaseSizeBeforeUpdate = devicesFieldsRepository.findAll().size();

        // Update the devicesFields
        DevicesFields updatedDevicesFields = devicesFieldsRepository.findById(devicesFields.getId()).get();
        // Disconnect from session so that the updates on updatedDevicesFields are not directly saved in db
        em.detach(updatedDevicesFields);
        updatedDevicesFields.fieldsName(UPDATED_FIELDS_NAME);

        restDevicesFieldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDevicesFields.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDevicesFields))
            )
            .andExpect(status().isOk());

        // Validate the DevicesFields in the database
        List<DevicesFields> devicesFieldsList = devicesFieldsRepository.findAll();
        assertThat(devicesFieldsList).hasSize(databaseSizeBeforeUpdate);
        DevicesFields testDevicesFields = devicesFieldsList.get(devicesFieldsList.size() - 1);
        assertThat(testDevicesFields.getFieldsName()).isEqualTo(UPDATED_FIELDS_NAME);
    }

    @Test
    @Transactional
    void putNonExistingDevicesFields() throws Exception {
        int databaseSizeBeforeUpdate = devicesFieldsRepository.findAll().size();
        devicesFields.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDevicesFieldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, devicesFields.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(devicesFields))
            )
            .andExpect(status().isBadRequest());

        // Validate the DevicesFields in the database
        List<DevicesFields> devicesFieldsList = devicesFieldsRepository.findAll();
        assertThat(devicesFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDevicesFields() throws Exception {
        int databaseSizeBeforeUpdate = devicesFieldsRepository.findAll().size();
        devicesFields.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDevicesFieldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(devicesFields))
            )
            .andExpect(status().isBadRequest());

        // Validate the DevicesFields in the database
        List<DevicesFields> devicesFieldsList = devicesFieldsRepository.findAll();
        assertThat(devicesFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDevicesFields() throws Exception {
        int databaseSizeBeforeUpdate = devicesFieldsRepository.findAll().size();
        devicesFields.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDevicesFieldsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(devicesFields)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DevicesFields in the database
        List<DevicesFields> devicesFieldsList = devicesFieldsRepository.findAll();
        assertThat(devicesFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDevicesFieldsWithPatch() throws Exception {
        // Initialize the database
        devicesFieldsRepository.saveAndFlush(devicesFields);

        int databaseSizeBeforeUpdate = devicesFieldsRepository.findAll().size();

        // Update the devicesFields using partial update
        DevicesFields partialUpdatedDevicesFields = new DevicesFields();
        partialUpdatedDevicesFields.setId(devicesFields.getId());

        partialUpdatedDevicesFields.fieldsName(UPDATED_FIELDS_NAME);

        restDevicesFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDevicesFields.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDevicesFields))
            )
            .andExpect(status().isOk());

        // Validate the DevicesFields in the database
        List<DevicesFields> devicesFieldsList = devicesFieldsRepository.findAll();
        assertThat(devicesFieldsList).hasSize(databaseSizeBeforeUpdate);
        DevicesFields testDevicesFields = devicesFieldsList.get(devicesFieldsList.size() - 1);
        assertThat(testDevicesFields.getFieldsName()).isEqualTo(UPDATED_FIELDS_NAME);
    }

    @Test
    @Transactional
    void fullUpdateDevicesFieldsWithPatch() throws Exception {
        // Initialize the database
        devicesFieldsRepository.saveAndFlush(devicesFields);

        int databaseSizeBeforeUpdate = devicesFieldsRepository.findAll().size();

        // Update the devicesFields using partial update
        DevicesFields partialUpdatedDevicesFields = new DevicesFields();
        partialUpdatedDevicesFields.setId(devicesFields.getId());

        partialUpdatedDevicesFields.fieldsName(UPDATED_FIELDS_NAME);

        restDevicesFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDevicesFields.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDevicesFields))
            )
            .andExpect(status().isOk());

        // Validate the DevicesFields in the database
        List<DevicesFields> devicesFieldsList = devicesFieldsRepository.findAll();
        assertThat(devicesFieldsList).hasSize(databaseSizeBeforeUpdate);
        DevicesFields testDevicesFields = devicesFieldsList.get(devicesFieldsList.size() - 1);
        assertThat(testDevicesFields.getFieldsName()).isEqualTo(UPDATED_FIELDS_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingDevicesFields() throws Exception {
        int databaseSizeBeforeUpdate = devicesFieldsRepository.findAll().size();
        devicesFields.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDevicesFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, devicesFields.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(devicesFields))
            )
            .andExpect(status().isBadRequest());

        // Validate the DevicesFields in the database
        List<DevicesFields> devicesFieldsList = devicesFieldsRepository.findAll();
        assertThat(devicesFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDevicesFields() throws Exception {
        int databaseSizeBeforeUpdate = devicesFieldsRepository.findAll().size();
        devicesFields.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDevicesFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(devicesFields))
            )
            .andExpect(status().isBadRequest());

        // Validate the DevicesFields in the database
        List<DevicesFields> devicesFieldsList = devicesFieldsRepository.findAll();
        assertThat(devicesFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDevicesFields() throws Exception {
        int databaseSizeBeforeUpdate = devicesFieldsRepository.findAll().size();
        devicesFields.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDevicesFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(devicesFields))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DevicesFields in the database
        List<DevicesFields> devicesFieldsList = devicesFieldsRepository.findAll();
        assertThat(devicesFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDevicesFields() throws Exception {
        // Initialize the database
        devicesFieldsRepository.saveAndFlush(devicesFields);

        int databaseSizeBeforeDelete = devicesFieldsRepository.findAll().size();

        // Delete the devicesFields
        restDevicesFieldsMockMvc
            .perform(delete(ENTITY_API_URL_ID, devicesFields.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DevicesFields> devicesFieldsList = devicesFieldsRepository.findAll();
        assertThat(devicesFieldsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
