package com.arnaugarcia.halospainleague.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.arnaugarcia.halospainleague.domain.enumeration.GameMode;

/**
 * A DTO for the MatchMode entity.
 */
public class MatchModeDTO implements Serializable {

    private Long id;

    private String reference;

    private Integer timeToWin;

    private Integer scoreToWin;

    private GameMode gameMode;

    private Long matchId;

    private String matchReference;

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

    public Integer getTimeToWin() {
        return timeToWin;
    }

    public void setTimeToWin(Integer timeToWin) {
        this.timeToWin = timeToWin;
    }

    public Integer getScoreToWin() {
        return scoreToWin;
    }

    public void setScoreToWin(Integer scoreToWin) {
        this.scoreToWin = scoreToWin;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public String getMatchReference() {
        return matchReference;
    }

    public void setMatchReference(String matchReference) {
        this.matchReference = matchReference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MatchModeDTO matchModeDTO = (MatchModeDTO) o;
        if(matchModeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), matchModeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MatchModeDTO{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", timeToWin='" + getTimeToWin() + "'" +
            ", scoreToWin='" + getScoreToWin() + "'" +
            ", gameMode='" + getGameMode() + "'" +
            "}";
    }
}
