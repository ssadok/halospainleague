package com.arnaugarcia.halospainleague.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.arnaugarcia.halospainleague.domain.enumeration.Platform;

/**
 * A DTO for the SocialAccount entity.
 */
public class SocialAccountDTO implements Serializable {

    private Long id;

    private String nick;

    private Platform platform;

    private String token;

    private Long playerId;

    private String playerName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SocialAccountDTO socialAccountDTO = (SocialAccountDTO) o;
        if(socialAccountDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), socialAccountDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SocialAccountDTO{" +
            "id=" + getId() +
            ", nick='" + getNick() + "'" +
            ", platform='" + getPlatform() + "'" +
            ", token='" + getToken() + "'" +
            "}";
    }
}
