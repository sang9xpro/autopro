package com.sang9xpro.autopro.service.impl;

import com.sang9xpro.autopro.domain.LoggersFields;
import com.sang9xpro.autopro.repository.LoggersFieldsRepository;
import com.sang9xpro.autopro.service.LoggersFieldsService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LoggersFields}.
 */
@Service
@Transactional
public class LoggersFieldsServiceImpl implements LoggersFieldsService {

    private final Logger log = LoggerFactory.getLogger(LoggersFieldsServiceImpl.class);

    private final LoggersFieldsRepository loggersFieldsRepository;

    public LoggersFieldsServiceImpl(LoggersFieldsRepository loggersFieldsRepository) {
        this.loggersFieldsRepository = loggersFieldsRepository;
    }

    @Override
    public LoggersFields save(LoggersFields loggersFields) {
        log.debug("Request to save LoggersFields : {}", loggersFields);
        return loggersFieldsRepository.save(loggersFields);
    }

    @Override
    public Optional<LoggersFields> partialUpdate(LoggersFields loggersFields) {
        log.debug("Request to partially update LoggersFields : {}", loggersFields);

        return loggersFieldsRepository
            .findById(loggersFields.getId())
            .map(existingLoggersFields -> {
                if (loggersFields.getFieldName() != null) {
                    existingLoggersFields.setFieldName(loggersFields.getFieldName());
                }

                return existingLoggersFields;
            })
            .map(loggersFieldsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoggersFields> findAll() {
        log.debug("Request to get all LoggersFields");
        return loggersFieldsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LoggersFields> findOne(Long id) {
        log.debug("Request to get LoggersFields : {}", id);
        return loggersFieldsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LoggersFields : {}", id);
        loggersFieldsRepository.deleteById(id);
    }
}
