package com.sang9xpro.autopro.service.impl;

import com.sang9xpro.autopro.domain.Applications;
import com.sang9xpro.autopro.repository.ApplicationsRepository;
import com.sang9xpro.autopro.service.ApplicationsService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Applications}.
 */
@Service
@Transactional
public class ApplicationsServiceImpl implements ApplicationsService {

    private final Logger log = LoggerFactory.getLogger(ApplicationsServiceImpl.class);

    private final ApplicationsRepository applicationsRepository;

    public ApplicationsServiceImpl(ApplicationsRepository applicationsRepository) {
        this.applicationsRepository = applicationsRepository;
    }

    @Override
    public Applications save(Applications applications) {
        log.debug("Request to save Applications : {}", applications);
        return applicationsRepository.save(applications);
    }

    @Override
    public Optional<Applications> partialUpdate(Applications applications) {
        log.debug("Request to partially update Applications : {}", applications);

        return applicationsRepository
            .findById(applications.getId())
            .map(existingApplications -> {
                if (applications.getAppName() != null) {
                    existingApplications.setAppName(applications.getAppName());
                }
                if (applications.getAppCode() != null) {
                    existingApplications.setAppCode(applications.getAppCode());
                }

                return existingApplications;
            })
            .map(applicationsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Applications> findAll() {
        log.debug("Request to get all Applications");
        return applicationsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Applications> findOne(Long id) {
        log.debug("Request to get Applications : {}", id);
        return applicationsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Applications : {}", id);
        applicationsRepository.deleteById(id);
    }
}
