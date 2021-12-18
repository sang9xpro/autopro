package com.sang9xpro.autopro.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sang9xpro.autopro.IntegrationTest;
import com.sang9xpro.autopro.domain.Facebook;
import com.sang9xpro.autopro.domain.enumeration.FbType;
import com.sang9xpro.autopro.repository.FacebookRepository;
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
 * Integration tests for the {@link FacebookResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FacebookResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_ID_ON_FB = "AAAAAAAAAA";
    private static final String UPDATED_ID_ON_FB = "BBBBBBBBBB";

    private static final FbType DEFAULT_TYPE = FbType.Post;
    private static final FbType UPDATED_TYPE = FbType.People;

    private static final String ENTITY_API_URL = "/api/facebooks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FacebookRepository facebookRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFacebookMockMvc;

    private Facebook facebook;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Facebook createEntity(EntityManager em) {
        Facebook facebook = new Facebook().name(DEFAULT_NAME).url(DEFAULT_URL).idOnFb(DEFAULT_ID_ON_FB).type(DEFAULT_TYPE);
        return facebook;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Facebook createUpdatedEntity(EntityManager em) {
        Facebook facebook = new Facebook().name(UPDATED_NAME).url(UPDATED_URL).idOnFb(UPDATED_ID_ON_FB).type(UPDATED_TYPE);
        return facebook;
    }

    @BeforeEach
    public void initTest() {
        facebook = createEntity(em);
    }

    @Test
    @Transactional
    void createFacebook() throws Exception {
        int databaseSizeBeforeCreate = facebookRepository.findAll().size();
        // Create the Facebook
        restFacebookMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(facebook)))
            .andExpect(status().isCreated());

        // Validate the Facebook in the database
        List<Facebook> facebookList = facebookRepository.findAll();
        assertThat(facebookList).hasSize(databaseSizeBeforeCreate + 1);
        Facebook testFacebook = facebookList.get(facebookList.size() - 1);
        assertThat(testFacebook.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFacebook.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testFacebook.getIdOnFb()).isEqualTo(DEFAULT_ID_ON_FB);
        assertThat(testFacebook.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void createFacebookWithExistingId() throws Exception {
        // Create the Facebook with an existing ID
        facebook.setId(1L);

        int databaseSizeBeforeCreate = facebookRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacebookMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(facebook)))
            .andExpect(status().isBadRequest());

        // Validate the Facebook in the database
        List<Facebook> facebookList = facebookRepository.findAll();
        assertThat(facebookList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFacebooks() throws Exception {
        // Initialize the database
        facebookRepository.saveAndFlush(facebook);

        // Get all the facebookList
        restFacebookMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facebook.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].idOnFb").value(hasItem(DEFAULT_ID_ON_FB)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    void getFacebook() throws Exception {
        // Initialize the database
        facebookRepository.saveAndFlush(facebook);

        // Get the facebook
        restFacebookMockMvc
            .perform(get(ENTITY_API_URL_ID, facebook.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(facebook.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.idOnFb").value(DEFAULT_ID_ON_FB))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFacebook() throws Exception {
        // Get the facebook
        restFacebookMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFacebook() throws Exception {
        // Initialize the database
        facebookRepository.saveAndFlush(facebook);

        int databaseSizeBeforeUpdate = facebookRepository.findAll().size();

        // Update the facebook
        Facebook updatedFacebook = facebookRepository.findById(facebook.getId()).get();
        // Disconnect from session so that the updates on updatedFacebook are not directly saved in db
        em.detach(updatedFacebook);
        updatedFacebook.name(UPDATED_NAME).url(UPDATED_URL).idOnFb(UPDATED_ID_ON_FB).type(UPDATED_TYPE);

        restFacebookMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFacebook.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFacebook))
            )
            .andExpect(status().isOk());

        // Validate the Facebook in the database
        List<Facebook> facebookList = facebookRepository.findAll();
        assertThat(facebookList).hasSize(databaseSizeBeforeUpdate);
        Facebook testFacebook = facebookList.get(facebookList.size() - 1);
        assertThat(testFacebook.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFacebook.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testFacebook.getIdOnFb()).isEqualTo(UPDATED_ID_ON_FB);
        assertThat(testFacebook.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingFacebook() throws Exception {
        int databaseSizeBeforeUpdate = facebookRepository.findAll().size();
        facebook.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFacebookMockMvc
            .perform(
                put(ENTITY_API_URL_ID, facebook.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(facebook))
            )
            .andExpect(status().isBadRequest());

        // Validate the Facebook in the database
        List<Facebook> facebookList = facebookRepository.findAll();
        assertThat(facebookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFacebook() throws Exception {
        int databaseSizeBeforeUpdate = facebookRepository.findAll().size();
        facebook.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFacebookMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(facebook))
            )
            .andExpect(status().isBadRequest());

        // Validate the Facebook in the database
        List<Facebook> facebookList = facebookRepository.findAll();
        assertThat(facebookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFacebook() throws Exception {
        int databaseSizeBeforeUpdate = facebookRepository.findAll().size();
        facebook.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFacebookMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(facebook)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Facebook in the database
        List<Facebook> facebookList = facebookRepository.findAll();
        assertThat(facebookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFacebookWithPatch() throws Exception {
        // Initialize the database
        facebookRepository.saveAndFlush(facebook);

        int databaseSizeBeforeUpdate = facebookRepository.findAll().size();

        // Update the facebook using partial update
        Facebook partialUpdatedFacebook = new Facebook();
        partialUpdatedFacebook.setId(facebook.getId());

        partialUpdatedFacebook.url(UPDATED_URL).idOnFb(UPDATED_ID_ON_FB);

        restFacebookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFacebook.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFacebook))
            )
            .andExpect(status().isOk());

        // Validate the Facebook in the database
        List<Facebook> facebookList = facebookRepository.findAll();
        assertThat(facebookList).hasSize(databaseSizeBeforeUpdate);
        Facebook testFacebook = facebookList.get(facebookList.size() - 1);
        assertThat(testFacebook.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFacebook.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testFacebook.getIdOnFb()).isEqualTo(UPDATED_ID_ON_FB);
        assertThat(testFacebook.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateFacebookWithPatch() throws Exception {
        // Initialize the database
        facebookRepository.saveAndFlush(facebook);

        int databaseSizeBeforeUpdate = facebookRepository.findAll().size();

        // Update the facebook using partial update
        Facebook partialUpdatedFacebook = new Facebook();
        partialUpdatedFacebook.setId(facebook.getId());

        partialUpdatedFacebook.name(UPDATED_NAME).url(UPDATED_URL).idOnFb(UPDATED_ID_ON_FB).type(UPDATED_TYPE);

        restFacebookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFacebook.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFacebook))
            )
            .andExpect(status().isOk());

        // Validate the Facebook in the database
        List<Facebook> facebookList = facebookRepository.findAll();
        assertThat(facebookList).hasSize(databaseSizeBeforeUpdate);
        Facebook testFacebook = facebookList.get(facebookList.size() - 1);
        assertThat(testFacebook.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFacebook.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testFacebook.getIdOnFb()).isEqualTo(UPDATED_ID_ON_FB);
        assertThat(testFacebook.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingFacebook() throws Exception {
        int databaseSizeBeforeUpdate = facebookRepository.findAll().size();
        facebook.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFacebookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, facebook.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(facebook))
            )
            .andExpect(status().isBadRequest());

        // Validate the Facebook in the database
        List<Facebook> facebookList = facebookRepository.findAll();
        assertThat(facebookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFacebook() throws Exception {
        int databaseSizeBeforeUpdate = facebookRepository.findAll().size();
        facebook.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFacebookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(facebook))
            )
            .andExpect(status().isBadRequest());

        // Validate the Facebook in the database
        List<Facebook> facebookList = facebookRepository.findAll();
        assertThat(facebookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFacebook() throws Exception {
        int databaseSizeBeforeUpdate = facebookRepository.findAll().size();
        facebook.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFacebookMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(facebook)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Facebook in the database
        List<Facebook> facebookList = facebookRepository.findAll();
        assertThat(facebookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFacebook() throws Exception {
        // Initialize the database
        facebookRepository.saveAndFlush(facebook);

        int databaseSizeBeforeDelete = facebookRepository.findAll().size();

        // Delete the facebook
        restFacebookMockMvc
            .perform(delete(ENTITY_API_URL_ID, facebook.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Facebook> facebookList = facebookRepository.findAll();
        assertThat(facebookList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
