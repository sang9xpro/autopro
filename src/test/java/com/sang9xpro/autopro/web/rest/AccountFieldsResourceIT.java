package com.sang9xpro.autopro.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sang9xpro.autopro.IntegrationTest;
import com.sang9xpro.autopro.domain.AccountFields;
import com.sang9xpro.autopro.repository.AccountFieldsRepository;
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
 * Integration tests for the {@link AccountFieldsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AccountFieldsResourceIT {

    private static final String DEFAULT_FIELD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/account-fields";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AccountFieldsRepository accountFieldsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccountFieldsMockMvc;

    private AccountFields accountFields;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountFields createEntity(EntityManager em) {
        AccountFields accountFields = new AccountFields().fieldName(DEFAULT_FIELD_NAME);
        return accountFields;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountFields createUpdatedEntity(EntityManager em) {
        AccountFields accountFields = new AccountFields().fieldName(UPDATED_FIELD_NAME);
        return accountFields;
    }

    @BeforeEach
    public void initTest() {
        accountFields = createEntity(em);
    }

    @Test
    @Transactional
    void createAccountFields() throws Exception {
        int databaseSizeBeforeCreate = accountFieldsRepository.findAll().size();
        // Create the AccountFields
        restAccountFieldsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountFields)))
            .andExpect(status().isCreated());

        // Validate the AccountFields in the database
        List<AccountFields> accountFieldsList = accountFieldsRepository.findAll();
        assertThat(accountFieldsList).hasSize(databaseSizeBeforeCreate + 1);
        AccountFields testAccountFields = accountFieldsList.get(accountFieldsList.size() - 1);
        assertThat(testAccountFields.getFieldName()).isEqualTo(DEFAULT_FIELD_NAME);
    }

    @Test
    @Transactional
    void createAccountFieldsWithExistingId() throws Exception {
        // Create the AccountFields with an existing ID
        accountFields.setId(1L);

        int databaseSizeBeforeCreate = accountFieldsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountFieldsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountFields)))
            .andExpect(status().isBadRequest());

        // Validate the AccountFields in the database
        List<AccountFields> accountFieldsList = accountFieldsRepository.findAll();
        assertThat(accountFieldsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAccountFields() throws Exception {
        // Initialize the database
        accountFieldsRepository.saveAndFlush(accountFields);

        // Get all the accountFieldsList
        restAccountFieldsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountFields.getId().intValue())))
            .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME)));
    }

    @Test
    @Transactional
    void getAccountFields() throws Exception {
        // Initialize the database
        accountFieldsRepository.saveAndFlush(accountFields);

        // Get the accountFields
        restAccountFieldsMockMvc
            .perform(get(ENTITY_API_URL_ID, accountFields.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accountFields.getId().intValue()))
            .andExpect(jsonPath("$.fieldName").value(DEFAULT_FIELD_NAME));
    }

    @Test
    @Transactional
    void getNonExistingAccountFields() throws Exception {
        // Get the accountFields
        restAccountFieldsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAccountFields() throws Exception {
        // Initialize the database
        accountFieldsRepository.saveAndFlush(accountFields);

        int databaseSizeBeforeUpdate = accountFieldsRepository.findAll().size();

        // Update the accountFields
        AccountFields updatedAccountFields = accountFieldsRepository.findById(accountFields.getId()).get();
        // Disconnect from session so that the updates on updatedAccountFields are not directly saved in db
        em.detach(updatedAccountFields);
        updatedAccountFields.fieldName(UPDATED_FIELD_NAME);

        restAccountFieldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAccountFields.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAccountFields))
            )
            .andExpect(status().isOk());

        // Validate the AccountFields in the database
        List<AccountFields> accountFieldsList = accountFieldsRepository.findAll();
        assertThat(accountFieldsList).hasSize(databaseSizeBeforeUpdate);
        AccountFields testAccountFields = accountFieldsList.get(accountFieldsList.size() - 1);
        assertThat(testAccountFields.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
    }

    @Test
    @Transactional
    void putNonExistingAccountFields() throws Exception {
        int databaseSizeBeforeUpdate = accountFieldsRepository.findAll().size();
        accountFields.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountFieldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accountFields.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountFields))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountFields in the database
        List<AccountFields> accountFieldsList = accountFieldsRepository.findAll();
        assertThat(accountFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAccountFields() throws Exception {
        int databaseSizeBeforeUpdate = accountFieldsRepository.findAll().size();
        accountFields.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountFieldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountFields))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountFields in the database
        List<AccountFields> accountFieldsList = accountFieldsRepository.findAll();
        assertThat(accountFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAccountFields() throws Exception {
        int databaseSizeBeforeUpdate = accountFieldsRepository.findAll().size();
        accountFields.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountFieldsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountFields)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AccountFields in the database
        List<AccountFields> accountFieldsList = accountFieldsRepository.findAll();
        assertThat(accountFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAccountFieldsWithPatch() throws Exception {
        // Initialize the database
        accountFieldsRepository.saveAndFlush(accountFields);

        int databaseSizeBeforeUpdate = accountFieldsRepository.findAll().size();

        // Update the accountFields using partial update
        AccountFields partialUpdatedAccountFields = new AccountFields();
        partialUpdatedAccountFields.setId(accountFields.getId());

        restAccountFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccountFields.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccountFields))
            )
            .andExpect(status().isOk());

        // Validate the AccountFields in the database
        List<AccountFields> accountFieldsList = accountFieldsRepository.findAll();
        assertThat(accountFieldsList).hasSize(databaseSizeBeforeUpdate);
        AccountFields testAccountFields = accountFieldsList.get(accountFieldsList.size() - 1);
        assertThat(testAccountFields.getFieldName()).isEqualTo(DEFAULT_FIELD_NAME);
    }

    @Test
    @Transactional
    void fullUpdateAccountFieldsWithPatch() throws Exception {
        // Initialize the database
        accountFieldsRepository.saveAndFlush(accountFields);

        int databaseSizeBeforeUpdate = accountFieldsRepository.findAll().size();

        // Update the accountFields using partial update
        AccountFields partialUpdatedAccountFields = new AccountFields();
        partialUpdatedAccountFields.setId(accountFields.getId());

        partialUpdatedAccountFields.fieldName(UPDATED_FIELD_NAME);

        restAccountFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccountFields.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccountFields))
            )
            .andExpect(status().isOk());

        // Validate the AccountFields in the database
        List<AccountFields> accountFieldsList = accountFieldsRepository.findAll();
        assertThat(accountFieldsList).hasSize(databaseSizeBeforeUpdate);
        AccountFields testAccountFields = accountFieldsList.get(accountFieldsList.size() - 1);
        assertThat(testAccountFields.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingAccountFields() throws Exception {
        int databaseSizeBeforeUpdate = accountFieldsRepository.findAll().size();
        accountFields.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, accountFields.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accountFields))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountFields in the database
        List<AccountFields> accountFieldsList = accountFieldsRepository.findAll();
        assertThat(accountFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAccountFields() throws Exception {
        int databaseSizeBeforeUpdate = accountFieldsRepository.findAll().size();
        accountFields.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accountFields))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountFields in the database
        List<AccountFields> accountFieldsList = accountFieldsRepository.findAll();
        assertThat(accountFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAccountFields() throws Exception {
        int databaseSizeBeforeUpdate = accountFieldsRepository.findAll().size();
        accountFields.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(accountFields))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AccountFields in the database
        List<AccountFields> accountFieldsList = accountFieldsRepository.findAll();
        assertThat(accountFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAccountFields() throws Exception {
        // Initialize the database
        accountFieldsRepository.saveAndFlush(accountFields);

        int databaseSizeBeforeDelete = accountFieldsRepository.findAll().size();

        // Delete the accountFields
        restAccountFieldsMockMvc
            .perform(delete(ENTITY_API_URL_ID, accountFields.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AccountFields> accountFieldsList = accountFieldsRepository.findAll();
        assertThat(accountFieldsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
