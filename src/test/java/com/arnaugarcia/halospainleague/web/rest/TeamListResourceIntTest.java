package com.arnaugarcia.halospainleague.web.rest;

import com.arnaugarcia.halospainleague.HalospainleagueApp;

import com.arnaugarcia.halospainleague.domain.TeamList;
import com.arnaugarcia.halospainleague.repository.TeamListRepository;
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
 * Test class for the TeamListResource REST controller.
 *
 * @see TeamListResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HalospainleagueApp.class)
public class TeamListResourceIntTest {

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final Integer DEFAULT_SCORE = 1;
    private static final Integer UPDATED_SCORE = 2;

    @Autowired
    private TeamListRepository teamListRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTeamListMockMvc;

    private TeamList teamList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TeamListResource teamListResource = new TeamListResource(teamListRepository);
        this.restTeamListMockMvc = MockMvcBuilders.standaloneSetup(teamListResource)
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
    public static TeamList createEntity(EntityManager em) {
        TeamList teamList = new TeamList()
            .reference(DEFAULT_REFERENCE)
            .score(DEFAULT_SCORE);
        return teamList;
    }

    @Before
    public void initTest() {
        teamList = createEntity(em);
    }

    @Test
    @Transactional
    public void createTeamList() throws Exception {
        int databaseSizeBeforeCreate = teamListRepository.findAll().size();

        // Create the TeamList
        restTeamListMockMvc.perform(post("/api/team-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamList)))
            .andExpect(status().isCreated());

        // Validate the TeamList in the database
        List<TeamList> teamListList = teamListRepository.findAll();
        assertThat(teamListList).hasSize(databaseSizeBeforeCreate + 1);
        TeamList testTeamList = teamListList.get(teamListList.size() - 1);
        assertThat(testTeamList.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testTeamList.getScore()).isEqualTo(DEFAULT_SCORE);
    }

    @Test
    @Transactional
    public void createTeamListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = teamListRepository.findAll().size();

        // Create the TeamList with an existing ID
        teamList.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeamListMockMvc.perform(post("/api/team-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamList)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TeamList> teamListList = teamListRepository.findAll();
        assertThat(teamListList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTeamLists() throws Exception {
        // Initialize the database
        teamListRepository.saveAndFlush(teamList);

        // Get all the teamListList
        restTeamListMockMvc.perform(get("/api/team-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teamList.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)));
    }

    @Test
    @Transactional
    public void getTeamList() throws Exception {
        // Initialize the database
        teamListRepository.saveAndFlush(teamList);

        // Get the teamList
        restTeamListMockMvc.perform(get("/api/team-lists/{id}", teamList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(teamList.getId().intValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE));
    }

    @Test
    @Transactional
    public void getNonExistingTeamList() throws Exception {
        // Get the teamList
        restTeamListMockMvc.perform(get("/api/team-lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTeamList() throws Exception {
        // Initialize the database
        teamListRepository.saveAndFlush(teamList);
        int databaseSizeBeforeUpdate = teamListRepository.findAll().size();

        // Update the teamList
        TeamList updatedTeamList = teamListRepository.findOne(teamList.getId());
        updatedTeamList
            .reference(UPDATED_REFERENCE)
            .score(UPDATED_SCORE);

        restTeamListMockMvc.perform(put("/api/team-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTeamList)))
            .andExpect(status().isOk());

        // Validate the TeamList in the database
        List<TeamList> teamListList = teamListRepository.findAll();
        assertThat(teamListList).hasSize(databaseSizeBeforeUpdate);
        TeamList testTeamList = teamListList.get(teamListList.size() - 1);
        assertThat(testTeamList.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testTeamList.getScore()).isEqualTo(UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void updateNonExistingTeamList() throws Exception {
        int databaseSizeBeforeUpdate = teamListRepository.findAll().size();

        // Create the TeamList

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTeamListMockMvc.perform(put("/api/team-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamList)))
            .andExpect(status().isCreated());

        // Validate the TeamList in the database
        List<TeamList> teamListList = teamListRepository.findAll();
        assertThat(teamListList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTeamList() throws Exception {
        // Initialize the database
        teamListRepository.saveAndFlush(teamList);
        int databaseSizeBeforeDelete = teamListRepository.findAll().size();

        // Get the teamList
        restTeamListMockMvc.perform(delete("/api/team-lists/{id}", teamList.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TeamList> teamListList = teamListRepository.findAll();
        assertThat(teamListList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeamList.class);
        TeamList teamList1 = new TeamList();
        teamList1.setId(1L);
        TeamList teamList2 = new TeamList();
        teamList2.setId(teamList1.getId());
        assertThat(teamList1).isEqualTo(teamList2);
        teamList2.setId(2L);
        assertThat(teamList1).isNotEqualTo(teamList2);
        teamList1.setId(null);
        assertThat(teamList1).isNotEqualTo(teamList2);
    }
}
