package com.sang9xpro.autopro.service.impl;

import com.sang9xpro.autopro.domain.Loggers;
import com.sang9xpro.autopro.repository.LoggersRepository;
import com.sang9xpro.autopro.service.LoggersService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Loggers}.
 */
@Service
@Transactional
public class LoggersServiceImpl implements LoggersService {

    private final Logger log = LoggerFactory.getLogger(LoggersServiceImpl.class);

    private final LoggersRepository loggersRepository;

    public LoggersServiceImpl(LoggersRepository loggersRepository) {
        this.loggersRepository = loggersRepository;
    }

    @Override
    public Loggers save(Loggers loggers) {
        log.debug("Request to save Loggers : {}", loggers);
        return loggersRepository.save(loggers);
    }

    @Override
    public Optional<Loggers> partialUpdate(Loggers loggers) {
        log.debug("Request to partially update Loggers : {}", loggers);

        return loggersRepository
            .findById(loggers.getId())
            .map(existingLoggers -> {
                if (loggers.getStatus() != null) {
                    existingLoggers.setStatus(loggers.getStatus());
                }
                if (loggers.getLogDetail() != null) {
                    existingLoggers.setLogDetail(loggers.getLogDetail());
                }
                if (loggers.getLogDetailContentType() != null) {
                    existingLoggers.setLogDetailContentType(loggers.getLogDetailContentType());
                }
                if (loggers.getLastUpdate() != null) {
                    existingLoggers.setLastUpdate(loggers.getLastUpdate());
                }

                return existingLoggers;
            })
            .map(loggersRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Loggers> findAll() {
        log.debug("Request to get all Loggers");
        return loggersRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Loggers> findOne(Long id) {
        log.debug("Request to get Loggers : {}", id);
        return loggersRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Loggers : {}", id);
        loggersRepository.deleteById(id);
    }
}
