package com.arnaugarcia.halospainleague.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ProfileConfiguration entity.
 */
public class ProfileConfigurationDTO implements Serializable {

    private Long id;

    private Boolean sendNews;

    private Boolean privateMessages;

    private Boolean teamInvites;

    private Boolean showDescription;

    private Boolean showScore;

    private Boolean showSocial;

    private Boolean showAge;

    private Boolean showGender;

    private Boolean active;

    private String timeZone;

    private ZonedDateTime lastLogin;

    private Boolean firstRun;

    private Boolean showTutorial;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isSendNews() {
        return sendNews;
    }

    public void setSendNews(Boolean sendNews) {
        this.sendNews = sendNews;
    }

    public Boolean isPrivateMessages() {
        return privateMessages;
    }

    public void setPrivateMessages(Boolean privateMessages) {
        this.privateMessages = privateMessages;
    }

    public Boolean isTeamInvites() {
        return teamInvites;
    }

    public void setTeamInvites(Boolean teamInvites) {
        this.teamInvites = teamInvites;
    }

    public Boolean isShowDescription() {
        return showDescription;
    }

    public void setShowDescription(Boolean showDescription) {
        this.showDescription = showDescription;
    }

    public Boolean isShowScore() {
        return showScore;
    }

    public void setShowScore(Boolean showScore) {
        this.showScore = showScore;
    }

    public Boolean isShowSocial() {
        return showSocial;
    }

    public void setShowSocial(Boolean showSocial) {
        this.showSocial = showSocial;
    }

    public Boolean isShowAge() {
        return showAge;
    }

    public void setShowAge(Boolean showAge) {
        this.showAge = showAge;
    }

    public Boolean isShowGender() {
        return showGender;
    }

    public void setShowGender(Boolean showGender) {
        this.showGender = showGender;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public ZonedDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(ZonedDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Boolean isFirstRun() {
        return firstRun;
    }

    public void setFirstRun(Boolean firstRun) {
        this.firstRun = firstRun;
    }

    public Boolean isShowTutorial() {
        return showTutorial;
    }

    public void setShowTutorial(Boolean showTutorial) {
        this.showTutorial = showTutorial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProfileConfigurationDTO profileConfigurationDTO = (ProfileConfigurationDTO) o;
        if(profileConfigurationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profileConfigurationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProfileConfigurationDTO{" +
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
