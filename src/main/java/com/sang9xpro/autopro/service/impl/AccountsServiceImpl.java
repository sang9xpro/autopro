package com.sang9xpro.autopro.service.impl;

import com.sang9xpro.autopro.domain.Accounts;
import com.sang9xpro.autopro.repository.AccountsRepository;
import com.sang9xpro.autopro.service.AccountsService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Accounts}.
 */
@Service
@Transactional
public class AccountsServiceImpl implements AccountsService {

    private final Logger log = LoggerFactory.getLogger(AccountsServiceImpl.class);

    private final AccountsRepository accountsRepository;

    public AccountsServiceImpl(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    @Override
    public Accounts save(Accounts accounts) {
        log.debug("Request to save Accounts : {}", accounts);
        return accountsRepository.save(accounts);
    }

    @Override
    public Optional<Accounts> partialUpdate(Accounts accounts) {
        log.debug("Request to partially update Accounts : {}", accounts);

        return accountsRepository
            .findById(accounts.getId())
            .map(existingAccounts -> {
                if (accounts.getUserName() != null) {
                    existingAccounts.setUserName(accounts.getUserName());
                }
                if (accounts.getPassword() != null) {
                    existingAccounts.setPassword(accounts.getPassword());
                }
                if (accounts.getUrlLogin() != null) {
                    existingAccounts.setUrlLogin(accounts.getUrlLogin());
                }
                if (accounts.getProfileFirefox() != null) {
                    existingAccounts.setProfileFirefox(accounts.getProfileFirefox());
                }
                if (accounts.getProfileChrome() != null) {
                    existingAccounts.setProfileChrome(accounts.getProfileChrome());
                }
                if (accounts.getLastUpdate() != null) {
                    existingAccounts.setLastUpdate(accounts.getLastUpdate());
                }
                if (accounts.getOwner() != null) {
                    existingAccounts.setOwner(accounts.getOwner());
                }
                if (accounts.getActived() != null) {
                    existingAccounts.setActived(accounts.getActived());
                }

                return existingAccounts;
            })
            .map(accountsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Accounts> findAll() {
        log.debug("Request to get all Accounts");
        return accountsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Accounts> findOne(Long id) {
        log.debug("Request to get Accounts : {}", id);
        return accountsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Accounts : {}", id);
        accountsRepository.deleteById(id);
    }
}
