package com.sang9xpro.autopro.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sang9xpro.autopro.IntegrationTest;
import com.sang9xpro.autopro.domain.AccountValues;
import com.sang9xpro.autopro.repository.AccountValuesRepository;
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
 * Integration tests for the {@link AccountValuesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AccountValuesResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/account-values";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AccountValuesRepository accountValuesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccountValuesMockMvc;

    private AccountValues accountValues;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountValues createEntity(EntityManager em) {
        AccountValues accountValues = new AccountValues().value(DEFAULT_VALUE);
        return accountValues;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountValues createUpdatedEntity(EntityManager em) {
        AccountValues accountValues = new AccountValues().value(UPDATED_VALUE);
        return accountValues;
    }

    @BeforeEach
    public void initTest() {
        accountValues = createEntity(em);
    }

    @Test
    @Transactional
    void createAccountValues() throws Exception {
        int databaseSizeBeforeCreate = accountValuesRepository.findAll().size();
        // Create the AccountValues
        restAccountValuesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountValues)))
            .andExpect(status().isCreated());

        // Validate the AccountValues in the database
        List<AccountValues> accountValuesList = accountValuesRepository.findAll();
        assertThat(accountValuesList).hasSize(databaseSizeBeforeCreate + 1);
        AccountValues testAccountValues = accountValuesList.get(accountValuesList.size() - 1);
        assertThat(testAccountValues.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void createAccountValuesWithExistingId() throws Exception {
        // Create the AccountValues with an existing ID
        accountValues.setId(1L);

        int databaseSizeBeforeCreate = accountValuesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountValuesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountValues)))
            .andExpect(status().isBadRequest());

        // Validate the AccountValues in the database
        List<AccountValues> accountValuesList = accountValuesRepository.findAll();
        assertThat(accountValuesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAccountValues() throws Exception {
        // Initialize the database
        accountValuesRepository.saveAndFlush(accountValues);

        // Get all the accountValuesList
        restAccountValuesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountValues.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    void getAccountValues() throws Exception {
        // Initialize the database
        accountValuesRepository.saveAndFlush(accountValues);

        // Get the accountValues
        restAccountValuesMockMvc
            .perform(get(ENTITY_API_URL_ID, accountValues.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accountValues.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingAccountValues() throws Exception {
        // Get the accountValues
        restAccountValuesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAccountValues() throws Exception {
        // Initialize the database
        accountValuesRepository.saveAndFlush(accountValues);

        int databaseSizeBeforeUpdate = accountValuesRepository.findAll().size();

        // Update the accountValues
        AccountValues updatedAccountValues = accountValuesRepository.findById(accountValues.getId()).get();
        // Disconnect from session so that the updates on updatedAccountValues are not directly saved in db
        em.detach(updatedAccountValues);
        updatedAccountValues.value(UPDATED_VALUE);

        restAccountValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAccountValues.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAccountValues))
            )
            .andExpect(status().isOk());

        // Validate the AccountValues in the database
        List<AccountValues> accountValuesList = accountValuesRepository.findAll();
        assertThat(accountValuesList).hasSize(databaseSizeBeforeUpdate);
        AccountValues testAccountValues = accountValuesList.get(accountValuesList.size() - 1);
        assertThat(testAccountValues.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingAccountValues() throws Exception {
        int databaseSizeBeforeUpdate = accountValuesRepository.findAll().size();
        accountValues.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accountValues.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountValues in the database
        List<AccountValues> accountValuesList = accountValuesRepository.findAll();
        assertThat(accountValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAccountValues() throws Exception {
        int databaseSizeBeforeUpdate = accountValuesRepository.findAll().size();
        accountValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountValues in the database
        List<AccountValues> accountValuesList = accountValuesRepository.findAll();
        assertThat(accountValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAccountValues() throws Exception {
        int databaseSizeBeforeUpdate = accountValuesRepository.findAll().size();
        accountValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountValuesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountValues)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AccountValues in the database
        List<AccountValues> accountValuesList = accountValuesRepository.findAll();
        assertThat(accountValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAccountValuesWithPatch() throws Exception {
        // Initialize the database
        accountValuesRepository.saveAndFlush(accountValues);

        int databaseSizeBeforeUpdate = accountValuesRepository.findAll().size();

        // Update the accountValues using partial update
        AccountValues partialUpdatedAccountValues = new AccountValues();
        partialUpdatedAccountValues.setId(accountValues.getId());

        restAccountValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccountValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccountValues))
            )
            .andExpect(status().isOk());

        // Validate the AccountValues in the database
        List<AccountValues> accountValuesList = accountValuesRepository.findAll();
        assertThat(accountValuesList).hasSize(databaseSizeBeforeUpdate);
        AccountValues testAccountValues = accountValuesList.get(accountValuesList.size() - 1);
        assertThat(testAccountValues.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateAccountValuesWithPatch() throws Exception {
        // Initialize the database
        accountValuesRepository.saveAndFlush(accountValues);

        int databaseSizeBeforeUpdate = accountValuesRepository.findAll().size();

        // Update the accountValues using partial update
        AccountValues partialUpdatedAccountValues = new AccountValues();
        partialUpdatedAccountValues.setId(accountValues.getId());

        partialUpdatedAccountValues.value(UPDATED_VALUE);

        restAccountValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccountValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccountValues))
            )
            .andExpect(status().isOk());

        // Validate the AccountValues in the database
        List<AccountValues> accountValuesList = accountValuesRepository.findAll();
        assertThat(accountValuesList).hasSize(databaseSizeBeforeUpdate);
        AccountValues testAccountValues = accountValuesList.get(accountValuesList.size() - 1);
        assertThat(testAccountValues.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingAccountValues() throws Exception {
        int databaseSizeBeforeUpdate = accountValuesRepository.findAll().size();
        accountValues.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, accountValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accountValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountValues in the database
        List<AccountValues> accountValuesList = accountValuesRepository.findAll();
        assertThat(accountValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAccountValues() throws Exception {
        int databaseSizeBeforeUpdate = accountValuesRepository.findAll().size();
        accountValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accountValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountValues in the database
        List<AccountValues> accountValuesList = accountValuesRepository.findAll();
        assertThat(accountValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAccountValues() throws Exception {
        int databaseSizeBeforeUpdate = accountValuesRepository.findAll().size();
        accountValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountValuesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(accountValues))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AccountValues in the database
        List<AccountValues> accountValuesList = accountValuesRepository.findAll();
        assertThat(accountValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAccountValues() throws Exception {
        // Initialize the database
        accountValuesRepository.saveAndFlush(accountValues);

        int databaseSizeBeforeDelete = accountValuesRepository.findAll().size();

        // Delete the accountValues
        restAccountValuesMockMvc
            .perform(delete(ENTITY_API_URL_ID, accountValues.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AccountValues> accountValuesList = accountValuesRepository.findAll();
        assertThat(accountValuesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
