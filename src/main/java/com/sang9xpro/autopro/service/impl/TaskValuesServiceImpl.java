package com.sang9xpro.autopro.service.impl;

import com.sang9xpro.autopro.domain.TaskValues;
import com.sang9xpro.autopro.repository.TaskValuesRepository;
import com.sang9xpro.autopro.service.TaskValuesService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TaskValues}.
 */
@Service
@Transactional
public class TaskValuesServiceImpl implements TaskValuesService {

    private final Logger log = LoggerFactory.getLogger(TaskValuesServiceImpl.class);

    private final TaskValuesRepository taskValuesRepository;

    public TaskValuesServiceImpl(TaskValuesRepository taskValuesRepository) {
        this.taskValuesRepository = taskValuesRepository;
    }

    @Override
    public TaskValues save(TaskValues taskValues) {
        log.debug("Request to save TaskValues : {}", taskValues);
        return taskValuesRepository.save(taskValues);
    }

    @Override
    public Optional<TaskValues> partialUpdate(TaskValues taskValues) {
        log.debug("Request to partially update TaskValues : {}", taskValues);

        return taskValuesRepository
            .findById(taskValues.getId())
            .map(existingTaskValues -> {
                if (taskValues.getValue() != null) {
                    existingTaskValues.setValue(taskValues.getValue());
                }

                return existingTaskValues;
            })
            .map(taskValuesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskValues> findAll() {
        log.debug("Request to get all TaskValues");
        return taskValuesRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TaskValues> findOne(Long id) {
        log.debug("Request to get TaskValues : {}", id);
        return taskValuesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TaskValues : {}", id);
        taskValuesRepository.deleteById(id);
    }
}
