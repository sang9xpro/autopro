package com.sang9xpro.autopro.service.impl;

import com.sang9xpro.autopro.domain.SchedulerTaskValues;
import com.sang9xpro.autopro.repository.SchedulerTaskValuesRepository;
import com.sang9xpro.autopro.service.SchedulerTaskValuesService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SchedulerTaskValues}.
 */
@Service
@Transactional
public class SchedulerTaskValuesServiceImpl implements SchedulerTaskValuesService {

    private final Logger log = LoggerFactory.getLogger(SchedulerTaskValuesServiceImpl.class);

    private final SchedulerTaskValuesRepository schedulerTaskValuesRepository;

    public SchedulerTaskValuesServiceImpl(SchedulerTaskValuesRepository schedulerTaskValuesRepository) {
        this.schedulerTaskValuesRepository = schedulerTaskValuesRepository;
    }

    @Override
    public SchedulerTaskValues save(SchedulerTaskValues schedulerTaskValues) {
        log.debug("Request to save SchedulerTaskValues : {}", schedulerTaskValues);
        return schedulerTaskValuesRepository.save(schedulerTaskValues);
    }

    @Override
    public Optional<SchedulerTaskValues> partialUpdate(SchedulerTaskValues schedulerTaskValues) {
        log.debug("Request to partially update SchedulerTaskValues : {}", schedulerTaskValues);

        return schedulerTaskValuesRepository
            .findById(schedulerTaskValues.getId())
            .map(existingSchedulerTaskValues -> {
                if (schedulerTaskValues.getValue() != null) {
                    existingSchedulerTaskValues.setValue(schedulerTaskValues.getValue());
                }

                return existingSchedulerTaskValues;
            })
            .map(schedulerTaskValuesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SchedulerTaskValues> findAll() {
        log.debug("Request to get all SchedulerTaskValues");
        return schedulerTaskValuesRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SchedulerTaskValues> findOne(Long id) {
        log.debug("Request to get SchedulerTaskValues : {}", id);
        return schedulerTaskValuesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SchedulerTaskValues : {}", id);
        schedulerTaskValuesRepository.deleteById(id);
    }
}
