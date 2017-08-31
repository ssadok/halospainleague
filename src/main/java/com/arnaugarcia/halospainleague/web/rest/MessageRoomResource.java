package com.arnaugarcia.halospainleague.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.halospainleague.domain.MessageRoom;

import com.arnaugarcia.halospainleague.repository.MessageRoomRepository;
import com.arnaugarcia.halospainleague.web.rest.util.HeaderUtil;
import com.arnaugarcia.halospainleague.service.dto.MessageRoomDTO;
import com.arnaugarcia.halospainleague.service.mapper.MessageRoomMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing MessageRoom.
 */
@RestController
@RequestMapping("/api")
public class MessageRoomResource {

    private final Logger log = LoggerFactory.getLogger(MessageRoomResource.class);

    private static final String ENTITY_NAME = "messageRoom";

    private final MessageRoomRepository messageRoomRepository;

    private final MessageRoomMapper messageRoomMapper;
    public MessageRoomResource(MessageRoomRepository messageRoomRepository, MessageRoomMapper messageRoomMapper) {
        this.messageRoomRepository = messageRoomRepository;
        this.messageRoomMapper = messageRoomMapper;
    }

    /**
     * POST  /message-rooms : Create a new messageRoom.
     *
     * @param messageRoomDTO the messageRoomDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new messageRoomDTO, or with status 400 (Bad Request) if the messageRoom has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/message-rooms")
    @Timed
    public ResponseEntity<MessageRoomDTO> createMessageRoom(@Valid @RequestBody MessageRoomDTO messageRoomDTO) throws URISyntaxException {
        log.debug("REST request to save MessageRoom : {}", messageRoomDTO);
        if (messageRoomDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new messageRoom cannot already have an ID")).body(null);
        }
        MessageRoom messageRoom = messageRoomMapper.toEntity(messageRoomDTO);
        messageRoom = messageRoomRepository.save(messageRoom);
        MessageRoomDTO result = messageRoomMapper.toDto(messageRoom);
        return ResponseEntity.created(new URI("/api/message-rooms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /message-rooms : Updates an existing messageRoom.
     *
     * @param messageRoomDTO the messageRoomDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated messageRoomDTO,
     * or with status 400 (Bad Request) if the messageRoomDTO is not valid,
     * or with status 500 (Internal Server Error) if the messageRoomDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/message-rooms")
    @Timed
    public ResponseEntity<MessageRoomDTO> updateMessageRoom(@Valid @RequestBody MessageRoomDTO messageRoomDTO) throws URISyntaxException {
        log.debug("REST request to update MessageRoom : {}", messageRoomDTO);
        if (messageRoomDTO.getId() == null) {
            return createMessageRoom(messageRoomDTO);
        }
        MessageRoom messageRoom = messageRoomMapper.toEntity(messageRoomDTO);
        messageRoom = messageRoomRepository.save(messageRoom);
        MessageRoomDTO result = messageRoomMapper.toDto(messageRoom);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, messageRoomDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /message-rooms : get all the messageRooms.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of messageRooms in body
     */
    @GetMapping("/message-rooms")
    @Timed
    public List<MessageRoomDTO> getAllMessageRooms(@RequestParam(required = false) String filter) {
        if ("message-is-null".equals(filter)) {
            log.debug("REST request to get all MessageRooms where message is null");
            return StreamSupport
                .stream(messageRoomRepository.findAll().spliterator(), false)
                .filter(messageRoom -> messageRoom.getMessage() == null)
                .map(messageRoomMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
        }
        log.debug("REST request to get all MessageRooms");
        List<MessageRoom> messageRooms = messageRoomRepository.findAllWithEagerRelationships();
        return messageRoomMapper.toDto(messageRooms);
        }

    /**
     * GET  /message-rooms/:id : get the "id" messageRoom.
     *
     * @param id the id of the messageRoomDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the messageRoomDTO, or with status 404 (Not Found)
     */
    @GetMapping("/message-rooms/{id}")
    @Timed
    public ResponseEntity<MessageRoomDTO> getMessageRoom(@PathVariable Long id) {
        log.debug("REST request to get MessageRoom : {}", id);
        MessageRoom messageRoom = messageRoomRepository.findOneWithEagerRelationships(id);
        MessageRoomDTO messageRoomDTO = messageRoomMapper.toDto(messageRoom);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(messageRoomDTO));
    }

    /**
     * DELETE  /message-rooms/:id : delete the "id" messageRoom.
     *
     * @param id the id of the messageRoomDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/message-rooms/{id}")
    @Timed
    public ResponseEntity<Void> deleteMessageRoom(@PathVariable Long id) {
        log.debug("REST request to delete MessageRoom : {}", id);
        messageRoomRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
