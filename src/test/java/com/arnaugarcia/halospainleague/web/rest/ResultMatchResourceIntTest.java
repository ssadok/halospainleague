package com.arnaugarcia.halospainleague.web.rest;

import com.arnaugarcia.halospainleague.HalospainleagueApp;

import com.arnaugarcia.halospainleague.domain.ResultMatch;
import com.arnaugarcia.halospainleague.repository.ResultMatchRepository;
import com.arnaugarcia.halospainleague.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ResultMatchResource REST controller.
 *
 * @see ResultMatchResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HalospainleagueApp.class)
public class ResultMatchResourceIntTest {

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    @Autowired
    private ResultMatchRepository resultMatchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restResultMatchMockMvc;

    private ResultMatch resultMatch;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResultMatchResource resultMatchResource = new ResultMatchResource(resultMatchRepository);
        this.restResultMatchMockMvc = MockMvcBuilders.standaloneSetup(resultMatchResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResultMatch createEntity(EntityManager em) {
        ResultMatch resultMatch = new ResultMatch()
            .reference(DEFAULT_REFERENCE);
        return resultMatch;
    }

    @Before
    public void initTest() {
        resultMatch = createEntity(em);
    }

    @Test
    @Transactional
    public void createResultMatch() throws Exception {
        int databaseSizeBeforeCreate = resultMatchRepository.findAll().size();

        // Create the ResultMatch
        restResultMatchMockMvc.perform(post("/api/result-matches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultMatch)))
            .andExpect(status().isCreated());

        // Validate the ResultMatch in the database
        List<ResultMatch> resultMatchList = resultMatchRepository.findAll();
        assertThat(resultMatchList).hasSize(databaseSizeBeforeCreate + 1);
        ResultMatch testResultMatch = resultMatchList.get(resultMatchList.size() - 1);
        assertThat(testResultMatch.getReference()).isEqualTo(DEFAULT_REFERENCE);
    }

    @Test
    @Transactional
    public void createResultMatchWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resultMatchRepository.findAll().size();

        // Create the ResultMatch with an existing ID
        resultMatch.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResultMatchMockMvc.perform(post("/api/result-matches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultMatch)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ResultMatch> resultMatchList = resultMatchRepository.findAll();
        assertThat(resultMatchList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllResultMatches() throws Exception {
        // Initialize the database
        resultMatchRepository.saveAndFlush(resultMatch);

        // Get all the resultMatchList
        restResultMatchMockMvc.perform(get("/api/result-matches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultMatch.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())));
    }

    @Test
    @Transactional
    public void getResultMatch() throws Exception {
        // Initialize the database
        resultMatchRepository.saveAndFlush(resultMatch);

        // Get the resultMatch
        restResultMatchMockMvc.perform(get("/api/result-matches/{id}", resultMatch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resultMatch.getId().intValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResultMatch() throws Exception {
        // Get the resultMatch
        restResultMatchMockMvc.perform(get("/api/result-matches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResultMatch() throws Exception {
        // Initialize the database
        resultMatchRepository.saveAndFlush(resultMatch);
        int databaseSizeBeforeUpdate = resultMatchRepository.findAll().size();

        // Update the resultMatch
        ResultMatch updatedResultMatch = resultMatchRepository.findOne(resultMatch.getId());
        updatedResultMatch
            .reference(UPDATED_REFERENCE);

        restResultMatchMockMvc.perform(put("/api/result-matches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResultMatch)))
            .andExpect(status().isOk());

        // Validate the ResultMatch in the database
        List<ResultMatch> resultMatchList = resultMatchRepository.findAll();
        assertThat(resultMatchList).hasSize(databaseSizeBeforeUpdate);
        ResultMatch testResultMatch = resultMatchList.get(resultMatchList.size() - 1);
        assertThat(testResultMatch.getReference()).isEqualTo(UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void updateNonExistingResultMatch() throws Exception {
        int databaseSizeBeforeUpdate = resultMatchRepository.findAll().size();

        // Create the ResultMatch

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restResultMatchMockMvc.perform(put("/api/result-matches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultMatch)))
            .andExpect(status().isCreated());

        // Validate the ResultMatch in the database
        List<ResultMatch> resultMatchList = resultMatchRepository.findAll();
        assertThat(resultMatchList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteResultMatch() throws Exception {
        // Initialize the database
        resultMatchRepository.saveAndFlush(resultMatch);
        int databaseSizeBeforeDelete = resultMatchRepository.findAll().size();

        // Get the resultMatch
        restResultMatchMockMvc.perform(delete("/api/result-matches/{id}", resultMatch.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ResultMatch> resultMatchList = resultMatchRepository.findAll();
        assertThat(resultMatchList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResultMatch.class);
        ResultMatch resultMatch1 = new ResultMatch();
        resultMatch1.setId(1L);
        ResultMatch resultMatch2 = new ResultMatch();
        resultMatch2.setId(resultMatch1.getId());
        assertThat(resultMatch1).isEqualTo(resultMatch2);
        resultMatch2.setId(2L);
        assertThat(resultMatch1).isNotEqualTo(resultMatch2);
        resultMatch1.setId(null);
        assertThat(resultMatch1).isNotEqualTo(resultMatch2);
    }
}
