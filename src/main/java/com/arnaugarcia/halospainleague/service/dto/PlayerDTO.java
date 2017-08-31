package com.arnaugarcia.halospainleague.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;
import com.arnaugarcia.halospainleague.domain.enumeration.PlayerState;
import com.arnaugarcia.halospainleague.domain.enumeration.Gender;

/**
 * A DTO for the Player entity.
 */
public class PlayerDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String surname;

    private String phone;

    @Lob
    private String description;

    @NotNull
    private ZonedDateTime created;

    @Lob
    private byte[] profilePhoto;
    private String profilePhotoContentType;

    @Lob
    private byte[] profileCover;
    private String profileCoverContentType;

    @NotNull
    private PlayerState state;

    private String instagram;

    private String twitter;

    private String youtube;

    private String facebook;

    private Gender gender;

    private Integer score;

    private String address;

    private String timeZone;

    private Long userId;

    private String userLogin;

    private Long profileConfigurationId;

    private Long countryId;

    private String countryName;

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public byte[] getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(byte[] profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getProfilePhotoContentType() {
        return profilePhotoContentType;
    }

    public void setProfilePhotoContentType(String profilePhotoContentType) {
        this.profilePhotoContentType = profilePhotoContentType;
    }

    public byte[] getProfileCover() {
        return profileCover;
    }

    public void setProfileCover(byte[] profileCover) {
        this.profileCover = profileCover;
    }

    public String getProfileCoverContentType() {
        return profileCoverContentType;
    }

    public void setProfileCoverContentType(String profileCoverContentType) {
        this.profileCoverContentType = profileCoverContentType;
    }

    public PlayerState getState() {
        return state;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Long getProfileConfigurationId() {
        return profileConfigurationId;
    }

    public void setProfileConfigurationId(Long profileConfigurationId) {
        this.profileConfigurationId = profileConfigurationId;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PlayerDTO playerDTO = (PlayerDTO) o;
        if(playerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), playerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlayerDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            ", phone='" + getPhone() + "'" +
            ", description='" + getDescription() + "'" +
            ", created='" + getCreated() + "'" +
            ", profilePhoto='" + getProfilePhoto() + "'" +
            ", profileCover='" + getProfileCover() + "'" +
            ", state='" + getState() + "'" +
            ", instagram='" + getInstagram() + "'" +
            ", twitter='" + getTwitter() + "'" +
            ", youtube='" + getYoutube() + "'" +
            ", facebook='" + getFacebook() + "'" +
            ", gender='" + getGender() + "'" +
            ", score='" + getScore() + "'" +
            ", address='" + getAddress() + "'" +
            ", timeZone='" + getTimeZone() + "'" +
            "}";
    }
}
