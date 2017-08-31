package com.arnaugarcia.halospainleague.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.arnaugarcia.halospainleague.domain.enumeration.DivisionType;

/**
 * A DTO for the Division entity.
 */
public class DivisionDTO implements Serializable {

    private Long id;

    private String name;

    private Integer maxPlayers;

    private DivisionType divisionType;

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

    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(Integer maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public DivisionType getDivisionType() {
        return divisionType;
    }

    public void setDivisionType(DivisionType divisionType) {
        this.divisionType = divisionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DivisionDTO divisionDTO = (DivisionDTO) o;
        if(divisionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), divisionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DivisionDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", maxPlayers='" + getMaxPlayers() + "'" +
            ", divisionType='" + getDivisionType() + "'" +
            "}";
    }
}
