package com.sang9xpro.autopro.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sang9xpro.autopro.IntegrationTest;
import com.sang9xpro.autopro.domain.MostOfContValues;
import com.sang9xpro.autopro.repository.MostOfContValuesRepository;
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
 * Integration tests for the {@link MostOfContValuesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MostOfContValuesResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/most-of-cont-values";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MostOfContValuesRepository mostOfContValuesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMostOfContValuesMockMvc;

    private MostOfContValues mostOfContValues;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MostOfContValues createEntity(EntityManager em) {
        MostOfContValues mostOfContValues = new MostOfContValues().value(DEFAULT_VALUE);
        return mostOfContValues;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MostOfContValues createUpdatedEntity(EntityManager em) {
        MostOfContValues mostOfContValues = new MostOfContValues().value(UPDATED_VALUE);
        return mostOfContValues;
    }

    @BeforeEach
    public void initTest() {
        mostOfContValues = createEntity(em);
    }

    @Test
    @Transactional
    void createMostOfContValues() throws Exception {
        int databaseSizeBeforeCreate = mostOfContValuesRepository.findAll().size();
        // Create the MostOfContValues
        restMostOfContValuesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mostOfContValues))
            )
            .andExpect(status().isCreated());

        // Validate the MostOfContValues in the database
        List<MostOfContValues> mostOfContValuesList = mostOfContValuesRepository.findAll();
        assertThat(mostOfContValuesList).hasSize(databaseSizeBeforeCreate + 1);
        MostOfContValues testMostOfContValues = mostOfContValuesList.get(mostOfContValuesList.size() - 1);
        assertThat(testMostOfContValues.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void createMostOfContValuesWithExistingId() throws Exception {
        // Create the MostOfContValues with an existing ID
        mostOfContValues.setId(1L);

        int databaseSizeBeforeCreate = mostOfContValuesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMostOfContValuesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mostOfContValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the MostOfContValues in the database
        List<MostOfContValues> mostOfContValuesList = mostOfContValuesRepository.findAll();
        assertThat(mostOfContValuesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMostOfContValues() throws Exception {
        // Initialize the database
        mostOfContValuesRepository.saveAndFlush(mostOfContValues);

        // Get all the mostOfContValuesList
        restMostOfContValuesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mostOfContValues.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    void getMostOfContValues() throws Exception {
        // Initialize the database
        mostOfContValuesRepository.saveAndFlush(mostOfContValues);

        // Get the mostOfContValues
        restMostOfContValuesMockMvc
            .perform(get(ENTITY_API_URL_ID, mostOfContValues.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mostOfContValues.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingMostOfContValues() throws Exception {
        // Get the mostOfContValues
        restMostOfContValuesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMostOfContValues() throws Exception {
        // Initialize the database
        mostOfContValuesRepository.saveAndFlush(mostOfContValues);

        int databaseSizeBeforeUpdate = mostOfContValuesRepository.findAll().size();

        // Update the mostOfContValues
        MostOfContValues updatedMostOfContValues = mostOfContValuesRepository.findById(mostOfContValues.getId()).get();
        // Disconnect from session so that the updates on updatedMostOfContValues are not directly saved in db
        em.detach(updatedMostOfContValues);
        updatedMostOfContValues.value(UPDATED_VALUE);

        restMostOfContValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMostOfContValues.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMostOfContValues))
            )
            .andExpect(status().isOk());

        // Validate the MostOfContValues in the database
        List<MostOfContValues> mostOfContValuesList = mostOfContValuesRepository.findAll();
        assertThat(mostOfContValuesList).hasSize(databaseSizeBeforeUpdate);
        MostOfContValues testMostOfContValues = mostOfContValuesList.get(mostOfContValuesList.size() - 1);
        assertThat(testMostOfContValues.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingMostOfContValues() throws Exception {
        int databaseSizeBeforeUpdate = mostOfContValuesRepository.findAll().size();
        mostOfContValues.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMostOfContValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mostOfContValues.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mostOfContValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the MostOfContValues in the database
        List<MostOfContValues> mostOfContValuesList = mostOfContValuesRepository.findAll();
        assertThat(mostOfContValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMostOfContValues() throws Exception {
        int databaseSizeBeforeUpdate = mostOfContValuesRepository.findAll().size();
        mostOfContValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMostOfContValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mostOfContValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the MostOfContValues in the database
        List<MostOfContValues> mostOfContValuesList = mostOfContValuesRepository.findAll();
        assertThat(mostOfContValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMostOfContValues() throws Exception {
        int databaseSizeBeforeUpdate = mostOfContValuesRepository.findAll().size();
        mostOfContValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMostOfContValuesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mostOfContValues))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MostOfContValues in the database
        List<MostOfContValues> mostOfContValuesList = mostOfContValuesRepository.findAll();
        assertThat(mostOfContValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMostOfContValuesWithPatch() throws Exception {
        // Initialize the database
        mostOfContValuesRepository.saveAndFlush(mostOfContValues);

        int databaseSizeBeforeUpdate = mostOfContValuesRepository.findAll().size();

        // Update the mostOfContValues using partial update
        MostOfContValues partialUpdatedMostOfContValues = new MostOfContValues();
        partialUpdatedMostOfContValues.setId(mostOfContValues.getId());

        restMostOfContValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMostOfContValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMostOfContValues))
            )
            .andExpect(status().isOk());

        // Validate the MostOfContValues in the database
        List<MostOfContValues> mostOfContValuesList = mostOfContValuesRepository.findAll();
        assertThat(mostOfContValuesList).hasSize(databaseSizeBeforeUpdate);
        MostOfContValues testMostOfContValues = mostOfContValuesList.get(mostOfContValuesList.size() - 1);
        assertThat(testMostOfContValues.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateMostOfContValuesWithPatch() throws Exception {
        // Initialize the database
        mostOfContValuesRepository.saveAndFlush(mostOfContValues);

        int databaseSizeBeforeUpdate = mostOfContValuesRepository.findAll().size();

        // Update the mostOfContValues using partial update
        MostOfContValues partialUpdatedMostOfContValues = new MostOfContValues();
        partialUpdatedMostOfContValues.setId(mostOfContValues.getId());

        partialUpdatedMostOfContValues.value(UPDATED_VALUE);

        restMostOfContValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMostOfContValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMostOfContValues))
            )
            .andExpect(status().isOk());

        // Validate the MostOfContValues in the database
        List<MostOfContValues> mostOfContValuesList = mostOfContValuesRepository.findAll();
        assertThat(mostOfContValuesList).hasSize(databaseSizeBeforeUpdate);
        MostOfContValues testMostOfContValues = mostOfContValuesList.get(mostOfContValuesList.size() - 1);
        assertThat(testMostOfContValues.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingMostOfContValues() throws Exception {
        int databaseSizeBeforeUpdate = mostOfContValuesRepository.findAll().size();
        mostOfContValues.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMostOfContValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mostOfContValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mostOfContValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the MostOfContValues in the database
        List<MostOfContValues> mostOfContValuesList = mostOfContValuesRepository.findAll();
        assertThat(mostOfContValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMostOfContValues() throws Exception {
        int databaseSizeBeforeUpdate = mostOfContValuesRepository.findAll().size();
        mostOfContValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMostOfContValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mostOfContValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the MostOfContValues in the database
        List<MostOfContValues> mostOfContValuesList = mostOfContValuesRepository.findAll();
        assertThat(mostOfContValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMostOfContValues() throws Exception {
        int databaseSizeBeforeUpdate = mostOfContValuesRepository.findAll().size();
        mostOfContValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMostOfContValuesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mostOfContValues))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MostOfContValues in the database
        List<MostOfContValues> mostOfContValuesList = mostOfContValuesRepository.findAll();
        assertThat(mostOfContValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMostOfContValues() throws Exception {
        // Initialize the database
        mostOfContValuesRepository.saveAndFlush(mostOfContValues);

        int databaseSizeBeforeDelete = mostOfContValuesRepository.findAll().size();

        // Delete the mostOfContValues
        restMostOfContValuesMockMvc
            .perform(delete(ENTITY_API_URL_ID, mostOfContValues.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MostOfContValues> mostOfContValuesList = mostOfContValuesRepository.findAll();
        assertThat(mostOfContValuesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
