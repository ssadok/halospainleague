package com.arnaugarcia.halospainleague.web.rest;

import com.arnaugarcia.halospainleague.HalospainleagueApp;

import com.arnaugarcia.halospainleague.domain.SocialAccount;
import com.arnaugarcia.halospainleague.repository.SocialAccountRepository;
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

import com.arnaugarcia.halospainleague.domain.enumeration.Platform;
/**
 * Test class for the SocialAccountResource REST controller.
 *
 * @see SocialAccountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HalospainleagueApp.class)
public class SocialAccountResourceIntTest {

    private static final String DEFAULT_NICK = "AAAAAAAAAA";
    private static final String UPDATED_NICK = "BBBBBBBBBB";

    private static final Platform DEFAULT_PLATFORM = Platform.PC;
    private static final Platform UPDATED_PLATFORM = Platform.XB360;

    private static final String DEFAULT_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN = "BBBBBBBBBB";

    @Autowired
    private SocialAccountRepository socialAccountRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSocialAccountMockMvc;

    private SocialAccount socialAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SocialAccountResource socialAccountResource = new SocialAccountResource(socialAccountRepository);
        this.restSocialAccountMockMvc = MockMvcBuilders.standaloneSetup(socialAccountResource)
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
    public static SocialAccount createEntity(EntityManager em) {
        SocialAccount socialAccount = new SocialAccount()
            .nick(DEFAULT_NICK)
            .platform(DEFAULT_PLATFORM)
            .token(DEFAULT_TOKEN);
        return socialAccount;
    }

    @Before
    public void initTest() {
        socialAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createSocialAccount() throws Exception {
        int databaseSizeBeforeCreate = socialAccountRepository.findAll().size();

        // Create the SocialAccount
        restSocialAccountMockMvc.perform(post("/api/social-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(socialAccount)))
            .andExpect(status().isCreated());

        // Validate the SocialAccount in the database
        List<SocialAccount> socialAccountList = socialAccountRepository.findAll();
        assertThat(socialAccountList).hasSize(databaseSizeBeforeCreate + 1);
        SocialAccount testSocialAccount = socialAccountList.get(socialAccountList.size() - 1);
        assertThat(testSocialAccount.getNick()).isEqualTo(DEFAULT_NICK);
        assertThat(testSocialAccount.getPlatform()).isEqualTo(DEFAULT_PLATFORM);
        assertThat(testSocialAccount.getToken()).isEqualTo(DEFAULT_TOKEN);
    }

    @Test
    @Transactional
    public void createSocialAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = socialAccountRepository.findAll().size();

        // Create the SocialAccount with an existing ID
        socialAccount.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocialAccountMockMvc.perform(post("/api/social-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(socialAccount)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SocialAccount> socialAccountList = socialAccountRepository.findAll();
        assertThat(socialAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSocialAccounts() throws Exception {
        // Initialize the database
        socialAccountRepository.saveAndFlush(socialAccount);

        // Get all the socialAccountList
        restSocialAccountMockMvc.perform(get("/api/social-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(socialAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].nick").value(hasItem(DEFAULT_NICK.toString())))
            .andExpect(jsonPath("$.[*].platform").value(hasItem(DEFAULT_PLATFORM.toString())))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN.toString())));
    }

    @Test
    @Transactional
    public void getSocialAccount() throws Exception {
        // Initialize the database
        socialAccountRepository.saveAndFlush(socialAccount);

        // Get the socialAccount
        restSocialAccountMockMvc.perform(get("/api/social-accounts/{id}", socialAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(socialAccount.getId().intValue()))
            .andExpect(jsonPath("$.nick").value(DEFAULT_NICK.toString()))
            .andExpect(jsonPath("$.platform").value(DEFAULT_PLATFORM.toString()))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSocialAccount() throws Exception {
        // Get the socialAccount
        restSocialAccountMockMvc.perform(get("/api/social-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSocialAccount() throws Exception {
        // Initialize the database
        socialAccountRepository.saveAndFlush(socialAccount);
        int databaseSizeBeforeUpdate = socialAccountRepository.findAll().size();

        // Update the socialAccount
        SocialAccount updatedSocialAccount = socialAccountRepository.findOne(socialAccount.getId());
        updatedSocialAccount
            .nick(UPDATED_NICK)
            .platform(UPDATED_PLATFORM)
            .token(UPDATED_TOKEN);

        restSocialAccountMockMvc.perform(put("/api/social-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSocialAccount)))
            .andExpect(status().isOk());

        // Validate the SocialAccount in the database
        List<SocialAccount> socialAccountList = socialAccountRepository.findAll();
        assertThat(socialAccountList).hasSize(databaseSizeBeforeUpdate);
        SocialAccount testSocialAccount = socialAccountList.get(socialAccountList.size() - 1);
        assertThat(testSocialAccount.getNick()).isEqualTo(UPDATED_NICK);
        assertThat(testSocialAccount.getPlatform()).isEqualTo(UPDATED_PLATFORM);
        assertThat(testSocialAccount.getToken()).isEqualTo(UPDATED_TOKEN);
    }

    @Test
    @Transactional
    public void updateNonExistingSocialAccount() throws Exception {
        int databaseSizeBeforeUpdate = socialAccountRepository.findAll().size();

        // Create the SocialAccount

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSocialAccountMockMvc.perform(put("/api/social-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(socialAccount)))
            .andExpect(status().isCreated());

        // Validate the SocialAccount in the database
        List<SocialAccount> socialAccountList = socialAccountRepository.findAll();
        assertThat(socialAccountList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSocialAccount() throws Exception {
        // Initialize the database
        socialAccountRepository.saveAndFlush(socialAccount);
        int databaseSizeBeforeDelete = socialAccountRepository.findAll().size();

        // Get the socialAccount
        restSocialAccountMockMvc.perform(delete("/api/social-accounts/{id}", socialAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SocialAccount> socialAccountList = socialAccountRepository.findAll();
        assertThat(socialAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SocialAccount.class);
        SocialAccount socialAccount1 = new SocialAccount();
        socialAccount1.setId(1L);
        SocialAccount socialAccount2 = new SocialAccount();
        socialAccount2.setId(socialAccount1.getId());
        assertThat(socialAccount1).isEqualTo(socialAccount2);
        socialAccount2.setId(2L);
        assertThat(socialAccount1).isNotEqualTo(socialAccount2);
        socialAccount1.setId(null);
        assertThat(socialAccount1).isNotEqualTo(socialAccount2);
    }
}
