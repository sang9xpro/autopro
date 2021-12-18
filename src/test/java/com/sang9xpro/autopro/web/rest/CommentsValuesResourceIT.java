package com.sang9xpro.autopro.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sang9xpro.autopro.IntegrationTest;
import com.sang9xpro.autopro.domain.CommentsValues;
import com.sang9xpro.autopro.repository.CommentsValuesRepository;
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
 * Integration tests for the {@link CommentsValuesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CommentsValuesResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/comments-values";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CommentsValuesRepository commentsValuesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommentsValuesMockMvc;

    private CommentsValues commentsValues;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommentsValues createEntity(EntityManager em) {
        CommentsValues commentsValues = new CommentsValues().value(DEFAULT_VALUE);
        return commentsValues;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommentsValues createUpdatedEntity(EntityManager em) {
        CommentsValues commentsValues = new CommentsValues().value(UPDATED_VALUE);
        return commentsValues;
    }

    @BeforeEach
    public void initTest() {
        commentsValues = createEntity(em);
    }

    @Test
    @Transactional
    void createCommentsValues() throws Exception {
        int databaseSizeBeforeCreate = commentsValuesRepository.findAll().size();
        // Create the CommentsValues
        restCommentsValuesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commentsValues))
            )
            .andExpect(status().isCreated());

        // Validate the CommentsValues in the database
        List<CommentsValues> commentsValuesList = commentsValuesRepository.findAll();
        assertThat(commentsValuesList).hasSize(databaseSizeBeforeCreate + 1);
        CommentsValues testCommentsValues = commentsValuesList.get(commentsValuesList.size() - 1);
        assertThat(testCommentsValues.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void createCommentsValuesWithExistingId() throws Exception {
        // Create the CommentsValues with an existing ID
        commentsValues.setId(1L);

        int databaseSizeBeforeCreate = commentsValuesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommentsValuesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commentsValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommentsValues in the database
        List<CommentsValues> commentsValuesList = commentsValuesRepository.findAll();
        assertThat(commentsValuesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCommentsValues() throws Exception {
        // Initialize the database
        commentsValuesRepository.saveAndFlush(commentsValues);

        // Get all the commentsValuesList
        restCommentsValuesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commentsValues.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    void getCommentsValues() throws Exception {
        // Initialize the database
        commentsValuesRepository.saveAndFlush(commentsValues);

        // Get the commentsValues
        restCommentsValuesMockMvc
            .perform(get(ENTITY_API_URL_ID, commentsValues.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commentsValues.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingCommentsValues() throws Exception {
        // Get the commentsValues
        restCommentsValuesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCommentsValues() throws Exception {
        // Initialize the database
        commentsValuesRepository.saveAndFlush(commentsValues);

        int databaseSizeBeforeUpdate = commentsValuesRepository.findAll().size();

        // Update the commentsValues
        CommentsValues updatedCommentsValues = commentsValuesRepository.findById(commentsValues.getId()).get();
        // Disconnect from session so that the updates on updatedCommentsValues are not directly saved in db
        em.detach(updatedCommentsValues);
        updatedCommentsValues.value(UPDATED_VALUE);

        restCommentsValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCommentsValues.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCommentsValues))
            )
            .andExpect(status().isOk());

        // Validate the CommentsValues in the database
        List<CommentsValues> commentsValuesList = commentsValuesRepository.findAll();
        assertThat(commentsValuesList).hasSize(databaseSizeBeforeUpdate);
        CommentsValues testCommentsValues = commentsValuesList.get(commentsValuesList.size() - 1);
        assertThat(testCommentsValues.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingCommentsValues() throws Exception {
        int databaseSizeBeforeUpdate = commentsValuesRepository.findAll().size();
        commentsValues.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommentsValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, commentsValues.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commentsValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommentsValues in the database
        List<CommentsValues> commentsValuesList = commentsValuesRepository.findAll();
        assertThat(commentsValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCommentsValues() throws Exception {
        int databaseSizeBeforeUpdate = commentsValuesRepository.findAll().size();
        commentsValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommentsValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commentsValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommentsValues in the database
        List<CommentsValues> commentsValuesList = commentsValuesRepository.findAll();
        assertThat(commentsValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCommentsValues() throws Exception {
        int databaseSizeBeforeUpdate = commentsValuesRepository.findAll().size();
        commentsValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommentsValuesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commentsValues)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CommentsValues in the database
        List<CommentsValues> commentsValuesList = commentsValuesRepository.findAll();
        assertThat(commentsValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCommentsValuesWithPatch() throws Exception {
        // Initialize the database
        commentsValuesRepository.saveAndFlush(commentsValues);

        int databaseSizeBeforeUpdate = commentsValuesRepository.findAll().size();

        // Update the commentsValues using partial update
        CommentsValues partialUpdatedCommentsValues = new CommentsValues();
        partialUpdatedCommentsValues.setId(commentsValues.getId());

        restCommentsValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommentsValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCommentsValues))
            )
            .andExpect(status().isOk());

        // Validate the CommentsValues in the database
        List<CommentsValues> commentsValuesList = commentsValuesRepository.findAll();
        assertThat(commentsValuesList).hasSize(databaseSizeBeforeUpdate);
        CommentsValues testCommentsValues = commentsValuesList.get(commentsValuesList.size() - 1);
        assertThat(testCommentsValues.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateCommentsValuesWithPatch() throws Exception {
        // Initialize the database
        commentsValuesRepository.saveAndFlush(commentsValues);

        int databaseSizeBeforeUpdate = commentsValuesRepository.findAll().size();

        // Update the commentsValues using partial update
        CommentsValues partialUpdatedCommentsValues = new CommentsValues();
        partialUpdatedCommentsValues.setId(commentsValues.getId());

        partialUpdatedCommentsValues.value(UPDATED_VALUE);

        restCommentsValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommentsValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCommentsValues))
            )
            .andExpect(status().isOk());

        // Validate the CommentsValues in the database
        List<CommentsValues> commentsValuesList = commentsValuesRepository.findAll();
        assertThat(commentsValuesList).hasSize(databaseSizeBeforeUpdate);
        CommentsValues testCommentsValues = commentsValuesList.get(commentsValuesList.size() - 1);
        assertThat(testCommentsValues.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingCommentsValues() throws Exception {
        int databaseSizeBeforeUpdate = commentsValuesRepository.findAll().size();
        commentsValues.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommentsValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, commentsValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(commentsValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommentsValues in the database
        List<CommentsValues> commentsValuesList = commentsValuesRepository.findAll();
        assertThat(commentsValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCommentsValues() throws Exception {
        int databaseSizeBeforeUpdate = commentsValuesRepository.findAll().size();
        commentsValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommentsValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(commentsValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommentsValues in the database
        List<CommentsValues> commentsValuesList = commentsValuesRepository.findAll();
        assertThat(commentsValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCommentsValues() throws Exception {
        int databaseSizeBeforeUpdate = commentsValuesRepository.findAll().size();
        commentsValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommentsValuesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(commentsValues))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CommentsValues in the database
        List<CommentsValues> commentsValuesList = commentsValuesRepository.findAll();
        assertThat(commentsValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCommentsValues() throws Exception {
        // Initialize the database
        commentsValuesRepository.saveAndFlush(commentsValues);

        int databaseSizeBeforeDelete = commentsValuesRepository.findAll().size();

        // Delete the commentsValues
        restCommentsValuesMockMvc
            .perform(delete(ENTITY_API_URL_ID, commentsValues.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CommentsValues> commentsValuesList = commentsValuesRepository.findAll();
        assertThat(commentsValuesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
