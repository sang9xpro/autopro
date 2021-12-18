package com.sang9xpro.autopro.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sang9xpro.autopro.IntegrationTest;
import com.sang9xpro.autopro.domain.LoggersValues;
import com.sang9xpro.autopro.repository.LoggersValuesRepository;
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
 * Integration tests for the {@link LoggersValuesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LoggersValuesResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/loggers-values";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LoggersValuesRepository loggersValuesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLoggersValuesMockMvc;

    private LoggersValues loggersValues;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoggersValues createEntity(EntityManager em) {
        LoggersValues loggersValues = new LoggersValues().value(DEFAULT_VALUE);
        return loggersValues;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoggersValues createUpdatedEntity(EntityManager em) {
        LoggersValues loggersValues = new LoggersValues().value(UPDATED_VALUE);
        return loggersValues;
    }

    @BeforeEach
    public void initTest() {
        loggersValues = createEntity(em);
    }

    @Test
    @Transactional
    void createLoggersValues() throws Exception {
        int databaseSizeBeforeCreate = loggersValuesRepository.findAll().size();
        // Create the LoggersValues
        restLoggersValuesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loggersValues)))
            .andExpect(status().isCreated());

        // Validate the LoggersValues in the database
        List<LoggersValues> loggersValuesList = loggersValuesRepository.findAll();
        assertThat(loggersValuesList).hasSize(databaseSizeBeforeCreate + 1);
        LoggersValues testLoggersValues = loggersValuesList.get(loggersValuesList.size() - 1);
        assertThat(testLoggersValues.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void createLoggersValuesWithExistingId() throws Exception {
        // Create the LoggersValues with an existing ID
        loggersValues.setId(1L);

        int databaseSizeBeforeCreate = loggersValuesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoggersValuesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loggersValues)))
            .andExpect(status().isBadRequest());

        // Validate the LoggersValues in the database
        List<LoggersValues> loggersValuesList = loggersValuesRepository.findAll();
        assertThat(loggersValuesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLoggersValues() throws Exception {
        // Initialize the database
        loggersValuesRepository.saveAndFlush(loggersValues);

        // Get all the loggersValuesList
        restLoggersValuesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loggersValues.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    void getLoggersValues() throws Exception {
        // Initialize the database
        loggersValuesRepository.saveAndFlush(loggersValues);

        // Get the loggersValues
        restLoggersValuesMockMvc
            .perform(get(ENTITY_API_URL_ID, loggersValues.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(loggersValues.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingLoggersValues() throws Exception {
        // Get the loggersValues
        restLoggersValuesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLoggersValues() throws Exception {
        // Initialize the database
        loggersValuesRepository.saveAndFlush(loggersValues);

        int databaseSizeBeforeUpdate = loggersValuesRepository.findAll().size();

        // Update the loggersValues
        LoggersValues updatedLoggersValues = loggersValuesRepository.findById(loggersValues.getId()).get();
        // Disconnect from session so that the updates on updatedLoggersValues are not directly saved in db
        em.detach(updatedLoggersValues);
        updatedLoggersValues.value(UPDATED_VALUE);

        restLoggersValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLoggersValues.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLoggersValues))
            )
            .andExpect(status().isOk());

        // Validate the LoggersValues in the database
        List<LoggersValues> loggersValuesList = loggersValuesRepository.findAll();
        assertThat(loggersValuesList).hasSize(databaseSizeBeforeUpdate);
        LoggersValues testLoggersValues = loggersValuesList.get(loggersValuesList.size() - 1);
        assertThat(testLoggersValues.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingLoggersValues() throws Exception {
        int databaseSizeBeforeUpdate = loggersValuesRepository.findAll().size();
        loggersValues.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoggersValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, loggersValues.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loggersValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoggersValues in the database
        List<LoggersValues> loggersValuesList = loggersValuesRepository.findAll();
        assertThat(loggersValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLoggersValues() throws Exception {
        int databaseSizeBeforeUpdate = loggersValuesRepository.findAll().size();
        loggersValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoggersValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loggersValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoggersValues in the database
        List<LoggersValues> loggersValuesList = loggersValuesRepository.findAll();
        assertThat(loggersValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLoggersValues() throws Exception {
        int databaseSizeBeforeUpdate = loggersValuesRepository.findAll().size();
        loggersValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoggersValuesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loggersValues)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoggersValues in the database
        List<LoggersValues> loggersValuesList = loggersValuesRepository.findAll();
        assertThat(loggersValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLoggersValuesWithPatch() throws Exception {
        // Initialize the database
        loggersValuesRepository.saveAndFlush(loggersValues);

        int databaseSizeBeforeUpdate = loggersValuesRepository.findAll().size();

        // Update the loggersValues using partial update
        LoggersValues partialUpdatedLoggersValues = new LoggersValues();
        partialUpdatedLoggersValues.setId(loggersValues.getId());

        restLoggersValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoggersValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoggersValues))
            )
            .andExpect(status().isOk());

        // Validate the LoggersValues in the database
        List<LoggersValues> loggersValuesList = loggersValuesRepository.findAll();
        assertThat(loggersValuesList).hasSize(databaseSizeBeforeUpdate);
        LoggersValues testLoggersValues = loggersValuesList.get(loggersValuesList.size() - 1);
        assertThat(testLoggersValues.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateLoggersValuesWithPatch() throws Exception {
        // Initialize the database
        loggersValuesRepository.saveAndFlush(loggersValues);

        int databaseSizeBeforeUpdate = loggersValuesRepository.findAll().size();

        // Update the loggersValues using partial update
        LoggersValues partialUpdatedLoggersValues = new LoggersValues();
        partialUpdatedLoggersValues.setId(loggersValues.getId());

        partialUpdatedLoggersValues.value(UPDATED_VALUE);

        restLoggersValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoggersValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoggersValues))
            )
            .andExpect(status().isOk());

        // Validate the LoggersValues in the database
        List<LoggersValues> loggersValuesList = loggersValuesRepository.findAll();
        assertThat(loggersValuesList).hasSize(databaseSizeBeforeUpdate);
        LoggersValues testLoggersValues = loggersValuesList.get(loggersValuesList.size() - 1);
        assertThat(testLoggersValues.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingLoggersValues() throws Exception {
        int databaseSizeBeforeUpdate = loggersValuesRepository.findAll().size();
        loggersValues.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoggersValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, loggersValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loggersValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoggersValues in the database
        List<LoggersValues> loggersValuesList = loggersValuesRepository.findAll();
        assertThat(loggersValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLoggersValues() throws Exception {
        int databaseSizeBeforeUpdate = loggersValuesRepository.findAll().size();
        loggersValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoggersValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loggersValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoggersValues in the database
        List<LoggersValues> loggersValuesList = loggersValuesRepository.findAll();
        assertThat(loggersValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLoggersValues() throws Exception {
        int databaseSizeBeforeUpdate = loggersValuesRepository.findAll().size();
        loggersValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoggersValuesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(loggersValues))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoggersValues in the database
        List<LoggersValues> loggersValuesList = loggersValuesRepository.findAll();
        assertThat(loggersValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLoggersValues() throws Exception {
        // Initialize the database
        loggersValuesRepository.saveAndFlush(loggersValues);

        int databaseSizeBeforeDelete = loggersValuesRepository.findAll().size();

        // Delete the loggersValues
        restLoggersValuesMockMvc
            .perform(delete(ENTITY_API_URL_ID, loggersValues.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LoggersValues> loggersValuesList = loggersValuesRepository.findAll();
        assertThat(loggersValuesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
