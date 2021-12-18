package com.sang9xpro.autopro.service.impl;

import com.sang9xpro.autopro.domain.ApplicationsValues;
import com.sang9xpro.autopro.repository.ApplicationsValuesRepository;
import com.sang9xpro.autopro.service.ApplicationsValuesService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ApplicationsValues}.
 */
@Service
@Transactional
public class ApplicationsValuesServiceImpl implements ApplicationsValuesService {

    private final Logger log = LoggerFactory.getLogger(ApplicationsValuesServiceImpl.class);

    private final ApplicationsValuesRepository applicationsValuesRepository;

    public ApplicationsValuesServiceImpl(ApplicationsValuesRepository applicationsValuesRepository) {
        this.applicationsValuesRepository = applicationsValuesRepository;
    }

    @Override
    public ApplicationsValues save(ApplicationsValues applicationsValues) {
        log.debug("Request to save ApplicationsValues : {}", applicationsValues);
        return applicationsValuesRepository.save(applicationsValues);
    }

    @Override
    public Optional<ApplicationsValues> partialUpdate(ApplicationsValues applicationsValues) {
        log.debug("Request to partially update ApplicationsValues : {}", applicationsValues);

        return applicationsValuesRepository
            .findById(applicationsValues.getId())
            .map(existingApplicationsValues -> {
                if (applicationsValues.getValue() != null) {
                    existingApplicationsValues.setValue(applicationsValues.getValue());
                }

                return existingApplicationsValues;
            })
            .map(applicationsValuesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationsValues> findAll() {
        log.debug("Request to get all ApplicationsValues");
        return applicationsValuesRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicationsValues> findOne(Long id) {
        log.debug("Request to get ApplicationsValues : {}", id);
        return applicationsValuesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApplicationsValues : {}", id);
        applicationsValuesRepository.deleteById(id);
    }
}
