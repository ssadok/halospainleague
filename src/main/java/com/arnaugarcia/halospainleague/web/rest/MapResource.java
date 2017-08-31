package com.arnaugarcia.halospainleague.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.halospainleague.domain.Map;

import com.arnaugarcia.halospainleague.repository.MapRepository;
import com.arnaugarcia.halospainleague.web.rest.util.HeaderUtil;
import com.arnaugarcia.halospainleague.service.dto.MapDTO;
import com.arnaugarcia.halospainleague.service.mapper.MapMapper;
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
 * REST controller for managing Map.
 */
@RestController
@RequestMapping("/api")
public class MapResource {

    private final Logger log = LoggerFactory.getLogger(MapResource.class);

    private static final String ENTITY_NAME = "map";

    private final MapRepository mapRepository;

    private final MapMapper mapMapper;
    public MapResource(MapRepository mapRepository, MapMapper mapMapper) {
        this.mapRepository = mapRepository;
        this.mapMapper = mapMapper;
    }

    /**
     * POST  /maps : Create a new map.
     *
     * @param mapDTO the mapDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mapDTO, or with status 400 (Bad Request) if the map has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/maps")
    @Timed
    public ResponseEntity<MapDTO> createMap(@RequestBody MapDTO mapDTO) throws URISyntaxException {
        log.debug("REST request to save Map : {}", mapDTO);
        if (mapDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new map cannot already have an ID")).body(null);
        }
        Map map = mapMapper.toEntity(mapDTO);
        map = mapRepository.save(map);
        MapDTO result = mapMapper.toDto(map);
        return ResponseEntity.created(new URI("/api/maps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /maps : Updates an existing map.
     *
     * @param mapDTO the mapDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mapDTO,
     * or with status 400 (Bad Request) if the mapDTO is not valid,
     * or with status 500 (Internal Server Error) if the mapDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/maps")
    @Timed
    public ResponseEntity<MapDTO> updateMap(@RequestBody MapDTO mapDTO) throws URISyntaxException {
        log.debug("REST request to update Map : {}", mapDTO);
        if (mapDTO.getId() == null) {
            return createMap(mapDTO);
        }
        Map map = mapMapper.toEntity(mapDTO);
        map = mapRepository.save(map);
        MapDTO result = mapMapper.toDto(map);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mapDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /maps : get all the maps.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of maps in body
     */
    @GetMapping("/maps")
    @Timed
    public List<MapDTO> getAllMaps() {
        log.debug("REST request to get all Maps");
        List<Map> maps = mapRepository.findAll();
        return mapMapper.toDto(maps);
        }

    /**
     * GET  /maps/:id : get the "id" map.
     *
     * @param id the id of the mapDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mapDTO, or with status 404 (Not Found)
     */
    @GetMapping("/maps/{id}")
    @Timed
    public ResponseEntity<MapDTO> getMap(@PathVariable Long id) {
        log.debug("REST request to get Map : {}", id);
        Map map = mapRepository.findOne(id);
        MapDTO mapDTO = mapMapper.toDto(map);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mapDTO));
    }

    /**
     * DELETE  /maps/:id : delete the "id" map.
     *
     * @param id the id of the mapDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/maps/{id}")
    @Timed
    public ResponseEntity<Void> deleteMap(@PathVariable Long id) {
        log.debug("REST request to delete Map : {}", id);
        mapRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
