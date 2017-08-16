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

import com.arnaugarcia.halospainleague.domain.enumeration.PlayerState;

import com.arnaugarcia.halospainleague.domain.enumeration.Gender;

/**
 * Class Player.
 * @author arnaugarcia.
 */
@ApiModel(description = "Class Player. @author arnaugarcia.")
@Entity
@Table(name = "player")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "phone")
    private String phone;

    @Lob
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "created", nullable = false)
    private ZonedDateTime created;

    @Lob
    @Column(name = "profile_photo")
    private byte[] profilePhoto;

    @Column(name = "profile_photo_content_type")
    private String profilePhotoContentType;

    @Lob
    @Column(name = "profile_cover")
    private byte[] profileCover;

    @Column(name = "profile_cover_content_type")
    private String profileCoverContentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private PlayerState state;

    @Column(name = "instagram")
    private String instagram;

    @Column(name = "twitter")
    private String twitter;

    @Column(name = "youtube")
    private String youtube;

    @Column(name = "facebook")
    private String facebook;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "score")
    private Integer score;

    @Column(name = "address")
    private String address;

    @Column(name = "time_zone")
    private String timeZone;

    /**
     * Profile, User, Account and ProfileConfiguration
     */
    @ApiModelProperty(value = "Profile, User, Account and ProfileConfiguration")
    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    /**
     * Accounts and Settings
     */
    @ApiModelProperty(value = "Accounts and Settings")
    @OneToMany(mappedBy = "player")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SocialAccount> socialAccounts = new HashSet<>();

    /**
     * Accounts and Settings
     */
    @ApiModelProperty(value = "Accounts and Settings")
    @ManyToOne
    private Country country;

    /**
     * Messages
     */
    @ApiModelProperty(value = "Messages")
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "player_message_room",
               joinColumns = @JoinColumn(name="players_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="message_rooms_id", referencedColumnName="id"))
    private Set<MessageRoom> messageRooms = new HashSet<>();

    @OneToOne(mappedBy = "player")
    @JsonIgnore
    private ProfileConfiguration profileConfiguration;

    @OneToMany(mappedBy = "player")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Notification> notifications = new HashSet<>();

    @ManyToMany(mappedBy = "players")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Achievement> achievements = new HashSet<>();

    @ManyToMany(mappedBy = "players")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Team> teams = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Player name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public Player surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public Player phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public Player description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public Player created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public byte[] getProfilePhoto() {
        return profilePhoto;
    }

    public Player profilePhoto(byte[] profilePhoto) {
        this.profilePhoto = profilePhoto;
        return this;
    }

    public void setProfilePhoto(byte[] profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getProfilePhotoContentType() {
        return profilePhotoContentType;
    }

    public Player profilePhotoContentType(String profilePhotoContentType) {
        this.profilePhotoContentType = profilePhotoContentType;
        return this;
    }

    public void setProfilePhotoContentType(String profilePhotoContentType) {
        this.profilePhotoContentType = profilePhotoContentType;
    }

    public byte[] getProfileCover() {
        return profileCover;
    }

    public Player profileCover(byte[] profileCover) {
        this.profileCover = profileCover;
        return this;
    }

    public void setProfileCover(byte[] profileCover) {
        this.profileCover = profileCover;
    }

    public String getProfileCoverContentType() {
        return profileCoverContentType;
    }

    public Player profileCoverContentType(String profileCoverContentType) {
        this.profileCoverContentType = profileCoverContentType;
        return this;
    }

    public void setProfileCoverContentType(String profileCoverContentType) {
        this.profileCoverContentType = profileCoverContentType;
    }

    public PlayerState getState() {
        return state;
    }

    public Player state(PlayerState state) {
        this.state = state;
        return this;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }

    public String getInstagram() {
        return instagram;
    }

    public Player instagram(String instagram) {
        this.instagram = instagram;
        return this;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getTwitter() {
        return twitter;
    }

    public Player twitter(String twitter) {
        this.twitter = twitter;
        return this;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getYoutube() {
        return youtube;
    }

    public Player youtube(String youtube) {
        this.youtube = youtube;
        return this;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getFacebook() {
        return facebook;
    }

    public Player facebook(String facebook) {
        this.facebook = facebook;
        return this;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public Gender getGender() {
        return gender;
    }

    public Player gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getScore() {
        return score;
    }

    public Player score(Integer score) {
        this.score = score;
        return this;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getAddress() {
        return address;
    }

    public Player address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public Player timeZone(String timeZone) {
        this.timeZone = timeZone;
        return this;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public User getUser() {
        return user;
    }

    public Player user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<SocialAccount> getSocialAccounts() {
        return socialAccounts;
    }

    public Player socialAccounts(Set<SocialAccount> socialAccounts) {
        this.socialAccounts = socialAccounts;
        return this;
    }

    public Player addSocialAccount(SocialAccount socialAccount) {
        this.socialAccounts.add(socialAccount);
        socialAccount.setPlayer(this);
        return this;
    }

    public Player removeSocialAccount(SocialAccount socialAccount) {
        this.socialAccounts.remove(socialAccount);
        socialAccount.setPlayer(null);
        return this;
    }

    public void setSocialAccounts(Set<SocialAccount> socialAccounts) {
        this.socialAccounts = socialAccounts;
    }

    public Country getCountry() {
        return country;
    }

    public Player country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Set<MessageRoom> getMessageRooms() {
        return messageRooms;
    }

    public Player messageRooms(Set<MessageRoom> messageRooms) {
        this.messageRooms = messageRooms;
        return this;
    }

    public Player addMessageRoom(MessageRoom messageRoom) {
        this.messageRooms.add(messageRoom);
        messageRoom.getPlayers().add(this);
        return this;
    }

    public Player removeMessageRoom(MessageRoom messageRoom) {
        this.messageRooms.remove(messageRoom);
        messageRoom.getPlayers().remove(this);
        return this;
    }

    public void setMessageRooms(Set<MessageRoom> messageRooms) {
        this.messageRooms = messageRooms;
    }

    public ProfileConfiguration getProfileConfiguration() {
        return profileConfiguration;
    }

    public Player profileConfiguration(ProfileConfiguration profileConfiguration) {
        this.profileConfiguration = profileConfiguration;
        return this;
    }

    public void setProfileConfiguration(ProfileConfiguration profileConfiguration) {
        this.profileConfiguration = profileConfiguration;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public Player notifications(Set<Notification> notifications) {
        this.notifications = notifications;
        return this;
    }

    public Player addNotification(Notification notification) {
        this.notifications.add(notification);
        notification.setPlayer(this);
        return this;
    }

    public Player removeNotification(Notification notification) {
        this.notifications.remove(notification);
        notification.setPlayer(null);
        return this;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }

    public Set<Achievement> getAchievements() {
        return achievements;
    }

    public Player achievements(Set<Achievement> achievements) {
        this.achievements = achievements;
        return this;
    }

    public Player addAchievement(Achievement achievement) {
        this.achievements.add(achievement);
        achievement.getPlayers().add(this);
        return this;
    }

    public Player removeAchievement(Achievement achievement) {
        this.achievements.remove(achievement);
        achievement.getPlayers().remove(this);
        return this;
    }

    public void setAchievements(Set<Achievement> achievements) {
        this.achievements = achievements;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public Player teams(Set<Team> teams) {
        this.teams = teams;
        return this;
    }

    public Player addTeam(Team team) {
        this.teams.add(team);
        team.getPlayers().add(this);
        return this;
    }

    public Player removeTeam(Team team) {
        this.teams.remove(team);
        team.getPlayers().remove(this);
        return this;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Player player = (Player) o;
        if (player.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), player.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Player{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            ", phone='" + getPhone() + "'" +
            ", description='" + getDescription() + "'" +
            ", created='" + getCreated() + "'" +
            ", profilePhoto='" + getProfilePhoto() + "'" +
            ", profilePhotoContentType='" + profilePhotoContentType + "'" +
            ", profileCover='" + getProfileCover() + "'" +
            ", profileCoverContentType='" + profileCoverContentType + "'" +
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
