package com.arnaugarcia.halospainleague.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.halospainleague.domain.Division;

import com.arnaugarcia.halospainleague.repository.DivisionRepository;
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
 * REST controller for managing Division.
 */
@RestController
@RequestMapping("/api")
public class DivisionResource {

    private final Logger log = LoggerFactory.getLogger(DivisionResource.class);

    private static final String ENTITY_NAME = "division";

    private final DivisionRepository divisionRepository;
    public DivisionResource(DivisionRepository divisionRepository) {
        this.divisionRepository = divisionRepository;
    }

    /**
     * POST  /divisions : Create a new division.
     *
     * @param division the division to create
     * @return the ResponseEntity with status 201 (Created) and with body the new division, or with status 400 (Bad Request) if the division has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/divisions")
    @Timed
    public ResponseEntity<Division> createDivision(@RequestBody Division division) throws URISyntaxException {
        log.debug("REST request to save Division : {}", division);
        if (division.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new division cannot already have an ID")).body(null);
        }
        Division result = divisionRepository.save(division);
        return ResponseEntity.created(new URI("/api/divisions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /divisions : Updates an existing division.
     *
     * @param division the division to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated division,
     * or with status 400 (Bad Request) if the division is not valid,
     * or with status 500 (Internal Server Error) if the division couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/divisions")
    @Timed
    public ResponseEntity<Division> updateDivision(@RequestBody Division division) throws URISyntaxException {
        log.debug("REST request to update Division : {}", division);
        if (division.getId() == null) {
            return createDivision(division);
        }
        Division result = divisionRepository.save(division);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, division.getId().toString()))
            .body(result);
    }

    /**
     * GET  /divisions : get all the divisions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of divisions in body
     */
    @GetMapping("/divisions")
    @Timed
    public List<Division> getAllDivisions() {
        log.debug("REST request to get all Divisions");
        return divisionRepository.findAll();
        }

    /**
     * GET  /divisions/:id : get the "id" division.
     *
     * @param id the id of the division to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the division, or with status 404 (Not Found)
     */
    @GetMapping("/divisions/{id}")
    @Timed
    public ResponseEntity<Division> getDivision(@PathVariable Long id) {
        log.debug("REST request to get Division : {}", id);
        Division division = divisionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(division));
    }

    /**
     * DELETE  /divisions/:id : delete the "id" division.
     *
     * @param id the id of the division to delete
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
