package com.arnaugarcia.halospainleague.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ResultMatch entity.
 */
public class ResultMatchDTO implements Serializable {

    private Long id;

    private String reference;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResultMatchDTO resultMatchDTO = (ResultMatchDTO) o;
        if(resultMatchDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resultMatchDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ResultMatchDTO{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            "}";
    }
}
