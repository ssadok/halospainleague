package com.arnaugarcia.halospainleague.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the TeamList entity.
 */
public class TeamListDTO implements Serializable {

    private Long id;

    private String reference;

    private Integer score;

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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TeamListDTO teamListDTO = (TeamListDTO) o;
        if(teamListDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), teamListDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TeamListDTO{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", score='" + getScore() + "'" +
            "}";
    }
}
