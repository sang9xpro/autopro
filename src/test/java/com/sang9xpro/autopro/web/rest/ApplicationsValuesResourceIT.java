package com.sang9xpro.autopro.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sang9xpro.autopro.IntegrationTest;
import com.sang9xpro.autopro.domain.ApplicationsValues;
import com.sang9xpro.autopro.repository.ApplicationsValuesRepository;
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
 * Integration tests for the {@link ApplicationsValuesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApplicationsValuesResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/applications-values";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApplicationsValuesRepository applicationsValuesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicationsValuesMockMvc;

    private ApplicationsValues applicationsValues;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationsValues createEntity(EntityManager em) {
        ApplicationsValues applicationsValues = new ApplicationsValues().value(DEFAULT_VALUE);
        return applicationsValues;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationsValues createUpdatedEntity(EntityManager em) {
        ApplicationsValues applicationsValues = new ApplicationsValues().value(UPDATED_VALUE);
        return applicationsValues;
    }

    @BeforeEach
    public void initTest() {
        applicationsValues = createEntity(em);
    }

    @Test
    @Transactional
    void createApplicationsValues() throws Exception {
        int databaseSizeBeforeCreate = applicationsValuesRepository.findAll().size();
        // Create the ApplicationsValues
        restApplicationsValuesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicationsValues))
            )
            .andExpect(status().isCreated());

        // Validate the ApplicationsValues in the database
        List<ApplicationsValues> applicationsValuesList = applicationsValuesRepository.findAll();
        assertThat(applicationsValuesList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationsValues testApplicationsValues = applicationsValuesList.get(applicationsValuesList.size() - 1);
        assertThat(testApplicationsValues.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void createApplicationsValuesWithExistingId() throws Exception {
        // Create the ApplicationsValues with an existing ID
        applicationsValues.setId(1L);

        int databaseSizeBeforeCreate = applicationsValuesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationsValuesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicationsValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationsValues in the database
        List<ApplicationsValues> applicationsValuesList = applicationsValuesRepository.findAll();
        assertThat(applicationsValuesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllApplicationsValues() throws Exception {
        // Initialize the database
        applicationsValuesRepository.saveAndFlush(applicationsValues);

        // Get all the applicationsValuesList
        restApplicationsValuesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationsValues.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    void getApplicationsValues() throws Exception {
        // Initialize the database
        applicationsValuesRepository.saveAndFlush(applicationsValues);

        // Get the applicationsValues
        restApplicationsValuesMockMvc
            .perform(get(ENTITY_API_URL_ID, applicationsValues.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applicationsValues.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingApplicationsValues() throws Exception {
        // Get the applicationsValues
        restApplicationsValuesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewApplicationsValues() throws Exception {
        // Initialize the database
        applicationsValuesRepository.saveAndFlush(applicationsValues);

        int databaseSizeBeforeUpdate = applicationsValuesRepository.findAll().size();

        // Update the applicationsValues
        ApplicationsValues updatedApplicationsValues = applicationsValuesRepository.findById(applicationsValues.getId()).get();
        // Disconnect from session so that the updates on updatedApplicationsValues are not directly saved in db
        em.detach(updatedApplicationsValues);
        updatedApplicationsValues.value(UPDATED_VALUE);

        restApplicationsValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedApplicationsValues.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedApplicationsValues))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationsValues in the database
        List<ApplicationsValues> applicationsValuesList = applicationsValuesRepository.findAll();
        assertThat(applicationsValuesList).hasSize(databaseSizeBeforeUpdate);
        ApplicationsValues testApplicationsValues = applicationsValuesList.get(applicationsValuesList.size() - 1);
        assertThat(testApplicationsValues.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingApplicationsValues() throws Exception {
        int databaseSizeBeforeUpdate = applicationsValuesRepository.findAll().size();
        applicationsValues.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationsValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, applicationsValues.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationsValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationsValues in the database
        List<ApplicationsValues> applicationsValuesList = applicationsValuesRepository.findAll();
        assertThat(applicationsValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApplicationsValues() throws Exception {
        int databaseSizeBeforeUpdate = applicationsValuesRepository.findAll().size();
        applicationsValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationsValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationsValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationsValues in the database
        List<ApplicationsValues> applicationsValuesList = applicationsValuesRepository.findAll();
        assertThat(applicationsValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApplicationsValues() throws Exception {
        int databaseSizeBeforeUpdate = applicationsValuesRepository.findAll().size();
        applicationsValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationsValuesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicationsValues))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApplicationsValues in the database
        List<ApplicationsValues> applicationsValuesList = applicationsValuesRepository.findAll();
        assertThat(applicationsValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApplicationsValuesWithPatch() throws Exception {
        // Initialize the database
        applicationsValuesRepository.saveAndFlush(applicationsValues);

        int databaseSizeBeforeUpdate = applicationsValuesRepository.findAll().size();

        // Update the applicationsValues using partial update
        ApplicationsValues partialUpdatedApplicationsValues = new ApplicationsValues();
        partialUpdatedApplicationsValues.setId(applicationsValues.getId());

        partialUpdatedApplicationsValues.value(UPDATED_VALUE);

        restApplicationsValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicationsValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplicationsValues))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationsValues in the database
        List<ApplicationsValues> applicationsValuesList = applicationsValuesRepository.findAll();
        assertThat(applicationsValuesList).hasSize(databaseSizeBeforeUpdate);
        ApplicationsValues testApplicationsValues = applicationsValuesList.get(applicationsValuesList.size() - 1);
        assertThat(testApplicationsValues.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateApplicationsValuesWithPatch() throws Exception {
        // Initialize the database
        applicationsValuesRepository.saveAndFlush(applicationsValues);

        int databaseSizeBeforeUpdate = applicationsValuesRepository.findAll().size();

        // Update the applicationsValues using partial update
        ApplicationsValues partialUpdatedApplicationsValues = new ApplicationsValues();
        partialUpdatedApplicationsValues.setId(applicationsValues.getId());

        partialUpdatedApplicationsValues.value(UPDATED_VALUE);

        restApplicationsValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicationsValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplicationsValues))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationsValues in the database
        List<ApplicationsValues> applicationsValuesList = applicationsValuesRepository.findAll();
        assertThat(applicationsValuesList).hasSize(databaseSizeBeforeUpdate);
        ApplicationsValues testApplicationsValues = applicationsValuesList.get(applicationsValuesList.size() - 1);
        assertThat(testApplicationsValues.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingApplicationsValues() throws Exception {
        int databaseSizeBeforeUpdate = applicationsValuesRepository.findAll().size();
        applicationsValues.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationsValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, applicationsValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicationsValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationsValues in the database
        List<ApplicationsValues> applicationsValuesList = applicationsValuesRepository.findAll();
        assertThat(applicationsValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApplicationsValues() throws Exception {
        int databaseSizeBeforeUpdate = applicationsValuesRepository.findAll().size();
        applicationsValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationsValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicationsValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationsValues in the database
        List<ApplicationsValues> applicationsValuesList = applicationsValuesRepository.findAll();
        assertThat(applicationsValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApplicationsValues() throws Exception {
        int databaseSizeBeforeUpdate = applicationsValuesRepository.findAll().size();
        applicationsValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationsValuesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicationsValues))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApplicationsValues in the database
        List<ApplicationsValues> applicationsValuesList = applicationsValuesRepository.findAll();
        assertThat(applicationsValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApplicationsValues() throws Exception {
        // Initialize the database
        applicationsValuesRepository.saveAndFlush(applicationsValues);

        int databaseSizeBeforeDelete = applicationsValuesRepository.findAll().size();

        // Delete the applicationsValues
        restApplicationsValuesMockMvc
            .perform(delete(ENTITY_API_URL_ID, applicationsValues.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicationsValues> applicationsValuesList = applicationsValuesRepository.findAll();
        assertThat(applicationsValuesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
