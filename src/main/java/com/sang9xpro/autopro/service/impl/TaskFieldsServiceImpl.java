package com.sang9xpro.autopro.service.impl;

import com.sang9xpro.autopro.domain.TaskFields;
import com.sang9xpro.autopro.repository.TaskFieldsRepository;
import com.sang9xpro.autopro.service.TaskFieldsService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TaskFields}.
 */
@Service
@Transactional
public class TaskFieldsServiceImpl implements TaskFieldsService {

    private final Logger log = LoggerFactory.getLogger(TaskFieldsServiceImpl.class);

    private final TaskFieldsRepository taskFieldsRepository;

    public TaskFieldsServiceImpl(TaskFieldsRepository taskFieldsRepository) {
        this.taskFieldsRepository = taskFieldsRepository;
    }

    @Override
    public TaskFields save(TaskFields taskFields) {
        log.debug("Request to save TaskFields : {}", taskFields);
        return taskFieldsRepository.save(taskFields);
    }

    @Override
    public Optional<TaskFields> partialUpdate(TaskFields taskFields) {
        log.debug("Request to partially update TaskFields : {}", taskFields);

        return taskFieldsRepository
            .findById(taskFields.getId())
            .map(existingTaskFields -> {
                if (taskFields.getFieldName() != null) {
                    existingTaskFields.setFieldName(taskFields.getFieldName());
                }

                return existingTaskFields;
            })
            .map(taskFieldsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskFields> findAll() {
        log.debug("Request to get all TaskFields");
        return taskFieldsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TaskFields> findOne(Long id) {
        log.debug("Request to get TaskFields : {}", id);
        return taskFieldsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TaskFields : {}", id);
        taskFieldsRepository.deleteById(id);
    }
}
