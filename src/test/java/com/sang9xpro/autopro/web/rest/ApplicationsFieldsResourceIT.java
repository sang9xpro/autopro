package com.sang9xpro.autopro.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sang9xpro.autopro.IntegrationTest;
import com.sang9xpro.autopro.domain.ApplicationsFields;
import com.sang9xpro.autopro.repository.ApplicationsFieldsRepository;
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
 * Integration tests for the {@link ApplicationsFieldsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApplicationsFieldsResourceIT {

    private static final String DEFAULT_FIELD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/applications-fields";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApplicationsFieldsRepository applicationsFieldsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicationsFieldsMockMvc;

    private ApplicationsFields applicationsFields;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationsFields createEntity(EntityManager em) {
        ApplicationsFields applicationsFields = new ApplicationsFields().fieldName(DEFAULT_FIELD_NAME);
        return applicationsFields;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationsFields createUpdatedEntity(EntityManager em) {
        ApplicationsFields applicationsFields = new ApplicationsFields().fieldName(UPDATED_FIELD_NAME);
        return applicationsFields;
    }

    @BeforeEach
    public void initTest() {
        applicationsFields = createEntity(em);
    }

    @Test
    @Transactional
    void createApplicationsFields() throws Exception {
        int databaseSizeBeforeCreate = applicationsFieldsRepository.findAll().size();
        // Create the ApplicationsFields
        restApplicationsFieldsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicationsFields))
            )
            .andExpect(status().isCreated());

        // Validate the ApplicationsFields in the database
        List<ApplicationsFields> applicationsFieldsList = applicationsFieldsRepository.findAll();
        assertThat(applicationsFieldsList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationsFields testApplicationsFields = applicationsFieldsList.get(applicationsFieldsList.size() - 1);
        assertThat(testApplicationsFields.getFieldName()).isEqualTo(DEFAULT_FIELD_NAME);
    }

    @Test
    @Transactional
    void createApplicationsFieldsWithExistingId() throws Exception {
        // Create the ApplicationsFields with an existing ID
        applicationsFields.setId(1L);

        int databaseSizeBeforeCreate = applicationsFieldsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationsFieldsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicationsFields))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationsFields in the database
        List<ApplicationsFields> applicationsFieldsList = applicationsFieldsRepository.findAll();
        assertThat(applicationsFieldsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllApplicationsFields() throws Exception {
        // Initialize the database
        applicationsFieldsRepository.saveAndFlush(applicationsFields);

        // Get all the applicationsFieldsList
        restApplicationsFieldsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationsFields.getId().intValue())))
            .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME)));
    }

    @Test
    @Transactional
    void getApplicationsFields() throws Exception {
        // Initialize the database
        applicationsFieldsRepository.saveAndFlush(applicationsFields);

        // Get the applicationsFields
        restApplicationsFieldsMockMvc
            .perform(get(ENTITY_API_URL_ID, applicationsFields.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applicationsFields.getId().intValue()))
            .andExpect(jsonPath("$.fieldName").value(DEFAULT_FIELD_NAME));
    }

    @Test
    @Transactional
    void getNonExistingApplicationsFields() throws Exception {
        // Get the applicationsFields
        restApplicationsFieldsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewApplicationsFields() throws Exception {
        // Initialize the database
        applicationsFieldsRepository.saveAndFlush(applicationsFields);

        int databaseSizeBeforeUpdate = applicationsFieldsRepository.findAll().size();

        // Update the applicationsFields
        ApplicationsFields updatedApplicationsFields = applicationsFieldsRepository.findById(applicationsFields.getId()).get();
        // Disconnect from session so that the updates on updatedApplicationsFields are not directly saved in db
        em.detach(updatedApplicationsFields);
        updatedApplicationsFields.fieldName(UPDATED_FIELD_NAME);

        restApplicationsFieldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedApplicationsFields.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedApplicationsFields))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationsFields in the database
        List<ApplicationsFields> applicationsFieldsList = applicationsFieldsRepository.findAll();
        assertThat(applicationsFieldsList).hasSize(databaseSizeBeforeUpdate);
        ApplicationsFields testApplicationsFields = applicationsFieldsList.get(applicationsFieldsList.size() - 1);
        assertThat(testApplicationsFields.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
    }

    @Test
    @Transactional
    void putNonExistingApplicationsFields() throws Exception {
        int databaseSizeBeforeUpdate = applicationsFieldsRepository.findAll().size();
        applicationsFields.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationsFieldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, applicationsFields.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationsFields))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationsFields in the database
        List<ApplicationsFields> applicationsFieldsList = applicationsFieldsRepository.findAll();
        assertThat(applicationsFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApplicationsFields() throws Exception {
        int databaseSizeBeforeUpdate = applicationsFieldsRepository.findAll().size();
        applicationsFields.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationsFieldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationsFields))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationsFields in the database
        List<ApplicationsFields> applicationsFieldsList = applicationsFieldsRepository.findAll();
        assertThat(applicationsFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApplicationsFields() throws Exception {
        int databaseSizeBeforeUpdate = applicationsFieldsRepository.findAll().size();
        applicationsFields.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationsFieldsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicationsFields))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApplicationsFields in the database
        List<ApplicationsFields> applicationsFieldsList = applicationsFieldsRepository.findAll();
        assertThat(applicationsFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApplicationsFieldsWithPatch() throws Exception {
        // Initialize the database
        applicationsFieldsRepository.saveAndFlush(applicationsFields);

        int databaseSizeBeforeUpdate = applicationsFieldsRepository.findAll().size();

        // Update the applicationsFields using partial update
        ApplicationsFields partialUpdatedApplicationsFields = new ApplicationsFields();
        partialUpdatedApplicationsFields.setId(applicationsFields.getId());

        partialUpdatedApplicationsFields.fieldName(UPDATED_FIELD_NAME);

        restApplicationsFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicationsFields.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplicationsFields))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationsFields in the database
        List<ApplicationsFields> applicationsFieldsList = applicationsFieldsRepository.findAll();
        assertThat(applicationsFieldsList).hasSize(databaseSizeBeforeUpdate);
        ApplicationsFields testApplicationsFields = applicationsFieldsList.get(applicationsFieldsList.size() - 1);
        assertThat(testApplicationsFields.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
    }

    @Test
    @Transactional
    void fullUpdateApplicationsFieldsWithPatch() throws Exception {
        // Initialize the database
        applicationsFieldsRepository.saveAndFlush(applicationsFields);

        int databaseSizeBeforeUpdate = applicationsFieldsRepository.findAll().size();

        // Update the applicationsFields using partial update
        ApplicationsFields partialUpdatedApplicationsFields = new ApplicationsFields();
        partialUpdatedApplicationsFields.setId(applicationsFields.getId());

        partialUpdatedApplicationsFields.fieldName(UPDATED_FIELD_NAME);

        restApplicationsFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicationsFields.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplicationsFields))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationsFields in the database
        List<ApplicationsFields> applicationsFieldsList = applicationsFieldsRepository.findAll();
        assertThat(applicationsFieldsList).hasSize(databaseSizeBeforeUpdate);
        ApplicationsFields testApplicationsFields = applicationsFieldsList.get(applicationsFieldsList.size() - 1);
        assertThat(testApplicationsFields.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingApplicationsFields() throws Exception {
        int databaseSizeBeforeUpdate = applicationsFieldsRepository.findAll().size();
        applicationsFields.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationsFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, applicationsFields.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicationsFields))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationsFields in the database
        List<ApplicationsFields> applicationsFieldsList = applicationsFieldsRepository.findAll();
        assertThat(applicationsFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApplicationsFields() throws Exception {
        int databaseSizeBeforeUpdate = applicationsFieldsRepository.findAll().size();
        applicationsFields.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationsFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicationsFields))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationsFields in the database
        List<ApplicationsFields> applicationsFieldsList = applicationsFieldsRepository.findAll();
        assertThat(applicationsFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApplicationsFields() throws Exception {
        int databaseSizeBeforeUpdate = applicationsFieldsRepository.findAll().size();
        applicationsFields.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationsFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicationsFields))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApplicationsFields in the database
        List<ApplicationsFields> applicationsFieldsList = applicationsFieldsRepository.findAll();
        assertThat(applicationsFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApplicationsFields() throws Exception {
        // Initialize the database
        applicationsFieldsRepository.saveAndFlush(applicationsFields);

        int databaseSizeBeforeDelete = applicationsFieldsRepository.findAll().size();

        // Delete the applicationsFields
        restApplicationsFieldsMockMvc
            .perform(delete(ENTITY_API_URL_ID, applicationsFields.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicationsFields> applicationsFieldsList = applicationsFieldsRepository.findAll();
        assertThat(applicationsFieldsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
