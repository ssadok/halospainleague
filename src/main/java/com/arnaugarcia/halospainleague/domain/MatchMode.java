package com.arnaugarcia.halospainleague.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.arnaugarcia.halospainleague.domain.enumeration.GameMode;

/**
 * Class ModeMatch.
 * @author arnaugarcia.
 */
@ApiModel(description = "Class ModeMatch. @author arnaugarcia.")
@Entity
@Table(name = "match_mode")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MatchMode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reference")
    private String reference;

    @Column(name = "time_to_win")
    private Integer timeToWin;

    @Column(name = "score_to_win")
    private Integer scoreToWin;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_mode")
    private GameMode gameMode;

    @ManyToOne
    private Match match;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public MatchMode reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Integer getTimeToWin() {
        return timeToWin;
    }

    public MatchMode timeToWin(Integer timeToWin) {
        this.timeToWin = timeToWin;
        return this;
    }

    public void setTimeToWin(Integer timeToWin) {
        this.timeToWin = timeToWin;
    }

    public Integer getScoreToWin() {
        return scoreToWin;
    }

    public MatchMode scoreToWin(Integer scoreToWin) {
        this.scoreToWin = scoreToWin;
        return this;
    }

    public void setScoreToWin(Integer scoreToWin) {
        this.scoreToWin = scoreToWin;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public MatchMode gameMode(GameMode gameMode) {
        this.gameMode = gameMode;
        return this;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public Match getMatch() {
        return match;
    }

    public MatchMode match(Match match) {
        this.match = match;
        return this;
    }

    public void setMatch(Match match) {
        this.match = match;
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
        MatchMode matchMode = (MatchMode) o;
        if (matchMode.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), matchMode.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MatchMode{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", timeToWin='" + getTimeToWin() + "'" +
            ", scoreToWin='" + getScoreToWin() + "'" +
            ", gameMode='" + getGameMode() + "'" +
            "}";
    }
}
