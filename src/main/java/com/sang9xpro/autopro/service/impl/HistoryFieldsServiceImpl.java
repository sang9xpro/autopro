package com.sang9xpro.autopro.service.impl;

import com.sang9xpro.autopro.domain.HistoryFields;
import com.sang9xpro.autopro.repository.HistoryFieldsRepository;
import com.sang9xpro.autopro.service.HistoryFieldsService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link HistoryFields}.
 */
@Service
@Transactional
public class HistoryFieldsServiceImpl implements HistoryFieldsService {

    private final Logger log = LoggerFactory.getLogger(HistoryFieldsServiceImpl.class);

    private final HistoryFieldsRepository historyFieldsRepository;

    public HistoryFieldsServiceImpl(HistoryFieldsRepository historyFieldsRepository) {
        this.historyFieldsRepository = historyFieldsRepository;
    }

    @Override
    public HistoryFields save(HistoryFields historyFields) {
        log.debug("Request to save HistoryFields : {}", historyFields);
        return historyFieldsRepository.save(historyFields);
    }

    @Override
    public Optional<HistoryFields> partialUpdate(HistoryFields historyFields) {
        log.debug("Request to partially update HistoryFields : {}", historyFields);

        return historyFieldsRepository
            .findById(historyFields.getId())
            .map(existingHistoryFields -> {
                if (historyFields.getFieldName() != null) {
                    existingHistoryFields.setFieldName(historyFields.getFieldName());
                }

                return existingHistoryFields;
            })
            .map(historyFieldsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistoryFields> findAll() {
        log.debug("Request to get all HistoryFields");
        return historyFieldsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HistoryFields> findOne(Long id) {
        log.debug("Request to get HistoryFields : {}", id);
        return historyFieldsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete HistoryFields : {}", id);
        historyFieldsRepository.deleteById(id);
    }
}
