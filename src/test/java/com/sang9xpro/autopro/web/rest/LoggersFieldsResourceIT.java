package com.sang9xpro.autopro.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sang9xpro.autopro.IntegrationTest;
import com.sang9xpro.autopro.domain.LoggersFields;
import com.sang9xpro.autopro.repository.LoggersFieldsRepository;
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
 * Integration tests for the {@link LoggersFieldsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LoggersFieldsResourceIT {

    private static final String DEFAULT_FIELD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/loggers-fields";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LoggersFieldsRepository loggersFieldsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLoggersFieldsMockMvc;

    private LoggersFields loggersFields;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoggersFields createEntity(EntityManager em) {
        LoggersFields loggersFields = new LoggersFields().fieldName(DEFAULT_FIELD_NAME);
        return loggersFields;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoggersFields createUpdatedEntity(EntityManager em) {
        LoggersFields loggersFields = new LoggersFields().fieldName(UPDATED_FIELD_NAME);
        return loggersFields;
    }

    @BeforeEach
    public void initTest() {
        loggersFields = createEntity(em);
    }

    @Test
    @Transactional
    void createLoggersFields() throws Exception {
        int databaseSizeBeforeCreate = loggersFieldsRepository.findAll().size();
        // Create the LoggersFields
        restLoggersFieldsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loggersFields)))
            .andExpect(status().isCreated());

        // Validate the LoggersFields in the database
        List<LoggersFields> loggersFieldsList = loggersFieldsRepository.findAll();
        assertThat(loggersFieldsList).hasSize(databaseSizeBeforeCreate + 1);
        LoggersFields testLoggersFields = loggersFieldsList.get(loggersFieldsList.size() - 1);
        assertThat(testLoggersFields.getFieldName()).isEqualTo(DEFAULT_FIELD_NAME);
    }

    @Test
    @Transactional
    void createLoggersFieldsWithExistingId() throws Exception {
        // Create the LoggersFields with an existing ID
        loggersFields.setId(1L);

        int databaseSizeBeforeCreate = loggersFieldsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoggersFieldsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loggersFields)))
            .andExpect(status().isBadRequest());

        // Validate the LoggersFields in the database
        List<LoggersFields> loggersFieldsList = loggersFieldsRepository.findAll();
        assertThat(loggersFieldsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLoggersFields() throws Exception {
        // Initialize the database
        loggersFieldsRepository.saveAndFlush(loggersFields);

        // Get all the loggersFieldsList
        restLoggersFieldsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loggersFields.getId().intValue())))
            .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME)));
    }

    @Test
    @Transactional
    void getLoggersFields() throws Exception {
        // Initialize the database
        loggersFieldsRepository.saveAndFlush(loggersFields);

        // Get the loggersFields
        restLoggersFieldsMockMvc
            .perform(get(ENTITY_API_URL_ID, loggersFields.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(loggersFields.getId().intValue()))
            .andExpect(jsonPath("$.fieldName").value(DEFAULT_FIELD_NAME));
    }

    @Test
    @Transactional
    void getNonExistingLoggersFields() throws Exception {
        // Get the loggersFields
        restLoggersFieldsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLoggersFields() throws Exception {
        // Initialize the database
        loggersFieldsRepository.saveAndFlush(loggersFields);

        int databaseSizeBeforeUpdate = loggersFieldsRepository.findAll().size();

        // Update the loggersFields
        LoggersFields updatedLoggersFields = loggersFieldsRepository.findById(loggersFields.getId()).get();
        // Disconnect from session so that the updates on updatedLoggersFields are not directly saved in db
        em.detach(updatedLoggersFields);
        updatedLoggersFields.fieldName(UPDATED_FIELD_NAME);

        restLoggersFieldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLoggersFields.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLoggersFields))
            )
            .andExpect(status().isOk());

        // Validate the LoggersFields in the database
        List<LoggersFields> loggersFieldsList = loggersFieldsRepository.findAll();
        assertThat(loggersFieldsList).hasSize(databaseSizeBeforeUpdate);
        LoggersFields testLoggersFields = loggersFieldsList.get(loggersFieldsList.size() - 1);
        assertThat(testLoggersFields.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
    }

    @Test
    @Transactional
    void putNonExistingLoggersFields() throws Exception {
        int databaseSizeBeforeUpdate = loggersFieldsRepository.findAll().size();
        loggersFields.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoggersFieldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, loggersFields.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loggersFields))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoggersFields in the database
        List<LoggersFields> loggersFieldsList = loggersFieldsRepository.findAll();
        assertThat(loggersFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLoggersFields() throws Exception {
        int databaseSizeBeforeUpdate = loggersFieldsRepository.findAll().size();
        loggersFields.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoggersFieldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loggersFields))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoggersFields in the database
        List<LoggersFields> loggersFieldsList = loggersFieldsRepository.findAll();
        assertThat(loggersFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLoggersFields() throws Exception {
        int databaseSizeBeforeUpdate = loggersFieldsRepository.findAll().size();
        loggersFields.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoggersFieldsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loggersFields)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoggersFields in the database
        List<LoggersFields> loggersFieldsList = loggersFieldsRepository.findAll();
        assertThat(loggersFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLoggersFieldsWithPatch() throws Exception {
        // Initialize the database
        loggersFieldsRepository.saveAndFlush(loggersFields);

        int databaseSizeBeforeUpdate = loggersFieldsRepository.findAll().size();

        // Update the loggersFields using partial update
        LoggersFields partialUpdatedLoggersFields = new LoggersFields();
        partialUpdatedLoggersFields.setId(loggersFields.getId());

        restLoggersFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoggersFields.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoggersFields))
            )
            .andExpect(status().isOk());

        // Validate the LoggersFields in the database
        List<LoggersFields> loggersFieldsList = loggersFieldsRepository.findAll();
        assertThat(loggersFieldsList).hasSize(databaseSizeBeforeUpdate);
        LoggersFields testLoggersFields = loggersFieldsList.get(loggersFieldsList.size() - 1);
        assertThat(testLoggersFields.getFieldName()).isEqualTo(DEFAULT_FIELD_NAME);
    }

    @Test
    @Transactional
    void fullUpdateLoggersFieldsWithPatch() throws Exception {
        // Initialize the database
        loggersFieldsRepository.saveAndFlush(loggersFields);

        int databaseSizeBeforeUpdate = loggersFieldsRepository.findAll().size();

        // Update the loggersFields using partial update
        LoggersFields partialUpdatedLoggersFields = new LoggersFields();
        partialUpdatedLoggersFields.setId(loggersFields.getId());

        partialUpdatedLoggersFields.fieldName(UPDATED_FIELD_NAME);

        restLoggersFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoggersFields.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoggersFields))
            )
            .andExpect(status().isOk());

        // Validate the LoggersFields in the database
        List<LoggersFields> loggersFieldsList = loggersFieldsRepository.findAll();
        assertThat(loggersFieldsList).hasSize(databaseSizeBeforeUpdate);
        LoggersFields testLoggersFields = loggersFieldsList.get(loggersFieldsList.size() - 1);
        assertThat(testLoggersFields.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingLoggersFields() throws Exception {
        int databaseSizeBeforeUpdate = loggersFieldsRepository.findAll().size();
        loggersFields.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoggersFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, loggersFields.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loggersFields))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoggersFields in the database
        List<LoggersFields> loggersFieldsList = loggersFieldsRepository.findAll();
        assertThat(loggersFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLoggersFields() throws Exception {
        int databaseSizeBeforeUpdate = loggersFieldsRepository.findAll().size();
        loggersFields.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoggersFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loggersFields))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoggersFields in the database
        List<LoggersFields> loggersFieldsList = loggersFieldsRepository.findAll();
        assertThat(loggersFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLoggersFields() throws Exception {
        int databaseSizeBeforeUpdate = loggersFieldsRepository.findAll().size();
        loggersFields.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoggersFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(loggersFields))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoggersFields in the database
        List<LoggersFields> loggersFieldsList = loggersFieldsRepository.findAll();
        assertThat(loggersFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLoggersFields() throws Exception {
        // Initialize the database
        loggersFieldsRepository.saveAndFlush(loggersFields);

        int databaseSizeBeforeDelete = loggersFieldsRepository.findAll().size();

        // Delete the loggersFields
        restLoggersFieldsMockMvc
            .perform(delete(ENTITY_API_URL_ID, loggersFields.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LoggersFields> loggersFieldsList = loggersFieldsRepository.findAll();
        assertThat(loggersFieldsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
