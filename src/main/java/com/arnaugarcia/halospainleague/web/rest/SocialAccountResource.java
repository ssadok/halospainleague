package com.arnaugarcia.halospainleague.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.halospainleague.domain.SocialAccount;

import com.arnaugarcia.halospainleague.repository.SocialAccountRepository;
import com.arnaugarcia.halospainleague.web.rest.util.HeaderUtil;
import com.arnaugarcia.halospainleague.service.dto.SocialAccountDTO;
import com.arnaugarcia.halospainleague.service.mapper.SocialAccountMapper;
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

    private final SocialAccountMapper socialAccountMapper;
    public SocialAccountResource(SocialAccountRepository socialAccountRepository, SocialAccountMapper socialAccountMapper) {
        this.socialAccountRepository = socialAccountRepository;
        this.socialAccountMapper = socialAccountMapper;
    }

    /**
     * POST  /social-accounts : Create a new socialAccount.
     *
     * @param socialAccountDTO the socialAccountDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new socialAccountDTO, or with status 400 (Bad Request) if the socialAccount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/social-accounts")
    @Timed
    public ResponseEntity<SocialAccountDTO> createSocialAccount(@RequestBody SocialAccountDTO socialAccountDTO) throws URISyntaxException {
        log.debug("REST request to save SocialAccount : {}", socialAccountDTO);
        if (socialAccountDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new socialAccount cannot already have an ID")).body(null);
        }
        SocialAccount socialAccount = socialAccountMapper.toEntity(socialAccountDTO);
        socialAccount = socialAccountRepository.save(socialAccount);
        SocialAccountDTO result = socialAccountMapper.toDto(socialAccount);
        return ResponseEntity.created(new URI("/api/social-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /social-accounts : Updates an existing socialAccount.
     *
     * @param socialAccountDTO the socialAccountDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated socialAccountDTO,
     * or with status 400 (Bad Request) if the socialAccountDTO is not valid,
     * or with status 500 (Internal Server Error) if the socialAccountDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/social-accounts")
    @Timed
    public ResponseEntity<SocialAccountDTO> updateSocialAccount(@RequestBody SocialAccountDTO socialAccountDTO) throws URISyntaxException {
        log.debug("REST request to update SocialAccount : {}", socialAccountDTO);
        if (socialAccountDTO.getId() == null) {
            return createSocialAccount(socialAccountDTO);
        }
        SocialAccount socialAccount = socialAccountMapper.toEntity(socialAccountDTO);
        socialAccount = socialAccountRepository.save(socialAccount);
        SocialAccountDTO result = socialAccountMapper.toDto(socialAccount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, socialAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /social-accounts : get all the socialAccounts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of socialAccounts in body
     */
    @GetMapping("/social-accounts")
    @Timed
    public List<SocialAccountDTO> getAllSocialAccounts() {
        log.debug("REST request to get all SocialAccounts");
        List<SocialAccount> socialAccounts = socialAccountRepository.findAll();
        return socialAccountMapper.toDto(socialAccounts);
        }

    /**
     * GET  /social-accounts/:id : get the "id" socialAccount.
     *
     * @param id the id of the socialAccountDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the socialAccountDTO, or with status 404 (Not Found)
     */
    @GetMapping("/social-accounts/{id}")
    @Timed
    public ResponseEntity<SocialAccountDTO> getSocialAccount(@PathVariable Long id) {
        log.debug("REST request to get SocialAccount : {}", id);
        SocialAccount socialAccount = socialAccountRepository.findOne(id);
        SocialAccountDTO socialAccountDTO = socialAccountMapper.toDto(socialAccount);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(socialAccountDTO));
    }

    /**
     * DELETE  /social-accounts/:id : delete the "id" socialAccount.
     *
     * @param id the id of the socialAccountDTO to delete
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
