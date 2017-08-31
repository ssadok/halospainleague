package com.arnaugarcia.halospainleague.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Class Match.
 * @author arnaugarcia.
 */
@ApiModel(description = "Class Match. @author arnaugarcia.")
@Entity
@Table(name = "team_list")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TeamList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reference")
    private String reference;

    @Column(name = "score")
    private Integer score;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "team_list_team",
               joinColumns = @JoinColumn(name="team_lists_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="teams_id", referencedColumnName="id"))
    private Set<Team> teams = new HashSet<>();

    @OneToOne(mappedBy = "teamList")
    @JsonIgnore
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

    public TeamList reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Integer getScore() {
        return score;
    }

    public TeamList score(Integer score) {
        this.score = score;
        return this;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public TeamList teams(Set<Team> teams) {
        this.teams = teams;
        return this;
    }

    public TeamList addTeam(Team team) {
        this.teams.add(team);
        team.getTeamLists().add(this);
        return this;
    }

    public TeamList removeTeam(Team team) {
        this.teams.remove(team);
        team.getTeamLists().remove(this);
        return this;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public Match getMatch() {
        return match;
    }

    public TeamList match(Match match) {
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
        TeamList teamList = (TeamList) o;
        if (teamList.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), teamList.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TeamList{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", score='" + getScore() + "'" +
            "}";
    }
}
