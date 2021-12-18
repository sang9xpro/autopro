package com.sang9xpro.autopro.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sang9xpro.autopro.IntegrationTest;
import com.sang9xpro.autopro.domain.SchedulerTaskValues;
import com.sang9xpro.autopro.repository.SchedulerTaskValuesRepository;
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
 * Integration tests for the {@link SchedulerTaskValuesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SchedulerTaskValuesResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/scheduler-task-values";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SchedulerTaskValuesRepository schedulerTaskValuesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSchedulerTaskValuesMockMvc;

    private SchedulerTaskValues schedulerTaskValues;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchedulerTaskValues createEntity(EntityManager em) {
        SchedulerTaskValues schedulerTaskValues = new SchedulerTaskValues().value(DEFAULT_VALUE);
        return schedulerTaskValues;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchedulerTaskValues createUpdatedEntity(EntityManager em) {
        SchedulerTaskValues schedulerTaskValues = new SchedulerTaskValues().value(UPDATED_VALUE);
        return schedulerTaskValues;
    }

    @BeforeEach
    public void initTest() {
        schedulerTaskValues = createEntity(em);
    }

    @Test
    @Transactional
    void createSchedulerTaskValues() throws Exception {
        int databaseSizeBeforeCreate = schedulerTaskValuesRepository.findAll().size();
        // Create the SchedulerTaskValues
        restSchedulerTaskValuesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schedulerTaskValues))
            )
            .andExpect(status().isCreated());

        // Validate the SchedulerTaskValues in the database
        List<SchedulerTaskValues> schedulerTaskValuesList = schedulerTaskValuesRepository.findAll();
        assertThat(schedulerTaskValuesList).hasSize(databaseSizeBeforeCreate + 1);
        SchedulerTaskValues testSchedulerTaskValues = schedulerTaskValuesList.get(schedulerTaskValuesList.size() - 1);
        assertThat(testSchedulerTaskValues.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void createSchedulerTaskValuesWithExistingId() throws Exception {
        // Create the SchedulerTaskValues with an existing ID
        schedulerTaskValues.setId(1L);

        int databaseSizeBeforeCreate = schedulerTaskValuesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchedulerTaskValuesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schedulerTaskValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the SchedulerTaskValues in the database
        List<SchedulerTaskValues> schedulerTaskValuesList = schedulerTaskValuesRepository.findAll();
        assertThat(schedulerTaskValuesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSchedulerTaskValues() throws Exception {
        // Initialize the database
        schedulerTaskValuesRepository.saveAndFlush(schedulerTaskValues);

        // Get all the schedulerTaskValuesList
        restSchedulerTaskValuesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schedulerTaskValues.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    void getSchedulerTaskValues() throws Exception {
        // Initialize the database
        schedulerTaskValuesRepository.saveAndFlush(schedulerTaskValues);

        // Get the schedulerTaskValues
        restSchedulerTaskValuesMockMvc
            .perform(get(ENTITY_API_URL_ID, schedulerTaskValues.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(schedulerTaskValues.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingSchedulerTaskValues() throws Exception {
        // Get the schedulerTaskValues
        restSchedulerTaskValuesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSchedulerTaskValues() throws Exception {
        // Initialize the database
        schedulerTaskValuesRepository.saveAndFlush(schedulerTaskValues);

        int databaseSizeBeforeUpdate = schedulerTaskValuesRepository.findAll().size();

        // Update the schedulerTaskValues
        SchedulerTaskValues updatedSchedulerTaskValues = schedulerTaskValuesRepository.findById(schedulerTaskValues.getId()).get();
        // Disconnect from session so that the updates on updatedSchedulerTaskValues are not directly saved in db
        em.detach(updatedSchedulerTaskValues);
        updatedSchedulerTaskValues.value(UPDATED_VALUE);

        restSchedulerTaskValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSchedulerTaskValues.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSchedulerTaskValues))
            )
            .andExpect(status().isOk());

        // Validate the SchedulerTaskValues in the database
        List<SchedulerTaskValues> schedulerTaskValuesList = schedulerTaskValuesRepository.findAll();
        assertThat(schedulerTaskValuesList).hasSize(databaseSizeBeforeUpdate);
        SchedulerTaskValues testSchedulerTaskValues = schedulerTaskValuesList.get(schedulerTaskValuesList.size() - 1);
        assertThat(testSchedulerTaskValues.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingSchedulerTaskValues() throws Exception {
        int databaseSizeBeforeUpdate = schedulerTaskValuesRepository.findAll().size();
        schedulerTaskValues.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchedulerTaskValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, schedulerTaskValues.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(schedulerTaskValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the SchedulerTaskValues in the database
        List<SchedulerTaskValues> schedulerTaskValuesList = schedulerTaskValuesRepository.findAll();
        assertThat(schedulerTaskValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSchedulerTaskValues() throws Exception {
        int databaseSizeBeforeUpdate = schedulerTaskValuesRepository.findAll().size();
        schedulerTaskValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchedulerTaskValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(schedulerTaskValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the SchedulerTaskValues in the database
        List<SchedulerTaskValues> schedulerTaskValuesList = schedulerTaskValuesRepository.findAll();
        assertThat(schedulerTaskValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSchedulerTaskValues() throws Exception {
        int databaseSizeBeforeUpdate = schedulerTaskValuesRepository.findAll().size();
        schedulerTaskValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchedulerTaskValuesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schedulerTaskValues))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SchedulerTaskValues in the database
        List<SchedulerTaskValues> schedulerTaskValuesList = schedulerTaskValuesRepository.findAll();
        assertThat(schedulerTaskValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSchedulerTaskValuesWithPatch() throws Exception {
        // Initialize the database
        schedulerTaskValuesRepository.saveAndFlush(schedulerTaskValues);

        int databaseSizeBeforeUpdate = schedulerTaskValuesRepository.findAll().size();

        // Update the schedulerTaskValues using partial update
        SchedulerTaskValues partialUpdatedSchedulerTaskValues = new SchedulerTaskValues();
        partialUpdatedSchedulerTaskValues.setId(schedulerTaskValues.getId());

        partialUpdatedSchedulerTaskValues.value(UPDATED_VALUE);

        restSchedulerTaskValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSchedulerTaskValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSchedulerTaskValues))
            )
            .andExpect(status().isOk());

        // Validate the SchedulerTaskValues in the database
        List<SchedulerTaskValues> schedulerTaskValuesList = schedulerTaskValuesRepository.findAll();
        assertThat(schedulerTaskValuesList).hasSize(databaseSizeBeforeUpdate);
        SchedulerTaskValues testSchedulerTaskValues = schedulerTaskValuesList.get(schedulerTaskValuesList.size() - 1);
        assertThat(testSchedulerTaskValues.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateSchedulerTaskValuesWithPatch() throws Exception {
        // Initialize the database
        schedulerTaskValuesRepository.saveAndFlush(schedulerTaskValues);

        int databaseSizeBeforeUpdate = schedulerTaskValuesRepository.findAll().size();

        // Update the schedulerTaskValues using partial update
        SchedulerTaskValues partialUpdatedSchedulerTaskValues = new SchedulerTaskValues();
        partialUpdatedSchedulerTaskValues.setId(schedulerTaskValues.getId());

        partialUpdatedSchedulerTaskValues.value(UPDATED_VALUE);

        restSchedulerTaskValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSchedulerTaskValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSchedulerTaskValues))
            )
            .andExpect(status().isOk());

        // Validate the SchedulerTaskValues in the database
        List<SchedulerTaskValues> schedulerTaskValuesList = schedulerTaskValuesRepository.findAll();
        assertThat(schedulerTaskValuesList).hasSize(databaseSizeBeforeUpdate);
        SchedulerTaskValues testSchedulerTaskValues = schedulerTaskValuesList.get(schedulerTaskValuesList.size() - 1);
        assertThat(testSchedulerTaskValues.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingSchedulerTaskValues() throws Exception {
        int databaseSizeBeforeUpdate = schedulerTaskValuesRepository.findAll().size();
        schedulerTaskValues.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchedulerTaskValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, schedulerTaskValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(schedulerTaskValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the SchedulerTaskValues in the database
        List<SchedulerTaskValues> schedulerTaskValuesList = schedulerTaskValuesRepository.findAll();
        assertThat(schedulerTaskValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSchedulerTaskValues() throws Exception {
        int databaseSizeBeforeUpdate = schedulerTaskValuesRepository.findAll().size();
        schedulerTaskValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchedulerTaskValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(schedulerTaskValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the SchedulerTaskValues in the database
        List<SchedulerTaskValues> schedulerTaskValuesList = schedulerTaskValuesRepository.findAll();
        assertThat(schedulerTaskValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSchedulerTaskValues() throws Exception {
        int databaseSizeBeforeUpdate = schedulerTaskValuesRepository.findAll().size();
        schedulerTaskValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchedulerTaskValuesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(schedulerTaskValues))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SchedulerTaskValues in the database
        List<SchedulerTaskValues> schedulerTaskValuesList = schedulerTaskValuesRepository.findAll();
        assertThat(schedulerTaskValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSchedulerTaskValues() throws Exception {
        // Initialize the database
        schedulerTaskValuesRepository.saveAndFlush(schedulerTaskValues);

        int databaseSizeBeforeDelete = schedulerTaskValuesRepository.findAll().size();

        // Delete the schedulerTaskValues
        restSchedulerTaskValuesMockMvc
            .perform(delete(ENTITY_API_URL_ID, schedulerTaskValues.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SchedulerTaskValues> schedulerTaskValuesList = schedulerTaskValuesRepository.findAll();
        assertThat(schedulerTaskValuesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
