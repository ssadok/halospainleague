package com.arnaugarcia.halospainleague.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.halospainleague.domain.Match;

import com.arnaugarcia.halospainleague.repository.MatchRepository;
import com.arnaugarcia.halospainleague.web.rest.util.HeaderUtil;
import com.arnaugarcia.halospainleague.service.dto.MatchDTO;
import com.arnaugarcia.halospainleague.service.mapper.MatchMapper;
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
 * REST controller for managing Match.
 */
@RestController
@RequestMapping("/api")
public class MatchResource {

    private final Logger log = LoggerFactory.getLogger(MatchResource.class);

    private static final String ENTITY_NAME = "match";

    private final MatchRepository matchRepository;

    private final MatchMapper matchMapper;
    public MatchResource(MatchRepository matchRepository, MatchMapper matchMapper) {
        this.matchRepository = matchRepository;
        this.matchMapper = matchMapper;
    }

    /**
     * POST  /matches : Create a new match.
     *
     * @param matchDTO the matchDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new matchDTO, or with status 400 (Bad Request) if the match has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/matches")
    @Timed
    public ResponseEntity<MatchDTO> createMatch(@RequestBody MatchDTO matchDTO) throws URISyntaxException {
        log.debug("REST request to save Match : {}", matchDTO);
        if (matchDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new match cannot already have an ID")).body(null);
        }
        Match match = matchMapper.toEntity(matchDTO);
        match = matchRepository.save(match);
        MatchDTO result = matchMapper.toDto(match);
        return ResponseEntity.created(new URI("/api/matches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /matches : Updates an existing match.
     *
     * @param matchDTO the matchDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated matchDTO,
     * or with status 400 (Bad Request) if the matchDTO is not valid,
     * or with status 500 (Internal Server Error) if the matchDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/matches")
    @Timed
    public ResponseEntity<MatchDTO> updateMatch(@RequestBody MatchDTO matchDTO) throws URISyntaxException {
        log.debug("REST request to update Match : {}", matchDTO);
        if (matchDTO.getId() == null) {
            return createMatch(matchDTO);
        }
        Match match = matchMapper.toEntity(matchDTO);
        match = matchRepository.save(match);
        MatchDTO result = matchMapper.toDto(match);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, matchDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /matches : get all the matches.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of matches in body
     */
    @GetMapping("/matches")
    @Timed
    public List<MatchDTO> getAllMatches() {
        log.debug("REST request to get all Matches");
        List<Match> matches = matchRepository.findAll();
        return matchMapper.toDto(matches);
        }

    /**
     * GET  /matches/:id : get the "id" match.
     *
     * @param id the id of the matchDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the matchDTO, or with status 404 (Not Found)
     */
    @GetMapping("/matches/{id}")
    @Timed
    public ResponseEntity<MatchDTO> getMatch(@PathVariable Long id) {
        log.debug("REST request to get Match : {}", id);
        Match match = matchRepository.findOne(id);
        MatchDTO matchDTO = matchMapper.toDto(match);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(matchDTO));
    }

    /**
     * DELETE  /matches/:id : delete the "id" match.
     *
     * @param id the id of the matchDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/matches/{id}")
    @Timed
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        log.debug("REST request to delete Match : {}", id);
        matchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
