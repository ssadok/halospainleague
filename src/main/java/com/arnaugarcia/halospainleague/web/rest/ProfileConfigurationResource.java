package com.arnaugarcia.halospainleague.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.halospainleague.domain.ProfileConfiguration;

import com.arnaugarcia.halospainleague.repository.ProfileConfigurationRepository;
import com.arnaugarcia.halospainleague.web.rest.util.HeaderUtil;
import com.arnaugarcia.halospainleague.service.dto.ProfileConfigurationDTO;
import com.arnaugarcia.halospainleague.service.mapper.ProfileConfigurationMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing ProfileConfiguration.
 */
@RestController
@RequestMapping("/api")
public class ProfileConfigurationResource {

    private final Logger log = LoggerFactory.getLogger(ProfileConfigurationResource.class);

    private static final String ENTITY_NAME = "profileConfiguration";

    private final ProfileConfigurationRepository profileConfigurationRepository;

    private final ProfileConfigurationMapper profileConfigurationMapper;
    public ProfileConfigurationResource(ProfileConfigurationRepository profileConfigurationRepository, ProfileConfigurationMapper profileConfigurationMapper) {
        this.profileConfigurationRepository = profileConfigurationRepository;
        this.profileConfigurationMapper = profileConfigurationMapper;
    }

    /**
     * POST  /profile-configurations : Create a new profileConfiguration.
     *
     * @param profileConfigurationDTO the profileConfigurationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new profileConfigurationDTO, or with status 400 (Bad Request) if the profileConfiguration has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/profile-configurations")
    @Timed
    public ResponseEntity<ProfileConfigurationDTO> createProfileConfiguration(@Valid @RequestBody ProfileConfigurationDTO profileConfigurationDTO) throws URISyntaxException {
        log.debug("REST request to save ProfileConfiguration : {}", profileConfigurationDTO);
        if (profileConfigurationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new profileConfiguration cannot already have an ID")).body(null);
        }
        ProfileConfiguration profileConfiguration = profileConfigurationMapper.toEntity(profileConfigurationDTO);
        profileConfiguration = profileConfigurationRepository.save(profileConfiguration);
        ProfileConfigurationDTO result = profileConfigurationMapper.toDto(profileConfiguration);
        return ResponseEntity.created(new URI("/api/profile-configurations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /profile-configurations : Updates an existing profileConfiguration.
     *
     * @param profileConfigurationDTO the profileConfigurationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated profileConfigurationDTO,
     * or with status 400 (Bad Request) if the profileConfigurationDTO is not valid,
     * or with status 500 (Internal Server Error) if the profileConfigurationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/profile-configurations")
    @Timed
    public ResponseEntity<ProfileConfigurationDTO> updateProfileConfiguration(@Valid @RequestBody ProfileConfigurationDTO profileConfigurationDTO) throws URISyntaxException {
        log.debug("REST request to update ProfileConfiguration : {}", profileConfigurationDTO);
        if (profileConfigurationDTO.getId() == null) {
            return createProfileConfiguration(profileConfigurationDTO);
        }
        ProfileConfiguration profileConfiguration = profileConfigurationMapper.toEntity(profileConfigurationDTO);
        profileConfiguration = profileConfigurationRepository.save(profileConfiguration);
        ProfileConfigurationDTO result = profileConfigurationMapper.toDto(profileConfiguration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, profileConfigurationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /profile-configurations : get all the profileConfigurations.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of profileConfigurations in body
     */
    @GetMapping("/profile-configurations")
    @Timed
    public List<ProfileConfigurationDTO> getAllProfileConfigurations(@RequestParam(required = false) String filter) {
        if ("player-is-null".equals(filter)) {
            log.debug("REST request to get all ProfileConfigurations where player is null");
            return StreamSupport
                .stream(profileConfigurationRepository.findAll().spliterator(), false)
                .filter(profileConfiguration -> profileConfiguration.getPlayer() == null)
                .map(profileConfigurationMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
        }
        log.debug("REST request to get all ProfileConfigurations");
        List<ProfileConfiguration> profileConfigurations = profileConfigurationRepository.findAll();
        return profileConfigurationMapper.toDto(profileConfigurations);
        }

    /**
     * GET  /profile-configurations/:id : get the "id" profileConfiguration.
     *
     * @param id the id of the profileConfigurationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the profileConfigurationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/profile-configurations/{id}")
    @Timed
    public ResponseEntity<ProfileConfigurationDTO> getProfileConfiguration(@PathVariable Long id) {
        log.debug("REST request to get ProfileConfiguration : {}", id);
        ProfileConfiguration profileConfiguration = profileConfigurationRepository.findOne(id);
        ProfileConfigurationDTO profileConfigurationDTO = profileConfigurationMapper.toDto(profileConfiguration);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(profileConfigurationDTO));
    }

    /**
     * DELETE  /profile-configurations/:id : delete the "id" profileConfiguration.
     *
     * @param id the id of the profileConfigurationDTO to delete
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
