package com.arnaugarcia.halospainleague.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.arnaugarcia.halospainleague.domain.enumeration.Platform;

/**
 * Class SocialAccount.
 * @author arnaugarcia.
 */
@ApiModel(description = "Class SocialAccount. @author arnaugarcia.")
@Entity
@Table(name = "social_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SocialAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nick")
    private String nick;

    @Enumerated(EnumType.STRING)
    @Column(name = "platform")
    private Platform platform;

    @Column(name = "token")
    private String token;

    @ManyToOne
    private Player player;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public SocialAccount nick(String nick) {
        this.nick = nick;
        return this;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Platform getPlatform() {
        return platform;
    }

    public SocialAccount platform(Platform platform) {
        this.platform = platform;
        return this;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public String getToken() {
        return token;
    }

    public SocialAccount token(String token) {
        this.token = token;
        return this;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Player getPlayer() {
        return player;
    }

    public SocialAccount player(Player player) {
        this.player = player;
        return this;
    }

    public void setPlayer(Player player) {
        this.player = player;
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
        SocialAccount socialAccount = (SocialAccount) o;
        if (socialAccount.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), socialAccount.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SocialAccount{" +
            "id=" + getId() +
            ", nick='" + getNick() + "'" +
            ", platform='" + getPlatform() + "'" +
            ", token='" + getToken() + "'" +
            "}";
    }
}
