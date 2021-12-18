package com.sang9xpro.autopro.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sang9xpro.autopro.IntegrationTest;
import com.sang9xpro.autopro.domain.FacebookValues;
import com.sang9xpro.autopro.repository.FacebookValuesRepository;
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
 * Integration tests for the {@link FacebookValuesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FacebookValuesResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/facebook-values";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FacebookValuesRepository facebookValuesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFacebookValuesMockMvc;

    private FacebookValues facebookValues;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FacebookValues createEntity(EntityManager em) {
        FacebookValues facebookValues = new FacebookValues().value(DEFAULT_VALUE);
        return facebookValues;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FacebookValues createUpdatedEntity(EntityManager em) {
        FacebookValues facebookValues = new FacebookValues().value(UPDATED_VALUE);
        return facebookValues;
    }

    @BeforeEach
    public void initTest() {
        facebookValues = createEntity(em);
    }

    @Test
    @Transactional
    void createFacebookValues() throws Exception {
        int databaseSizeBeforeCreate = facebookValuesRepository.findAll().size();
        // Create the FacebookValues
        restFacebookValuesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(facebookValues))
            )
            .andExpect(status().isCreated());

        // Validate the FacebookValues in the database
        List<FacebookValues> facebookValuesList = facebookValuesRepository.findAll();
        assertThat(facebookValuesList).hasSize(databaseSizeBeforeCreate + 1);
        FacebookValues testFacebookValues = facebookValuesList.get(facebookValuesList.size() - 1);
        assertThat(testFacebookValues.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void createFacebookValuesWithExistingId() throws Exception {
        // Create the FacebookValues with an existing ID
        facebookValues.setId(1L);

        int databaseSizeBeforeCreate = facebookValuesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacebookValuesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(facebookValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the FacebookValues in the database
        List<FacebookValues> facebookValuesList = facebookValuesRepository.findAll();
        assertThat(facebookValuesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFacebookValues() throws Exception {
        // Initialize the database
        facebookValuesRepository.saveAndFlush(facebookValues);

        // Get all the facebookValuesList
        restFacebookValuesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facebookValues.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    void getFacebookValues() throws Exception {
        // Initialize the database
        facebookValuesRepository.saveAndFlush(facebookValues);

        // Get the facebookValues
        restFacebookValuesMockMvc
            .perform(get(ENTITY_API_URL_ID, facebookValues.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(facebookValues.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingFacebookValues() throws Exception {
        // Get the facebookValues
        restFacebookValuesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFacebookValues() throws Exception {
        // Initialize the database
        facebookValuesRepository.saveAndFlush(facebookValues);

        int databaseSizeBeforeUpdate = facebookValuesRepository.findAll().size();

        // Update the facebookValues
        FacebookValues updatedFacebookValues = facebookValuesRepository.findById(facebookValues.getId()).get();
        // Disconnect from session so that the updates on updatedFacebookValues are not directly saved in db
        em.detach(updatedFacebookValues);
        updatedFacebookValues.value(UPDATED_VALUE);

        restFacebookValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFacebookValues.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFacebookValues))
            )
            .andExpect(status().isOk());

        // Validate the FacebookValues in the database
        List<FacebookValues> facebookValuesList = facebookValuesRepository.findAll();
        assertThat(facebookValuesList).hasSize(databaseSizeBeforeUpdate);
        FacebookValues testFacebookValues = facebookValuesList.get(facebookValuesList.size() - 1);
        assertThat(testFacebookValues.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingFacebookValues() throws Exception {
        int databaseSizeBeforeUpdate = facebookValuesRepository.findAll().size();
        facebookValues.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFacebookValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, facebookValues.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(facebookValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the FacebookValues in the database
        List<FacebookValues> facebookValuesList = facebookValuesRepository.findAll();
        assertThat(facebookValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFacebookValues() throws Exception {
        int databaseSizeBeforeUpdate = facebookValuesRepository.findAll().size();
        facebookValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFacebookValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(facebookValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the FacebookValues in the database
        List<FacebookValues> facebookValuesList = facebookValuesRepository.findAll();
        assertThat(facebookValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFacebookValues() throws Exception {
        int databaseSizeBeforeUpdate = facebookValuesRepository.findAll().size();
        facebookValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFacebookValuesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(facebookValues)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FacebookValues in the database
        List<FacebookValues> facebookValuesList = facebookValuesRepository.findAll();
        assertThat(facebookValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFacebookValuesWithPatch() throws Exception {
        // Initialize the database
        facebookValuesRepository.saveAndFlush(facebookValues);

        int databaseSizeBeforeUpdate = facebookValuesRepository.findAll().size();

        // Update the facebookValues using partial update
        FacebookValues partialUpdatedFacebookValues = new FacebookValues();
        partialUpdatedFacebookValues.setId(facebookValues.getId());

        restFacebookValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFacebookValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFacebookValues))
            )
            .andExpect(status().isOk());

        // Validate the FacebookValues in the database
        List<FacebookValues> facebookValuesList = facebookValuesRepository.findAll();
        assertThat(facebookValuesList).hasSize(databaseSizeBeforeUpdate);
        FacebookValues testFacebookValues = facebookValuesList.get(facebookValuesList.size() - 1);
        assertThat(testFacebookValues.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateFacebookValuesWithPatch() throws Exception {
        // Initialize the database
        facebookValuesRepository.saveAndFlush(facebookValues);

        int databaseSizeBeforeUpdate = facebookValuesRepository.findAll().size();

        // Update the facebookValues using partial update
        FacebookValues partialUpdatedFacebookValues = new FacebookValues();
        partialUpdatedFacebookValues.setId(facebookValues.getId());

        partialUpdatedFacebookValues.value(UPDATED_VALUE);

        restFacebookValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFacebookValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFacebookValues))
            )
            .andExpect(status().isOk());

        // Validate the FacebookValues in the database
        List<FacebookValues> facebookValuesList = facebookValuesRepository.findAll();
        assertThat(facebookValuesList).hasSize(databaseSizeBeforeUpdate);
        FacebookValues testFacebookValues = facebookValuesList.get(facebookValuesList.size() - 1);
        assertThat(testFacebookValues.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingFacebookValues() throws Exception {
        int databaseSizeBeforeUpdate = facebookValuesRepository.findAll().size();
        facebookValues.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFacebookValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, facebookValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(facebookValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the FacebookValues in the database
        List<FacebookValues> facebookValuesList = facebookValuesRepository.findAll();
        assertThat(facebookValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFacebookValues() throws Exception {
        int databaseSizeBeforeUpdate = facebookValuesRepository.findAll().size();
        facebookValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFacebookValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(facebookValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the FacebookValues in the database
        List<FacebookValues> facebookValuesList = facebookValuesRepository.findAll();
        assertThat(facebookValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFacebookValues() throws Exception {
        int databaseSizeBeforeUpdate = facebookValuesRepository.findAll().size();
        facebookValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFacebookValuesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(facebookValues))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FacebookValues in the database
        List<FacebookValues> facebookValuesList = facebookValuesRepository.findAll();
        assertThat(facebookValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFacebookValues() throws Exception {
        // Initialize the database
        facebookValuesRepository.saveAndFlush(facebookValues);

        int databaseSizeBeforeDelete = facebookValuesRepository.findAll().size();

        // Delete the facebookValues
        restFacebookValuesMockMvc
            .perform(delete(ENTITY_API_URL_ID, facebookValues.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FacebookValues> facebookValuesList = facebookValuesRepository.findAll();
        assertThat(facebookValuesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
