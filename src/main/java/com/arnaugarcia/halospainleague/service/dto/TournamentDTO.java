package com.arnaugarcia.halospainleague.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;
import com.arnaugarcia.halospainleague.domain.enumeration.Platform;
import com.arnaugarcia.halospainleague.domain.enumeration.TournamentType;

/**
 * A DTO for the Tournament entity.
 */
public class TournamentDTO implements Serializable {

    private Long id;

    private String name;

    @Lob
    private byte[] coverImage;
    private String coverImageContentType;

    private Platform platform;

    @NotNull
    private Integer maxTeams;

    private ZonedDateTime registrationStarts;

    private ZonedDateTime registrationEnds;

    private ZonedDateTime tournamentBegins;

    private Double price;

    private Integer gamesPerRound;

    @Lob
    private String description;

    private TournamentType type;

    private Set<TeamDTO> teams = new HashSet<>();

    private Long gameId;

    private String gameName;

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

    public byte[] getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(byte[] coverImage) {
        this.coverImage = coverImage;
    }

    public String getCoverImageContentType() {
        return coverImageContentType;
    }

    public void setCoverImageContentType(String coverImageContentType) {
        this.coverImageContentType = coverImageContentType;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Integer getMaxTeams() {
        return maxTeams;
    }

    public void setMaxTeams(Integer maxTeams) {
        this.maxTeams = maxTeams;
    }

    public ZonedDateTime getRegistrationStarts() {
        return registrationStarts;
    }

    public void setRegistrationStarts(ZonedDateTime registrationStarts) {
        this.registrationStarts = registrationStarts;
    }

    public ZonedDateTime getRegistrationEnds() {
        return registrationEnds;
    }

    public void setRegistrationEnds(ZonedDateTime registrationEnds) {
        this.registrationEnds = registrationEnds;
    }

    public ZonedDateTime getTournamentBegins() {
        return tournamentBegins;
    }

    public void setTournamentBegins(ZonedDateTime tournamentBegins) {
        this.tournamentBegins = tournamentBegins;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getGamesPerRound() {
        return gamesPerRound;
    }

    public void setGamesPerRound(Integer gamesPerRound) {
        this.gamesPerRound = gamesPerRound;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TournamentType getType() {
        return type;
    }

    public void setType(TournamentType type) {
        this.type = type;
    }

    public Set<TeamDTO> getTeams() {
        return teams;
    }

    public void setTeams(Set<TeamDTO> teams) {
        this.teams = teams;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TournamentDTO tournamentDTO = (TournamentDTO) o;
        if(tournamentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tournamentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TournamentDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", coverImage='" + getCoverImage() + "'" +
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
