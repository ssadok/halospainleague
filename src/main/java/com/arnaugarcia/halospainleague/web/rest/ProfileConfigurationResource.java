package com.arnaugarcia.halospainleague.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.halospainleague.domain.ProfileConfiguration;

import com.arnaugarcia.halospainleague.repository.ProfileConfigurationRepository;
import com.arnaugarcia.halospainleague.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ProfileConfiguration.
 */
@RestController
@RequestMapping("/api")
public class ProfileConfigurationResource {

    private final Logger log = LoggerFactory.getLogger(ProfileConfigurationResource.class);

    private static final String ENTITY_NAME = "profileConfiguration";

    private final ProfileConfigurationRepository profileConfigurationRepository;

    public ProfileConfigurationResource(ProfileConfigurationRepository profileConfigurationRepository) {
        this.profileConfigurationRepository = profileConfigurationRepository;
    }

    /**
     * POST  /profile-configurations : Create a new profileConfiguration.
     *
     * @param profileConfiguration the profileConfiguration to create
     * @return the ResponseEntity with status 201 (Created) and with body the new profileConfiguration, or with status 400 (Bad Request) if the profileConfiguration has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/profile-configurations")
    @Timed
    public ResponseEntity<ProfileConfiguration> createProfileConfiguration(@Valid @RequestBody ProfileConfiguration profileConfiguration) throws URISyntaxException {
        log.debug("REST request to save ProfileConfiguration : {}", profileConfiguration);
        if (profileConfiguration.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new profileConfiguration cannot already have an ID")).body(null);
        }
        ProfileConfiguration result = profileConfigurationRepository.save(profileConfiguration);
        return ResponseEntity.created(new URI("/api/profile-configurations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /profile-configurations : Updates an existing profileConfiguration.
     *
     * @param profileConfiguration the profileConfiguration to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated profileConfiguration,
     * or with status 400 (Bad Request) if the profileConfiguration is not valid,
     * or with status 500 (Internal Server Error) if the profileConfiguration couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/profile-configurations")
    @Timed
    public ResponseEntity<ProfileConfiguration> updateProfileConfiguration(@Valid @RequestBody ProfileConfiguration profileConfiguration) throws URISyntaxException {
        log.debug("REST request to update ProfileConfiguration : {}", profileConfiguration);
        if (profileConfiguration.getId() == null) {
            return createProfileConfiguration(profileConfiguration);
        }
        ProfileConfiguration result = profileConfigurationRepository.save(profileConfiguration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, profileConfiguration.getId().toString()))
            .body(result);
    }

    /**
     * GET  /profile-configurations : get all the profileConfigurations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of profileConfigurations in body
     */
    @GetMapping("/profile-configurations")
    @Timed
    public List<ProfileConfiguration> getAllProfileConfigurations() {
        log.debug("REST request to get all ProfileConfigurations");
        return profileConfigurationRepository.findAll();
    }

    /**
     * GET  /profile-configurations/:id : get the "id" profileConfiguration.
     *
     * @param id the id of the profileConfiguration to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the profileConfiguration, or with status 404 (Not Found)
     */
    @GetMapping("/profile-configurations/{id}")
    @Timed
    public ResponseEntity<ProfileConfiguration> getProfileConfiguration(@PathVariable Long id) {
        log.debug("REST request to get ProfileConfiguration : {}", id);
        ProfileConfiguration profileConfiguration = profileConfigurationRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(profileConfiguration));
    }

    /**
     * DELETE  /profile-configurations/:id : delete the "id" profileConfiguration.
     *
     * @param id the id of the profileConfiguration to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/profile-configurations/{id}")
    @Timed
    public ResponseEntity<Void> deleteProfileConfiguration(@PathVariable Long id) {
        log.debug("REST request to delete ProfileConfiguration : {}", id);
        profileConfigurationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
