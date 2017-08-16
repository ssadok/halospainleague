package com.arnaugarcia.halospainleague.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Class MessageRoom.
 * @author arnaugarcia.
 */
@ApiModel(description = "Class MessageRoom. @author arnaugarcia.")
@Entity
@Table(name = "message_room")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MessageRoom implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Lob
    @Column(name = "cover")
    private byte[] cover;

    @Column(name = "cover_content_type")
    private String coverContentType;

    @Column(name = "crated")
    private ZonedDateTime crated;

    @Column(name = "is_public")
    private Boolean isPublic;

    @OneToMany(mappedBy = "messageRoom")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Theme> themes = new HashSet<>();

    /**
     * Messages
     */
    @ApiModelProperty(value = "Messages")
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "message_room_player",
               joinColumns = @JoinColumn(name="message_rooms_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="players_id", referencedColumnName="id"))
    private Set<Player> players = new HashSet<>();

    @OneToOne(mappedBy = "messageRoom")
    @JsonIgnore
    private Message message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public MessageRoom title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getImage() {
        return image;
    }

    public MessageRoom image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public MessageRoom imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public byte[] getCover() {
        return cover;
    }

    public MessageRoom cover(byte[] cover) {
        this.cover = cover;
        return this;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    public String getCoverContentType() {
        return coverContentType;
    }

    public MessageRoom coverContentType(String coverContentType) {
        this.coverContentType = coverContentType;
        return this;
    }

    public void setCoverContentType(String coverContentType) {
        this.coverContentType = coverContentType;
    }

    public ZonedDateTime getCrated() {
        return crated;
    }

    public MessageRoom crated(ZonedDateTime crated) {
        this.crated = crated;
        return this;
    }

    public void setCrated(ZonedDateTime crated) {
        this.crated = crated;
    }

    public Boolean isIsPublic() {
        return isPublic;
    }

    public MessageRoom isPublic(Boolean isPublic) {
        this.isPublic = isPublic;
        return this;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Set<Theme> getThemes() {
        return themes;
    }

    public MessageRoom themes(Set<Theme> themes) {
        this.themes = themes;
        return this;
    }

    public MessageRoom addTheme(Theme theme) {
        this.themes.add(theme);
        theme.setMessageRoom(this);
        return this;
    }

    public MessageRoom removeTheme(Theme theme) {
        this.themes.remove(theme);
        theme.setMessageRoom(null);
        return this;
    }

    public void setThemes(Set<Theme> themes) {
        this.themes = themes;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public MessageRoom players(Set<Player> players) {
        this.players = players;
        return this;
    }

    public MessageRoom addPlayer(Player player) {
        this.players.add(player);
        player.getMessageRooms().add(this);
        return this;
    }

    public MessageRoom removePlayer(Player player) {
        this.players.remove(player);
        player.getMessageRooms().remove(this);
        return this;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public Message getMessage() {
        return message;
    }

    public MessageRoom message(Message message) {
        this.message = message;
        return this;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MessageRoom messageRoom = (MessageRoom) o;
        if (messageRoom.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), messageRoom.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MessageRoom{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + imageContentType + "'" +
            ", cover='" + getCover() + "'" +
            ", coverContentType='" + coverContentType + "'" +
            ", crated='" + getCrated() + "'" +
            ", isPublic='" + isIsPublic() + "'" +
            "}";
    }
}
