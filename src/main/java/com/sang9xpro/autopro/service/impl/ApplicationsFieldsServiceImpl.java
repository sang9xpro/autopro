package com.sang9xpro.autopro.service.impl;

import com.sang9xpro.autopro.domain.ApplicationsFields;
import com.sang9xpro.autopro.repository.ApplicationsFieldsRepository;
import com.sang9xpro.autopro.service.ApplicationsFieldsService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ApplicationsFields}.
 */
@Service
@Transactional
public class ApplicationsFieldsServiceImpl implements ApplicationsFieldsService {

    private final Logger log = LoggerFactory.getLogger(ApplicationsFieldsServiceImpl.class);

    private final ApplicationsFieldsRepository applicationsFieldsRepository;

    public ApplicationsFieldsServiceImpl(ApplicationsFieldsRepository applicationsFieldsRepository) {
        this.applicationsFieldsRepository = applicationsFieldsRepository;
    }

    @Override
    public ApplicationsFields save(ApplicationsFields applicationsFields) {
        log.debug("Request to save ApplicationsFields : {}", applicationsFields);
        return applicationsFieldsRepository.save(applicationsFields);
    }

    @Override
    public Optional<ApplicationsFields> partialUpdate(ApplicationsFields applicationsFields) {
        log.debug("Request to partially update ApplicationsFields : {}", applicationsFields);

        return applicationsFieldsRepository
            .findById(applicationsFields.getId())
            .map(existingApplicationsFields -> {
                if (applicationsFields.getFieldName() != null) {
                    existingApplicationsFields.setFieldName(applicationsFields.getFieldName());
                }

                return existingApplicationsFields;
            })
            .map(applicationsFieldsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationsFields> findAll() {
        log.debug("Request to get all ApplicationsFields");
        return applicationsFieldsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicationsFields> findOne(Long id) {
        log.debug("Request to get ApplicationsFields : {}", id);
        return applicationsFieldsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApplicationsFields : {}", id);
        applicationsFieldsRepository.deleteById(id);
    }
}
