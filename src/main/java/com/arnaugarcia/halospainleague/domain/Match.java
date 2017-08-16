package com.arnaugarcia.halospainleague.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Class Match.
 * @author arnaugarcia.
 */
@ApiModel(description = "Class Match. @author arnaugarcia.")
@Entity
@Table(name = "jhi_match")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Match implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reference")
    private String reference;

    @Column(name = "duration")
    private Integer duration;

    /**
     * Matches, Tornaments and Games
     */
    @ApiModelProperty(value = "Matches, Tornaments and Games")
    @OneToOne
    @JoinColumn(unique = true)
    private ResultMatch resultMatch;

    @ManyToOne
    private Tournament tournament;

    @ManyToOne
    private Map map;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public Match reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Integer getDuration() {
        return duration;
    }

    public Match duration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public ResultMatch getResultMatch() {
        return resultMatch;
    }

    public Match resultMatch(ResultMatch resultMatch) {
        this.resultMatch = resultMatch;
        return this;
    }

    public void setResultMatch(ResultMatch resultMatch) {
        this.resultMatch = resultMatch;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public Match tournament(Tournament tournament) {
        this.tournament = tournament;
        return this;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Map getMap() {
        return map;
    }

    public Match map(Map map) {
        this.map = map;
        return this;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Match match = (Match) o;
        if (match.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), match.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Match{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", duration='" + getDuration() + "'" +
            "}";
    }
}
