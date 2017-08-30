package com.arnaugarcia.halospainleague.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.halospainleague.domain.MatchMode;

import com.arnaugarcia.halospainleague.repository.MatchModeRepository;
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
 * REST controller for managing MatchMode.
 */
@RestController
@RequestMapping("/api")
public class MatchModeResource {

    private final Logger log = LoggerFactory.getLogger(MatchModeResource.class);

    private static final String ENTITY_NAME = "matchMode";

    private final MatchModeRepository matchModeRepository;
    public MatchModeResource(MatchModeRepository matchModeRepository) {
        this.matchModeRepository = matchModeRepository;
    }

    /**
     * POST  /match-modes : Create a new matchMode.
     *
     * @param matchMode the matchMode to create
     * @return the ResponseEntity with status 201 (Created) and with body the new matchMode, or with status 400 (Bad Request) if the matchMode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/match-modes")
    @Timed
    public ResponseEntity<MatchMode> createMatchMode(@RequestBody MatchMode matchMode) throws URISyntaxException {
        log.debug("REST request to save MatchMode : {}", matchMode);
        if (matchMode.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new matchMode cannot already have an ID")).body(null);
        }
        MatchMode result = matchModeRepository.save(matchMode);
        return ResponseEntity.created(new URI("/api/match-modes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /match-modes : Updates an existing matchMode.
     *
     * @param matchMode the matchMode to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated matchMode,
     * or with status 400 (Bad Request) if the matchMode is not valid,
     * or with status 500 (Internal Server Error) if the matchMode couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/match-modes")
    @Timed
    public ResponseEntity<MatchMode> updateMatchMode(@RequestBody MatchMode matchMode) throws URISyntaxException {
        log.debug("REST request to update MatchMode : {}", matchMode);
        if (matchMode.getId() == null) {
            return createMatchMode(matchMode);
        }
        MatchMode result = matchModeRepository.save(matchMode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, matchMode.getId().toString()))
            .body(result);
    }

    /**
     * GET  /match-modes : get all the matchModes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of matchModes in body
     */
    @GetMapping("/match-modes")
    @Timed
    public List<MatchMode> getAllMatchModes() {
        log.debug("REST request to get all MatchModes");
        return matchModeRepository.findAll();
        }

    /**
     * GET  /match-modes/:id : get the "id" matchMode.
     *
     * @param id the id of the matchMode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the matchMode, or with status 404 (Not Found)
     */
    @GetMapping("/match-modes/{id}")
    @Timed
    public ResponseEntity<MatchMode> getMatchMode(@PathVariable Long id) {
        log.debug("REST request to get MatchMode : {}", id);
        MatchMode matchMode = matchModeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(matchMode));
    }

    /**
     * DELETE  /match-modes/:id : delete the "id" matchMode.
     *
     * @param id the id of the matchMode to delete
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
