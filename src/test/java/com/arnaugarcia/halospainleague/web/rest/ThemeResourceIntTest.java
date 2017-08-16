package com.arnaugarcia.halospainleague.web.rest;

import com.arnaugarcia.halospainleague.HalospainleagueApp;

import com.arnaugarcia.halospainleague.domain.Theme;
import com.arnaugarcia.halospainleague.repository.ThemeRepository;
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
 * Test class for the ThemeResource REST controller.
 *
 * @see ThemeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HalospainleagueApp.class)
public class ThemeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_HEADERCOLOR = "AAAAAAAAAA";
    private static final String UPDATED_HEADERCOLOR = "BBBBBBBBBB";

    private static final String DEFAULT_FONTCOLOR = "AAAAAAAAAA";
    private static final String UPDATED_FONTCOLOR = "BBBBBBBBBB";

    private static final String DEFAULT_LINKCOLOR = "AAAAAAAAAA";
    private static final String UPDATED_LINKCOLOR = "BBBBBBBBBB";

    private static final String DEFAULT_BACKGROUNDCOLOR = "AAAAAAAAAA";
    private static final String UPDATED_BACKGROUNDCOLOR = "BBBBBBBBBB";

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restThemeMockMvc;

    private Theme theme;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ThemeResource themeResource = new ThemeResource(themeRepository);
        this.restThemeMockMvc = MockMvcBuilders.standaloneSetup(themeResource)
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
    public static Theme createEntity(EntityManager em) {
        Theme theme = new Theme()
            .name(DEFAULT_NAME)
            .active(DEFAULT_ACTIVE)
            .headercolor(DEFAULT_HEADERCOLOR)
            .fontcolor(DEFAULT_FONTCOLOR)
            .linkcolor(DEFAULT_LINKCOLOR)
            .backgroundcolor(DEFAULT_BACKGROUNDCOLOR);
        return theme;
    }

    @Before
    public void initTest() {
        theme = createEntity(em);
    }

    @Test
    @Transactional
    public void createTheme() throws Exception {
        int databaseSizeBeforeCreate = themeRepository.findAll().size();

        // Create the Theme
        restThemeMockMvc.perform(post("/api/themes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(theme)))
            .andExpect(status().isCreated());

        // Validate the Theme in the database
        List<Theme> themeList = themeRepository.findAll();
        assertThat(themeList).hasSize(databaseSizeBeforeCreate + 1);
        Theme testTheme = themeList.get(themeList.size() - 1);
        assertThat(testTheme.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTheme.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testTheme.getHeadercolor()).isEqualTo(DEFAULT_HEADERCOLOR);
        assertThat(testTheme.getFontcolor()).isEqualTo(DEFAULT_FONTCOLOR);
        assertThat(testTheme.getLinkcolor()).isEqualTo(DEFAULT_LINKCOLOR);
        assertThat(testTheme.getBackgroundcolor()).isEqualTo(DEFAULT_BACKGROUNDCOLOR);
    }

    @Test
    @Transactional
    public void createThemeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = themeRepository.findAll().size();

        // Create the Theme with an existing ID
        theme.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restThemeMockMvc.perform(post("/api/themes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(theme)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Theme> themeList = themeRepository.findAll();
        assertThat(themeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllThemes() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList
        restThemeMockMvc.perform(get("/api/themes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(theme.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].headercolor").value(hasItem(DEFAULT_HEADERCOLOR.toString())))
            .andExpect(jsonPath("$.[*].fontcolor").value(hasItem(DEFAULT_FONTCOLOR.toString())))
            .andExpect(jsonPath("$.[*].linkcolor").value(hasItem(DEFAULT_LINKCOLOR.toString())))
            .andExpect(jsonPath("$.[*].backgroundcolor").value(hasItem(DEFAULT_BACKGROUNDCOLOR.toString())));
    }

    @Test
    @Transactional
    public void getTheme() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get the theme
        restThemeMockMvc.perform(get("/api/themes/{id}", theme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(theme.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.headercolor").value(DEFAULT_HEADERCOLOR.toString()))
            .andExpect(jsonPath("$.fontcolor").value(DEFAULT_FONTCOLOR.toString()))
            .andExpect(jsonPath("$.linkcolor").value(DEFAULT_LINKCOLOR.toString()))
            .andExpect(jsonPath("$.backgroundcolor").value(DEFAULT_BACKGROUNDCOLOR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTheme() throws Exception {
        // Get the theme
        restThemeMockMvc.perform(get("/api/themes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTheme() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);
        int databaseSizeBeforeUpdate = themeRepository.findAll().size();

        // Update the theme
        Theme updatedTheme = themeRepository.findOne(theme.getId());
        updatedTheme
            .name(UPDATED_NAME)
            .active(UPDATED_ACTIVE)
            .headercolor(UPDATED_HEADERCOLOR)
            .fontcolor(UPDATED_FONTCOLOR)
            .linkcolor(UPDATED_LINKCOLOR)
            .backgroundcolor(UPDATED_BACKGROUNDCOLOR);

        restThemeMockMvc.perform(put("/api/themes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTheme)))
            .andExpect(status().isOk());

        // Validate the Theme in the database
        List<Theme> themeList = themeRepository.findAll();
        assertThat(themeList).hasSize(databaseSizeBeforeUpdate);
        Theme testTheme = themeList.get(themeList.size() - 1);
        assertThat(testTheme.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTheme.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testTheme.getHeadercolor()).isEqualTo(UPDATED_HEADERCOLOR);
        assertThat(testTheme.getFontcolor()).isEqualTo(UPDATED_FONTCOLOR);
        assertThat(testTheme.getLinkcolor()).isEqualTo(UPDATED_LINKCOLOR);
        assertThat(testTheme.getBackgroundcolor()).isEqualTo(UPDATED_BACKGROUNDCOLOR);
    }

    @Test
    @Transactional
    public void updateNonExistingTheme() throws Exception {
        int databaseSizeBeforeUpdate = themeRepository.findAll().size();

        // Create the Theme

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restThemeMockMvc.perform(put("/api/themes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(theme)))
            .andExpect(status().isCreated());

        // Validate the Theme in the database
        List<Theme> themeList = themeRepository.findAll();
        assertThat(themeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTheme() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);
        int databaseSizeBeforeDelete = themeRepository.findAll().size();

        // Get the theme
        restThemeMockMvc.perform(delete("/api/themes/{id}", theme.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Theme> themeList = themeRepository.findAll();
        assertThat(themeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Theme.class);
        Theme theme1 = new Theme();
        theme1.setId(1L);
        Theme theme2 = new Theme();
        theme2.setId(theme1.getId());
        assertThat(theme1).isEqualTo(theme2);
        theme2.setId(2L);
        assertThat(theme1).isNotEqualTo(theme2);
        theme1.setId(null);
        assertThat(theme1).isNotEqualTo(theme2);
    }
}
