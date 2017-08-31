package com.arnaugarcia.halospainleague.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.halospainleague.domain.Tournament;

import com.arnaugarcia.halospainleague.repository.TournamentRepository;
import com.arnaugarcia.halospainleague.web.rest.util.HeaderUtil;
import com.arnaugarcia.halospainleague.service.dto.TournamentDTO;
import com.arnaugarcia.halospainleague.service.mapper.TournamentMapper;
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
 * REST controller for managing Tournament.
 */
@RestController
@RequestMapping("/api")
public class TournamentResource {

    private final Logger log = LoggerFactory.getLogger(TournamentResource.class);

    private static final String ENTITY_NAME = "tournament";

    private final TournamentRepository tournamentRepository;

    private final TournamentMapper tournamentMapper;
    public TournamentResource(TournamentRepository tournamentRepository, TournamentMapper tournamentMapper) {
        this.tournamentRepository = tournamentRepository;
        this.tournamentMapper = tournamentMapper;
    }

    /**
     * POST  /tournaments : Create a new tournament.
     *
     * @param tournamentDTO the tournamentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tournamentDTO, or with status 400 (Bad Request) if the tournament has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tournaments")
    @Timed
    public ResponseEntity<TournamentDTO> createTournament(@Valid @RequestBody TournamentDTO tournamentDTO) throws URISyntaxException {
        log.debug("REST request to save Tournament : {}", tournamentDTO);
        if (tournamentDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tournament cannot already have an ID")).body(null);
        }
        Tournament tournament = tournamentMapper.toEntity(tournamentDTO);
        tournament = tournamentRepository.save(tournament);
        TournamentDTO result = tournamentMapper.toDto(tournament);
        return ResponseEntity.created(new URI("/api/tournaments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tournaments : Updates an existing tournament.
     *
     * @param tournamentDTO the tournamentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tournamentDTO,
     * or with status 400 (Bad Request) if the tournamentDTO is not valid,
     * or with status 500 (Internal Server Error) if the tournamentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tournaments")
    @Timed
    public ResponseEntity<TournamentDTO> updateTournament(@Valid @RequestBody TournamentDTO tournamentDTO) throws URISyntaxException {
        log.debug("REST request to update Tournament : {}", tournamentDTO);
        if (tournamentDTO.getId() == null) {
            return createTournament(tournamentDTO);
        }
        Tournament tournament = tournamentMapper.toEntity(tournamentDTO);
        tournament = tournamentRepository.save(tournament);
        TournamentDTO result = tournamentMapper.toDto(tournament);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tournamentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tournaments : get all the tournaments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tournaments in body
     */
    @GetMapping("/tournaments")
    @Timed
    public List<TournamentDTO> getAllTournaments() {
        log.debug("REST request to get all Tournaments");
        List<Tournament> tournaments = tournamentRepository.findAllWithEagerRelationships();
        return tournamentMapper.toDto(tournaments);
        }

    /**
     * GET  /tournaments/:id : get the "id" tournament.
     *
     * @param id the id of the tournamentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tournamentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tournaments/{id}")
    @Timed
    public ResponseEntity<TournamentDTO> getTournament(@PathVariable Long id) {
        log.debug("REST request to get Tournament : {}", id);
        Tournament tournament = tournamentRepository.findOneWithEagerRelationships(id);
        TournamentDTO tournamentDTO = tournamentMapper.toDto(tournament);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tournamentDTO));
    }

    /**
     * DELETE  /tournaments/:id : delete the "id" tournament.
     *
     * @param id the id of the tournamentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tournaments/{id}")
    @Timed
    public ResponseEntity<Void> deleteTournament(@PathVariable Long id) {
        log.debug("REST request to delete Tournament : {}", id);
        tournamentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
