package com.arnaugarcia.halospainleague.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.halospainleague.domain.TeamList;

import com.arnaugarcia.halospainleague.repository.TeamListRepository;
import com.arnaugarcia.halospainleague.web.rest.util.HeaderUtil;
import com.arnaugarcia.halospainleague.service.dto.TeamListDTO;
import com.arnaugarcia.halospainleague.service.mapper.TeamListMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing TeamList.
 */
@RestController
@RequestMapping("/api")
public class TeamListResource {

    private final Logger log = LoggerFactory.getLogger(TeamListResource.class);

    private static final String ENTITY_NAME = "teamList";

    private final TeamListRepository teamListRepository;

    private final TeamListMapper teamListMapper;
    public TeamListResource(TeamListRepository teamListRepository, TeamListMapper teamListMapper) {
        this.teamListRepository = teamListRepository;
        this.teamListMapper = teamListMapper;
    }

    /**
     * POST  /team-lists : Create a new teamList.
     *
     * @param teamListDTO the teamListDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new teamListDTO, or with status 400 (Bad Request) if the teamList has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/team-lists")
    @Timed
    public ResponseEntity<TeamListDTO> createTeamList(@RequestBody TeamListDTO teamListDTO) throws URISyntaxException {
        log.debug("REST request to save TeamList : {}", teamListDTO);
        if (teamListDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new teamList cannot already have an ID")).body(null);
        }
        TeamList teamList = teamListMapper.toEntity(teamListDTO);
        teamList = teamListRepository.save(teamList);
        TeamListDTO result = teamListMapper.toDto(teamList);
        return ResponseEntity.created(new URI("/api/team-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /team-lists : Updates an existing teamList.
     *
     * @param teamListDTO the teamListDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated teamListDTO,
     * or with status 400 (Bad Request) if the teamListDTO is not valid,
     * or with status 500 (Internal Server Error) if the teamListDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/team-lists")
    @Timed
    public ResponseEntity<TeamListDTO> updateTeamList(@RequestBody TeamListDTO teamListDTO) throws URISyntaxException {
        log.debug("REST request to update TeamList : {}", teamListDTO);
        if (teamListDTO.getId() == null) {
            return createTeamList(teamListDTO);
        }
        TeamList teamList = teamListMapper.toEntity(teamListDTO);
        teamList = teamListRepository.save(teamList);
        TeamListDTO result = teamListMapper.toDto(teamList);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, teamListDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /team-lists : get all the teamLists.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of teamLists in body
     */
    @GetMapping("/team-lists")
    @Timed
    public List<TeamListDTO> getAllTeamLists(@RequestParam(required = false) String filter) {
        if ("match-is-null".equals(filter)) {
            log.debug("REST request to get all TeamLists where match is null");
            return StreamSupport
                .stream(teamListRepository.findAll().spliterator(), false)
                .filter(teamList -> teamList.getMatch() == null)
                .map(teamListMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
        }
        log.debug("REST request to get all TeamLists");
        List<TeamList> teamLists = teamListRepository.findAllWithEagerRelationships();
        return teamListMapper.toDto(teamLists);
        }

    /**
     * GET  /team-lists/:id : get the "id" teamList.
     *
     * @param id the id of the teamListDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the teamListDTO, or with status 404 (Not Found)
     */
    @GetMapping("/team-lists/{id}")
    @Timed
    public ResponseEntity<TeamListDTO> getTeamList(@PathVariable Long id) {
        log.debug("REST request to get TeamList : {}", id);
        TeamList teamList = teamListRepository.findOneWithEagerRelationships(id);
        TeamListDTO teamListDTO = teamListMapper.toDto(teamList);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(teamListDTO));
    }

    /**
     * DELETE  /team-lists/:id : delete the "id" teamList.
     *
     * @param id the id of the teamListDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/team-lists/{id}")
    @Timed
    public ResponseEntity<Void> deleteTeamList(@PathVariable Long id) {
        log.debug("REST request to delete TeamList : {}", id);
        teamListRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
