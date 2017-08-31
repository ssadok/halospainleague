package com.arnaugarcia.halospainleague.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.halospainleague.domain.Division;

import com.arnaugarcia.halospainleague.repository.DivisionRepository;
import com.arnaugarcia.halospainleague.web.rest.util.HeaderUtil;
import com.arnaugarcia.halospainleague.service.dto.DivisionDTO;
import com.arnaugarcia.halospainleague.service.mapper.DivisionMapper;
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
 * REST controller for managing Division.
 */
@RestController
@RequestMapping("/api")
public class DivisionResource {

    private final Logger log = LoggerFactory.getLogger(DivisionResource.class);

    private static final String ENTITY_NAME = "division";

    private final DivisionRepository divisionRepository;

    private final DivisionMapper divisionMapper;
    public DivisionResource(DivisionRepository divisionRepository, DivisionMapper divisionMapper) {
        this.divisionRepository = divisionRepository;
        this.divisionMapper = divisionMapper;
    }

    /**
     * POST  /divisions : Create a new division.
     *
     * @param divisionDTO the divisionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new divisionDTO, or with status 400 (Bad Request) if the division has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/divisions")
    @Timed
    public ResponseEntity<DivisionDTO> createDivision(@RequestBody DivisionDTO divisionDTO) throws URISyntaxException {
        log.debug("REST request to save Division : {}", divisionDTO);
        if (divisionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new division cannot already have an ID")).body(null);
        }
        Division division = divisionMapper.toEntity(divisionDTO);
        division = divisionRepository.save(division);
        DivisionDTO result = divisionMapper.toDto(division);
        return ResponseEntity.created(new URI("/api/divisions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /divisions : Updates an existing division.
     *
     * @param divisionDTO the divisionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated divisionDTO,
     * or with status 400 (Bad Request) if the divisionDTO is not valid,
     * or with status 500 (Internal Server Error) if the divisionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/divisions")
    @Timed
    public ResponseEntity<DivisionDTO> updateDivision(@RequestBody DivisionDTO divisionDTO) throws URISyntaxException {
        log.debug("REST request to update Division : {}", divisionDTO);
        if (divisionDTO.getId() == null) {
            return createDivision(divisionDTO);
        }
        Division division = divisionMapper.toEntity(divisionDTO);
        division = divisionRepository.save(division);
        DivisionDTO result = divisionMapper.toDto(division);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, divisionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /divisions : get all the divisions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of divisions in body
     */
    @GetMapping("/divisions")
    @Timed
    public List<DivisionDTO> getAllDivisions() {
        log.debug("REST request to get all Divisions");
        List<Division> divisions = divisionRepository.findAll();
        return divisionMapper.toDto(divisions);
        }

    /**
     * GET  /divisions/:id : get the "id" division.
     *
     * @param id the id of the divisionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the divisionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/divisions/{id}")
    @Timed
    public ResponseEntity<DivisionDTO> getDivision(@PathVariable Long id) {
        log.debug("REST request to get Division : {}", id);
        Division division = divisionRepository.findOne(id);
        DivisionDTO divisionDTO = divisionMapper.toDto(division);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(divisionDTO));
    }

    /**
     * DELETE  /divisions/:id : delete the "id" division.
     *
     * @param id the id of the divisionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/divisions/{id}")
    @Timed
    public ResponseEntity<Void> deleteDivision(@PathVariable Long id) {
        log.debug("REST request to delete Division : {}", id);
        divisionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
