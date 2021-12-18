package com.sang9xpro.autopro.service.impl;

import com.sang9xpro.autopro.domain.HistoryValues;
import com.sang9xpro.autopro.repository.HistoryValuesRepository;
import com.sang9xpro.autopro.service.HistoryValuesService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link HistoryValues}.
 */
@Service
@Transactional
public class HistoryValuesServiceImpl implements HistoryValuesService {

    private final Logger log = LoggerFactory.getLogger(HistoryValuesServiceImpl.class);

    private final HistoryValuesRepository historyValuesRepository;

    public HistoryValuesServiceImpl(HistoryValuesRepository historyValuesRepository) {
        this.historyValuesRepository = historyValuesRepository;
    }

    @Override
    public HistoryValues save(HistoryValues historyValues) {
        log.debug("Request to save HistoryValues : {}", historyValues);
        return historyValuesRepository.save(historyValues);
    }

    @Override
    public Optional<HistoryValues> partialUpdate(HistoryValues historyValues) {
        log.debug("Request to partially update HistoryValues : {}", historyValues);

        return historyValuesRepository
            .findById(historyValues.getId())
            .map(existingHistoryValues -> {
                if (historyValues.getValue() != null) {
                    existingHistoryValues.setValue(historyValues.getValue());
                }

                return existingHistoryValues;
            })
            .map(historyValuesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistoryValues> findAll() {
        log.debug("Request to get all HistoryValues");
        return historyValuesRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HistoryValues> findOne(Long id) {
        log.debug("Request to get HistoryValues : {}", id);
        return historyValuesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete HistoryValues : {}", id);
        historyValuesRepository.deleteById(id);
    }
}
