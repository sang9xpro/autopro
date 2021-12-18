package com.sang9xpro.autopro.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sang9xpro.autopro.IntegrationTest;
import com.sang9xpro.autopro.domain.SchedulerTask;
import com.sang9xpro.autopro.domain.enumeration.SchedulerStatus;
import com.sang9xpro.autopro.repository.SchedulerTaskRepository;
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
 * Integration tests for the {@link SchedulerTaskResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SchedulerTaskResourceIT {

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_COUNT_FROM = 1;
    private static final Integer UPDATED_COUNT_FROM = 2;

    private static final Integer DEFAULT_COUNT_TO = 1;
    private static final Integer UPDATED_COUNT_TO = 2;

    private static final Double DEFAULT_POINT = 1D;
    private static final Double UPDATED_POINT = 2D;

    private static final Instant DEFAULT_LAST_UPDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_OWNER = "BBBBBBBBBB";

    private static final SchedulerStatus DEFAULT_STATUS = SchedulerStatus.Open;
    private static final SchedulerStatus UPDATED_STATUS = SchedulerStatus.Inprogess;

    private static final String ENTITY_API_URL = "/api/scheduler-tasks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SchedulerTaskRepository schedulerTaskRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSchedulerTaskMockMvc;

    private SchedulerTask schedulerTask;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchedulerTask createEntity(EntityManager em) {
        SchedulerTask schedulerTask = new SchedulerTask()
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .countFrom(DEFAULT_COUNT_FROM)
            .countTo(DEFAULT_COUNT_TO)
            .point(DEFAULT_POINT)
            .lastUpdate(DEFAULT_LAST_UPDATE)
            .owner(DEFAULT_OWNER)
            .status(DEFAULT_STATUS);
        return schedulerTask;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchedulerTask createUpdatedEntity(EntityManager em) {
        SchedulerTask schedulerTask = new SchedulerTask()
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .countFrom(UPDATED_COUNT_FROM)
            .countTo(UPDATED_COUNT_TO)
            .point(UPDATED_POINT)
            .lastUpdate(UPDATED_LAST_UPDATE)
            .owner(UPDATED_OWNER)
            .status(UPDATED_STATUS);
        return schedulerTask;
    }

    @BeforeEach
    public void initTest() {
        schedulerTask = createEntity(em);
    }

    @Test
    @Transactional
    void createSchedulerTask() throws Exception {
        int databaseSizeBeforeCreate = schedulerTaskRepository.findAll().size();
        // Create the SchedulerTask
        restSchedulerTaskMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schedulerTask)))
            .andExpect(status().isCreated());

        // Validate the SchedulerTask in the database
        List<SchedulerTask> schedulerTaskList = schedulerTaskRepository.findAll();
        assertThat(schedulerTaskList).hasSize(databaseSizeBeforeCreate + 1);
        SchedulerTask testSchedulerTask = schedulerTaskList.get(schedulerTaskList.size() - 1);
        assertThat(testSchedulerTask.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testSchedulerTask.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testSchedulerTask.getCountFrom()).isEqualTo(DEFAULT_COUNT_FROM);
        assertThat(testSchedulerTask.getCountTo()).isEqualTo(DEFAULT_COUNT_TO);
        assertThat(testSchedulerTask.getPoint()).isEqualTo(DEFAULT_POINT);
        assertThat(testSchedulerTask.getLastUpdate()).isEqualTo(DEFAULT_LAST_UPDATE);
        assertThat(testSchedulerTask.getOwner()).isEqualTo(DEFAULT_OWNER);
        assertThat(testSchedulerTask.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createSchedulerTaskWithExistingId() throws Exception {
        // Create the SchedulerTask with an existing ID
        schedulerTask.setId(1L);

        int databaseSizeBeforeCreate = schedulerTaskRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchedulerTaskMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schedulerTask)))
            .andExpect(status().isBadRequest());

        // Validate the SchedulerTask in the database
        List<SchedulerTask> schedulerTaskList = schedulerTaskRepository.findAll();
        assertThat(schedulerTaskList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSchedulerTasks() throws Exception {
        // Initialize the database
        schedulerTaskRepository.saveAndFlush(schedulerTask);

        // Get all the schedulerTaskList
        restSchedulerTaskMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schedulerTask.getId().intValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].countFrom").value(hasItem(DEFAULT_COUNT_FROM)))
            .andExpect(jsonPath("$.[*].countTo").value(hasItem(DEFAULT_COUNT_TO)))
            .andExpect(jsonPath("$.[*].point").value(hasItem(DEFAULT_POINT.doubleValue())))
            .andExpect(jsonPath("$.[*].lastUpdate").value(hasItem(DEFAULT_LAST_UPDATE.toString())))
            .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getSchedulerTask() throws Exception {
        // Initialize the database
        schedulerTaskRepository.saveAndFlush(schedulerTask);

        // Get the schedulerTask
        restSchedulerTaskMockMvc
            .perform(get(ENTITY_API_URL_ID, schedulerTask.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(schedulerTask.getId().intValue()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()))
            .andExpect(jsonPath("$.countFrom").value(DEFAULT_COUNT_FROM))
            .andExpect(jsonPath("$.countTo").value(DEFAULT_COUNT_TO))
            .andExpect(jsonPath("$.point").value(DEFAULT_POINT.doubleValue()))
            .andExpect(jsonPath("$.lastUpdate").value(DEFAULT_LAST_UPDATE.toString()))
            .andExpect(jsonPath("$.owner").value(DEFAULT_OWNER))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSchedulerTask() throws Exception {
        // Get the schedulerTask
        restSchedulerTaskMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSchedulerTask() throws Exception {
        // Initialize the database
        schedulerTaskRepository.saveAndFlush(schedulerTask);

        int databaseSizeBeforeUpdate = schedulerTaskRepository.findAll().size();

        // Update the schedulerTask
        SchedulerTask updatedSchedulerTask = schedulerTaskRepository.findById(schedulerTask.getId()).get();
        // Disconnect from session so that the updates on updatedSchedulerTask are not directly saved in db
        em.detach(updatedSchedulerTask);
        updatedSchedulerTask
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .countFrom(UPDATED_COUNT_FROM)
            .countTo(UPDATED_COUNT_TO)
            .point(UPDATED_POINT)
            .lastUpdate(UPDATED_LAST_UPDATE)
            .owner(UPDATED_OWNER)
            .status(UPDATED_STATUS);

        restSchedulerTaskMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSchedulerTask.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSchedulerTask))
            )
            .andExpect(status().isOk());

        // Validate the SchedulerTask in the database
        List<SchedulerTask> schedulerTaskList = schedulerTaskRepository.findAll();
        assertThat(schedulerTaskList).hasSize(databaseSizeBeforeUpdate);
        SchedulerTask testSchedulerTask = schedulerTaskList.get(schedulerTaskList.size() - 1);
        assertThat(testSchedulerTask.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testSchedulerTask.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testSchedulerTask.getCountFrom()).isEqualTo(UPDATED_COUNT_FROM);
        assertThat(testSchedulerTask.getCountTo()).isEqualTo(UPDATED_COUNT_TO);
        assertThat(testSchedulerTask.getPoint()).isEqualTo(UPDATED_POINT);
        assertThat(testSchedulerTask.getLastUpdate()).isEqualTo(UPDATED_LAST_UPDATE);
        assertThat(testSchedulerTask.getOwner()).isEqualTo(UPDATED_OWNER);
        assertThat(testSchedulerTask.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingSchedulerTask() throws Exception {
        int databaseSizeBeforeUpdate = schedulerTaskRepository.findAll().size();
        schedulerTask.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchedulerTaskMockMvc
            .perform(
                put(ENTITY_API_URL_ID, schedulerTask.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(schedulerTask))
            )
            .andExpect(status().isBadRequest());

        // Validate the SchedulerTask in the database
        List<SchedulerTask> schedulerTaskList = schedulerTaskRepository.findAll();
        assertThat(schedulerTaskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSchedulerTask() throws Exception {
        int databaseSizeBeforeUpdate = schedulerTaskRepository.findAll().size();
        schedulerTask.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchedulerTaskMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(schedulerTask))
            )
            .andExpect(status().isBadRequest());

        // Validate the SchedulerTask in the database
        List<SchedulerTask> schedulerTaskList = schedulerTaskRepository.findAll();
        assertThat(schedulerTaskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSchedulerTask() throws Exception {
        int databaseSizeBeforeUpdate = schedulerTaskRepository.findAll().size();
        schedulerTask.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchedulerTaskMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schedulerTask)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SchedulerTask in the database
        List<SchedulerTask> schedulerTaskList = schedulerTaskRepository.findAll();
        assertThat(schedulerTaskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSchedulerTaskWithPatch() throws Exception {
        // Initialize the database
        schedulerTaskRepository.saveAndFlush(schedulerTask);

        int databaseSizeBeforeUpdate = schedulerTaskRepository.findAll().size();

        // Update the schedulerTask using partial update
        SchedulerTask partialUpdatedSchedulerTask = new SchedulerTask();
        partialUpdatedSchedulerTask.setId(schedulerTask.getId());

        partialUpdatedSchedulerTask.startTime(UPDATED_START_TIME).endTime(UPDATED_END_TIME).status(UPDATED_STATUS);

        restSchedulerTaskMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSchedulerTask.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSchedulerTask))
            )
            .andExpect(status().isOk());

        // Validate the SchedulerTask in the database
        List<SchedulerTask> schedulerTaskList = schedulerTaskRepository.findAll();
        assertThat(schedulerTaskList).hasSize(databaseSizeBeforeUpdate);
        SchedulerTask testSchedulerTask = schedulerTaskList.get(schedulerTaskList.size() - 1);
        assertThat(testSchedulerTask.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testSchedulerTask.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testSchedulerTask.getCountFrom()).isEqualTo(DEFAULT_COUNT_FROM);
        assertThat(testSchedulerTask.getCountTo()).isEqualTo(DEFAULT_COUNT_TO);
        assertThat(testSchedulerTask.getPoint()).isEqualTo(DEFAULT_POINT);
        assertThat(testSchedulerTask.getLastUpdate()).isEqualTo(DEFAULT_LAST_UPDATE);
        assertThat(testSchedulerTask.getOwner()).isEqualTo(DEFAULT_OWNER);
        assertThat(testSchedulerTask.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateSchedulerTaskWithPatch() throws Exception {
        // Initialize the database
        schedulerTaskRepository.saveAndFlush(schedulerTask);

        int databaseSizeBeforeUpdate = schedulerTaskRepository.findAll().size();

        // Update the schedulerTask using partial update
        SchedulerTask partialUpdatedSchedulerTask = new SchedulerTask();
        partialUpdatedSchedulerTask.setId(schedulerTask.getId());

        partialUpdatedSchedulerTask
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .countFrom(UPDATED_COUNT_FROM)
            .countTo(UPDATED_COUNT_TO)
            .point(UPDATED_POINT)
            .lastUpdate(UPDATED_LAST_UPDATE)
            .owner(UPDATED_OWNER)
            .status(UPDATED_STATUS);

        restSchedulerTaskMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSchedulerTask.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSchedulerTask))
            )
            .andExpect(status().isOk());

        // Validate the SchedulerTask in the database
        List<SchedulerTask> schedulerTaskList = schedulerTaskRepository.findAll();
        assertThat(schedulerTaskList).hasSize(databaseSizeBeforeUpdate);
        SchedulerTask testSchedulerTask = schedulerTaskList.get(schedulerTaskList.size() - 1);
        assertThat(testSchedulerTask.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testSchedulerTask.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testSchedulerTask.getCountFrom()).isEqualTo(UPDATED_COUNT_FROM);
        assertThat(testSchedulerTask.getCountTo()).isEqualTo(UPDATED_COUNT_TO);
        assertThat(testSchedulerTask.getPoint()).isEqualTo(UPDATED_POINT);
        assertThat(testSchedulerTask.getLastUpdate()).isEqualTo(UPDATED_LAST_UPDATE);
        assertThat(testSchedulerTask.getOwner()).isEqualTo(UPDATED_OWNER);
        assertThat(testSchedulerTask.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingSchedulerTask() throws Exception {
        int databaseSizeBeforeUpdate = schedulerTaskRepository.findAll().size();
        schedulerTask.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchedulerTaskMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, schedulerTask.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(schedulerTask))
            )
            .andExpect(status().isBadRequest());

        // Validate the SchedulerTask in the database
        List<SchedulerTask> schedulerTaskList = schedulerTaskRepository.findAll();
        assertThat(schedulerTaskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSchedulerTask() throws Exception {
        int databaseSizeBeforeUpdate = schedulerTaskRepository.findAll().size();
        schedulerTask.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchedulerTaskMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(schedulerTask))
            )
            .andExpect(status().isBadRequest());

        // Validate the SchedulerTask in the database
        List<SchedulerTask> schedulerTaskList = schedulerTaskRepository.findAll();
        assertThat(schedulerTaskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSchedulerTask() throws Exception {
        int databaseSizeBeforeUpdate = schedulerTaskRepository.findAll().size();
        schedulerTask.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchedulerTaskMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(schedulerTask))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SchedulerTask in the database
        List<SchedulerTask> schedulerTaskList = schedulerTaskRepository.findAll();
        assertThat(schedulerTaskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSchedulerTask() throws Exception {
        // Initialize the database
        schedulerTaskRepository.saveAndFlush(schedulerTask);

        int databaseSizeBeforeDelete = schedulerTaskRepository.findAll().size();

        // Delete the schedulerTask
        restSchedulerTaskMockMvc
            .perform(delete(ENTITY_API_URL_ID, schedulerTask.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SchedulerTask> schedulerTaskList = schedulerTaskRepository.findAll();
        assertThat(schedulerTaskList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
