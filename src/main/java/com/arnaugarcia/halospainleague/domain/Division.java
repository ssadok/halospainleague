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

import com.arnaugarcia.halospainleague.domain.enumeration.DivisionType;

/**
 * Class Division.
 * @author arnaugarcia.
 */
@ApiModel(description = "Class Division. @author arnaugarcia.")
@Entity
@Table(name = "division")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Division implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "max_players")
    private Integer maxPlayers;

    @Enumerated(EnumType.STRING)
    @Column(name = "division_type")
    private DivisionType divisionType;

    @OneToMany(mappedBy = "division")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Team> teams = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Division name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    public Division maxPlayers(Integer maxPlayers) {
        this.maxPlayers = maxPlayers;
        return this;
    }

    public void setMaxPlayers(Integer maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public DivisionType getDivisionType() {
        return divisionType;
    }

    public Division divisionType(DivisionType divisionType) {
        this.divisionType = divisionType;
        return this;
    }

    public void setDivisionType(DivisionType divisionType) {
        this.divisionType = divisionType;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public Division teams(Set<Team> teams) {
        this.teams = teams;
        return this;
    }

    public Division addTeam(Team team) {
        this.teams.add(team);
        team.setDivision(this);
        return this;
    }

    public Division removeTeam(Team team) {
        this.teams.remove(team);
        team.setDivision(null);
        return this;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
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
        Division division = (Division) o;
        if (division.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), division.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Division{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", maxPlayers='" + getMaxPlayers() + "'" +
            ", divisionType='" + getDivisionType() + "'" +
            "}";
    }
}
