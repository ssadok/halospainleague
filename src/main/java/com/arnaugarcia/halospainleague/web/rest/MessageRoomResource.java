package com.arnaugarcia.halospainleague.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.halospainleague.domain.MessageRoom;

import com.arnaugarcia.halospainleague.repository.MessageRoomRepository;
import com.arnaugarcia.halospainleague.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

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
    public MessageRoomResource(MessageRoomRepository messageRoomRepository) {
        this.messageRoomRepository = messageRoomRepository;
    }

    /**
     * POST  /message-rooms : Create a new messageRoom.
     *
     * @param messageRoom the messageRoom to create
     * @return the ResponseEntity with status 201 (Created) and with body the new messageRoom, or with status 400 (Bad Request) if the messageRoom has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/message-rooms")
    @Timed
    public ResponseEntity<MessageRoom> createMessageRoom(@Valid @RequestBody MessageRoom messageRoom) throws URISyntaxException {
        log.debug("REST request to save MessageRoom : {}", messageRoom);
        if (messageRoom.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new messageRoom cannot already have an ID")).body(null);
        }
        MessageRoom result = messageRoomRepository.save(messageRoom);
        return ResponseEntity.created(new URI("/api/message-rooms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /message-rooms : Updates an existing messageRoom.
     *
     * @param messageRoom the messageRoom to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated messageRoom,
     * or with status 400 (Bad Request) if the messageRoom is not valid,
     * or with status 500 (Internal Server Error) if the messageRoom couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/message-rooms")
    @Timed
    public ResponseEntity<MessageRoom> updateMessageRoom(@Valid @RequestBody MessageRoom messageRoom) throws URISyntaxException {
        log.debug("REST request to update MessageRoom : {}", messageRoom);
        if (messageRoom.getId() == null) {
            return createMessageRoom(messageRoom);
        }
        MessageRoom result = messageRoomRepository.save(messageRoom);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, messageRoom.getId().toString()))
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
    public List<MessageRoom> getAllMessageRooms(@RequestParam(required = false) String filter) {
        if ("message-is-null".equals(filter)) {
            log.debug("REST request to get all MessageRooms where message is null");
            return StreamSupport
                .stream(messageRoomRepository.findAll().spliterator(), false)
                .filter(messageRoom -> messageRoom.getMessage() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all MessageRooms");
        return messageRoomRepository.findAllWithEagerRelationships();
        }

    /**
     * GET  /message-rooms/:id : get the "id" messageRoom.
     *
     * @param id the id of the messageRoom to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the messageRoom, or with status 404 (Not Found)
     */
    @GetMapping("/message-rooms/{id}")
    @Timed
    public ResponseEntity<MessageRoom> getMessageRoom(@PathVariable Long id) {
        log.debug("REST request to get MessageRoom : {}", id);
        MessageRoom messageRoom = messageRoomRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(messageRoom));
    }

    /**
     * DELETE  /message-rooms/:id : delete the "id" messageRoom.
     *
     * @param id the id of the messageRoom to delete
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
