package com.sang9xpro.autopro.web.rest;

import com.sang9xpro.autopro.domain.AccountValues;
import com.sang9xpro.autopro.repository.AccountValuesRepository;
import com.sang9xpro.autopro.service.AccountValuesService;
import com.sang9xpro.autopro.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.sang9xpro.autopro.domain.AccountValues}.
 */
@RestController
@RequestMapping("/api")
public class AccountValuesResource {

    private final Logger log = LoggerFactory.getLogger(AccountValuesResource.class);

    private static final String ENTITY_NAME = "accountValues";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccountValuesService accountValuesService;

    private final AccountValuesRepository accountValuesRepository;

    public AccountValuesResource(AccountValuesService accountValuesService, AccountValuesRepository accountValuesRepository) {
        this.accountValuesService = accountValuesService;
        this.accountValuesRepository = accountValuesRepository;
    }

    /**
     * {@code POST  /account-values} : Create a new accountValues.
     *
     * @param accountValues the accountValues to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accountValues, or with status {@code 400 (Bad Request)} if the accountValues has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/account-values")
    public ResponseEntity<AccountValues> createAccountValues(@RequestBody AccountValues accountValues) throws URISyntaxException {
        log.debug("REST request to save AccountValues : {}", accountValues);
        if (accountValues.getId() != null) {
            throw new BadRequestAlertException("A new accountValues cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountValues result = accountValuesService.save(accountValues);
        return ResponseEntity
            .created(new URI("/api/account-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /account-values/:id} : Updates an existing accountValues.
     *
     * @param id the id of the accountValues to save.
     * @param accountValues the accountValues to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountValues,
     * or with status {@code 400 (Bad Request)} if the accountValues is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accountValues couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/account-values/{id}")
    public ResponseEntity<AccountValues> updateAccountValues(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AccountValues accountValues
    ) throws URISyntaxException {
        log.debug("REST request to update AccountValues : {}, {}", id, accountValues);
        if (accountValues.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accountValues.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accountValuesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AccountValues result = accountValuesService.save(accountValues);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountValues.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /account-values/:id} : Partial updates given fields of an existing accountValues, field will ignore if it is null
     *
     * @param id the id of the accountValues to save.
     * @param accountValues the accountValues to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountValues,
     * or with status {@code 400 (Bad Request)} if the accountValues is not valid,
     * or with status {@code 404 (Not Found)} if the accountValues is not found,
     * or with status {@code 500 (Internal Server Error)} if the accountValues couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/account-values/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AccountValues> partialUpdateAccountValues(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AccountValues accountValues
    ) throws URISyntaxException {
        log.debug("REST request to partial update AccountValues partially : {}, {}", id, accountValues);
        if (accountValues.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accountValues.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accountValuesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AccountValues> result = accountValuesService.partialUpdate(accountValues);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountValues.getId().toString())
        );
    }

    /**
     * {@code GET  /account-values} : get all the accountValues.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accountValues in body.
     */
    @GetMapping("/account-values")
    public List<AccountValues> getAllAccountValues() {
        log.debug("REST request to get all AccountValues");
        return accountValuesService.findAll();
    }

    /**
     * {@code GET  /account-values/:id} : get the "id" accountValues.
     *
     * @param id the id of the accountValues to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountValues, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/account-values/{id}")
    public ResponseEntity<AccountValues> getAccountValues(@PathVariable Long id) {
        log.debug("REST request to get AccountValues : {}", id);
        Optional<AccountValues> accountValues = accountValuesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accountValues);
    }

    /**
     * {@code DELETE  /account-values/:id} : delete the "id" accountValues.
     *
     * @param id the id of the accountValues to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/account-values/{id}")
    public ResponseEntity<Void> deleteAccountValues(@PathVariable Long id) {
        log.debug("REST request to delete AccountValues : {}", id);
        accountValuesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
