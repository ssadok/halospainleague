package com.arnaugarcia.halospainleague.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Message entity.
 */
public class MessageDTO implements Serializable {

    private Long id;

    @Lob
    private String content;

    private ZonedDateTime created;

    private ZonedDateTime sent;

    private ZonedDateTime recived;

    private Long messageRoomId;

    private String messageRoomTitle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public ZonedDateTime getSent() {
        return sent;
    }

    public void setSent(ZonedDateTime sent) {
        this.sent = sent;
    }

    public ZonedDateTime getRecived() {
        return recived;
    }

    public void setRecived(ZonedDateTime recived) {
        this.recived = recived;
    }

    public Long getMessageRoomId() {
        return messageRoomId;
    }

    public void setMessageRoomId(Long messageRoomId) {
        this.messageRoomId = messageRoomId;
    }

    public String getMessageRoomTitle() {
        return messageRoomTitle;
    }

    public void setMessageRoomTitle(String messageRoomTitle) {
        this.messageRoomTitle = messageRoomTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MessageDTO messageDTO = (MessageDTO) o;
        if(messageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), messageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", created='" + getCreated() + "'" +
            ", sent='" + getSent() + "'" +
            ", recived='" + getRecived() + "'" +
            "}";
    }
}
