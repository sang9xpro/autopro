package com.sang9xpro.autopro.web.rest;

import com.sang9xpro.autopro.domain.AccountFields;
import com.sang9xpro.autopro.repository.AccountFieldsRepository;
import com.sang9xpro.autopro.service.AccountFieldsService;
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
 * REST controller for managing {@link com.sang9xpro.autopro.domain.AccountFields}.
 */
@RestController
@RequestMapping("/api")
public class AccountFieldsResource {

    private final Logger log = LoggerFactory.getLogger(AccountFieldsResource.class);

    private static final String ENTITY_NAME = "accountFields";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccountFieldsService accountFieldsService;

    private final AccountFieldsRepository accountFieldsRepository;

    public AccountFieldsResource(AccountFieldsService accountFieldsService, AccountFieldsRepository accountFieldsRepository) {
        this.accountFieldsService = accountFieldsService;
        this.accountFieldsRepository = accountFieldsRepository;
    }

    /**
     * {@code POST  /account-fields} : Create a new accountFields.
     *
     * @param accountFields the accountFields to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accountFields, or with status {@code 400 (Bad Request)} if the accountFields has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/account-fields")
    public ResponseEntity<AccountFields> createAccountFields(@RequestBody AccountFields accountFields) throws URISyntaxException {
        log.debug("REST request to save AccountFields : {}", accountFields);
        if (accountFields.getId() != null) {
            throw new BadRequestAlertException("A new accountFields cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountFields result = accountFieldsService.save(accountFields);
        return ResponseEntity
            .created(new URI("/api/account-fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /account-fields/:id} : Updates an existing accountFields.
     *
     * @param id the id of the accountFields to save.
     * @param accountFields the accountFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountFields,
     * or with status {@code 400 (Bad Request)} if the accountFields is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accountFields couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/account-fields/{id}")
    public ResponseEntity<AccountFields> updateAccountFields(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AccountFields accountFields
    ) throws URISyntaxException {
        log.debug("REST request to update AccountFields : {}, {}", id, accountFields);
        if (accountFields.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accountFields.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accountFieldsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AccountFields result = accountFieldsService.save(accountFields);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountFields.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /account-fields/:id} : Partial updates given fields of an existing accountFields, field will ignore if it is null
     *
     * @param id the id of the accountFields to save.
     * @param accountFields the accountFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountFields,
     * or with status {@code 400 (Bad Request)} if the accountFields is not valid,
     * or with status {@code 404 (Not Found)} if the accountFields is not found,
     * or with status {@code 500 (Internal Server Error)} if the accountFields couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/account-fields/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AccountFields> partialUpdateAccountFields(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AccountFields accountFields
    ) throws URISyntaxException {
        log.debug("REST request to partial update AccountFields partially : {}, {}", id, accountFields);
        if (accountFields.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accountFields.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accountFieldsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AccountFields> result = accountFieldsService.partialUpdate(accountFields);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountFields.getId().toString())
        );
    }

    /**
     * {@code GET  /account-fields} : get all the accountFields.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accountFields in body.
     */
    @GetMapping("/account-fields")
    public List<AccountFields> getAllAccountFields() {
        log.debug("REST request to get all AccountFields");
        return accountFieldsService.findAll();
    }

    /**
     * {@code GET  /account-fields/:id} : get the "id" accountFields.
     *
     * @param id the id of the accountFields to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountFields, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/account-fields/{id}")
    public ResponseEntity<AccountFields> getAccountFields(@PathVariable Long id) {
        log.debug("REST request to get AccountFields : {}", id);
        Optional<AccountFields> accountFields = accountFieldsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accountFields);
    }

    /**
     * {@code DELETE  /account-fields/:id} : delete the "id" accountFields.
     *
     * @param id the id of the accountFields to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/account-fields/{id}")
    public ResponseEntity<Void> deleteAccountFields(@PathVariable Long id) {
        log.debug("REST request to delete AccountFields : {}", id);
        accountFieldsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
