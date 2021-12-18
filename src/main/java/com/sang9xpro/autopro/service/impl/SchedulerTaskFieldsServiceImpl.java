package com.sang9xpro.autopro.service.impl;

import com.sang9xpro.autopro.domain.SchedulerTaskFields;
import com.sang9xpro.autopro.repository.SchedulerTaskFieldsRepository;
import com.sang9xpro.autopro.service.SchedulerTaskFieldsService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SchedulerTaskFields}.
 */
@Service
@Transactional
public class SchedulerTaskFieldsServiceImpl implements SchedulerTaskFieldsService {

    private final Logger log = LoggerFactory.getLogger(SchedulerTaskFieldsServiceImpl.class);

    private final SchedulerTaskFieldsRepository schedulerTaskFieldsRepository;

    public SchedulerTaskFieldsServiceImpl(SchedulerTaskFieldsRepository schedulerTaskFieldsRepository) {
        this.schedulerTaskFieldsRepository = schedulerTaskFieldsRepository;
    }

    @Override
    public SchedulerTaskFields save(SchedulerTaskFields schedulerTaskFields) {
        log.debug("Request to save SchedulerTaskFields : {}", schedulerTaskFields);
        return schedulerTaskFieldsRepository.save(schedulerTaskFields);
    }

    @Override
    public Optional<SchedulerTaskFields> partialUpdate(SchedulerTaskFields schedulerTaskFields) {
        log.debug("Request to partially update SchedulerTaskFields : {}", schedulerTaskFields);

        return schedulerTaskFieldsRepository
            .findById(schedulerTaskFields.getId())
            .map(existingSchedulerTaskFields -> {
                if (schedulerTaskFields.getFieldName() != null) {
                    existingSchedulerTaskFields.setFieldName(schedulerTaskFields.getFieldName());
                }

                return existingSchedulerTaskFields;
            })
            .map(schedulerTaskFieldsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SchedulerTaskFields> findAll() {
        log.debug("Request to get all SchedulerTaskFields");
        return schedulerTaskFieldsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SchedulerTaskFields> findOne(Long id) {
        log.debug("Request to get SchedulerTaskFields : {}", id);
        return schedulerTaskFieldsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SchedulerTaskFields : {}", id);
        schedulerTaskFieldsRepository.deleteById(id);
    }
}
