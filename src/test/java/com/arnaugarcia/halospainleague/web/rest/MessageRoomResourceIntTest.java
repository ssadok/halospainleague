package com.arnaugarcia.halospainleague.web.rest;

import com.arnaugarcia.halospainleague.HalospainleagueApp;

import com.arnaugarcia.halospainleague.domain.MessageRoom;
import com.arnaugarcia.halospainleague.domain.Theme;
import com.arnaugarcia.halospainleague.domain.Message;
import com.arnaugarcia.halospainleague.repository.MessageRoomRepository;
import com.arnaugarcia.halospainleague.service.dto.MessageRoomDTO;
import com.arnaugarcia.halospainleague.service.mapper.MessageRoomMapper;
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

/**
 * Test class for the MessageRoomResource REST controller.
 *
 * @see MessageRoomResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HalospainleagueApp.class)
public class MessageRoomResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_COVER = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_COVER = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_COVER_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_COVER_CONTENT_TYPE = "image/png";

    private static final ZonedDateTime DEFAULT_CRATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CRATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_IS_PUBLIC = false;
    private static final Boolean UPDATED_IS_PUBLIC = true;

    @Autowired
    private MessageRoomRepository messageRoomRepository;

    @Autowired
    private MessageRoomMapper messageRoomMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMessageRoomMockMvc;

    private MessageRoom messageRoom;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MessageRoomResource messageRoomResource = new MessageRoomResource(messageRoomRepository, messageRoomMapper);
        this.restMessageRoomMockMvc = MockMvcBuilders.standaloneSetup(messageRoomResource)
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
    public static MessageRoom createEntity(EntityManager em) {
        MessageRoom messageRoom = new MessageRoom()
            .title(DEFAULT_TITLE)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .cover(DEFAULT_COVER)
            .coverContentType(DEFAULT_COVER_CONTENT_TYPE)
            .crated(DEFAULT_CRATED)
            .isPublic(DEFAULT_IS_PUBLIC);
        // Add required entity
        Theme theme = ThemeResourceIntTest.createEntity(em);
        em.persist(theme);
        em.flush();
        messageRoom.getThemes().add(theme);
        // Add required entity
        Message message = MessageResourceIntTest.createEntity(em);
        em.persist(message);
        em.flush();
        messageRoom.setMessage(message);
        return messageRoom;
    }

    @Before
    public void initTest() {
        messageRoom = createEntity(em);
    }

    @Test
    @Transactional
    public void createMessageRoom() throws Exception {
        int databaseSizeBeforeCreate = messageRoomRepository.findAll().size();

        // Create the MessageRoom
        MessageRoomDTO messageRoomDTO = messageRoomMapper.toDto(messageRoom);
        restMessageRoomMockMvc.perform(post("/api/message-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageRoomDTO)))
            .andExpect(status().isCreated());

        // Validate the MessageRoom in the database
        List<MessageRoom> messageRoomList = messageRoomRepository.findAll();
        assertThat(messageRoomList).hasSize(databaseSizeBeforeCreate + 1);
        MessageRoom testMessageRoom = messageRoomList.get(messageRoomList.size() - 1);
        assertThat(testMessageRoom.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testMessageRoom.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testMessageRoom.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testMessageRoom.getCover()).isEqualTo(DEFAULT_COVER);
        assertThat(testMessageRoom.getCoverContentType()).isEqualTo(DEFAULT_COVER_CONTENT_TYPE);
        assertThat(testMessageRoom.getCrated()).isEqualTo(DEFAULT_CRATED);
        assertThat(testMessageRoom.isIsPublic()).isEqualTo(DEFAULT_IS_PUBLIC);
    }

    @Test
    @Transactional
    public void createMessageRoomWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = messageRoomRepository.findAll().size();

        // Create the MessageRoom with an existing ID
        messageRoom.setId(1L);
        MessageRoomDTO messageRoomDTO = messageRoomMapper.toDto(messageRoom);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMessageRoomMockMvc.perform(post("/api/message-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageRoomDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MessageRoom> messageRoomList = messageRoomRepository.findAll();
        assertThat(messageRoomList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMessageRooms() throws Exception {
        // Initialize the database
        messageRoomRepository.saveAndFlush(messageRoom);

        // Get all the messageRoomList
        restMessageRoomMockMvc.perform(get("/api/message-rooms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(messageRoom.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].coverContentType").value(hasItem(DEFAULT_COVER_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].cover").value(hasItem(Base64Utils.encodeToString(DEFAULT_COVER))))
            .andExpect(jsonPath("$.[*].crated").value(hasItem(sameInstant(DEFAULT_CRATED))))
            .andExpect(jsonPath("$.[*].isPublic").value(hasItem(DEFAULT_IS_PUBLIC.booleanValue())));
    }

    @Test
    @Transactional
    public void getMessageRoom() throws Exception {
        // Initialize the database
        messageRoomRepository.saveAndFlush(messageRoom);

        // Get the messageRoom
        restMessageRoomMockMvc.perform(get("/api/message-rooms/{id}", messageRoom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(messageRoom.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.coverContentType").value(DEFAULT_COVER_CONTENT_TYPE))
            .andExpect(jsonPath("$.cover").value(Base64Utils.encodeToString(DEFAULT_COVER)))
            .andExpect(jsonPath("$.crated").value(sameInstant(DEFAULT_CRATED)))
            .andExpect(jsonPath("$.isPublic").value(DEFAULT_IS_PUBLIC.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMessageRoom() throws Exception {
        // Get the messageRoom
        restMessageRoomMockMvc.perform(get("/api/message-rooms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMessageRoom() throws Exception {
        // Initialize the database
        messageRoomRepository.saveAndFlush(messageRoom);
        int databaseSizeBeforeUpdate = messageRoomRepository.findAll().size();

        // Update the messageRoom
        MessageRoom updatedMessageRoom = messageRoomRepository.findOne(messageRoom.getId());
        updatedMessageRoom
            .title(UPDATED_TITLE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .cover(UPDATED_COVER)
            .coverContentType(UPDATED_COVER_CONTENT_TYPE)
            .crated(UPDATED_CRATED)
            .isPublic(UPDATED_IS_PUBLIC);
        MessageRoomDTO messageRoomDTO = messageRoomMapper.toDto(updatedMessageRoom);

        restMessageRoomMockMvc.perform(put("/api/message-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageRoomDTO)))
            .andExpect(status().isOk());

        // Validate the MessageRoom in the database
        List<MessageRoom> messageRoomList = messageRoomRepository.findAll();
        assertThat(messageRoomList).hasSize(databaseSizeBeforeUpdate);
        MessageRoom testMessageRoom = messageRoomList.get(messageRoomList.size() - 1);
        assertThat(testMessageRoom.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testMessageRoom.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testMessageRoom.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testMessageRoom.getCover()).isEqualTo(UPDATED_COVER);
        assertThat(testMessageRoom.getCoverContentType()).isEqualTo(UPDATED_COVER_CONTENT_TYPE);
        assertThat(testMessageRoom.getCrated()).isEqualTo(UPDATED_CRATED);
        assertThat(testMessageRoom.isIsPublic()).isEqualTo(UPDATED_IS_PUBLIC);
    }

    @Test
    @Transactional
    public void updateNonExistingMessageRoom() throws Exception {
        int databaseSizeBeforeUpdate = messageRoomRepository.findAll().size();

        // Create the MessageRoom
        MessageRoomDTO messageRoomDTO = messageRoomMapper.toDto(messageRoom);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMessageRoomMockMvc.perform(put("/api/message-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageRoomDTO)))
            .andExpect(status().isCreated());

        // Validate the MessageRoom in the database
        List<MessageRoom> messageRoomList = messageRoomRepository.findAll();
        assertThat(messageRoomList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMessageRoom() throws Exception {
        // Initialize the database
        messageRoomRepository.saveAndFlush(messageRoom);
        int databaseSizeBeforeDelete = messageRoomRepository.findAll().size();

        // Get the messageRoom
        restMessageRoomMockMvc.perform(delete("/api/message-rooms/{id}", messageRoom.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MessageRoom> messageRoomList = messageRoomRepository.findAll();
        assertThat(messageRoomList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MessageRoom.class);
        MessageRoom messageRoom1 = new MessageRoom();
        messageRoom1.setId(1L);
        MessageRoom messageRoom2 = new MessageRoom();
        messageRoom2.setId(messageRoom1.getId());
        assertThat(messageRoom1).isEqualTo(messageRoom2);
        messageRoom2.setId(2L);
        assertThat(messageRoom1).isNotEqualTo(messageRoom2);
        messageRoom1.setId(null);
        assertThat(messageRoom1).isNotEqualTo(messageRoom2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MessageRoomDTO.class);
        MessageRoomDTO messageRoomDTO1 = new MessageRoomDTO();
        messageRoomDTO1.setId(1L);
        MessageRoomDTO messageRoomDTO2 = new MessageRoomDTO();
        assertThat(messageRoomDTO1).isNotEqualTo(messageRoomDTO2);
        messageRoomDTO2.setId(messageRoomDTO1.getId());
        assertThat(messageRoomDTO1).isEqualTo(messageRoomDTO2);
        messageRoomDTO2.setId(2L);
        assertThat(messageRoomDTO1).isNotEqualTo(messageRoomDTO2);
        messageRoomDTO1.setId(null);
        assertThat(messageRoomDTO1).isNotEqualTo(messageRoomDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(messageRoomMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(messageRoomMapper.fromId(null)).isNull();
    }
}
