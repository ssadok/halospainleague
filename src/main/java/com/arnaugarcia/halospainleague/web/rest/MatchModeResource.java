package com.arnaugarcia.halospainleague.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.halospainleague.domain.MatchMode;

import com.arnaugarcia.halospainleague.repository.MatchModeRepository;
import com.arnaugarcia.halospainleague.web.rest.util.HeaderUtil;
import com.arnaugarcia.halospainleague.service.dto.MatchModeDTO;
import com.arnaugarcia.halospainleague.service.mapper.MatchModeMapper;
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
 * REST controller for managing MatchMode.
 */
@RestController
@RequestMapping("/api")
public class MatchModeResource {

    private final Logger log = LoggerFactory.getLogger(MatchModeResource.class);

    private static final String ENTITY_NAME = "matchMode";

    private final MatchModeRepository matchModeRepository;

    private final MatchModeMapper matchModeMapper;
    public MatchModeResource(MatchModeRepository matchModeRepository, MatchModeMapper matchModeMapper) {
        this.matchModeRepository = matchModeRepository;
        this.matchModeMapper = matchModeMapper;
    }

    /**
     * POST  /match-modes : Create a new matchMode.
     *
     * @param matchModeDTO the matchModeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new matchModeDTO, or with status 400 (Bad Request) if the matchMode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/match-modes")
    @Timed
    public ResponseEntity<MatchModeDTO> createMatchMode(@RequestBody MatchModeDTO matchModeDTO) throws URISyntaxException {
        log.debug("REST request to save MatchMode : {}", matchModeDTO);
        if (matchModeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new matchMode cannot already have an ID")).body(null);
        }
        MatchMode matchMode = matchModeMapper.toEntity(matchModeDTO);
        matchMode = matchModeRepository.save(matchMode);
        MatchModeDTO result = matchModeMapper.toDto(matchMode);
        return ResponseEntity.created(new URI("/api/match-modes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /match-modes : Updates an existing matchMode.
     *
     * @param matchModeDTO the matchModeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated matchModeDTO,
     * or with status 400 (Bad Request) if the matchModeDTO is not valid,
     * or with status 500 (Internal Server Error) if the matchModeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/match-modes")
    @Timed
    public ResponseEntity<MatchModeDTO> updateMatchMode(@RequestBody MatchModeDTO matchModeDTO) throws URISyntaxException {
        log.debug("REST request to update MatchMode : {}", matchModeDTO);
        if (matchModeDTO.getId() == null) {
            return createMatchMode(matchModeDTO);
        }
        MatchMode matchMode = matchModeMapper.toEntity(matchModeDTO);
        matchMode = matchModeRepository.save(matchMode);
        MatchModeDTO result = matchModeMapper.toDto(matchMode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, matchModeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /match-modes : get all the matchModes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of matchModes in body
     */
    @GetMapping("/match-modes")
    @Timed
    public List<MatchModeDTO> getAllMatchModes() {
        log.debug("REST request to get all MatchModes");
        List<MatchMode> matchModes = matchModeRepository.findAll();
        return matchModeMapper.toDto(matchModes);
        }

    /**
     * GET  /match-modes/:id : get the "id" matchMode.
     *
     * @param id the id of the matchModeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the matchModeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/match-modes/{id}")
    @Timed
    public ResponseEntity<MatchModeDTO> getMatchMode(@PathVariable Long id) {
        log.debug("REST request to get MatchMode : {}", id);
        MatchMode matchMode = matchModeRepository.findOne(id);
        MatchModeDTO matchModeDTO = matchModeMapper.toDto(matchMode);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(matchModeDTO));
    }

    /**
     * DELETE  /match-modes/:id : delete the "id" matchMode.
     *
     * @param id the id of the matchModeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/match-modes/{id}")
    @Timed
    public ResponseEntity<Void> deleteMatchMode(@PathVariable Long id) {
        log.debug("REST request to delete MatchMode : {}", id);
        matchModeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
