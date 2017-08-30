package com.arnaugarcia.halospainleague.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.arnaugarcia.halospainleague.domain.enumeration.AchievementType;

/**
 * Class Achievement.
 * @author arnaugarcia.
 */
@ApiModel(description = "Class Achievement. @author arnaugarcia.")
@Entity
@Table(name = "achievement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Achievement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "score")
    private Integer score;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private AchievementType type;

    /**
     * Achivements and Badgets
     */
    @ApiModelProperty(value = "Achivements and Badgets")
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "achievement_player",
               joinColumns = @JoinColumn(name="achievements_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="players_id", referencedColumnName="id"))
    private Set<Player> players = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Achievement title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Achievement description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getScore() {
        return score;
    }

    public Achievement score(Integer score) {
        this.score = score;
        return this;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public AchievementType getType() {
        return type;
    }

    public Achievement type(AchievementType type) {
        this.type = type;
        return this;
    }

    public void setType(AchievementType type) {
        this.type = type;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public Achievement players(Set<Player> players) {
        this.players = players;
        return this;
    }

    public Achievement addPlayer(Player player) {
        this.players.add(player);
        player.getAchievements().add(this);
        return this;
    }

    public Achievement removePlayer(Player player) {
        this.players.remove(player);
        player.getAchievements().remove(this);
        return this;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
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
        Achievement achievement = (Achievement) o;
        if (achievement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), achievement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Achievement{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", score='" + getScore() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
