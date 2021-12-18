package com.sang9xpro.autopro.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sang9xpro.autopro.IntegrationTest;
import com.sang9xpro.autopro.domain.SchedulerTaskFields;
import com.sang9xpro.autopro.repository.SchedulerTaskFieldsRepository;
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
 * Integration tests for the {@link SchedulerTaskFieldsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SchedulerTaskFieldsResourceIT {

    private static final String DEFAULT_FIELD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/scheduler-task-fields";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SchedulerTaskFieldsRepository schedulerTaskFieldsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSchedulerTaskFieldsMockMvc;

    private SchedulerTaskFields schedulerTaskFields;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchedulerTaskFields createEntity(EntityManager em) {
        SchedulerTaskFields schedulerTaskFields = new SchedulerTaskFields().fieldName(DEFAULT_FIELD_NAME);
        return schedulerTaskFields;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchedulerTaskFields createUpdatedEntity(EntityManager em) {
        SchedulerTaskFields schedulerTaskFields = new SchedulerTaskFields().fieldName(UPDATED_FIELD_NAME);
        return schedulerTaskFields;
    }

    @BeforeEach
    public void initTest() {
        schedulerTaskFields = createEntity(em);
    }

    @Test
    @Transactional
    void createSchedulerTaskFields() throws Exception {
        int databaseSizeBeforeCreate = schedulerTaskFieldsRepository.findAll().size();
        // Create the SchedulerTaskFields
        restSchedulerTaskFieldsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schedulerTaskFields))
            )
            .andExpect(status().isCreated());

        // Validate the SchedulerTaskFields in the database
        List<SchedulerTaskFields> schedulerTaskFieldsList = schedulerTaskFieldsRepository.findAll();
        assertThat(schedulerTaskFieldsList).hasSize(databaseSizeBeforeCreate + 1);
        SchedulerTaskFields testSchedulerTaskFields = schedulerTaskFieldsList.get(schedulerTaskFieldsList.size() - 1);
        assertThat(testSchedulerTaskFields.getFieldName()).isEqualTo(DEFAULT_FIELD_NAME);
    }

    @Test
    @Transactional
    void createSchedulerTaskFieldsWithExistingId() throws Exception {
        // Create the SchedulerTaskFields with an existing ID
        schedulerTaskFields.setId(1L);

        int databaseSizeBeforeCreate = schedulerTaskFieldsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchedulerTaskFieldsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schedulerTaskFields))
            )
            .andExpect(status().isBadRequest());

        // Validate the SchedulerTaskFields in the database
        List<SchedulerTaskFields> schedulerTaskFieldsList = schedulerTaskFieldsRepository.findAll();
        assertThat(schedulerTaskFieldsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSchedulerTaskFields() throws Exception {
        // Initialize the database
        schedulerTaskFieldsRepository.saveAndFlush(schedulerTaskFields);

        // Get all the schedulerTaskFieldsList
        restSchedulerTaskFieldsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schedulerTaskFields.getId().intValue())))
            .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME)));
    }

    @Test
    @Transactional
    void getSchedulerTaskFields() throws Exception {
        // Initialize the database
        schedulerTaskFieldsRepository.saveAndFlush(schedulerTaskFields);

        // Get the schedulerTaskFields
        restSchedulerTaskFieldsMockMvc
            .perform(get(ENTITY_API_URL_ID, schedulerTaskFields.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(schedulerTaskFields.getId().intValue()))
            .andExpect(jsonPath("$.fieldName").value(DEFAULT_FIELD_NAME));
    }

    @Test
    @Transactional
    void getNonExistingSchedulerTaskFields() throws Exception {
        // Get the schedulerTaskFields
        restSchedulerTaskFieldsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSchedulerTaskFields() throws Exception {
        // Initialize the database
        schedulerTaskFieldsRepository.saveAndFlush(schedulerTaskFields);

        int databaseSizeBeforeUpdate = schedulerTaskFieldsRepository.findAll().size();

        // Update the schedulerTaskFields
        SchedulerTaskFields updatedSchedulerTaskFields = schedulerTaskFieldsRepository.findById(schedulerTaskFields.getId()).get();
        // Disconnect from session so that the updates on updatedSchedulerTaskFields are not directly saved in db
        em.detach(updatedSchedulerTaskFields);
        updatedSchedulerTaskFields.fieldName(UPDATED_FIELD_NAME);

        restSchedulerTaskFieldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSchedulerTaskFields.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSchedulerTaskFields))
            )
            .andExpect(status().isOk());

        // Validate the SchedulerTaskFields in the database
        List<SchedulerTaskFields> schedulerTaskFieldsList = schedulerTaskFieldsRepository.findAll();
        assertThat(schedulerTaskFieldsList).hasSize(databaseSizeBeforeUpdate);
        SchedulerTaskFields testSchedulerTaskFields = schedulerTaskFieldsList.get(schedulerTaskFieldsList.size() - 1);
        assertThat(testSchedulerTaskFields.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
    }

    @Test
    @Transactional
    void putNonExistingSchedulerTaskFields() throws Exception {
        int databaseSizeBeforeUpdate = schedulerTaskFieldsRepository.findAll().size();
        schedulerTaskFields.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchedulerTaskFieldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, schedulerTaskFields.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(schedulerTaskFields))
            )
            .andExpect(status().isBadRequest());

        // Validate the SchedulerTaskFields in the database
        List<SchedulerTaskFields> schedulerTaskFieldsList = schedulerTaskFieldsRepository.findAll();
        assertThat(schedulerTaskFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSchedulerTaskFields() throws Exception {
        int databaseSizeBeforeUpdate = schedulerTaskFieldsRepository.findAll().size();
        schedulerTaskFields.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchedulerTaskFieldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(schedulerTaskFields))
            )
            .andExpect(status().isBadRequest());

        // Validate the SchedulerTaskFields in the database
        List<SchedulerTaskFields> schedulerTaskFieldsList = schedulerTaskFieldsRepository.findAll();
        assertThat(schedulerTaskFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSchedulerTaskFields() throws Exception {
        int databaseSizeBeforeUpdate = schedulerTaskFieldsRepository.findAll().size();
        schedulerTaskFields.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchedulerTaskFieldsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schedulerTaskFields))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SchedulerTaskFields in the database
        List<SchedulerTaskFields> schedulerTaskFieldsList = schedulerTaskFieldsRepository.findAll();
        assertThat(schedulerTaskFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSchedulerTaskFieldsWithPatch() throws Exception {
        // Initialize the database
        schedulerTaskFieldsRepository.saveAndFlush(schedulerTaskFields);

        int databaseSizeBeforeUpdate = schedulerTaskFieldsRepository.findAll().size();

        // Update the schedulerTaskFields using partial update
        SchedulerTaskFields partialUpdatedSchedulerTaskFields = new SchedulerTaskFields();
        partialUpdatedSchedulerTaskFields.setId(schedulerTaskFields.getId());

        partialUpdatedSchedulerTaskFields.fieldName(UPDATED_FIELD_NAME);

        restSchedulerTaskFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSchedulerTaskFields.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSchedulerTaskFields))
            )
            .andExpect(status().isOk());

        // Validate the SchedulerTaskFields in the database
        List<SchedulerTaskFields> schedulerTaskFieldsList = schedulerTaskFieldsRepository.findAll();
        assertThat(schedulerTaskFieldsList).hasSize(databaseSizeBeforeUpdate);
        SchedulerTaskFields testSchedulerTaskFields = schedulerTaskFieldsList.get(schedulerTaskFieldsList.size() - 1);
        assertThat(testSchedulerTaskFields.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
    }

    @Test
    @Transactional
    void fullUpdateSchedulerTaskFieldsWithPatch() throws Exception {
        // Initialize the database
        schedulerTaskFieldsRepository.saveAndFlush(schedulerTaskFields);

        int databaseSizeBeforeUpdate = schedulerTaskFieldsRepository.findAll().size();

        // Update the schedulerTaskFields using partial update
        SchedulerTaskFields partialUpdatedSchedulerTaskFields = new SchedulerTaskFields();
        partialUpdatedSchedulerTaskFields.setId(schedulerTaskFields.getId());

        partialUpdatedSchedulerTaskFields.fieldName(UPDATED_FIELD_NAME);

        restSchedulerTaskFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSchedulerTaskFields.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSchedulerTaskFields))
            )
            .andExpect(status().isOk());

        // Validate the SchedulerTaskFields in the database
        List<SchedulerTaskFields> schedulerTaskFieldsList = schedulerTaskFieldsRepository.findAll();
        assertThat(schedulerTaskFieldsList).hasSize(databaseSizeBeforeUpdate);
        SchedulerTaskFields testSchedulerTaskFields = schedulerTaskFieldsList.get(schedulerTaskFieldsList.size() - 1);
        assertThat(testSchedulerTaskFields.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingSchedulerTaskFields() throws Exception {
        int databaseSizeBeforeUpdate = schedulerTaskFieldsRepository.findAll().size();
        schedulerTaskFields.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchedulerTaskFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, schedulerTaskFields.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(schedulerTaskFields))
            )
            .andExpect(status().isBadRequest());

        // Validate the SchedulerTaskFields in the database
        List<SchedulerTaskFields> schedulerTaskFieldsList = schedulerTaskFieldsRepository.findAll();
        assertThat(schedulerTaskFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSchedulerTaskFields() throws Exception {
        int databaseSizeBeforeUpdate = schedulerTaskFieldsRepository.findAll().size();
        schedulerTaskFields.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchedulerTaskFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(schedulerTaskFields))
            )
            .andExpect(status().isBadRequest());

        // Validate the SchedulerTaskFields in the database
        List<SchedulerTaskFields> schedulerTaskFieldsList = schedulerTaskFieldsRepository.findAll();
        assertThat(schedulerTaskFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSchedulerTaskFields() throws Exception {
        int databaseSizeBeforeUpdate = schedulerTaskFieldsRepository.findAll().size();
        schedulerTaskFields.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchedulerTaskFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(schedulerTaskFields))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SchedulerTaskFields in the database
        List<SchedulerTaskFields> schedulerTaskFieldsList = schedulerTaskFieldsRepository.findAll();
        assertThat(schedulerTaskFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSchedulerTaskFields() throws Exception {
        // Initialize the database
        schedulerTaskFieldsRepository.saveAndFlush(schedulerTaskFields);

        int databaseSizeBeforeDelete = schedulerTaskFieldsRepository.findAll().size();

        // Delete the schedulerTaskFields
        restSchedulerTaskFieldsMockMvc
            .perform(delete(ENTITY_API_URL_ID, schedulerTaskFields.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SchedulerTaskFields> schedulerTaskFieldsList = schedulerTaskFieldsRepository.findAll();
        assertThat(schedulerTaskFieldsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
