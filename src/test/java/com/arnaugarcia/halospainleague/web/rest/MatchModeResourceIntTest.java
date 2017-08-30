package com.arnaugarcia.halospainleague.web.rest;

import com.arnaugarcia.halospainleague.HalospainleagueApp;

import com.arnaugarcia.halospainleague.domain.MatchMode;
import com.arnaugarcia.halospainleague.repository.MatchModeRepository;
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

import com.arnaugarcia.halospainleague.domain.enumeration.GameMode;
/**
 * Test class for the MatchModeResource REST controller.
 *
 * @see MatchModeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HalospainleagueApp.class)
public class MatchModeResourceIntTest {

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final Integer DEFAULT_TIME_TO_WIN = 1;
    private static final Integer UPDATED_TIME_TO_WIN = 2;

    private static final Integer DEFAULT_SCORE_TO_WIN = 1;
    private static final Integer UPDATED_SCORE_TO_WIN = 2;

    private static final GameMode DEFAULT_GAME_MODE = GameMode.SLAYER;
    private static final GameMode UPDATED_GAME_MODE = GameMode.FLAGS;

    @Autowired
    private MatchModeRepository matchModeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMatchModeMockMvc;

    private MatchMode matchMode;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MatchModeResource matchModeResource = new MatchModeResource(matchModeRepository);
        this.restMatchModeMockMvc = MockMvcBuilders.standaloneSetup(matchModeResource)
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
    public static MatchMode createEntity(EntityManager em) {
        MatchMode matchMode = new MatchMode()
            .reference(DEFAULT_REFERENCE)
            .timeToWin(DEFAULT_TIME_TO_WIN)
            .scoreToWin(DEFAULT_SCORE_TO_WIN)
            .gameMode(DEFAULT_GAME_MODE);
        return matchMode;
    }

    @Before
    public void initTest() {
        matchMode = createEntity(em);
    }

    @Test
    @Transactional
    public void createMatchMode() throws Exception {
        int databaseSizeBeforeCreate = matchModeRepository.findAll().size();

        // Create the MatchMode
        restMatchModeMockMvc.perform(post("/api/match-modes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchMode)))
            .andExpect(status().isCreated());

        // Validate the MatchMode in the database
        List<MatchMode> matchModeList = matchModeRepository.findAll();
        assertThat(matchModeList).hasSize(databaseSizeBeforeCreate + 1);
        MatchMode testMatchMode = matchModeList.get(matchModeList.size() - 1);
        assertThat(testMatchMode.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testMatchMode.getTimeToWin()).isEqualTo(DEFAULT_TIME_TO_WIN);
        assertThat(testMatchMode.getScoreToWin()).isEqualTo(DEFAULT_SCORE_TO_WIN);
        assertThat(testMatchMode.getGameMode()).isEqualTo(DEFAULT_GAME_MODE);
    }

    @Test
    @Transactional
    public void createMatchModeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = matchModeRepository.findAll().size();

        // Create the MatchMode with an existing ID
        matchMode.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatchModeMockMvc.perform(post("/api/match-modes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchMode)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MatchMode> matchModeList = matchModeRepository.findAll();
        assertThat(matchModeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMatchModes() throws Exception {
        // Initialize the database
        matchModeRepository.saveAndFlush(matchMode);

        // Get all the matchModeList
        restMatchModeMockMvc.perform(get("/api/match-modes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchMode.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].timeToWin").value(hasItem(DEFAULT_TIME_TO_WIN)))
            .andExpect(jsonPath("$.[*].scoreToWin").value(hasItem(DEFAULT_SCORE_TO_WIN)))
            .andExpect(jsonPath("$.[*].gameMode").value(hasItem(DEFAULT_GAME_MODE.toString())));
    }

    @Test
    @Transactional
    public void getMatchMode() throws Exception {
        // Initialize the database
        matchModeRepository.saveAndFlush(matchMode);

        // Get the matchMode
        restMatchModeMockMvc.perform(get("/api/match-modes/{id}", matchMode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(matchMode.getId().intValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()))
            .andExpect(jsonPath("$.timeToWin").value(DEFAULT_TIME_TO_WIN))
            .andExpect(jsonPath("$.scoreToWin").value(DEFAULT_SCORE_TO_WIN))
            .andExpect(jsonPath("$.gameMode").value(DEFAULT_GAME_MODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMatchMode() throws Exception {
        // Get the matchMode
        restMatchModeMockMvc.perform(get("/api/match-modes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMatchMode() throws Exception {
        // Initialize the database
        matchModeRepository.saveAndFlush(matchMode);
        int databaseSizeBeforeUpdate = matchModeRepository.findAll().size();

        // Update the matchMode
        MatchMode updatedMatchMode = matchModeRepository.findOne(matchMode.getId());
        updatedMatchMode
            .reference(UPDATED_REFERENCE)
            .timeToWin(UPDATED_TIME_TO_WIN)
            .scoreToWin(UPDATED_SCORE_TO_WIN)
            .gameMode(UPDATED_GAME_MODE);

        restMatchModeMockMvc.perform(put("/api/match-modes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMatchMode)))
            .andExpect(status().isOk());

        // Validate the MatchMode in the database
        List<MatchMode> matchModeList = matchModeRepository.findAll();
        assertThat(matchModeList).hasSize(databaseSizeBeforeUpdate);
        MatchMode testMatchMode = matchModeList.get(matchModeList.size() - 1);
        assertThat(testMatchMode.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testMatchMode.getTimeToWin()).isEqualTo(UPDATED_TIME_TO_WIN);
        assertThat(testMatchMode.getScoreToWin()).isEqualTo(UPDATED_SCORE_TO_WIN);
        assertThat(testMatchMode.getGameMode()).isEqualTo(UPDATED_GAME_MODE);
    }

    @Test
    @Transactional
    public void updateNonExistingMatchMode() throws Exception {
        int databaseSizeBeforeUpdate = matchModeRepository.findAll().size();

        // Create the MatchMode

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMatchModeMockMvc.perform(put("/api/match-modes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchMode)))
            .andExpect(status().isCreated());

        // Validate the MatchMode in the database
        List<MatchMode> matchModeList = matchModeRepository.findAll();
        assertThat(matchModeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMatchMode() throws Exception {
        // Initialize the database
        matchModeRepository.saveAndFlush(matchMode);
        int databaseSizeBeforeDelete = matchModeRepository.findAll().size();

        // Get the matchMode
        restMatchModeMockMvc.perform(delete("/api/match-modes/{id}", matchMode.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MatchMode> matchModeList = matchModeRepository.findAll();
        assertThat(matchModeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchMode.class);
        MatchMode matchMode1 = new MatchMode();
        matchMode1.setId(1L);
        MatchMode matchMode2 = new MatchMode();
        matchMode2.setId(matchMode1.getId());
        assertThat(matchMode1).isEqualTo(matchMode2);
        matchMode2.setId(2L);
        assertThat(matchMode1).isNotEqualTo(matchMode2);
        matchMode1.setId(null);
        assertThat(matchMode1).isNotEqualTo(matchMode2);
    }
}
