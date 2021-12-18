package com.sang9xpro.autopro.service.impl;

import com.sang9xpro.autopro.domain.SchedulerTask;
import com.sang9xpro.autopro.repository.SchedulerTaskRepository;
import com.sang9xpro.autopro.service.SchedulerTaskService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SchedulerTask}.
 */
@Service
@Transactional
public class SchedulerTaskServiceImpl implements SchedulerTaskService {

    private final Logger log = LoggerFactory.getLogger(SchedulerTaskServiceImpl.class);

    private final SchedulerTaskRepository schedulerTaskRepository;

    public SchedulerTaskServiceImpl(SchedulerTaskRepository schedulerTaskRepository) {
        this.schedulerTaskRepository = schedulerTaskRepository;
    }

    @Override
    public SchedulerTask save(SchedulerTask schedulerTask) {
        log.debug("Request to save SchedulerTask : {}", schedulerTask);
        return schedulerTaskRepository.save(schedulerTask);
    }

    @Override
    public Optional<SchedulerTask> partialUpdate(SchedulerTask schedulerTask) {
        log.debug("Request to partially update SchedulerTask : {}", schedulerTask);

        return schedulerTaskRepository
            .findById(schedulerTask.getId())
            .map(existingSchedulerTask -> {
                if (schedulerTask.getStartTime() != null) {
                    existingSchedulerTask.setStartTime(schedulerTask.getStartTime());
                }
                if (schedulerTask.getEndTime() != null) {
                    existingSchedulerTask.setEndTime(schedulerTask.getEndTime());
                }
                if (schedulerTask.getCountFrom() != null) {
                    existingSchedulerTask.setCountFrom(schedulerTask.getCountFrom());
                }
                if (schedulerTask.getCountTo() != null) {
                    existingSchedulerTask.setCountTo(schedulerTask.getCountTo());
                }
                if (schedulerTask.getPoint() != null) {
                    existingSchedulerTask.setPoint(schedulerTask.getPoint());
                }
                if (schedulerTask.getLastUpdate() != null) {
                    existingSchedulerTask.setLastUpdate(schedulerTask.getLastUpdate());
                }
                if (schedulerTask.getOwner() != null) {
                    existingSchedulerTask.setOwner(schedulerTask.getOwner());
                }
                if (schedulerTask.getStatus() != null) {
                    existingSchedulerTask.setStatus(schedulerTask.getStatus());
                }

                return existingSchedulerTask;
            })
            .map(schedulerTaskRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SchedulerTask> findAll() {
        log.debug("Request to get all SchedulerTasks");
        return schedulerTaskRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SchedulerTask> findOne(Long id) {
        log.debug("Request to get SchedulerTask : {}", id);
        return schedulerTaskRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SchedulerTask : {}", id);
        schedulerTaskRepository.deleteById(id);
    }
}
