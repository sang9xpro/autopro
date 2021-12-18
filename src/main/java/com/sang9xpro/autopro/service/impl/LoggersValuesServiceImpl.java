package com.sang9xpro.autopro.service.impl;

import com.sang9xpro.autopro.domain.LoggersValues;
import com.sang9xpro.autopro.repository.LoggersValuesRepository;
import com.sang9xpro.autopro.service.LoggersValuesService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LoggersValues}.
 */
@Service
@Transactional
public class LoggersValuesServiceImpl implements LoggersValuesService {

    private final Logger log = LoggerFactory.getLogger(LoggersValuesServiceImpl.class);

    private final LoggersValuesRepository loggersValuesRepository;

    public LoggersValuesServiceImpl(LoggersValuesRepository loggersValuesRepository) {
        this.loggersValuesRepository = loggersValuesRepository;
    }

    @Override
    public LoggersValues save(LoggersValues loggersValues) {
        log.debug("Request to save LoggersValues : {}", loggersValues);
        return loggersValuesRepository.save(loggersValues);
    }

    @Override
    public Optional<LoggersValues> partialUpdate(LoggersValues loggersValues) {
        log.debug("Request to partially update LoggersValues : {}", loggersValues);

        return loggersValuesRepository
            .findById(loggersValues.getId())
            .map(existingLoggersValues -> {
                if (loggersValues.getValue() != null) {
                    existingLoggersValues.setValue(loggersValues.getValue());
                }

                return existingLoggersValues;
            })
            .map(loggersValuesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoggersValues> findAll() {
        log.debug("Request to get all LoggersValues");
        return loggersValuesRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LoggersValues> findOne(Long id) {
        log.debug("Request to get LoggersValues : {}", id);
        return loggersValuesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LoggersValues : {}", id);
        loggersValuesRepository.deleteById(id);
    }
}
