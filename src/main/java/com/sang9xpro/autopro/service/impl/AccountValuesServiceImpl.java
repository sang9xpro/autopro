package com.sang9xpro.autopro.service.impl;

import com.sang9xpro.autopro.domain.AccountValues;
import com.sang9xpro.autopro.repository.AccountValuesRepository;
import com.sang9xpro.autopro.service.AccountValuesService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AccountValues}.
 */
@Service
@Transactional
public class AccountValuesServiceImpl implements AccountValuesService {

    private final Logger log = LoggerFactory.getLogger(AccountValuesServiceImpl.class);

    private final AccountValuesRepository accountValuesRepository;

    public AccountValuesServiceImpl(AccountValuesRepository accountValuesRepository) {
        this.accountValuesRepository = accountValuesRepository;
    }

    @Override
    public AccountValues save(AccountValues accountValues) {
        log.debug("Request to save AccountValues : {}", accountValues);
        return accountValuesRepository.save(accountValues);
    }

    @Override
    public Optional<AccountValues> partialUpdate(AccountValues accountValues) {
        log.debug("Request to partially update AccountValues : {}", accountValues);

        return accountValuesRepository
            .findById(accountValues.getId())
            .map(existingAccountValues -> {
                if (accountValues.getValue() != null) {
                    existingAccountValues.setValue(accountValues.getValue());
                }

                return existingAccountValues;
            })
            .map(accountValuesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountValues> findAll() {
        log.debug("Request to get all AccountValues");
        return accountValuesRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AccountValues> findOne(Long id) {
        log.debug("Request to get AccountValues : {}", id);
        return accountValuesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AccountValues : {}", id);
        accountValuesRepository.deleteById(id);
    }
}
