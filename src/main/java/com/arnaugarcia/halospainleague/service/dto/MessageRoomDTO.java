package com.arnaugarcia.halospainleague.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the MessageRoom entity.
 */
public class MessageRoomDTO implements Serializable {

    private Long id;

    private String title;

    @Lob
    private byte[] image;
    private String imageContentType;

    @Lob
    private byte[] cover;
    private String coverContentType;

    private ZonedDateTime crated;

    private Boolean isPublic;

    private Set<PlayerDTO> players = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    public String getCoverContentType() {
        return coverContentType;
    }

    public void setCoverContentType(String coverContentType) {
        this.coverContentType = coverContentType;
    }

    public ZonedDateTime getCrated() {
        return crated;
    }

    public void setCrated(ZonedDateTime crated) {
        this.crated = crated;
    }

    public Boolean isIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Set<PlayerDTO> getPlayers() {
        return players;
    }

    public void setPlayers(Set<PlayerDTO> players) {
        this.players = players;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MessageRoomDTO messageRoomDTO = (MessageRoomDTO) o;
        if(messageRoomDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), messageRoomDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MessageRoomDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", image='" + getImage() + "'" +
            ", cover='" + getCover() + "'" +
            ", crated='" + getCrated() + "'" +
            ", isPublic='" + isIsPublic() + "'" +
            "}";
    }
}
