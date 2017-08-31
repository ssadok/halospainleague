package com.arnaugarcia.halospainleague.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Theme entity.
 */
public class ThemeDTO implements Serializable {

    private Long id;

    private String name;

    private Boolean active;

    private String headercolor;

    private String fontcolor;

    private String linkcolor;

    private String backgroundcolor;

    private Long profileConfigurationId;

    private Long messageRoomId;

    private String messageRoomTitle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getHeadercolor() {
        return headercolor;
    }

    public void setHeadercolor(String headercolor) {
        this.headercolor = headercolor;
    }

    public String getFontcolor() {
        return fontcolor;
    }

    public void setFontcolor(String fontcolor) {
        this.fontcolor = fontcolor;
    }

    public String getLinkcolor() {
        return linkcolor;
    }

    public void setLinkcolor(String linkcolor) {
        this.linkcolor = linkcolor;
    }

    public String getBackgroundcolor() {
        return backgroundcolor;
    }

    public void setBackgroundcolor(String backgroundcolor) {
        this.backgroundcolor = backgroundcolor;
    }

    public Long getProfileConfigurationId() {
        return profileConfigurationId;
    }

    public void setProfileConfigurationId(Long profileConfigurationId) {
        this.profileConfigurationId = profileConfigurationId;
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

        ThemeDTO themeDTO = (ThemeDTO) o;
        if(themeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), themeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ThemeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", active='" + isActive() + "'" +
            ", headercolor='" + getHeadercolor() + "'" +
            ", fontcolor='" + getFontcolor() + "'" +
            ", linkcolor='" + getLinkcolor() + "'" +
            ", backgroundcolor='" + getBackgroundcolor() + "'" +
            "}";
    }
}
