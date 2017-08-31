package com.arnaugarcia.halospainleague.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.halospainleague.domain.Achievement;

import com.arnaugarcia.halospainleague.repository.AchievementRepository;
import com.arnaugarcia.halospainleague.web.rest.util.HeaderUtil;
import com.arnaugarcia.halospainleague.service.dto.AchievementDTO;
import com.arnaugarcia.halospainleague.service.mapper.AchievementMapper;
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
 * REST controller for managing Achievement.
 */
@RestController
@RequestMapping("/api")
public class AchievementResource {

    private final Logger log = LoggerFactory.getLogger(AchievementResource.class);

    private static final String ENTITY_NAME = "achievement";

    private final AchievementRepository achievementRepository;

    private final AchievementMapper achievementMapper;
    public AchievementResource(AchievementRepository achievementRepository, AchievementMapper achievementMapper) {
        this.achievementRepository = achievementRepository;
        this.achievementMapper = achievementMapper;
    }

    /**
     * POST  /achievements : Create a new achievement.
     *
     * @param achievementDTO the achievementDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new achievementDTO, or with status 400 (Bad Request) if the achievement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/achievements")
    @Timed
    public ResponseEntity<AchievementDTO> createAchievement(@Valid @RequestBody AchievementDTO achievementDTO) throws URISyntaxException {
        log.debug("REST request to save Achievement : {}", achievementDTO);
        if (achievementDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new achievement cannot already have an ID")).body(null);
        }
        Achievement achievement = achievementMapper.toEntity(achievementDTO);
        achievement = achievementRepository.save(achievement);
        AchievementDTO result = achievementMapper.toDto(achievement);
        return ResponseEntity.created(new URI("/api/achievements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /achievements : Updates an existing achievement.
     *
     * @param achievementDTO the achievementDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated achievementDTO,
     * or with status 400 (Bad Request) if the achievementDTO is not valid,
     * or with status 500 (Internal Server Error) if the achievementDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/achievements")
    @Timed
    public ResponseEntity<AchievementDTO> updateAchievement(@Valid @RequestBody AchievementDTO achievementDTO) throws URISyntaxException {
        log.debug("REST request to update Achievement : {}", achievementDTO);
        if (achievementDTO.getId() == null) {
            return createAchievement(achievementDTO);
        }
        Achievement achievement = achievementMapper.toEntity(achievementDTO);
        achievement = achievementRepository.save(achievement);
        AchievementDTO result = achievementMapper.toDto(achievement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, achievementDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /achievements : get all the achievements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of achievements in body
     */
    @GetMapping("/achievements")
    @Timed
    public List<AchievementDTO> getAllAchievements() {
        log.debug("REST request to get all Achievements");
        List<Achievement> achievements = achievementRepository.findAllWithEagerRelationships();
        return achievementMapper.toDto(achievements);
        }

    /**
     * GET  /achievements/:id : get the "id" achievement.
     *
     * @param id the id of the achievementDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the achievementDTO, or with status 404 (Not Found)
     */
    @GetMapping("/achievements/{id}")
    @Timed
    public ResponseEntity<AchievementDTO> getAchievement(@PathVariable Long id) {
        log.debug("REST request to get Achievement : {}", id);
        Achievement achievement = achievementRepository.findOneWithEagerRelationships(id);
        AchievementDTO achievementDTO = achievementMapper.toDto(achievement);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(achievementDTO));
    }

    /**
     * DELETE  /achievements/:id : delete the "id" achievement.
     *
     * @param id the id of the achievementDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/achievements/{id}")
    @Timed
    public ResponseEntity<Void> deleteAchievement(@PathVariable Long id) {
        log.debug("REST request to delete Achievement : {}", id);
        achievementRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
