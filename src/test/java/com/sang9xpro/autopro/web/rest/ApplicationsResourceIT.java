package com.sang9xpro.autopro.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sang9xpro.autopro.IntegrationTest;
import com.sang9xpro.autopro.domain.Applications;
import com.sang9xpro.autopro.repository.ApplicationsRepository;
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
 * Integration tests for the {@link ApplicationsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApplicationsResourceIT {

    private static final String DEFAULT_APP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_APP_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_APP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_APP_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/applications";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApplicationsRepository applicationsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicationsMockMvc;

    private Applications applications;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Applications createEntity(EntityManager em) {
        Applications applications = new Applications().appName(DEFAULT_APP_NAME).appCode(DEFAULT_APP_CODE);
        return applications;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Applications createUpdatedEntity(EntityManager em) {
        Applications applications = new Applications().appName(UPDATED_APP_NAME).appCode(UPDATED_APP_CODE);
        return applications;
    }

    @BeforeEach
    public void initTest() {
        applications = createEntity(em);
    }

    @Test
    @Transactional
    void createApplications() throws Exception {
        int databaseSizeBeforeCreate = applicationsRepository.findAll().size();
        // Create the Applications
        restApplicationsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applications)))
            .andExpect(status().isCreated());

        // Validate the Applications in the database
        List<Applications> applicationsList = applicationsRepository.findAll();
        assertThat(applicationsList).hasSize(databaseSizeBeforeCreate + 1);
        Applications testApplications = applicationsList.get(applicationsList.size() - 1);
        assertThat(testApplications.getAppName()).isEqualTo(DEFAULT_APP_NAME);
        assertThat(testApplications.getAppCode()).isEqualTo(DEFAULT_APP_CODE);
    }

    @Test
    @Transactional
    void createApplicationsWithExistingId() throws Exception {
        // Create the Applications with an existing ID
        applications.setId(1L);

        int databaseSizeBeforeCreate = applicationsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applications)))
            .andExpect(status().isBadRequest());

        // Validate the Applications in the database
        List<Applications> applicationsList = applicationsRepository.findAll();
        assertThat(applicationsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllApplications() throws Exception {
        // Initialize the database
        applicationsRepository.saveAndFlush(applications);

        // Get all the applicationsList
        restApplicationsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applications.getId().intValue())))
            .andExpect(jsonPath("$.[*].appName").value(hasItem(DEFAULT_APP_NAME)))
            .andExpect(jsonPath("$.[*].appCode").value(hasItem(DEFAULT_APP_CODE)));
    }

    @Test
    @Transactional
    void getApplications() throws Exception {
        // Initialize the database
        applicationsRepository.saveAndFlush(applications);

        // Get the applications
        restApplicationsMockMvc
            .perform(get(ENTITY_API_URL_ID, applications.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applications.getId().intValue()))
            .andExpect(jsonPath("$.appName").value(DEFAULT_APP_NAME))
            .andExpect(jsonPath("$.appCode").value(DEFAULT_APP_CODE));
    }

    @Test
    @Transactional
    void getNonExistingApplications() throws Exception {
        // Get the applications
        restApplicationsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewApplications() throws Exception {
        // Initialize the database
        applicationsRepository.saveAndFlush(applications);

        int databaseSizeBeforeUpdate = applicationsRepository.findAll().size();

        // Update the applications
        Applications updatedApplications = applicationsRepository.findById(applications.getId()).get();
        // Disconnect from session so that the updates on updatedApplications are not directly saved in db
        em.detach(updatedApplications);
        updatedApplications.appName(UPDATED_APP_NAME).appCode(UPDATED_APP_CODE);

        restApplicationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedApplications.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedApplications))
            )
            .andExpect(status().isOk());

        // Validate the Applications in the database
        List<Applications> applicationsList = applicationsRepository.findAll();
        assertThat(applicationsList).hasSize(databaseSizeBeforeUpdate);
        Applications testApplications = applicationsList.get(applicationsList.size() - 1);
        assertThat(testApplications.getAppName()).isEqualTo(UPDATED_APP_NAME);
        assertThat(testApplications.getAppCode()).isEqualTo(UPDATED_APP_CODE);
    }

    @Test
    @Transactional
    void putNonExistingApplications() throws Exception {
        int databaseSizeBeforeUpdate = applicationsRepository.findAll().size();
        applications.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, applications.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applications))
            )
            .andExpect(status().isBadRequest());

        // Validate the Applications in the database
        List<Applications> applicationsList = applicationsRepository.findAll();
        assertThat(applicationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApplications() throws Exception {
        int databaseSizeBeforeUpdate = applicationsRepository.findAll().size();
        applications.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applications))
            )
            .andExpect(status().isBadRequest());

        // Validate the Applications in the database
        List<Applications> applicationsList = applicationsRepository.findAll();
        assertThat(applicationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApplications() throws Exception {
        int databaseSizeBeforeUpdate = applicationsRepository.findAll().size();
        applications.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applications)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Applications in the database
        List<Applications> applicationsList = applicationsRepository.findAll();
        assertThat(applicationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApplicationsWithPatch() throws Exception {
        // Initialize the database
        applicationsRepository.saveAndFlush(applications);

        int databaseSizeBeforeUpdate = applicationsRepository.findAll().size();

        // Update the applications using partial update
        Applications partialUpdatedApplications = new Applications();
        partialUpdatedApplications.setId(applications.getId());

        restApplicationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplications.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplications))
            )
            .andExpect(status().isOk());

        // Validate the Applications in the database
        List<Applications> applicationsList = applicationsRepository.findAll();
        assertThat(applicationsList).hasSize(databaseSizeBeforeUpdate);
        Applications testApplications = applicationsList.get(applicationsList.size() - 1);
        assertThat(testApplications.getAppName()).isEqualTo(DEFAULT_APP_NAME);
        assertThat(testApplications.getAppCode()).isEqualTo(DEFAULT_APP_CODE);
    }

    @Test
    @Transactional
    void fullUpdateApplicationsWithPatch() throws Exception {
        // Initialize the database
        applicationsRepository.saveAndFlush(applications);

        int databaseSizeBeforeUpdate = applicationsRepository.findAll().size();

        // Update the applications using partial update
        Applications partialUpdatedApplications = new Applications();
        partialUpdatedApplications.setId(applications.getId());

        partialUpdatedApplications.appName(UPDATED_APP_NAME).appCode(UPDATED_APP_CODE);

        restApplicationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplications.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplications))
            )
            .andExpect(status().isOk());

        // Validate the Applications in the database
        List<Applications> applicationsList = applicationsRepository.findAll();
        assertThat(applicationsList).hasSize(databaseSizeBeforeUpdate);
        Applications testApplications = applicationsList.get(applicationsList.size() - 1);
        assertThat(testApplications.getAppName()).isEqualTo(UPDATED_APP_NAME);
        assertThat(testApplications.getAppCode()).isEqualTo(UPDATED_APP_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingApplications() throws Exception {
        int databaseSizeBeforeUpdate = applicationsRepository.findAll().size();
        applications.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, applications.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applications))
            )
            .andExpect(status().isBadRequest());

        // Validate the Applications in the database
        List<Applications> applicationsList = applicationsRepository.findAll();
        assertThat(applicationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApplications() throws Exception {
        int databaseSizeBeforeUpdate = applicationsRepository.findAll().size();
        applications.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applications))
            )
            .andExpect(status().isBadRequest());

        // Validate the Applications in the database
        List<Applications> applicationsList = applicationsRepository.findAll();
        assertThat(applicationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApplications() throws Exception {
        int databaseSizeBeforeUpdate = applicationsRepository.findAll().size();
        applications.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(applications))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Applications in the database
        List<Applications> applicationsList = applicationsRepository.findAll();
        assertThat(applicationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApplications() throws Exception {
        // Initialize the database
        applicationsRepository.saveAndFlush(applications);

        int databaseSizeBeforeDelete = applicationsRepository.findAll().size();

        // Delete the applications
        restApplicationsMockMvc
            .perform(delete(ENTITY_API_URL_ID, applications.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Applications> applicationsList = applicationsRepository.findAll();
        assertThat(applicationsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
