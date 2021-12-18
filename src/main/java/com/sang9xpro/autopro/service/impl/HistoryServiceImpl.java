package com.sang9xpro.autopro.service.impl;

import com.sang9xpro.autopro.domain.History;
import com.sang9xpro.autopro.repository.HistoryRepository;
import com.sang9xpro.autopro.service.HistoryService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link History}.
 */
@Service
@Transactional
public class HistoryServiceImpl implements HistoryService {

    private final Logger log = LoggerFactory.getLogger(HistoryServiceImpl.class);

    private final HistoryRepository historyRepository;

    public HistoryServiceImpl(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Override
    public History save(History history) {
        log.debug("Request to save History : {}", history);
        return historyRepository.save(history);
    }

    @Override
    public Optional<History> partialUpdate(History history) {
        log.debug("Request to partially update History : {}", history);

        return historyRepository
            .findById(history.getId())
            .map(existingHistory -> {
                if (history.getUrl() != null) {
                    existingHistory.setUrl(history.getUrl());
                }
                if (history.getTaskId() != null) {
                    existingHistory.setTaskId(history.getTaskId());
                }
                if (history.getDeviceId() != null) {
                    existingHistory.setDeviceId(history.getDeviceId());
                }
                if (history.getAccountId() != null) {
                    existingHistory.setAccountId(history.getAccountId());
                }
                if (history.getLastUpdate() != null) {
                    existingHistory.setLastUpdate(history.getLastUpdate());
                }

                return existingHistory;
            })
            .map(historyRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<History> findAll() {
        log.debug("Request to get all Histories");
        return historyRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<History> findOne(Long id) {
        log.debug("Request to get History : {}", id);
        return historyRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete History : {}", id);
        historyRepository.deleteById(id);
    }
}
