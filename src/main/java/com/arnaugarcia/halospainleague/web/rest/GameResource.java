package com.arnaugarcia.halospainleague.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.halospainleague.domain.Game;

import com.arnaugarcia.halospainleague.repository.GameRepository;
import com.arnaugarcia.halospainleague.web.rest.util.HeaderUtil;
import com.arnaugarcia.halospainleague.service.dto.GameDTO;
import com.arnaugarcia.halospainleague.service.mapper.GameMapper;
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
 * REST controller for managing Game.
 */
@RestController
@RequestMapping("/api")
public class GameResource {

    private final Logger log = LoggerFactory.getLogger(GameResource.class);

    private static final String ENTITY_NAME = "game";

    private final GameRepository gameRepository;

    private final GameMapper gameMapper;
    public GameResource(GameRepository gameRepository, GameMapper gameMapper) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
    }

    /**
     * POST  /games : Create a new game.
     *
     * @param gameDTO the gameDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gameDTO, or with status 400 (Bad Request) if the game has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/games")
    @Timed
    public ResponseEntity<GameDTO> createGame(@Valid @RequestBody GameDTO gameDTO) throws URISyntaxException {
        log.debug("REST request to save Game : {}", gameDTO);
        if (gameDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new game cannot already have an ID")).body(null);
        }
        Game game = gameMapper.toEntity(gameDTO);
        game = gameRepository.save(game);
        GameDTO result = gameMapper.toDto(game);
        return ResponseEntity.created(new URI("/api/games/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /games : Updates an existing game.
     *
     * @param gameDTO the gameDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gameDTO,
     * or with status 400 (Bad Request) if the gameDTO is not valid,
     * or with status 500 (Internal Server Error) if the gameDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/games")
    @Timed
    public ResponseEntity<GameDTO> updateGame(@Valid @RequestBody GameDTO gameDTO) throws URISyntaxException {
        log.debug("REST request to update Game : {}", gameDTO);
        if (gameDTO.getId() == null) {
            return createGame(gameDTO);
        }
        Game game = gameMapper.toEntity(gameDTO);
        game = gameRepository.save(game);
        GameDTO result = gameMapper.toDto(game);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gameDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /games : get all the games.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of games in body
     */
    @GetMapping("/games")
    @Timed
    public List<GameDTO> getAllGames() {
        log.debug("REST request to get all Games");
        List<Game> games = gameRepository.findAll();
        return gameMapper.toDto(games);
        }

    /**
     * GET  /games/:id : get the "id" game.
     *
     * @param id the id of the gameDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gameDTO, or with status 404 (Not Found)
     */
    @GetMapping("/games/{id}")
    @Timed
    public ResponseEntity<GameDTO> getGame(@PathVariable Long id) {
        log.debug("REST request to get Game : {}", id);
        Game game = gameRepository.findOne(id);
        GameDTO gameDTO = gameMapper.toDto(game);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(gameDTO));
    }

    /**
     * DELETE  /games/:id : delete the "id" game.
     *
     * @param id the id of the gameDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/games/{id}")
    @Timed
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        log.debug("REST request to delete Game : {}", id);
        gameRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
