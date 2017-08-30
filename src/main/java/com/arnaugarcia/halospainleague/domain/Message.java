package com.arnaugarcia.halospainleague.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Class Message.
 * @author arnaugarcia.
 */
@ApiModel(description = "Class Message. @author arnaugarcia.")
@Entity
@Table(name = "message")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "created")
    private ZonedDateTime created;

    @Column(name = "sent")
    private ZonedDateTime sent;

    @Column(name = "recived")
    private ZonedDateTime recived;

    /**
     * MESSAGES
     */
    @ApiModelProperty(value = "MESSAGES")
    @OneToOne
    @JoinColumn(unique = true)
    private MessageRoom messageRoom;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public Message content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public Message created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public ZonedDateTime getSent() {
        return sent;
    }

    public Message sent(ZonedDateTime sent) {
        this.sent = sent;
        return this;
    }

    public void setSent(ZonedDateTime sent) {
        this.sent = sent;
    }

    public ZonedDateTime getRecived() {
        return recived;
    }

    public Message recived(ZonedDateTime recived) {
        this.recived = recived;
        return this;
    }

    public void setRecived(ZonedDateTime recived) {
        this.recived = recived;
    }

    public MessageRoom getMessageRoom() {
        return messageRoom;
    }

    public Message messageRoom(MessageRoom messageRoom) {
        this.messageRoom = messageRoom;
        return this;
    }

    public void setMessageRoom(MessageRoom messageRoom) {
        this.messageRoom = messageRoom;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Message message = (Message) o;
        if (message.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), message.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Message{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", created='" + getCreated() + "'" +
            ", sent='" + getSent() + "'" +
            ", recived='" + getRecived() + "'" +
            "}";
    }
}
