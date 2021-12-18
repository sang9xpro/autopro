package com.sang9xpro.autopro.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sang9xpro.autopro.IntegrationTest;
import com.sang9xpro.autopro.domain.Accounts;
import com.sang9xpro.autopro.repository.AccountsRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link AccountsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AccountsResourceIT {

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_URL_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_URL_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_PROFILE_FIREFOX = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE_FIREFOX = "BBBBBBBBBB";

    private static final String DEFAULT_PROFILE_CHROME = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE_CHROME = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_UPDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_OWNER = "BBBBBBBBBB";

    private static final Integer DEFAULT_ACTIVED = 1;
    private static final Integer UPDATED_ACTIVED = 2;

    private static final String ENTITY_API_URL = "/api/accounts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccountsMockMvc;

    private Accounts accounts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Accounts createEntity(EntityManager em) {
        Accounts accounts = new Accounts()
            .userName(DEFAULT_USER_NAME)
            .password(DEFAULT_PASSWORD)
            .urlLogin(DEFAULT_URL_LOGIN)
            .profileFirefox(DEFAULT_PROFILE_FIREFOX)
            .profileChrome(DEFAULT_PROFILE_CHROME)
            .lastUpdate(DEFAULT_LAST_UPDATE)
            .owner(DEFAULT_OWNER)
            .actived(DEFAULT_ACTIVED);
        return accounts;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Accounts createUpdatedEntity(EntityManager em) {
        Accounts accounts = new Accounts()
            .userName(UPDATED_USER_NAME)
            .password(UPDATED_PASSWORD)
            .urlLogin(UPDATED_URL_LOGIN)
            .profileFirefox(UPDATED_PROFILE_FIREFOX)
            .profileChrome(UPDATED_PROFILE_CHROME)
            .lastUpdate(UPDATED_LAST_UPDATE)
            .owner(UPDATED_OWNER)
            .actived(UPDATED_ACTIVED);
        return accounts;
    }

    @BeforeEach
    public void initTest() {
        accounts = createEntity(em);
    }

    @Test
    @Transactional
    void createAccounts() throws Exception {
        int databaseSizeBeforeCreate = accountsRepository.findAll().size();
        // Create the Accounts
        restAccountsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accounts)))
            .andExpect(status().isCreated());

        // Validate the Accounts in the database
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeCreate + 1);
        Accounts testAccounts = accountsList.get(accountsList.size() - 1);
        assertThat(testAccounts.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testAccounts.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testAccounts.getUrlLogin()).isEqualTo(DEFAULT_URL_LOGIN);
        assertThat(testAccounts.getProfileFirefox()).isEqualTo(DEFAULT_PROFILE_FIREFOX);
        assertThat(testAccounts.getProfileChrome()).isEqualTo(DEFAULT_PROFILE_CHROME);
        assertThat(testAccounts.getLastUpdate()).isEqualTo(DEFAULT_LAST_UPDATE);
        assertThat(testAccounts.getOwner()).isEqualTo(DEFAULT_OWNER);
        assertThat(testAccounts.getActived()).isEqualTo(DEFAULT_ACTIVED);
    }

    @Test
    @Transactional
    void createAccountsWithExistingId() throws Exception {
        // Create the Accounts with an existing ID
        accounts.setId(1L);

        int databaseSizeBeforeCreate = accountsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accounts)))
            .andExpect(status().isBadRequest());

        // Validate the Accounts in the database
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAccounts() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList
        restAccountsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accounts.getId().intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].urlLogin").value(hasItem(DEFAULT_URL_LOGIN)))
            .andExpect(jsonPath("$.[*].profileFirefox").value(hasItem(DEFAULT_PROFILE_FIREFOX)))
            .andExpect(jsonPath("$.[*].profileChrome").value(hasItem(DEFAULT_PROFILE_CHROME)))
            .andExpect(jsonPath("$.[*].lastUpdate").value(hasItem(DEFAULT_LAST_UPDATE.toString())))
            .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER)))
            .andExpect(jsonPath("$.[*].actived").value(hasItem(DEFAULT_ACTIVED)));
    }

    @Test
    @Transactional
    void getAccounts() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get the accounts
        restAccountsMockMvc
            .perform(get(ENTITY_API_URL_ID, accounts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accounts.getId().intValue()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.urlLogin").value(DEFAULT_URL_LOGIN))
            .andExpect(jsonPath("$.profileFirefox").value(DEFAULT_PROFILE_FIREFOX))
            .andExpect(jsonPath("$.profileChrome").value(DEFAULT_PROFILE_CHROME))
            .andExpect(jsonPath("$.lastUpdate").value(DEFAULT_LAST_UPDATE.toString()))
            .andExpect(jsonPath("$.owner").value(DEFAULT_OWNER))
            .andExpect(jsonPath("$.actived").value(DEFAULT_ACTIVED));
    }

    @Test
    @Transactional
    void getNonExistingAccounts() throws Exception {
        // Get the accounts
        restAccountsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAccounts() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        int databaseSizeBeforeUpdate = accountsRepository.findAll().size();

        // Update the accounts
        Accounts updatedAccounts = accountsRepository.findById(accounts.getId()).get();
        // Disconnect from session so that the updates on updatedAccounts are not directly saved in db
        em.detach(updatedAccounts);
        updatedAccounts
            .userName(UPDATED_USER_NAME)
            .password(UPDATED_PASSWORD)
            .urlLogin(UPDATED_URL_LOGIN)
            .profileFirefox(UPDATED_PROFILE_FIREFOX)
            .profileChrome(UPDATED_PROFILE_CHROME)
            .lastUpdate(UPDATED_LAST_UPDATE)
            .owner(UPDATED_OWNER)
            .actived(UPDATED_ACTIVED);

        restAccountsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAccounts.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAccounts))
            )
            .andExpect(status().isOk());

        // Validate the Accounts in the database
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeUpdate);
        Accounts testAccounts = accountsList.get(accountsList.size() - 1);
        assertThat(testAccounts.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testAccounts.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testAccounts.getUrlLogin()).isEqualTo(UPDATED_URL_LOGIN);
        assertThat(testAccounts.getProfileFirefox()).isEqualTo(UPDATED_PROFILE_FIREFOX);
        assertThat(testAccounts.getProfileChrome()).isEqualTo(UPDATED_PROFILE_CHROME);
        assertThat(testAccounts.getLastUpdate()).isEqualTo(UPDATED_LAST_UPDATE);
        assertThat(testAccounts.getOwner()).isEqualTo(UPDATED_OWNER);
        assertThat(testAccounts.getActived()).isEqualTo(UPDATED_ACTIVED);
    }

    @Test
    @Transactional
    void putNonExistingAccounts() throws Exception {
        int databaseSizeBeforeUpdate = accountsRepository.findAll().size();
        accounts.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accounts.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accounts))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accounts in the database
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAccounts() throws Exception {
        int databaseSizeBeforeUpdate = accountsRepository.findAll().size();
        accounts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accounts))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accounts in the database
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAccounts() throws Exception {
        int databaseSizeBeforeUpdate = accountsRepository.findAll().size();
        accounts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accounts)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Accounts in the database
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAccountsWithPatch() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        int databaseSizeBeforeUpdate = accountsRepository.findAll().size();

        // Update the accounts using partial update
        Accounts partialUpdatedAccounts = new Accounts();
        partialUpdatedAccounts.setId(accounts.getId());

        partialUpdatedAccounts
            .userName(UPDATED_USER_NAME)
            .password(UPDATED_PASSWORD)
            .urlLogin(UPDATED_URL_LOGIN)
            .lastUpdate(UPDATED_LAST_UPDATE);

        restAccountsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccounts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccounts))
            )
            .andExpect(status().isOk());

        // Validate the Accounts in the database
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeUpdate);
        Accounts testAccounts = accountsList.get(accountsList.size() - 1);
        assertThat(testAccounts.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testAccounts.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testAccounts.getUrlLogin()).isEqualTo(UPDATED_URL_LOGIN);
        assertThat(testAccounts.getProfileFirefox()).isEqualTo(DEFAULT_PROFILE_FIREFOX);
        assertThat(testAccounts.getProfileChrome()).isEqualTo(DEFAULT_PROFILE_CHROME);
        assertThat(testAccounts.getLastUpdate()).isEqualTo(UPDATED_LAST_UPDATE);
        assertThat(testAccounts.getOwner()).isEqualTo(DEFAULT_OWNER);
        assertThat(testAccounts.getActived()).isEqualTo(DEFAULT_ACTIVED);
    }

    @Test
    @Transactional
    void fullUpdateAccountsWithPatch() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        int databaseSizeBeforeUpdate = accountsRepository.findAll().size();

        // Update the accounts using partial update
        Accounts partialUpdatedAccounts = new Accounts();
        partialUpdatedAccounts.setId(accounts.getId());

        partialUpdatedAccounts
            .userName(UPDATED_USER_NAME)
            .password(UPDATED_PASSWORD)
            .urlLogin(UPDATED_URL_LOGIN)
            .profileFirefox(UPDATED_PROFILE_FIREFOX)
            .profileChrome(UPDATED_PROFILE_CHROME)
            .lastUpdate(UPDATED_LAST_UPDATE)
            .owner(UPDATED_OWNER)
            .actived(UPDATED_ACTIVED);

        restAccountsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccounts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccounts))
            )
            .andExpect(status().isOk());

        // Validate the Accounts in the database
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeUpdate);
        Accounts testAccounts = accountsList.get(accountsList.size() - 1);
        assertThat(testAccounts.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testAccounts.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testAccounts.getUrlLogin()).isEqualTo(UPDATED_URL_LOGIN);
        assertThat(testAccounts.getProfileFirefox()).isEqualTo(UPDATED_PROFILE_FIREFOX);
        assertThat(testAccounts.getProfileChrome()).isEqualTo(UPDATED_PROFILE_CHROME);
        assertThat(testAccounts.getLastUpdate()).isEqualTo(UPDATED_LAST_UPDATE);
        assertThat(testAccounts.getOwner()).isEqualTo(UPDATED_OWNER);
        assertThat(testAccounts.getActived()).isEqualTo(UPDATED_ACTIVED);
    }

    @Test
    @Transactional
    void patchNonExistingAccounts() throws Exception {
        int databaseSizeBeforeUpdate = accountsRepository.findAll().size();
        accounts.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, accounts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accounts))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accounts in the database
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAccounts() throws Exception {
        int databaseSizeBeforeUpdate = accountsRepository.findAll().size();
        accounts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accounts))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accounts in the database
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAccounts() throws Exception {
        int databaseSizeBeforeUpdate = accountsRepository.findAll().size();
        accounts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(accounts)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Accounts in the database
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAccounts() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        int databaseSizeBeforeDelete = accountsRepository.findAll().size();

        // Delete the accounts
        restAccountsMockMvc
            .perform(delete(ENTITY_API_URL_ID, accounts.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
