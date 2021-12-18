package com.sang9xpro.autopro.service.impl;

import com.sang9xpro.autopro.domain.MostOfContValues;
import com.sang9xpro.autopro.repository.MostOfContValuesRepository;
import com.sang9xpro.autopro.service.MostOfContValuesService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MostOfContValues}.
 */
@Service
@Transactional
public class MostOfContValuesServiceImpl implements MostOfContValuesService {

    private final Logger log = LoggerFactory.getLogger(MostOfContValuesServiceImpl.class);

    private final MostOfContValuesRepository mostOfContValuesRepository;

    public MostOfContValuesServiceImpl(MostOfContValuesRepository mostOfContValuesRepository) {
        this.mostOfContValuesRepository = mostOfContValuesRepository;
    }

    @Override
    public MostOfContValues save(MostOfContValues mostOfContValues) {
        log.debug("Request to save MostOfContValues : {}", mostOfContValues);
        return mostOfContValuesRepository.save(mostOfContValues);
    }

    @Override
    public Optional<MostOfContValues> partialUpdate(MostOfContValues mostOfContValues) {
        log.debug("Request to partially update MostOfContValues : {}", mostOfContValues);

        return mostOfContValuesRepository
            .findById(mostOfContValues.getId())
            .map(existingMostOfContValues -> {
                if (mostOfContValues.getValue() != null) {
                    existingMostOfContValues.setValue(mostOfContValues.getValue());
                }

                return existingMostOfContValues;
            })
            .map(mostOfContValuesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MostOfContValues> findAll() {
        log.debug("Request to get all MostOfContValues");
        return mostOfContValuesRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MostOfContValues> findOne(Long id) {
        log.debug("Request to get MostOfContValues : {}", id);
        return mostOfContValuesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MostOfContValues : {}", id);
        mostOfContValuesRepository.deleteById(id);
    }
}
