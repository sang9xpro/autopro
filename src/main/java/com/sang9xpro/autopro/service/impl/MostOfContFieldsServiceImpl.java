package com.sang9xpro.autopro.service.impl;

import com.sang9xpro.autopro.domain.MostOfContFields;
import com.sang9xpro.autopro.repository.MostOfContFieldsRepository;
import com.sang9xpro.autopro.service.MostOfContFieldsService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MostOfContFields}.
 */
@Service
@Transactional
public class MostOfContFieldsServiceImpl implements MostOfContFieldsService {

    private final Logger log = LoggerFactory.getLogger(MostOfContFieldsServiceImpl.class);

    private final MostOfContFieldsRepository mostOfContFieldsRepository;

    public MostOfContFieldsServiceImpl(MostOfContFieldsRepository mostOfContFieldsRepository) {
        this.mostOfContFieldsRepository = mostOfContFieldsRepository;
    }

    @Override
    public MostOfContFields save(MostOfContFields mostOfContFields) {
        log.debug("Request to save MostOfContFields : {}", mostOfContFields);
        return mostOfContFieldsRepository.save(mostOfContFields);
    }

    @Override
    public Optional<MostOfContFields> partialUpdate(MostOfContFields mostOfContFields) {
        log.debug("Request to partially update MostOfContFields : {}", mostOfContFields);

        return mostOfContFieldsRepository
            .findById(mostOfContFields.getId())
            .map(existingMostOfContFields -> {
                if (mostOfContFields.getFieldName() != null) {
                    existingMostOfContFields.setFieldName(mostOfContFields.getFieldName());
                }

                return existingMostOfContFields;
            })
            .map(mostOfContFieldsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MostOfContFields> findAll() {
        log.debug("Request to get all MostOfContFields");
        return mostOfContFieldsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MostOfContFields> findOne(Long id) {
        log.debug("Request to get MostOfContFields : {}", id);
        return mostOfContFieldsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MostOfContFields : {}", id);
        mostOfContFieldsRepository.deleteById(id);
    }
}
