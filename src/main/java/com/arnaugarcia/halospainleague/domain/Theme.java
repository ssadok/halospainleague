package com.arnaugarcia.halospainleague.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Class Theme.
 * @author arnaugarcia.
 */
@ApiModel(description = "Class Theme. @author arnaugarcia.")
@Entity
@Table(name = "theme")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Theme implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "headercolor")
    private String headercolor;

    @Column(name = "fontcolor")
    private String fontcolor;

    @Column(name = "linkcolor")
    private String linkcolor;

    @Column(name = "backgroundcolor")
    private String backgroundcolor;

    @ManyToOne
    private ProfileConfiguration profileConfiguration;

    @ManyToOne
    private MessageRoom messageRoom;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Theme name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isActive() {
        return active;
    }

    public Theme active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getHeadercolor() {
        return headercolor;
    }

    public Theme headercolor(String headercolor) {
        this.headercolor = headercolor;
        return this;
    }

    public void setHeadercolor(String headercolor) {
        this.headercolor = headercolor;
    }

    public String getFontcolor() {
        return fontcolor;
    }

    public Theme fontcolor(String fontcolor) {
        this.fontcolor = fontcolor;
        return this;
    }

    public void setFontcolor(String fontcolor) {
        this.fontcolor = fontcolor;
    }

    public String getLinkcolor() {
        return linkcolor;
    }

    public Theme linkcolor(String linkcolor) {
        this.linkcolor = linkcolor;
        return this;
    }

    public void setLinkcolor(String linkcolor) {
        this.linkcolor = linkcolor;
    }

    public String getBackgroundcolor() {
        return backgroundcolor;
    }

    public Theme backgroundcolor(String backgroundcolor) {
        this.backgroundcolor = backgroundcolor;
        return this;
    }

    public void setBackgroundcolor(String backgroundcolor) {
        this.backgroundcolor = backgroundcolor;
    }

    public ProfileConfiguration getProfileConfiguration() {
        return profileConfiguration;
    }

    public Theme profileConfiguration(ProfileConfiguration profileConfiguration) {
        this.profileConfiguration = profileConfiguration;
        return this;
    }

    public void setProfileConfiguration(ProfileConfiguration profileConfiguration) {
        this.profileConfiguration = profileConfiguration;
    }

    public MessageRoom getMessageRoom() {
        return messageRoom;
    }

    public Theme messageRoom(MessageRoom messageRoom) {
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
        Theme theme = (Theme) o;
        if (theme.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), theme.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Theme{" +
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
