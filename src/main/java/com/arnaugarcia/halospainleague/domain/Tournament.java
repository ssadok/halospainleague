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

import com.arnaugarcia.halospainleague.domain.enumeration.Platform;

import com.arnaugarcia.halospainleague.domain.enumeration.TournamentType;

/**
 * Class Tournament.
 * @author arnaugarcia.
 */
@ApiModel(description = "Class Tournament. @author arnaugarcia.")
@Entity
@Table(name = "tournament")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tournament implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "cover_image")
    private byte[] coverImage;

    @Column(name = "cover_image_content_type")
    private String coverImageContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "platform")
    private Platform platform;

    @NotNull
    @Column(name = "max_teams", nullable = false)
    private Integer maxTeams;

    @Column(name = "registration_starts")
    private ZonedDateTime registrationStarts;

    @Column(name = "registration_ends")
    private ZonedDateTime registrationEnds;

    @Column(name = "tournament_begins")
    private ZonedDateTime tournamentBegins;

    @Column(name = "price")
    private Double price;

    @Column(name = "games_per_round")
    private Integer gamesPerRound;

    @Lob
    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private TournamentType type;

    @OneToMany(mappedBy = "tournament")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Match> matches = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "tournament_team",
               joinColumns = @JoinColumn(name="tournaments_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="teams_id", referencedColumnName="id"))
    private Set<Team> teams = new HashSet<>();

    @ManyToOne
    private Game game;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Tournament name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getCoverImage() {
        return coverImage;
    }

    public Tournament coverImage(byte[] coverImage) {
        this.coverImage = coverImage;
        return this;
    }

    public void setCoverImage(byte[] coverImage) {
        this.coverImage = coverImage;
    }

    public String getCoverImageContentType() {
        return coverImageContentType;
    }

    public Tournament coverImageContentType(String coverImageContentType) {
        this.coverImageContentType = coverImageContentType;
        return this;
    }

    public void setCoverImageContentType(String coverImageContentType) {
        this.coverImageContentType = coverImageContentType;
    }

    public Platform getPlatform() {
        return platform;
    }

    public Tournament platform(Platform platform) {
        this.platform = platform;
        return this;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Integer getMaxTeams() {
        return maxTeams;
    }

    public Tournament maxTeams(Integer maxTeams) {
        this.maxTeams = maxTeams;
        return this;
    }

    public void setMaxTeams(Integer maxTeams) {
        this.maxTeams = maxTeams;
    }

    public ZonedDateTime getRegistrationStarts() {
        return registrationStarts;
    }

    public Tournament registrationStarts(ZonedDateTime registrationStarts) {
        this.registrationStarts = registrationStarts;
        return this;
    }

    public void setRegistrationStarts(ZonedDateTime registrationStarts) {
        this.registrationStarts = registrationStarts;
    }

    public ZonedDateTime getRegistrationEnds() {
        return registrationEnds;
    }

    public Tournament registrationEnds(ZonedDateTime registrationEnds) {
        this.registrationEnds = registrationEnds;
        return this;
    }

    public void setRegistrationEnds(ZonedDateTime registrationEnds) {
        this.registrationEnds = registrationEnds;
    }

    public ZonedDateTime getTournamentBegins() {
        return tournamentBegins;
    }

    public Tournament tournamentBegins(ZonedDateTime tournamentBegins) {
        this.tournamentBegins = tournamentBegins;
        return this;
    }

    public void setTournamentBegins(ZonedDateTime tournamentBegins) {
        this.tournamentBegins = tournamentBegins;
    }

    public Double getPrice() {
        return price;
    }

    public Tournament price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getGamesPerRound() {
        return gamesPerRound;
    }

    public Tournament gamesPerRound(Integer gamesPerRound) {
        this.gamesPerRound = gamesPerRound;
        return this;
    }

    public void setGamesPerRound(Integer gamesPerRound) {
        this.gamesPerRound = gamesPerRound;
    }

    public String getDescription() {
        return description;
    }

    public Tournament description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TournamentType getType() {
        return type;
    }

    public Tournament type(TournamentType type) {
        this.type = type;
        return this;
    }

    public void setType(TournamentType type) {
        this.type = type;
    }

    public Set<Match> getMatches() {
        return matches;
    }

    public Tournament matches(Set<Match> matches) {
        this.matches = matches;
        return this;
    }

    public Tournament addMatch(Match match) {
        this.matches.add(match);
        match.setTournament(this);
        return this;
    }

    public Tournament removeMatch(Match match) {
        this.matches.remove(match);
        match.setTournament(null);
        return this;
    }

    public void setMatches(Set<Match> matches) {
        this.matches = matches;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public Tournament teams(Set<Team> teams) {
        this.teams = teams;
        return this;
    }

    public Tournament addTeam(Team team) {
        this.teams.add(team);
        team.getTorunaments().add(this);
        return this;
    }

    public Tournament removeTeam(Team team) {
        this.teams.remove(team);
        team.getTorunaments().remove(this);
        return this;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public Game getGame() {
        return game;
    }

    public Tournament game(Game game) {
        this.game = game;
        return this;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tournament tournament = (Tournament) o;
        if (tournament.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tournament.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tournament{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", coverImage='" + getCoverImage() + "'" +
            ", coverImageContentType='" + coverImageContentType + "'" +
            ", platform='" + getPlatform() + "'" +
            ", maxTeams='" + getMaxTeams() + "'" +
            ", registrationStarts='" + getRegistrationStarts() + "'" +
            ", registrationEnds='" + getRegistrationEnds() + "'" +
            ", tournamentBegins='" + getTournamentBegins() + "'" +
            ", price='" + getPrice() + "'" +
            ", gamesPerRound='" + getGamesPerRound() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
