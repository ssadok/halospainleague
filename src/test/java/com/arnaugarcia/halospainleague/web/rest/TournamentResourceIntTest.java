package com.arnaugarcia.halospainleague.web.rest;

import com.arnaugarcia.halospainleague.HalospainleagueApp;

import com.arnaugarcia.halospainleague.domain.Tournament;
import com.arnaugarcia.halospainleague.repository.TournamentRepository;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.arnaugarcia.halospainleague.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.arnaugarcia.halospainleague.domain.enumeration.Platform;
import com.arnaugarcia.halospainleague.domain.enumeration.TournamentType;
/**
 * Test class for the TournamentResource REST controller.
 *
 * @see TournamentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HalospainleagueApp.class)
public class TournamentResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_COVER_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_COVER_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_COVER_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_COVER_IMAGE_CONTENT_TYPE = "image/png";

    private static final Platform DEFAULT_PLATFORM = Platform.PC;
    private static final Platform UPDATED_PLATFORM = Platform.XB360;

    private static final Integer DEFAULT_MAX_TEAMS = 1;
    private static final Integer UPDATED_MAX_TEAMS = 2;

    private static final ZonedDateTime DEFAULT_REGISTRATION_STARTS = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_REGISTRATION_STARTS = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_REGISTRATION_ENDS = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_REGISTRATION_ENDS = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_TOURNAMENT_BEGINS = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TOURNAMENT_BEGINS = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final Integer DEFAULT_GAMES_PER_ROUND = 1;
    private static final Integer UPDATED_GAMES_PER_ROUND = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final TournamentType DEFAULT_TYPE = TournamentType.ONE_VS_ONE;
    private static final TournamentType UPDATED_TYPE = TournamentType.TWO_VS_TWO;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTournamentMockMvc;

    private Tournament tournament;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TournamentResource tournamentResource = new TournamentResource(tournamentRepository);
        this.restTournamentMockMvc = MockMvcBuilders.standaloneSetup(tournamentResource)
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
    public static Tournament createEntity(EntityManager em) {
        Tournament tournament = new Tournament()
            .name(DEFAULT_NAME)
            .coverImage(DEFAULT_COVER_IMAGE)
            .coverImageContentType(DEFAULT_COVER_IMAGE_CONTENT_TYPE)
            .platform(DEFAULT_PLATFORM)
            .maxTeams(DEFAULT_MAX_TEAMS)
            .registrationStarts(DEFAULT_REGISTRATION_STARTS)
            .registrationEnds(DEFAULT_REGISTRATION_ENDS)
            .tournamentBegins(DEFAULT_TOURNAMENT_BEGINS)
            .price(DEFAULT_PRICE)
            .gamesPerRound(DEFAULT_GAMES_PER_ROUND)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE);
        return tournament;
    }

    @Before
    public void initTest() {
        tournament = createEntity(em);
    }

    @Test
    @Transactional
    public void createTournament() throws Exception {
        int databaseSizeBeforeCreate = tournamentRepository.findAll().size();

        // Create the Tournament
        restTournamentMockMvc.perform(post("/api/tournaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tournament)))
            .andExpect(status().isCreated());

        // Validate the Tournament in the database
        List<Tournament> tournamentList = tournamentRepository.findAll();
        assertThat(tournamentList).hasSize(databaseSizeBeforeCreate + 1);
        Tournament testTournament = tournamentList.get(tournamentList.size() - 1);
        assertThat(testTournament.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTournament.getCoverImage()).isEqualTo(DEFAULT_COVER_IMAGE);
        assertThat(testTournament.getCoverImageContentType()).isEqualTo(DEFAULT_COVER_IMAGE_CONTENT_TYPE);
        assertThat(testTournament.getPlatform()).isEqualTo(DEFAULT_PLATFORM);
        assertThat(testTournament.getMaxTeams()).isEqualTo(DEFAULT_MAX_TEAMS);
        assertThat(testTournament.getRegistrationStarts()).isEqualTo(DEFAULT_REGISTRATION_STARTS);
        assertThat(testTournament.getRegistrationEnds()).isEqualTo(DEFAULT_REGISTRATION_ENDS);
        assertThat(testTournament.getTournamentBegins()).isEqualTo(DEFAULT_TOURNAMENT_BEGINS);
        assertThat(testTournament.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testTournament.getGamesPerRound()).isEqualTo(DEFAULT_GAMES_PER_ROUND);
        assertThat(testTournament.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTournament.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createTournamentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tournamentRepository.findAll().size();

        // Create the Tournament with an existing ID
        tournament.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTournamentMockMvc.perform(post("/api/tournaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tournament)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tournament> tournamentList = tournamentRepository.findAll();
        assertThat(tournamentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMaxTeamsIsRequired() throws Exception {
        int databaseSizeBeforeTest = tournamentRepository.findAll().size();
        // set the field null
        tournament.setMaxTeams(null);

        // Create the Tournament, which fails.

        restTournamentMockMvc.perform(post("/api/tournaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tournament)))
            .andExpect(status().isBadRequest());

        List<Tournament> tournamentList = tournamentRepository.findAll();
        assertThat(tournamentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTournaments() throws Exception {
        // Initialize the database
        tournamentRepository.saveAndFlush(tournament);

        // Get all the tournamentList
        restTournamentMockMvc.perform(get("/api/tournaments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tournament.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].coverImageContentType").value(hasItem(DEFAULT_COVER_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].coverImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_COVER_IMAGE))))
            .andExpect(jsonPath("$.[*].platform").value(hasItem(DEFAULT_PLATFORM.toString())))
            .andExpect(jsonPath("$.[*].maxTeams").value(hasItem(DEFAULT_MAX_TEAMS)))
            .andExpect(jsonPath("$.[*].registrationStarts").value(hasItem(sameInstant(DEFAULT_REGISTRATION_STARTS))))
            .andExpect(jsonPath("$.[*].registrationEnds").value(hasItem(sameInstant(DEFAULT_REGISTRATION_ENDS))))
            .andExpect(jsonPath("$.[*].tournamentBegins").value(hasItem(sameInstant(DEFAULT_TOURNAMENT_BEGINS))))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].gamesPerRound").value(hasItem(DEFAULT_GAMES_PER_ROUND)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getTournament() throws Exception {
        // Initialize the database
        tournamentRepository.saveAndFlush(tournament);

        // Get the tournament
        restTournamentMockMvc.perform(get("/api/tournaments/{id}", tournament.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tournament.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.coverImageContentType").value(DEFAULT_COVER_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.coverImage").value(Base64Utils.encodeToString(DEFAULT_COVER_IMAGE)))
            .andExpect(jsonPath("$.platform").value(DEFAULT_PLATFORM.toString()))
            .andExpect(jsonPath("$.maxTeams").value(DEFAULT_MAX_TEAMS))
            .andExpect(jsonPath("$.registrationStarts").value(sameInstant(DEFAULT_REGISTRATION_STARTS)))
            .andExpect(jsonPath("$.registrationEnds").value(sameInstant(DEFAULT_REGISTRATION_ENDS)))
            .andExpect(jsonPath("$.tournamentBegins").value(sameInstant(DEFAULT_TOURNAMENT_BEGINS)))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.gamesPerRound").value(DEFAULT_GAMES_PER_ROUND))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTournament() throws Exception {
        // Get the tournament
        restTournamentMockMvc.perform(get("/api/tournaments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTournament() throws Exception {
        // Initialize the database
        tournamentRepository.saveAndFlush(tournament);
        int databaseSizeBeforeUpdate = tournamentRepository.findAll().size();

        // Update the tournament
        Tournament updatedTournament = tournamentRepository.findOne(tournament.getId());
        updatedTournament
            .name(UPDATED_NAME)
            .coverImage(UPDATED_COVER_IMAGE)
            .coverImageContentType(UPDATED_COVER_IMAGE_CONTENT_TYPE)
            .platform(UPDATED_PLATFORM)
            .maxTeams(UPDATED_MAX_TEAMS)
            .registrationStarts(UPDATED_REGISTRATION_STARTS)
            .registrationEnds(UPDATED_REGISTRATION_ENDS)
            .tournamentBegins(UPDATED_TOURNAMENT_BEGINS)
            .price(UPDATED_PRICE)
            .gamesPerRound(UPDATED_GAMES_PER_ROUND)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE);

        restTournamentMockMvc.perform(put("/api/tournaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTournament)))
            .andExpect(status().isOk());

        // Validate the Tournament in the database
        List<Tournament> tournamentList = tournamentRepository.findAll();
        assertThat(tournamentList).hasSize(databaseSizeBeforeUpdate);
        Tournament testTournament = tournamentList.get(tournamentList.size() - 1);
        assertThat(testTournament.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTournament.getCoverImage()).isEqualTo(UPDATED_COVER_IMAGE);
        assertThat(testTournament.getCoverImageContentType()).isEqualTo(UPDATED_COVER_IMAGE_CONTENT_TYPE);
        assertThat(testTournament.getPlatform()).isEqualTo(UPDATED_PLATFORM);
        assertThat(testTournament.getMaxTeams()).isEqualTo(UPDATED_MAX_TEAMS);
        assertThat(testTournament.getRegistrationStarts()).isEqualTo(UPDATED_REGISTRATION_STARTS);
        assertThat(testTournament.getRegistrationEnds()).isEqualTo(UPDATED_REGISTRATION_ENDS);
        assertThat(testTournament.getTournamentBegins()).isEqualTo(UPDATED_TOURNAMENT_BEGINS);
        assertThat(testTournament.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testTournament.getGamesPerRound()).isEqualTo(UPDATED_GAMES_PER_ROUND);
        assertThat(testTournament.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTournament.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingTournament() throws Exception {
        int databaseSizeBeforeUpdate = tournamentRepository.findAll().size();

        // Create the Tournament

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTournamentMockMvc.perform(put("/api/tournaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tournament)))
            .andExpect(status().isCreated());

        // Validate the Tournament in the database
        List<Tournament> tournamentList = tournamentRepository.findAll();
        assertThat(tournamentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTournament() throws Exception {
        // Initialize the database
        tournamentRepository.saveAndFlush(tournament);
        int databaseSizeBeforeDelete = tournamentRepository.findAll().size();

        // Get the tournament
        restTournamentMockMvc.perform(delete("/api/tournaments/{id}", tournament.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Tournament> tournamentList = tournamentRepository.findAll();
        assertThat(tournamentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tournament.class);
        Tournament tournament1 = new Tournament();
        tournament1.setId(1L);
        Tournament tournament2 = new Tournament();
        tournament2.setId(tournament1.getId());
        assertThat(tournament1).isEqualTo(tournament2);
        tournament2.setId(2L);
        assertThat(tournament1).isNotEqualTo(tournament2);
        tournament1.setId(null);
        assertThat(tournament1).isNotEqualTo(tournament2);
    }
}
