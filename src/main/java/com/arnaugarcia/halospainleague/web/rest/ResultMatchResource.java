package com.arnaugarcia.halospainleague.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.halospainleague.domain.ResultMatch;

import com.arnaugarcia.halospainleague.repository.ResultMatchRepository;
import com.arnaugarcia.halospainleague.web.rest.util.HeaderUtil;
import com.arnaugarcia.halospainleague.service.dto.ResultMatchDTO;
import com.arnaugarcia.halospainleague.service.mapper.ResultMatchMapper;
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
 * REST controller for managing ResultMatch.
 */
@RestController
@RequestMapping("/api")
public class ResultMatchResource {

    private final Logger log = LoggerFactory.getLogger(ResultMatchResource.class);

    private static final String ENTITY_NAME = "resultMatch";

    private final ResultMatchRepository resultMatchRepository;

    private final ResultMatchMapper resultMatchMapper;
    public ResultMatchResource(ResultMatchRepository resultMatchRepository, ResultMatchMapper resultMatchMapper) {
        this.resultMatchRepository = resultMatchRepository;
        this.resultMatchMapper = resultMatchMapper;
    }

    /**
     * POST  /result-matches : Create a new resultMatch.
     *
     * @param resultMatchDTO the resultMatchDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new resultMatchDTO, or with status 400 (Bad Request) if the resultMatch has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/result-matches")
    @Timed
    public ResponseEntity<ResultMatchDTO> createResultMatch(@RequestBody ResultMatchDTO resultMatchDTO) throws URISyntaxException {
        log.debug("REST request to save ResultMatch : {}", resultMatchDTO);
        if (resultMatchDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new resultMatch cannot already have an ID")).body(null);
        }
        ResultMatch resultMatch = resultMatchMapper.toEntity(resultMatchDTO);
        resultMatch = resultMatchRepository.save(resultMatch);
        ResultMatchDTO result = resultMatchMapper.toDto(resultMatch);
        return ResponseEntity.created(new URI("/api/result-matches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /result-matches : Updates an existing resultMatch.
     *
     * @param resultMatchDTO the resultMatchDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated resultMatchDTO,
     * or with status 400 (Bad Request) if the resultMatchDTO is not valid,
     * or with status 500 (Internal Server Error) if the resultMatchDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/result-matches")
    @Timed
    public ResponseEntity<ResultMatchDTO> updateResultMatch(@RequestBody ResultMatchDTO resultMatchDTO) throws URISyntaxException {
        log.debug("REST request to update ResultMatch : {}", resultMatchDTO);
        if (resultMatchDTO.getId() == null) {
            return createResultMatch(resultMatchDTO);
        }
        ResultMatch resultMatch = resultMatchMapper.toEntity(resultMatchDTO);
        resultMatch = resultMatchRepository.save(resultMatch);
        ResultMatchDTO result = resultMatchMapper.toDto(resultMatch);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, resultMatchDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /result-matches : get all the resultMatches.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of resultMatches in body
     */
    @GetMapping("/result-matches")
    @Timed
    public List<ResultMatchDTO> getAllResultMatches(@RequestParam(required = false) String filter) {
        if ("match-is-null".equals(filter)) {
            log.debug("REST request to get all ResultMatchs where match is null");
            return StreamSupport
                .stream(resultMatchRepository.findAll().spliterator(), false)
                .filter(resultMatch -> resultMatch.getMatch() == null)
                .map(resultMatchMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
        }
        log.debug("REST request to get all ResultMatches");
        List<ResultMatch> resultMatches = resultMatchRepository.findAll();
        return resultMatchMapper.toDto(resultMatches);
        }

    /**
     * GET  /result-matches/:id : get the "id" resultMatch.
     *
     * @param id the id of the resultMatchDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resultMatchDTO, or with status 404 (Not Found)
     */
    @GetMapping("/result-matches/{id}")
    @Timed
    public ResponseEntity<ResultMatchDTO> getResultMatch(@PathVariable Long id) {
        log.debug("REST request to get ResultMatch : {}", id);
        ResultMatch resultMatch = resultMatchRepository.findOne(id);
        ResultMatchDTO resultMatchDTO = resultMatchMapper.toDto(resultMatch);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(resultMatchDTO));
    }

    /**
     * DELETE  /result-matches/:id : delete the "id" resultMatch.
     *
     * @param id the id of the resultMatchDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/result-matches/{id}")
    @Timed
    public ResponseEntity<Void> deleteResultMatch(@PathVariable Long id) {
        log.debug("REST request to delete ResultMatch : {}", id);
        resultMatchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
