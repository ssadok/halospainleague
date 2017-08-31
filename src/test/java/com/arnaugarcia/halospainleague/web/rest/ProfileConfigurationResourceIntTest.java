package com.arnaugarcia.halospainleague.web.rest;

import com.arnaugarcia.halospainleague.HalospainleagueApp;

import com.arnaugarcia.halospainleague.domain.ProfileConfiguration;
import com.arnaugarcia.halospainleague.domain.Theme;
import com.arnaugarcia.halospainleague.domain.Player;
import com.arnaugarcia.halospainleague.repository.ProfileConfigurationRepository;
import com.arnaugarcia.halospainleague.service.dto.ProfileConfigurationDTO;
import com.arnaugarcia.halospainleague.service.mapper.ProfileConfigurationMapper;
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

/**
 * Test class for the ProfileConfigurationResource REST controller.
 *
 * @see ProfileConfigurationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HalospainleagueApp.class)
public class ProfileConfigurationResourceIntTest {

    private static final Boolean DEFAULT_SEND_NEWS = false;
    private static final Boolean UPDATED_SEND_NEWS = true;

    private static final Boolean DEFAULT_PRIVATE_MESSAGES = false;
    private static final Boolean UPDATED_PRIVATE_MESSAGES = true;

    private static final Boolean DEFAULT_TEAM_INVITES = false;
    private static final Boolean UPDATED_TEAM_INVITES = true;

    private static final Boolean DEFAULT_SHOW_DESCRIPTION = false;
    private static final Boolean UPDATED_SHOW_DESCRIPTION = true;

    private static final Boolean DEFAULT_SHOW_SCORE = false;
    private static final Boolean UPDATED_SHOW_SCORE = true;

    private static final Boolean DEFAULT_SHOW_SOCIAL = false;
    private static final Boolean UPDATED_SHOW_SOCIAL = true;

    private static final Boolean DEFAULT_SHOW_AGE = false;
    private static final Boolean UPDATED_SHOW_AGE = true;

    private static final Boolean DEFAULT_SHOW_GENDER = false;
    private static final Boolean UPDATED_SHOW_GENDER = true;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_TIME_ZONE = "AAAAAAAAAA";
    private static final String UPDATED_TIME_ZONE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_LOGIN = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_LOGIN = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_FIRST_RUN = false;
    private static final Boolean UPDATED_FIRST_RUN = true;

    private static final Boolean DEFAULT_SHOW_TUTORIAL = false;
    private static final Boolean UPDATED_SHOW_TUTORIAL = true;

    @Autowired
    private ProfileConfigurationRepository profileConfigurationRepository;

    @Autowired
    private ProfileConfigurationMapper profileConfigurationMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProfileConfigurationMockMvc;

    private ProfileConfiguration profileConfiguration;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProfileConfigurationResource profileConfigurationResource = new ProfileConfigurationResource(profileConfigurationRepository, profileConfigurationMapper);
        this.restProfileConfigurationMockMvc = MockMvcBuilders.standaloneSetup(profileConfigurationResource)
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
    public static ProfileConfiguration createEntity(EntityManager em) {
        ProfileConfiguration profileConfiguration = new ProfileConfiguration()
            .sendNews(DEFAULT_SEND_NEWS)
            .privateMessages(DEFAULT_PRIVATE_MESSAGES)
            .teamInvites(DEFAULT_TEAM_INVITES)
            .showDescription(DEFAULT_SHOW_DESCRIPTION)
            .showScore(DEFAULT_SHOW_SCORE)
            .showSocial(DEFAULT_SHOW_SOCIAL)
            .showAge(DEFAULT_SHOW_AGE)
            .showGender(DEFAULT_SHOW_GENDER)
            .active(DEFAULT_ACTIVE)
            .timeZone(DEFAULT_TIME_ZONE)
            .lastLogin(DEFAULT_LAST_LOGIN)
            .firstRun(DEFAULT_FIRST_RUN)
            .showTutorial(DEFAULT_SHOW_TUTORIAL);
        // Add required entity
        Theme theme = ThemeResourceIntTest.createEntity(em);
        em.persist(theme);
        em.flush();
        profileConfiguration.getThemes().add(theme);
        // Add required entity
        Player player = PlayerResourceIntTest.createEntity(em);
        em.persist(player);
        em.flush();
        profileConfiguration.setPlayer(player);
        return profileConfiguration;
    }

    @Before
    public void initTest() {
        profileConfiguration = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfileConfiguration() throws Exception {
        int databaseSizeBeforeCreate = profileConfigurationRepository.findAll().size();

        // Create the ProfileConfiguration
        ProfileConfigurationDTO profileConfigurationDTO = profileConfigurationMapper.toDto(profileConfiguration);
        restProfileConfigurationMockMvc.perform(post("/api/profile-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileConfigurationDTO)))
            .andExpect(status().isCreated());

        // Validate the ProfileConfiguration in the database
        List<ProfileConfiguration> profileConfigurationList = profileConfigurationRepository.findAll();
        assertThat(profileConfigurationList).hasSize(databaseSizeBeforeCreate + 1);
        ProfileConfiguration testProfileConfiguration = profileConfigurationList.get(profileConfigurationList.size() - 1);
        assertThat(testProfileConfiguration.isSendNews()).isEqualTo(DEFAULT_SEND_NEWS);
        assertThat(testProfileConfiguration.isPrivateMessages()).isEqualTo(DEFAULT_PRIVATE_MESSAGES);
        assertThat(testProfileConfiguration.isTeamInvites()).isEqualTo(DEFAULT_TEAM_INVITES);
        assertThat(testProfileConfiguration.isShowDescription()).isEqualTo(DEFAULT_SHOW_DESCRIPTION);
        assertThat(testProfileConfiguration.isShowScore()).isEqualTo(DEFAULT_SHOW_SCORE);
        assertThat(testProfileConfiguration.isShowSocial()).isEqualTo(DEFAULT_SHOW_SOCIAL);
        assertThat(testProfileConfiguration.isShowAge()).isEqualTo(DEFAULT_SHOW_AGE);
        assertThat(testProfileConfiguration.isShowGender()).isEqualTo(DEFAULT_SHOW_GENDER);
        assertThat(testProfileConfiguration.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testProfileConfiguration.getTimeZone()).isEqualTo(DEFAULT_TIME_ZONE);
        assertThat(testProfileConfiguration.getLastLogin()).isEqualTo(DEFAULT_LAST_LOGIN);
        assertThat(testProfileConfiguration.isFirstRun()).isEqualTo(DEFAULT_FIRST_RUN);
        assertThat(testProfileConfiguration.isShowTutorial()).isEqualTo(DEFAULT_SHOW_TUTORIAL);
    }

    @Test
    @Transactional
    public void createProfileConfigurationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profileConfigurationRepository.findAll().size();

        // Create the ProfileConfiguration with an existing ID
        profileConfiguration.setId(1L);
        ProfileConfigurationDTO profileConfigurationDTO = profileConfigurationMapper.toDto(profileConfiguration);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfileConfigurationMockMvc.perform(post("/api/profile-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileConfigurationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProfileConfiguration> profileConfigurationList = profileConfigurationRepository.findAll();
        assertThat(profileConfigurationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProfileConfigurations() throws Exception {
        // Initialize the database
        profileConfigurationRepository.saveAndFlush(profileConfiguration);

        // Get all the profileConfigurationList
        restProfileConfigurationMockMvc.perform(get("/api/profile-configurations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profileConfiguration.getId().intValue())))
            .andExpect(jsonPath("$.[*].sendNews").value(hasItem(DEFAULT_SEND_NEWS.booleanValue())))
            .andExpect(jsonPath("$.[*].privateMessages").value(hasItem(DEFAULT_PRIVATE_MESSAGES.booleanValue())))
            .andExpect(jsonPath("$.[*].teamInvites").value(hasItem(DEFAULT_TEAM_INVITES.booleanValue())))
            .andExpect(jsonPath("$.[*].showDescription").value(hasItem(DEFAULT_SHOW_DESCRIPTION.booleanValue())))
            .andExpect(jsonPath("$.[*].showScore").value(hasItem(DEFAULT_SHOW_SCORE.booleanValue())))
            .andExpect(jsonPath("$.[*].showSocial").value(hasItem(DEFAULT_SHOW_SOCIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].showAge").value(hasItem(DEFAULT_SHOW_AGE.booleanValue())))
            .andExpect(jsonPath("$.[*].showGender").value(hasItem(DEFAULT_SHOW_GENDER.booleanValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].timeZone").value(hasItem(DEFAULT_TIME_ZONE.toString())))
            .andExpect(jsonPath("$.[*].lastLogin").value(hasItem(sameInstant(DEFAULT_LAST_LOGIN))))
            .andExpect(jsonPath("$.[*].firstRun").value(hasItem(DEFAULT_FIRST_RUN.booleanValue())))
            .andExpect(jsonPath("$.[*].showTutorial").value(hasItem(DEFAULT_SHOW_TUTORIAL.booleanValue())));
    }

    @Test
    @Transactional
    public void getProfileConfiguration() throws Exception {
        // Initialize the database
        profileConfigurationRepository.saveAndFlush(profileConfiguration);

        // Get the profileConfiguration
        restProfileConfigurationMockMvc.perform(get("/api/profile-configurations/{id}", profileConfiguration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(profileConfiguration.getId().intValue()))
            .andExpect(jsonPath("$.sendNews").value(DEFAULT_SEND_NEWS.booleanValue()))
            .andExpect(jsonPath("$.privateMessages").value(DEFAULT_PRIVATE_MESSAGES.booleanValue()))
            .andExpect(jsonPath("$.teamInvites").value(DEFAULT_TEAM_INVITES.booleanValue()))
            .andExpect(jsonPath("$.showDescription").value(DEFAULT_SHOW_DESCRIPTION.booleanValue()))
            .andExpect(jsonPath("$.showScore").value(DEFAULT_SHOW_SCORE.booleanValue()))
            .andExpect(jsonPath("$.showSocial").value(DEFAULT_SHOW_SOCIAL.booleanValue()))
            .andExpect(jsonPath("$.showAge").value(DEFAULT_SHOW_AGE.booleanValue()))
            .andExpect(jsonPath("$.showGender").value(DEFAULT_SHOW_GENDER.booleanValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.timeZone").value(DEFAULT_TIME_ZONE.toString()))
            .andExpect(jsonPath("$.lastLogin").value(sameInstant(DEFAULT_LAST_LOGIN)))
            .andExpect(jsonPath("$.firstRun").value(DEFAULT_FIRST_RUN.booleanValue()))
            .andExpect(jsonPath("$.showTutorial").value(DEFAULT_SHOW_TUTORIAL.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProfileConfiguration() throws Exception {
        // Get the profileConfiguration
        restProfileConfigurationMockMvc.perform(get("/api/profile-configurations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfileConfiguration() throws Exception {
        // Initialize the database
        profileConfigurationRepository.saveAndFlush(profileConfiguration);
        int databaseSizeBeforeUpdate = profileConfigurationRepository.findAll().size();

        // Update the profileConfiguration
        ProfileConfiguration updatedProfileConfiguration = profileConfigurationRepository.findOne(profileConfiguration.getId());
        updatedProfileConfiguration
            .sendNews(UPDATED_SEND_NEWS)
            .privateMessages(UPDATED_PRIVATE_MESSAGES)
            .teamInvites(UPDATED_TEAM_INVITES)
            .showDescription(UPDATED_SHOW_DESCRIPTION)
            .showScore(UPDATED_SHOW_SCORE)
            .showSocial(UPDATED_SHOW_SOCIAL)
            .showAge(UPDATED_SHOW_AGE)
            .showGender(UPDATED_SHOW_GENDER)
            .active(UPDATED_ACTIVE)
            .timeZone(UPDATED_TIME_ZONE)
            .lastLogin(UPDATED_LAST_LOGIN)
            .firstRun(UPDATED_FIRST_RUN)
            .showTutorial(UPDATED_SHOW_TUTORIAL);
        ProfileConfigurationDTO profileConfigurationDTO = profileConfigurationMapper.toDto(updatedProfileConfiguration);

        restProfileConfigurationMockMvc.perform(put("/api/profile-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileConfigurationDTO)))
            .andExpect(status().isOk());

        // Validate the ProfileConfiguration in the database
        List<ProfileConfiguration> profileConfigurationList = profileConfigurationRepository.findAll();
        assertThat(profileConfigurationList).hasSize(databaseSizeBeforeUpdate);
        ProfileConfiguration testProfileConfiguration = profileConfigurationList.get(profileConfigurationList.size() - 1);
        assertThat(testProfileConfiguration.isSendNews()).isEqualTo(UPDATED_SEND_NEWS);
        assertThat(testProfileConfiguration.isPrivateMessages()).isEqualTo(UPDATED_PRIVATE_MESSAGES);
        assertThat(testProfileConfiguration.isTeamInvites()).isEqualTo(UPDATED_TEAM_INVITES);
        assertThat(testProfileConfiguration.isShowDescription()).isEqualTo(UPDATED_SHOW_DESCRIPTION);
        assertThat(testProfileConfiguration.isShowScore()).isEqualTo(UPDATED_SHOW_SCORE);
        assertThat(testProfileConfiguration.isShowSocial()).isEqualTo(UPDATED_SHOW_SOCIAL);
        assertThat(testProfileConfiguration.isShowAge()).isEqualTo(UPDATED_SHOW_AGE);
        assertThat(testProfileConfiguration.isShowGender()).isEqualTo(UPDATED_SHOW_GENDER);
        assertThat(testProfileConfiguration.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testProfileConfiguration.getTimeZone()).isEqualTo(UPDATED_TIME_ZONE);
        assertThat(testProfileConfiguration.getLastLogin()).isEqualTo(UPDATED_LAST_LOGIN);
        assertThat(testProfileConfiguration.isFirstRun()).isEqualTo(UPDATED_FIRST_RUN);
        assertThat(testProfileConfiguration.isShowTutorial()).isEqualTo(UPDATED_SHOW_TUTORIAL);
    }

    @Test
    @Transactional
    public void updateNonExistingProfileConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = profileConfigurationRepository.findAll().size();

        // Create the ProfileConfiguration
        ProfileConfigurationDTO profileConfigurationDTO = profileConfigurationMapper.toDto(profileConfiguration);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProfileConfigurationMockMvc.perform(put("/api/profile-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileConfigurationDTO)))
            .andExpect(status().isCreated());

        // Validate the ProfileConfiguration in the database
        List<ProfileConfiguration> profileConfigurationList = profileConfigurationRepository.findAll();
        assertThat(profileConfigurationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProfileConfiguration() throws Exception {
        // Initialize the database
        profileConfigurationRepository.saveAndFlush(profileConfiguration);
        int databaseSizeBeforeDelete = profileConfigurationRepository.findAll().size();

        // Get the profileConfiguration
        restProfileConfigurationMockMvc.perform(delete("/api/profile-configurations/{id}", profileConfiguration.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProfileConfiguration> profileConfigurationList = profileConfigurationRepository.findAll();
        assertThat(profileConfigurationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfileConfiguration.class);
        ProfileConfiguration profileConfiguration1 = new ProfileConfiguration();
        profileConfiguration1.setId(1L);
        ProfileConfiguration profileConfiguration2 = new ProfileConfiguration();
        profileConfiguration2.setId(profileConfiguration1.getId());
        assertThat(profileConfiguration1).isEqualTo(profileConfiguration2);
        profileConfiguration2.setId(2L);
        assertThat(profileConfiguration1).isNotEqualTo(profileConfiguration2);
        profileConfiguration1.setId(null);
        assertThat(profileConfiguration1).isNotEqualTo(profileConfiguration2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfileConfigurationDTO.class);
        ProfileConfigurationDTO profileConfigurationDTO1 = new ProfileConfigurationDTO();
        profileConfigurationDTO1.setId(1L);
        ProfileConfigurationDTO profileConfigurationDTO2 = new ProfileConfigurationDTO();
        assertThat(profileConfigurationDTO1).isNotEqualTo(profileConfigurationDTO2);
        profileConfigurationDTO2.setId(profileConfigurationDTO1.getId());
        assertThat(profileConfigurationDTO1).isEqualTo(profileConfigurationDTO2);
        profileConfigurationDTO2.setId(2L);
        assertThat(profileConfigurationDTO1).isNotEqualTo(profileConfigurationDTO2);
        profileConfigurationDTO1.setId(null);
        assertThat(profileConfigurationDTO1).isNotEqualTo(profileConfigurationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(profileConfigurationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(profileConfigurationMapper.fromId(null)).isNull();
    }
}
