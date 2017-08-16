package com.arnaugarcia.halospainleague.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.halospainleague.domain.SocialAccount;

import com.arnaugarcia.halospainleague.repository.SocialAccountRepository;
import com.arnaugarcia.halospainleague.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SocialAccount.
 */
@RestController
@RequestMapping("/api")
public class SocialAccountResource {

    private final Logger log = LoggerFactory.getLogger(SocialAccountResource.class);

    private static final String ENTITY_NAME = "socialAccount";

    private final SocialAccountRepository socialAccountRepository;

    public SocialAccountResource(SocialAccountRepository socialAccountRepository) {
        this.socialAccountRepository = socialAccountRepository;
    }

    /**
     * POST  /social-accounts : Create a new socialAccount.
     *
     * @param socialAccount the socialAccount to create
     * @return the ResponseEntity with status 201 (Created) and with body the new socialAccount, or with status 400 (Bad Request) if the socialAccount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/social-accounts")
    @Timed
    public ResponseEntity<SocialAccount> createSocialAccount(@RequestBody SocialAccount socialAccount) throws URISyntaxException {
        log.debug("REST request to save SocialAccount : {}", socialAccount);
        if (socialAccount.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new socialAccount cannot already have an ID")).body(null);
        }
        SocialAccount result = socialAccountRepository.save(socialAccount);
        return ResponseEntity.created(new URI("/api/social-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /social-accounts : Updates an existing socialAccount.
     *
     * @param socialAccount the socialAccount to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated socialAccount,
     * or with status 400 (Bad Request) if the socialAccount is not valid,
     * or with status 500 (Internal Server Error) if the socialAccount couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/social-accounts")
    @Timed
    public ResponseEntity<SocialAccount> updateSocialAccount(@RequestBody SocialAccount socialAccount) throws URISyntaxException {
        log.debug("REST request to update SocialAccount : {}", socialAccount);
        if (socialAccount.getId() == null) {
            return createSocialAccount(socialAccount);
        }
        SocialAccount result = socialAccountRepository.save(socialAccount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, socialAccount.getId().toString()))
            .body(result);
    }

    /**
     * GET  /social-accounts : get all the socialAccounts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of socialAccounts in body
     */
    @GetMapping("/social-accounts")
    @Timed
    public List<SocialAccount> getAllSocialAccounts() {
        log.debug("REST request to get all SocialAccounts");
        return socialAccountRepository.findAll();
    }

    /**
     * GET  /social-accounts/:id : get the "id" socialAccount.
     *
     * @param id the id of the socialAccount to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the socialAccount, or with status 404 (Not Found)
     */
    @GetMapping("/social-accounts/{id}")
    @Timed
    public ResponseEntity<SocialAccount> getSocialAccount(@PathVariable Long id) {
        log.debug("REST request to get SocialAccount : {}", id);
        SocialAccount socialAccount = socialAccountRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(socialAccount));
    }

    /**
     * DELETE  /social-accounts/:id : delete the "id" socialAccount.
     *
     * @param id the id of the socialAccount to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/social-accounts/{id}")
    @Timed
    public ResponseEntity<Void> deleteSocialAccount(@PathVariable Long id) {
        log.debug("REST request to delete SocialAccount : {}", id);
        socialAccountRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
