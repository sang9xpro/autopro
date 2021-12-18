package com.sang9xpro.autopro.service.impl;

import com.sang9xpro.autopro.domain.Facebook;
import com.sang9xpro.autopro.repository.FacebookRepository;
import com.sang9xpro.autopro.service.FacebookService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Facebook}.
 */
@Service
@Transactional
public class FacebookServiceImpl implements FacebookService {

    private final Logger log = LoggerFactory.getLogger(FacebookServiceImpl.class);

    private final FacebookRepository facebookRepository;

    public FacebookServiceImpl(FacebookRepository facebookRepository) {
        this.facebookRepository = facebookRepository;
    }

    @Override
    public Facebook save(Facebook facebook) {
        log.debug("Request to save Facebook : {}", facebook);
        return facebookRepository.save(facebook);
    }

    @Override
    public Optional<Facebook> partialUpdate(Facebook facebook) {
        log.debug("Request to partially update Facebook : {}", facebook);

        return facebookRepository
            .findById(facebook.getId())
            .map(existingFacebook -> {
                if (facebook.getName() != null) {
                    existingFacebook.setName(facebook.getName());
                }
                if (facebook.getUrl() != null) {
                    existingFacebook.setUrl(facebook.getUrl());
                }
                if (facebook.getIdOnFb() != null) {
                    existingFacebook.setIdOnFb(facebook.getIdOnFb());
                }
                if (facebook.getType() != null) {
                    existingFacebook.setType(facebook.getType());
                }

                return existingFacebook;
            })
            .map(facebookRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Facebook> findAll() {
        log.debug("Request to get all Facebooks");
        return facebookRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Facebook> findOne(Long id) {
        log.debug("Request to get Facebook : {}", id);
        return facebookRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Facebook : {}", id);
        facebookRepository.deleteById(id);
    }
}
