package com.arnaugarcia.halospainleague.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;
import com.arnaugarcia.halospainleague.domain.enumeration.TournamentType;

/**
 * A DTO for the Team entity.
 */
public class TeamDTO implements Serializable {

    private Long id;

    private String name;

    @Lob
    private byte[] cover;
    private String coverContentType;

    @Lob
    private byte[] profile;
    private String profileContentType;

    private String web;

    private Integer experience;

    private Integer ping;

    private TournamentType tournamentType;

    private ZonedDateTime created;

    private Integer wins;

    private Integer losses;

    private Boolean streak;

    private Integer position;

    private Boolean premium;

    private Set<PlayerDTO> players = new HashSet<>();

    private Set<TeamListDTO> teamLists = new HashSet<>();

    private Long divisionId;

    private String divisionName;

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

    public byte[] getProfile() {
        return profile;
    }

    public void setProfile(byte[] profile) {
        this.profile = profile;
    }

    public String getProfileContentType() {
        return profileContentType;
    }

    public void setProfileContentType(String profileContentType) {
        this.profileContentType = profileContentType;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getPing() {
        return ping;
    }

    public void setPing(Integer ping) {
        this.ping = ping;
    }

    public TournamentType getTournamentType() {
        return tournamentType;
    }

    public void setTournamentType(TournamentType tournamentType) {
        this.tournamentType = tournamentType;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getLosses() {
        return losses;
    }

    public void setLosses(Integer losses) {
        this.losses = losses;
    }

    public Boolean isStreak() {
        return streak;
    }

    public void setStreak(Boolean streak) {
        this.streak = streak;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Boolean isPremium() {
        return premium;
    }

    public void setPremium(Boolean premium) {
        this.premium = premium;
    }

    public Set<PlayerDTO> getPlayers() {
        return players;
    }

    public void setPlayers(Set<PlayerDTO> players) {
        this.players = players;
    }

    public Set<TeamListDTO> getTeamLists() {
        return teamLists;
    }

    public void setTeamLists(Set<TeamListDTO> teamLists) {
        this.teamLists = teamLists;
    }

    public Long getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(Long divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TeamDTO teamDTO = (TeamDTO) o;
        if(teamDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), teamDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TeamDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", cover='" + getCover() + "'" +
            ", profile='" + getProfile() + "'" +
            ", web='" + getWeb() + "'" +
            ", experience='" + getExperience() + "'" +
            ", ping='" + getPing() + "'" +
            ", tournamentType='" + getTournamentType() + "'" +
            ", created='" + getCreated() + "'" +
            ", wins='" + getWins() + "'" +
            ", losses='" + getLosses() + "'" +
            ", streak='" + isStreak() + "'" +
            ", position='" + getPosition() + "'" +
            ", premium='" + isPremium() + "'" +
            "}";
    }
}
