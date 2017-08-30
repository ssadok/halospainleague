package com.arnaugarcia.halospainleague.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.arnaugarcia.halospainleague.domain.enumeration.Platform;

/**
 * Class Game.
 * @author arnaugarcia.
 */
@ApiModel(description = "Class Game. @author arnaugarcia.")
@Entity
@Table(name = "game")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "jhi_year")
    private ZonedDateTime year;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @DecimalMin(value = "0")
    @DecimalMax(value = "5")
    @Column(name = "rate")
    private Double rate;

    @Enumerated(EnumType.STRING)
    @Column(name = "platform")
    private Platform platform;

    /**
     * Matches, Tornaments, Games and Maps
     */
    @ApiModelProperty(value = "Matches, Tornaments, Games and Maps")
    @OneToMany(mappedBy = "game")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Map> maps = new HashSet<>();

    @OneToMany(mappedBy = "game")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tournament> tournaments = new HashSet<>();

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

    public Game name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getYear() {
        return year;
    }

    public Game year(ZonedDateTime year) {
        this.year = year;
        return this;
    }

    public void setYear(ZonedDateTime year) {
        this.year = year;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Game photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public Game photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public Double getRate() {
        return rate;
    }

    public Game rate(Double rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Platform getPlatform() {
        return platform;
    }

    public Game platform(Platform platform) {
        this.platform = platform;
        return this;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Set<Map> getMaps() {
        return maps;
    }

    public Game maps(Set<Map> maps) {
        this.maps = maps;
        return this;
    }

    public Game addMap(Map map) {
        this.maps.add(map);
        map.setGame(this);
        return this;
    }

    public Game removeMap(Map map) {
        this.maps.remove(map);
        map.setGame(null);
        return this;
    }

    public void setMaps(Set<Map> maps) {
        this.maps = maps;
    }

    public Set<Tournament> getTournaments() {
        return tournaments;
    }

    public Game tournaments(Set<Tournament> tournaments) {
        this.tournaments = tournaments;
        return this;
    }

    public Game addTournament(Tournament tournament) {
        this.tournaments.add(tournament);
        tournament.setGame(this);
        return this;
    }

    public Game removeTournament(Tournament tournament) {
        this.tournaments.remove(tournament);
        tournament.setGame(null);
        return this;
    }

    public void setTournaments(Set<Tournament> tournaments) {
        this.tournaments = tournaments;
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
        Game game = (Game) o;
        if (game.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), game.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Game{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", year='" + getYear() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + photoContentType + "'" +
            ", rate='" + getRate() + "'" +
            ", platform='" + getPlatform() + "'" +
            "}";
    }
}
