package com.sang9xpro.autopro.service.impl;

import com.sang9xpro.autopro.domain.FacebookValues;
import com.sang9xpro.autopro.repository.FacebookValuesRepository;
import com.sang9xpro.autopro.service.FacebookValuesService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FacebookValues}.
 */
@Service
@Transactional
public class FacebookValuesServiceImpl implements FacebookValuesService {

    private final Logger log = LoggerFactory.getLogger(FacebookValuesServiceImpl.class);

    private final FacebookValuesRepository facebookValuesRepository;

    public FacebookValuesServiceImpl(FacebookValuesRepository facebookValuesRepository) {
        this.facebookValuesRepository = facebookValuesRepository;
    }

    @Override
    public FacebookValues save(FacebookValues facebookValues) {
        log.debug("Request to save FacebookValues : {}", facebookValues);
        return facebookValuesRepository.save(facebookValues);
    }

    @Override
    public Optional<FacebookValues> partialUpdate(FacebookValues facebookValues) {
        log.debug("Request to partially update FacebookValues : {}", facebookValues);

        return facebookValuesRepository
            .findById(facebookValues.getId())
            .map(existingFacebookValues -> {
                if (facebookValues.getValue() != null) {
                    existingFacebookValues.setValue(facebookValues.getValue());
                }

                return existingFacebookValues;
            })
            .map(facebookValuesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FacebookValues> findAll() {
        log.debug("Request to get all FacebookValues");
        return facebookValuesRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FacebookValues> findOne(Long id) {
        log.debug("Request to get FacebookValues : {}", id);
        return facebookValuesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FacebookValues : {}", id);
        facebookValuesRepository.deleteById(id);
    }
}
