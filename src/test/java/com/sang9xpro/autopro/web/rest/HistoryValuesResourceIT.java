package com.sang9xpro.autopro.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sang9xpro.autopro.IntegrationTest;
import com.sang9xpro.autopro.domain.HistoryValues;
import com.sang9xpro.autopro.repository.HistoryValuesRepository;
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
 * Integration tests for the {@link HistoryValuesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HistoryValuesResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/history-values";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HistoryValuesRepository historyValuesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHistoryValuesMockMvc;

    private HistoryValues historyValues;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HistoryValues createEntity(EntityManager em) {
        HistoryValues historyValues = new HistoryValues().value(DEFAULT_VALUE);
        return historyValues;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HistoryValues createUpdatedEntity(EntityManager em) {
        HistoryValues historyValues = new HistoryValues().value(UPDATED_VALUE);
        return historyValues;
    }

    @BeforeEach
    public void initTest() {
        historyValues = createEntity(em);
    }

    @Test
    @Transactional
    void createHistoryValues() throws Exception {
        int databaseSizeBeforeCreate = historyValuesRepository.findAll().size();
        // Create the HistoryValues
        restHistoryValuesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historyValues)))
            .andExpect(status().isCreated());

        // Validate the HistoryValues in the database
        List<HistoryValues> historyValuesList = historyValuesRepository.findAll();
        assertThat(historyValuesList).hasSize(databaseSizeBeforeCreate + 1);
        HistoryValues testHistoryValues = historyValuesList.get(historyValuesList.size() - 1);
        assertThat(testHistoryValues.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void createHistoryValuesWithExistingId() throws Exception {
        // Create the HistoryValues with an existing ID
        historyValues.setId(1L);

        int databaseSizeBeforeCreate = historyValuesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHistoryValuesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historyValues)))
            .andExpect(status().isBadRequest());

        // Validate the HistoryValues in the database
        List<HistoryValues> historyValuesList = historyValuesRepository.findAll();
        assertThat(historyValuesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHistoryValues() throws Exception {
        // Initialize the database
        historyValuesRepository.saveAndFlush(historyValues);

        // Get all the historyValuesList
        restHistoryValuesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historyValues.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    void getHistoryValues() throws Exception {
        // Initialize the database
        historyValuesRepository.saveAndFlush(historyValues);

        // Get the historyValues
        restHistoryValuesMockMvc
            .perform(get(ENTITY_API_URL_ID, historyValues.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(historyValues.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingHistoryValues() throws Exception {
        // Get the historyValues
        restHistoryValuesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewHistoryValues() throws Exception {
        // Initialize the database
        historyValuesRepository.saveAndFlush(historyValues);

        int databaseSizeBeforeUpdate = historyValuesRepository.findAll().size();

        // Update the historyValues
        HistoryValues updatedHistoryValues = historyValuesRepository.findById(historyValues.getId()).get();
        // Disconnect from session so that the updates on updatedHistoryValues are not directly saved in db
        em.detach(updatedHistoryValues);
        updatedHistoryValues.value(UPDATED_VALUE);

        restHistoryValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHistoryValues.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedHistoryValues))
            )
            .andExpect(status().isOk());

        // Validate the HistoryValues in the database
        List<HistoryValues> historyValuesList = historyValuesRepository.findAll();
        assertThat(historyValuesList).hasSize(databaseSizeBeforeUpdate);
        HistoryValues testHistoryValues = historyValuesList.get(historyValuesList.size() - 1);
        assertThat(testHistoryValues.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingHistoryValues() throws Exception {
        int databaseSizeBeforeUpdate = historyValuesRepository.findAll().size();
        historyValues.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistoryValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, historyValues.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(historyValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistoryValues in the database
        List<HistoryValues> historyValuesList = historyValuesRepository.findAll();
        assertThat(historyValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHistoryValues() throws Exception {
        int databaseSizeBeforeUpdate = historyValuesRepository.findAll().size();
        historyValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoryValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(historyValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistoryValues in the database
        List<HistoryValues> historyValuesList = historyValuesRepository.findAll();
        assertThat(historyValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHistoryValues() throws Exception {
        int databaseSizeBeforeUpdate = historyValuesRepository.findAll().size();
        historyValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoryValuesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historyValues)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HistoryValues in the database
        List<HistoryValues> historyValuesList = historyValuesRepository.findAll();
        assertThat(historyValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHistoryValuesWithPatch() throws Exception {
        // Initialize the database
        historyValuesRepository.saveAndFlush(historyValues);

        int databaseSizeBeforeUpdate = historyValuesRepository.findAll().size();

        // Update the historyValues using partial update
        HistoryValues partialUpdatedHistoryValues = new HistoryValues();
        partialUpdatedHistoryValues.setId(historyValues.getId());

        partialUpdatedHistoryValues.value(UPDATED_VALUE);

        restHistoryValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHistoryValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHistoryValues))
            )
            .andExpect(status().isOk());

        // Validate the HistoryValues in the database
        List<HistoryValues> historyValuesList = historyValuesRepository.findAll();
        assertThat(historyValuesList).hasSize(databaseSizeBeforeUpdate);
        HistoryValues testHistoryValues = historyValuesList.get(historyValuesList.size() - 1);
        assertThat(testHistoryValues.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateHistoryValuesWithPatch() throws Exception {
        // Initialize the database
        historyValuesRepository.saveAndFlush(historyValues);

        int databaseSizeBeforeUpdate = historyValuesRepository.findAll().size();

        // Update the historyValues using partial update
        HistoryValues partialUpdatedHistoryValues = new HistoryValues();
        partialUpdatedHistoryValues.setId(historyValues.getId());

        partialUpdatedHistoryValues.value(UPDATED_VALUE);

        restHistoryValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHistoryValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHistoryValues))
            )
            .andExpect(status().isOk());

        // Validate the HistoryValues in the database
        List<HistoryValues> historyValuesList = historyValuesRepository.findAll();
        assertThat(historyValuesList).hasSize(databaseSizeBeforeUpdate);
        HistoryValues testHistoryValues = historyValuesList.get(historyValuesList.size() - 1);
        assertThat(testHistoryValues.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingHistoryValues() throws Exception {
        int databaseSizeBeforeUpdate = historyValuesRepository.findAll().size();
        historyValues.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistoryValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, historyValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(historyValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistoryValues in the database
        List<HistoryValues> historyValuesList = historyValuesRepository.findAll();
        assertThat(historyValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHistoryValues() throws Exception {
        int databaseSizeBeforeUpdate = historyValuesRepository.findAll().size();
        historyValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoryValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(historyValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistoryValues in the database
        List<HistoryValues> historyValuesList = historyValuesRepository.findAll();
        assertThat(historyValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHistoryValues() throws Exception {
        int databaseSizeBeforeUpdate = historyValuesRepository.findAll().size();
        historyValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoryValuesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(historyValues))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HistoryValues in the database
        List<HistoryValues> historyValuesList = historyValuesRepository.findAll();
        assertThat(historyValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHistoryValues() throws Exception {
        // Initialize the database
        historyValuesRepository.saveAndFlush(historyValues);

        int databaseSizeBeforeDelete = historyValuesRepository.findAll().size();

        // Delete the historyValues
        restHistoryValuesMockMvc
            .perform(delete(ENTITY_API_URL_ID, historyValues.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HistoryValues> historyValuesList = historyValuesRepository.findAll();
        assertThat(historyValuesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
