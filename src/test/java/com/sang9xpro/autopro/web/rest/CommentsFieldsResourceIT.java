package com.sang9xpro.autopro.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sang9xpro.autopro.IntegrationTest;
import com.sang9xpro.autopro.domain.CommentsFields;
import com.sang9xpro.autopro.repository.CommentsFieldsRepository;
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
 * Integration tests for the {@link CommentsFieldsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CommentsFieldsResourceIT {

    private static final String DEFAULT_FIELD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/comments-fields";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CommentsFieldsRepository commentsFieldsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommentsFieldsMockMvc;

    private CommentsFields commentsFields;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommentsFields createEntity(EntityManager em) {
        CommentsFields commentsFields = new CommentsFields().fieldName(DEFAULT_FIELD_NAME);
        return commentsFields;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommentsFields createUpdatedEntity(EntityManager em) {
        CommentsFields commentsFields = new CommentsFields().fieldName(UPDATED_FIELD_NAME);
        return commentsFields;
    }

    @BeforeEach
    public void initTest() {
        commentsFields = createEntity(em);
    }

    @Test
    @Transactional
    void createCommentsFields() throws Exception {
        int databaseSizeBeforeCreate = commentsFieldsRepository.findAll().size();
        // Create the CommentsFields
        restCommentsFieldsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commentsFields))
            )
            .andExpect(status().isCreated());

        // Validate the CommentsFields in the database
        List<CommentsFields> commentsFieldsList = commentsFieldsRepository.findAll();
        assertThat(commentsFieldsList).hasSize(databaseSizeBeforeCreate + 1);
        CommentsFields testCommentsFields = commentsFieldsList.get(commentsFieldsList.size() - 1);
        assertThat(testCommentsFields.getFieldName()).isEqualTo(DEFAULT_FIELD_NAME);
    }

    @Test
    @Transactional
    void createCommentsFieldsWithExistingId() throws Exception {
        // Create the CommentsFields with an existing ID
        commentsFields.setId(1L);

        int databaseSizeBeforeCreate = commentsFieldsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommentsFieldsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commentsFields))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommentsFields in the database
        List<CommentsFields> commentsFieldsList = commentsFieldsRepository.findAll();
        assertThat(commentsFieldsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCommentsFields() throws Exception {
        // Initialize the database
        commentsFieldsRepository.saveAndFlush(commentsFields);

        // Get all the commentsFieldsList
        restCommentsFieldsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commentsFields.getId().intValue())))
            .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME)));
    }

    @Test
    @Transactional
    void getCommentsFields() throws Exception {
        // Initialize the database
        commentsFieldsRepository.saveAndFlush(commentsFields);

        // Get the commentsFields
        restCommentsFieldsMockMvc
            .perform(get(ENTITY_API_URL_ID, commentsFields.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commentsFields.getId().intValue()))
            .andExpect(jsonPath("$.fieldName").value(DEFAULT_FIELD_NAME));
    }

    @Test
    @Transactional
    void getNonExistingCommentsFields() throws Exception {
        // Get the commentsFields
        restCommentsFieldsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCommentsFields() throws Exception {
        // Initialize the database
        commentsFieldsRepository.saveAndFlush(commentsFields);

        int databaseSizeBeforeUpdate = commentsFieldsRepository.findAll().size();

        // Update the commentsFields
        CommentsFields updatedCommentsFields = commentsFieldsRepository.findById(commentsFields.getId()).get();
        // Disconnect from session so that the updates on updatedCommentsFields are not directly saved in db
        em.detach(updatedCommentsFields);
        updatedCommentsFields.fieldName(UPDATED_FIELD_NAME);

        restCommentsFieldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCommentsFields.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCommentsFields))
            )
            .andExpect(status().isOk());

        // Validate the CommentsFields in the database
        List<CommentsFields> commentsFieldsList = commentsFieldsRepository.findAll();
        assertThat(commentsFieldsList).hasSize(databaseSizeBeforeUpdate);
        CommentsFields testCommentsFields = commentsFieldsList.get(commentsFieldsList.size() - 1);
        assertThat(testCommentsFields.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
    }

    @Test
    @Transactional
    void putNonExistingCommentsFields() throws Exception {
        int databaseSizeBeforeUpdate = commentsFieldsRepository.findAll().size();
        commentsFields.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommentsFieldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, commentsFields.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commentsFields))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommentsFields in the database
        List<CommentsFields> commentsFieldsList = commentsFieldsRepository.findAll();
        assertThat(commentsFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCommentsFields() throws Exception {
        int databaseSizeBeforeUpdate = commentsFieldsRepository.findAll().size();
        commentsFields.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommentsFieldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commentsFields))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommentsFields in the database
        List<CommentsFields> commentsFieldsList = commentsFieldsRepository.findAll();
        assertThat(commentsFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCommentsFields() throws Exception {
        int databaseSizeBeforeUpdate = commentsFieldsRepository.findAll().size();
        commentsFields.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommentsFieldsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commentsFields)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CommentsFields in the database
        List<CommentsFields> commentsFieldsList = commentsFieldsRepository.findAll();
        assertThat(commentsFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCommentsFieldsWithPatch() throws Exception {
        // Initialize the database
        commentsFieldsRepository.saveAndFlush(commentsFields);

        int databaseSizeBeforeUpdate = commentsFieldsRepository.findAll().size();

        // Update the commentsFields using partial update
        CommentsFields partialUpdatedCommentsFields = new CommentsFields();
        partialUpdatedCommentsFields.setId(commentsFields.getId());

        partialUpdatedCommentsFields.fieldName(UPDATED_FIELD_NAME);

        restCommentsFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommentsFields.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCommentsFields))
            )
            .andExpect(status().isOk());

        // Validate the CommentsFields in the database
        List<CommentsFields> commentsFieldsList = commentsFieldsRepository.findAll();
        assertThat(commentsFieldsList).hasSize(databaseSizeBeforeUpdate);
        CommentsFields testCommentsFields = commentsFieldsList.get(commentsFieldsList.size() - 1);
        assertThat(testCommentsFields.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
    }

    @Test
    @Transactional
    void fullUpdateCommentsFieldsWithPatch() throws Exception {
        // Initialize the database
        commentsFieldsRepository.saveAndFlush(commentsFields);

        int databaseSizeBeforeUpdate = commentsFieldsRepository.findAll().size();

        // Update the commentsFields using partial update
        CommentsFields partialUpdatedCommentsFields = new CommentsFields();
        partialUpdatedCommentsFields.setId(commentsFields.getId());

        partialUpdatedCommentsFields.fieldName(UPDATED_FIELD_NAME);

        restCommentsFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommentsFields.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCommentsFields))
            )
            .andExpect(status().isOk());

        // Validate the CommentsFields in the database
        List<CommentsFields> commentsFieldsList = commentsFieldsRepository.findAll();
        assertThat(commentsFieldsList).hasSize(databaseSizeBeforeUpdate);
        CommentsFields testCommentsFields = commentsFieldsList.get(commentsFieldsList.size() - 1);
        assertThat(testCommentsFields.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingCommentsFields() throws Exception {
        int databaseSizeBeforeUpdate = commentsFieldsRepository.findAll().size();
        commentsFields.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommentsFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, commentsFields.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(commentsFields))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommentsFields in the database
        List<CommentsFields> commentsFieldsList = commentsFieldsRepository.findAll();
        assertThat(commentsFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCommentsFields() throws Exception {
        int databaseSizeBeforeUpdate = commentsFieldsRepository.findAll().size();
        commentsFields.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommentsFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(commentsFields))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommentsFields in the database
        List<CommentsFields> commentsFieldsList = commentsFieldsRepository.findAll();
        assertThat(commentsFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCommentsFields() throws Exception {
        int databaseSizeBeforeUpdate = commentsFieldsRepository.findAll().size();
        commentsFields.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommentsFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(commentsFields))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CommentsFields in the database
        List<CommentsFields> commentsFieldsList = commentsFieldsRepository.findAll();
        assertThat(commentsFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCommentsFields() throws Exception {
        // Initialize the database
        commentsFieldsRepository.saveAndFlush(commentsFields);

        int databaseSizeBeforeDelete = commentsFieldsRepository.findAll().size();

        // Delete the commentsFields
        restCommentsFieldsMockMvc
            .perform(delete(ENTITY_API_URL_ID, commentsFields.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CommentsFields> commentsFieldsList = commentsFieldsRepository.findAll();
        assertThat(commentsFieldsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
