package com.arnaugarcia.halospainleague.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.arnaugarcia.halospainleague.domain.enumeration.TournamentType;

/**
 * Class Team.
 * @author arnaugarcia.
 */
@ApiModel(description = "Class Team. @author arnaugarcia.")
@Entity
@Table(name = "team")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "cover")
    private byte[] cover;

    @Column(name = "cover_content_type")
    private String coverContentType;

    @Lob
    @Column(name = "jhi_profile")
    private byte[] profile;

    @Column(name = "jhi_profile_content_type")
    private String profileContentType;

    @Column(name = "web")
    private String web;

    @Column(name = "experience")
    private Integer experience;

    @Column(name = "ping")
    private Integer ping;

    @Enumerated(EnumType.STRING)
    @Column(name = "tournament_type")
    private TournamentType tournamentType;

    @Column(name = "created")
    private ZonedDateTime created;

    @Column(name = "wins")
    private Integer wins;

    @Column(name = "losses")
    private Integer losses;

    @Column(name = "streak")
    private Boolean streak;

    @Column(name = "position")
    private Integer position;

    @Column(name = "premium")
    private Boolean premium;

    /**
     * Matches, Tornaments and Games
     */
    @ApiModelProperty(value = "Matches, Tornaments and Games")
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "team_player",
               joinColumns = @JoinColumn(name="teams_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="players_id", referencedColumnName="id"))
    private Set<Player> players = new HashSet<>();

    @ManyToMany(mappedBy = "teams")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tournament> torunaments = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Team name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getCover() {
        return cover;
    }

    public Team cover(byte[] cover) {
        this.cover = cover;
        return this;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    public String getCoverContentType() {
        return coverContentType;
    }

    public Team coverContentType(String coverContentType) {
        this.coverContentType = coverContentType;
        return this;
    }

    public void setCoverContentType(String coverContentType) {
        this.coverContentType = coverContentType;
    }

    public byte[] getProfile() {
        return profile;
    }

    public Team profile(byte[] profile) {
        this.profile = profile;
        return this;
    }

    public void setProfile(byte[] profile) {
        this.profile = profile;
    }

    public String getProfileContentType() {
        return profileContentType;
    }

    public Team profileContentType(String profileContentType) {
        this.profileContentType = profileContentType;
        return this;
    }

    public void setProfileContentType(String profileContentType) {
        this.profileContentType = profileContentType;
    }

    public String getWeb() {
        return web;
    }

    public Team web(String web) {
        this.web = web;
        return this;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public Integer getExperience() {
        return experience;
    }

    public Team experience(Integer experience) {
        this.experience = experience;
        return this;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getPing() {
        return ping;
    }

    public Team ping(Integer ping) {
        this.ping = ping;
        return this;
    }

    public void setPing(Integer ping) {
        this.ping = ping;
    }

    public TournamentType getTournamentType() {
        return tournamentType;
    }

    public Team tournamentType(TournamentType tournamentType) {
        this.tournamentType = tournamentType;
        return this;
    }

    public void setTournamentType(TournamentType tournamentType) {
        this.tournamentType = tournamentType;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public Team created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public Integer getWins() {
        return wins;
    }

    public Team wins(Integer wins) {
        this.wins = wins;
        return this;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getLosses() {
        return losses;
    }

    public Team losses(Integer losses) {
        this.losses = losses;
        return this;
    }

    public void setLosses(Integer losses) {
        this.losses = losses;
    }

    public Boolean isStreak() {
        return streak;
    }

    public Team streak(Boolean streak) {
        this.streak = streak;
        return this;
    }

    public void setStreak(Boolean streak) {
        this.streak = streak;
    }

    public Integer getPosition() {
        return position;
    }

    public Team position(Integer position) {
        this.position = position;
        return this;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Boolean isPremium() {
        return premium;
    }

    public Team premium(Boolean premium) {
        this.premium = premium;
        return this;
    }

    public void setPremium(Boolean premium) {
        this.premium = premium;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public Team players(Set<Player> players) {
        this.players = players;
        return this;
    }

    public Team addPlayer(Player player) {
        this.players.add(player);
        player.getTeams().add(this);
        return this;
    }

    public Team removePlayer(Player player) {
        this.players.remove(player);
        player.getTeams().remove(this);
        return this;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public Set<Tournament> getTorunaments() {
        return torunaments;
    }

    public Team torunaments(Set<Tournament> tournaments) {
        this.torunaments = tournaments;
        return this;
    }

    public Team addTorunament(Tournament tournament) {
        this.torunaments.add(tournament);
        tournament.getTeams().add(this);
        return this;
    }

    public Team removeTorunament(Tournament tournament) {
        this.torunaments.remove(tournament);
        tournament.getTeams().remove(this);
        return this;
    }

    public void setTorunaments(Set<Tournament> tournaments) {
        this.torunaments = tournaments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Team team = (Team) o;
        if (team.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), team.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Team{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", cover='" + getCover() + "'" +
            ", coverContentType='" + coverContentType + "'" +
            ", profile='" + getProfile() + "'" +
            ", profileContentType='" + profileContentType + "'" +
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
