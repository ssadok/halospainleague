package com.arnaugarcia.halospainleague.web.rest;

import com.arnaugarcia.halospainleague.HalospainleagueApp;

import com.arnaugarcia.halospainleague.domain.Team;
import com.arnaugarcia.halospainleague.repository.TeamRepository;
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

import com.arnaugarcia.halospainleague.domain.enumeration.TournamentType;
/**
 * Test class for the TeamResource REST controller.
 *
 * @see TeamResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HalospainleagueApp.class)
public class TeamResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_COVER = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_COVER = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_COVER_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_COVER_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_PROFILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PROFILE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PROFILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PROFILE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_WEB = "AAAAAAAAAA";
    private static final String UPDATED_WEB = "BBBBBBBBBB";

    private static final Integer DEFAULT_EXPERIENCE = 1;
    private static final Integer UPDATED_EXPERIENCE = 2;

    private static final Integer DEFAULT_PING = 1;
    private static final Integer UPDATED_PING = 2;

    private static final TournamentType DEFAULT_TOURNAMENT_TYPE = TournamentType.ONE_VS_ONE;
    private static final TournamentType UPDATED_TOURNAMENT_TYPE = TournamentType.TWO_VS_TWO;

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_WINS = 1;
    private static final Integer UPDATED_WINS = 2;

    private static final Integer DEFAULT_LOSSES = 1;
    private static final Integer UPDATED_LOSSES = 2;

    private static final Boolean DEFAULT_STREAK = false;
    private static final Boolean UPDATED_STREAK = true;

    private static final Integer DEFAULT_POSITION = 1;
    private static final Integer UPDATED_POSITION = 2;

    private static final Boolean DEFAULT_PREMIUM = false;
    private static final Boolean UPDATED_PREMIUM = true;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTeamMockMvc;

    private Team team;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TeamResource teamResource = new TeamResource(teamRepository);
        this.restTeamMockMvc = MockMvcBuilders.standaloneSetup(teamResource)
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
    public static Team createEntity(EntityManager em) {
        Team team = new Team()
            .name(DEFAULT_NAME)
            .cover(DEFAULT_COVER)
            .coverContentType(DEFAULT_COVER_CONTENT_TYPE)
            .profile(DEFAULT_PROFILE)
            .profileContentType(DEFAULT_PROFILE_CONTENT_TYPE)
            .web(DEFAULT_WEB)
            .experience(DEFAULT_EXPERIENCE)
            .ping(DEFAULT_PING)
            .tournamentType(DEFAULT_TOURNAMENT_TYPE)
            .created(DEFAULT_CREATED)
            .wins(DEFAULT_WINS)
            .losses(DEFAULT_LOSSES)
            .streak(DEFAULT_STREAK)
            .position(DEFAULT_POSITION)
            .premium(DEFAULT_PREMIUM);
        return team;
    }

    @Before
    public void initTest() {
        team = createEntity(em);
    }

    @Test
    @Transactional
    public void createTeam() throws Exception {
        int databaseSizeBeforeCreate = teamRepository.findAll().size();

        // Create the Team
        restTeamMockMvc.perform(post("/api/teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(team)))
            .andExpect(status().isCreated());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeCreate + 1);
        Team testTeam = teamList.get(teamList.size() - 1);
        assertThat(testTeam.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTeam.getCover()).isEqualTo(DEFAULT_COVER);
        assertThat(testTeam.getCoverContentType()).isEqualTo(DEFAULT_COVER_CONTENT_TYPE);
        assertThat(testTeam.getProfile()).isEqualTo(DEFAULT_PROFILE);
        assertThat(testTeam.getProfileContentType()).isEqualTo(DEFAULT_PROFILE_CONTENT_TYPE);
        assertThat(testTeam.getWeb()).isEqualTo(DEFAULT_WEB);
        assertThat(testTeam.getExperience()).isEqualTo(DEFAULT_EXPERIENCE);
        assertThat(testTeam.getPing()).isEqualTo(DEFAULT_PING);
        assertThat(testTeam.getTournamentType()).isEqualTo(DEFAULT_TOURNAMENT_TYPE);
        assertThat(testTeam.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testTeam.getWins()).isEqualTo(DEFAULT_WINS);
        assertThat(testTeam.getLosses()).isEqualTo(DEFAULT_LOSSES);
        assertThat(testTeam.isStreak()).isEqualTo(DEFAULT_STREAK);
        assertThat(testTeam.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testTeam.isPremium()).isEqualTo(DEFAULT_PREMIUM);
    }

    @Test
    @Transactional
    public void createTeamWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = teamRepository.findAll().size();

        // Create the Team with an existing ID
        team.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeamMockMvc.perform(post("/api/teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(team)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTeams() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList
        restTeamMockMvc.perform(get("/api/teams?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(team.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].coverContentType").value(hasItem(DEFAULT_COVER_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].cover").value(hasItem(Base64Utils.encodeToString(DEFAULT_COVER))))
            .andExpect(jsonPath("$.[*].profileContentType").value(hasItem(DEFAULT_PROFILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].profile").value(hasItem(Base64Utils.encodeToString(DEFAULT_PROFILE))))
            .andExpect(jsonPath("$.[*].web").value(hasItem(DEFAULT_WEB.toString())))
            .andExpect(jsonPath("$.[*].experience").value(hasItem(DEFAULT_EXPERIENCE)))
            .andExpect(jsonPath("$.[*].ping").value(hasItem(DEFAULT_PING)))
            .andExpect(jsonPath("$.[*].tournamentType").value(hasItem(DEFAULT_TOURNAMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))))
            .andExpect(jsonPath("$.[*].wins").value(hasItem(DEFAULT_WINS)))
            .andExpect(jsonPath("$.[*].losses").value(hasItem(DEFAULT_LOSSES)))
            .andExpect(jsonPath("$.[*].streak").value(hasItem(DEFAULT_STREAK.booleanValue())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].premium").value(hasItem(DEFAULT_PREMIUM.booleanValue())));
    }

    @Test
    @Transactional
    public void getTeam() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get the team
        restTeamMockMvc.perform(get("/api/teams/{id}", team.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(team.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.coverContentType").value(DEFAULT_COVER_CONTENT_TYPE))
            .andExpect(jsonPath("$.cover").value(Base64Utils.encodeToString(DEFAULT_COVER)))
            .andExpect(jsonPath("$.profileContentType").value(DEFAULT_PROFILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.profile").value(Base64Utils.encodeToString(DEFAULT_PROFILE)))
            .andExpect(jsonPath("$.web").value(DEFAULT_WEB.toString()))
            .andExpect(jsonPath("$.experience").value(DEFAULT_EXPERIENCE))
            .andExpect(jsonPath("$.ping").value(DEFAULT_PING))
            .andExpect(jsonPath("$.tournamentType").value(DEFAULT_TOURNAMENT_TYPE.toString()))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)))
            .andExpect(jsonPath("$.wins").value(DEFAULT_WINS))
            .andExpect(jsonPath("$.losses").value(DEFAULT_LOSSES))
            .andExpect(jsonPath("$.streak").value(DEFAULT_STREAK.booleanValue()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.premium").value(DEFAULT_PREMIUM.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTeam() throws Exception {
        // Get the team
        restTeamMockMvc.perform(get("/api/teams/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTeam() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);
        int databaseSizeBeforeUpdate = teamRepository.findAll().size();

        // Update the team
        Team updatedTeam = teamRepository.findOne(team.getId());
        updatedTeam
            .name(UPDATED_NAME)
            .cover(UPDATED_COVER)
            .coverContentType(UPDATED_COVER_CONTENT_TYPE)
            .profile(UPDATED_PROFILE)
            .profileContentType(UPDATED_PROFILE_CONTENT_TYPE)
            .web(UPDATED_WEB)
            .experience(UPDATED_EXPERIENCE)
            .ping(UPDATED_PING)
            .tournamentType(UPDATED_TOURNAMENT_TYPE)
            .created(UPDATED_CREATED)
            .wins(UPDATED_WINS)
            .losses(UPDATED_LOSSES)
            .streak(UPDATED_STREAK)
            .position(UPDATED_POSITION)
            .premium(UPDATED_PREMIUM);

        restTeamMockMvc.perform(put("/api/teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTeam)))
            .andExpect(status().isOk());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
        Team testTeam = teamList.get(teamList.size() - 1);
        assertThat(testTeam.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTeam.getCover()).isEqualTo(UPDATED_COVER);
        assertThat(testTeam.getCoverContentType()).isEqualTo(UPDATED_COVER_CONTENT_TYPE);
        assertThat(testTeam.getProfile()).isEqualTo(UPDATED_PROFILE);
        assertThat(testTeam.getProfileContentType()).isEqualTo(UPDATED_PROFILE_CONTENT_TYPE);
        assertThat(testTeam.getWeb()).isEqualTo(UPDATED_WEB);
        assertThat(testTeam.getExperience()).isEqualTo(UPDATED_EXPERIENCE);
        assertThat(testTeam.getPing()).isEqualTo(UPDATED_PING);
        assertThat(testTeam.getTournamentType()).isEqualTo(UPDATED_TOURNAMENT_TYPE);
        assertThat(testTeam.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testTeam.getWins()).isEqualTo(UPDATED_WINS);
        assertThat(testTeam.getLosses()).isEqualTo(UPDATED_LOSSES);
        assertThat(testTeam.isStreak()).isEqualTo(UPDATED_STREAK);
        assertThat(testTeam.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testTeam.isPremium()).isEqualTo(UPDATED_PREMIUM);
    }

    @Test
    @Transactional
    public void updateNonExistingTeam() throws Exception {
        int databaseSizeBeforeUpdate = teamRepository.findAll().size();

        // Create the Team

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTeamMockMvc.perform(put("/api/teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(team)))
            .andExpect(status().isCreated());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTeam() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);
        int databaseSizeBeforeDelete = teamRepository.findAll().size();

        // Get the team
        restTeamMockMvc.perform(delete("/api/teams/{id}", team.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Team.class);
        Team team1 = new Team();
        team1.setId(1L);
        Team team2 = new Team();
        team2.setId(team1.getId());
        assertThat(team1).isEqualTo(team2);
        team2.setId(2L);
        assertThat(team1).isNotEqualTo(team2);
        team1.setId(null);
        assertThat(team1).isNotEqualTo(team2);
    }
}
