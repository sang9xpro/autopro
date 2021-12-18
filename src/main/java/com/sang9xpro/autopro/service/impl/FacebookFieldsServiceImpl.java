package com.sang9xpro.autopro.service.impl;

import com.sang9xpro.autopro.domain.FacebookFields;
import com.sang9xpro.autopro.repository.FacebookFieldsRepository;
import com.sang9xpro.autopro.service.FacebookFieldsService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FacebookFields}.
 */
@Service
@Transactional
public class FacebookFieldsServiceImpl implements FacebookFieldsService {

    private final Logger log = LoggerFactory.getLogger(FacebookFieldsServiceImpl.class);

    private final FacebookFieldsRepository facebookFieldsRepository;

    public FacebookFieldsServiceImpl(FacebookFieldsRepository facebookFieldsRepository) {
        this.facebookFieldsRepository = facebookFieldsRepository;
    }

    @Override
    public FacebookFields save(FacebookFields facebookFields) {
        log.debug("Request to save FacebookFields : {}", facebookFields);
        return facebookFieldsRepository.save(facebookFields);
    }

    @Override
    public Optional<FacebookFields> partialUpdate(FacebookFields facebookFields) {
        log.debug("Request to partially update FacebookFields : {}", facebookFields);

        return facebookFieldsRepository
            .findById(facebookFields.getId())
            .map(existingFacebookFields -> {
                if (facebookFields.getFieldName() != null) {
                    existingFacebookFields.setFieldName(facebookFields.getFieldName());
                }

                return existingFacebookFields;
            })
            .map(facebookFieldsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FacebookFields> findAll() {
        log.debug("Request to get all FacebookFields");
        return facebookFieldsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FacebookFields> findOne(Long id) {
        log.debug("Request to get FacebookFields : {}", id);
        return facebookFieldsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FacebookFields : {}", id);
        facebookFieldsRepository.deleteById(id);
    }
}
