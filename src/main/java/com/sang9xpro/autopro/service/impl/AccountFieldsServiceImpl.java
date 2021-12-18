package com.sang9xpro.autopro.service.impl;

import com.sang9xpro.autopro.domain.AccountFields;
import com.sang9xpro.autopro.repository.AccountFieldsRepository;
import com.sang9xpro.autopro.service.AccountFieldsService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AccountFields}.
 */
@Service
@Transactional
public class AccountFieldsServiceImpl implements AccountFieldsService {

    private final Logger log = LoggerFactory.getLogger(AccountFieldsServiceImpl.class);

    private final AccountFieldsRepository accountFieldsRepository;

    public AccountFieldsServiceImpl(AccountFieldsRepository accountFieldsRepository) {
        this.accountFieldsRepository = accountFieldsRepository;
    }

    @Override
    public AccountFields save(AccountFields accountFields) {
        log.debug("Request to save AccountFields : {}", accountFields);
        return accountFieldsRepository.save(accountFields);
    }

    @Override
    public Optional<AccountFields> partialUpdate(AccountFields accountFields) {
        log.debug("Request to partially update AccountFields : {}", accountFields);

        return accountFieldsRepository
            .findById(accountFields.getId())
            .map(existingAccountFields -> {
                if (accountFields.getFieldName() != null) {
                    existingAccountFields.setFieldName(accountFields.getFieldName());
                }

                return existingAccountFields;
            })
            .map(accountFieldsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountFields> findAll() {
        log.debug("Request to get all AccountFields");
        return accountFieldsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AccountFields> findOne(Long id) {
        log.debug("Request to get AccountFields : {}", id);
        return accountFieldsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AccountFields : {}", id);
        accountFieldsRepository.deleteById(id);
    }
}
