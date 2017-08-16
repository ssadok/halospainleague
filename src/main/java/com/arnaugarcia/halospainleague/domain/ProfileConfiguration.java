package com.arnaugarcia.halospainleague.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
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
 * Class ProfileConfiguration.
 * @author arnaugarcia.
 */
@ApiModel(description = "Class ProfileConfiguration. @author arnaugarcia.")
@Entity
@Table(name = "profile_configuration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProfileConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "send_news")
    private Boolean sendNews;

    @Column(name = "private_messages")
    private Boolean privateMessages;

    @Column(name = "team_invites")
    private Boolean teamInvites;

    @Column(name = "show_description")
    private Boolean showDescription;

    @Column(name = "show_score")
    private Boolean showScore;

    @Column(name = "show_social")
    private Boolean showSocial;

    @Column(name = "show_age")
    private Boolean showAge;

    @Column(name = "show_gender")
    private Boolean showGender;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "time_zone")
    private String timeZone;

    @Column(name = "last_login")
    private ZonedDateTime lastLogin;

    @Column(name = "first_run")
    private Boolean firstRun;

    @Column(name = "show_tutorial")
    private Boolean showTutorial;

    @OneToMany(mappedBy = "profileConfiguration")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Theme> themes = new HashSet<>();

    @OneToOne(mappedBy = "profileConfiguration")
    @JsonIgnore
    private Player player;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isSendNews() {
        return sendNews;
    }

    public ProfileConfiguration sendNews(Boolean sendNews) {
        this.sendNews = sendNews;
        return this;
    }

    public void setSendNews(Boolean sendNews) {
        this.sendNews = sendNews;
    }

    public Boolean isPrivateMessages() {
        return privateMessages;
    }

    public ProfileConfiguration privateMessages(Boolean privateMessages) {
        this.privateMessages = privateMessages;
        return this;
    }

    public void setPrivateMessages(Boolean privateMessages) {
        this.privateMessages = privateMessages;
    }

    public Boolean isTeamInvites() {
        return teamInvites;
    }

    public ProfileConfiguration teamInvites(Boolean teamInvites) {
        this.teamInvites = teamInvites;
        return this;
    }

    public void setTeamInvites(Boolean teamInvites) {
        this.teamInvites = teamInvites;
    }

    public Boolean isShowDescription() {
        return showDescription;
    }

    public ProfileConfiguration showDescription(Boolean showDescription) {
        this.showDescription = showDescription;
        return this;
    }

    public void setShowDescription(Boolean showDescription) {
        this.showDescription = showDescription;
    }

    public Boolean isShowScore() {
        return showScore;
    }

    public ProfileConfiguration showScore(Boolean showScore) {
        this.showScore = showScore;
        return this;
    }

    public void setShowScore(Boolean showScore) {
        this.showScore = showScore;
    }

    public Boolean isShowSocial() {
        return showSocial;
    }

    public ProfileConfiguration showSocial(Boolean showSocial) {
        this.showSocial = showSocial;
        return this;
    }

    public void setShowSocial(Boolean showSocial) {
        this.showSocial = showSocial;
    }

    public Boolean isShowAge() {
        return showAge;
    }

    public ProfileConfiguration showAge(Boolean showAge) {
        this.showAge = showAge;
        return this;
    }

    public void setShowAge(Boolean showAge) {
        this.showAge = showAge;
    }

    public Boolean isShowGender() {
        return showGender;
    }

    public ProfileConfiguration showGender(Boolean showGender) {
        this.showGender = showGender;
        return this;
    }

    public void setShowGender(Boolean showGender) {
        this.showGender = showGender;
    }

    public Boolean isActive() {
        return active;
    }

    public ProfileConfiguration active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public ProfileConfiguration timeZone(String timeZone) {
        this.timeZone = timeZone;
        return this;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public ZonedDateTime getLastLogin() {
        return lastLogin;
    }

    public ProfileConfiguration lastLogin(ZonedDateTime lastLogin) {
        this.lastLogin = lastLogin;
        return this;
    }

    public void setLastLogin(ZonedDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Boolean isFirstRun() {
        return firstRun;
    }

    public ProfileConfiguration firstRun(Boolean firstRun) {
        this.firstRun = firstRun;
        return this;
    }

    public void setFirstRun(Boolean firstRun) {
        this.firstRun = firstRun;
    }

    public Boolean isShowTutorial() {
        return showTutorial;
    }

    public ProfileConfiguration showTutorial(Boolean showTutorial) {
        this.showTutorial = showTutorial;
        return this;
    }

    public void setShowTutorial(Boolean showTutorial) {
        this.showTutorial = showTutorial;
    }

    public Set<Theme> getThemes() {
        return themes;
    }

    public ProfileConfiguration themes(Set<Theme> themes) {
        this.themes = themes;
        return this;
    }

    public ProfileConfiguration addTheme(Theme theme) {
        this.themes.add(theme);
        theme.setProfileConfiguration(this);
        return this;
    }

    public ProfileConfiguration removeTheme(Theme theme) {
        this.themes.remove(theme);
        theme.setProfileConfiguration(null);
        return this;
    }

    public void setThemes(Set<Theme> themes) {
        this.themes = themes;
    }

    public Player getPlayer() {
        return player;
    }

    public ProfileConfiguration player(Player player) {
        this.player = player;
        return this;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProfileConfiguration profileConfiguration = (ProfileConfiguration) o;
        if (profileConfiguration.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profileConfiguration.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProfileConfiguration{" +
            "id=" + getId() +
            ", sendNews='" + isSendNews() + "'" +
            ", privateMessages='" + isPrivateMessages() + "'" +
            ", teamInvites='" + isTeamInvites() + "'" +
            ", showDescription='" + isShowDescription() + "'" +
            ", showScore='" + isShowScore() + "'" +
            ", showSocial='" + isShowSocial() + "'" +
            ", showAge='" + isShowAge() + "'" +
            ", showGender='" + isShowGender() + "'" +
            ", active='" + isActive() + "'" +
            ", timeZone='" + getTimeZone() + "'" +
            ", lastLogin='" + getLastLogin() + "'" +
            ", firstRun='" + isFirstRun() + "'" +
            ", showTutorial='" + isShowTutorial() + "'" +
            "}";
    }
}
