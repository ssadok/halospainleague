package com.arnaugarcia.halospainleague.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Match entity.
 */
public class MatchDTO implements Serializable {

    private Long id;

    private String reference;

    private Integer duration;

    private Long resultMatchId;

    private String resultMatchReference;

    private Long teamListId;

    private String teamListReference;

    private Long tournamentId;

    private String tournamentName;

    private Long mapId;

    private String mapName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Long getResultMatchId() {
        return resultMatchId;
    }

    public void setResultMatchId(Long resultMatchId) {
        this.resultMatchId = resultMatchId;
    }

    public String getResultMatchReference() {
        return resultMatchReference;
    }

    public void setResultMatchReference(String resultMatchReference) {
        this.resultMatchReference = resultMatchReference;
    }

    public Long getTeamListId() {
        return teamListId;
    }

    public void setTeamListId(Long teamListId) {
        this.teamListId = teamListId;
    }

    public String getTeamListReference() {
        return teamListReference;
    }

    public void setTeamListReference(String teamListReference) {
        this.teamListReference = teamListReference;
    }

    public Long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Long tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public Long getMapId() {
        return mapId;
    }

    public void setMapId(Long mapId) {
        this.mapId = mapId;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MatchDTO matchDTO = (MatchDTO) o;
        if(matchDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), matchDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MatchDTO{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", duration='" + getDuration() + "'" +
            "}";
    }
}
