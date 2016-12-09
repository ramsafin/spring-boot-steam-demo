package ru.kpfu.itis.model.entity;

import org.joda.time.LocalDateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails{

    private Long id;

    private String fullName;

    private String steamNickname;

    private String avatarUrl;

    private LocalDateTime joinDateTime;

    private String aboutMe;

    private String telephone;

    private Set<UserOpenIds> userOpenIdsSet = new HashSet<>();

    private Set<Game> gamesSet = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_game",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id"))
    public Set<Game> getGamesSet () {
        return gamesSet;
    }

    public void setGamesSet(Set<Game> gamesSet) {
        this.gamesSet = gamesSet;
    }

    public User() {}

    public User(Long id, String fullName, String aboutMe, String telephone) {
        this.id = id;
        this.fullName = fullName;
        this.aboutMe = aboutMe;
        this.telephone = telephone;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "full_name")
    public String getFullName() {
        return fullName;
    }


    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Column(name = "joined_at")
    public LocalDateTime getJoinDateTime() {
        return joinDateTime;
    }

    public void setJoinDateTime(LocalDateTime joinDateTime) {
        this.joinDateTime = joinDateTime;
    }

    @Column(name = "telephone")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Column(name = "about_me")
    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<UserOpenIds> getUserOpenIdsSet() {
        return userOpenIdsSet;
    }

    public void setUserOpenIdsSet(Set<UserOpenIds> userOpenIdsSet) {
        this.userOpenIdsSet = userOpenIdsSet;
    }

    public void addOpenId(UserOpenIds openIds) {
        this.userOpenIdsSet.add(openIds);
    }


    @PrePersist
    public void setUpBeforePersist() {
        //set up joined at date
        this.joinDateTime = LocalDateTime.now();
    }

    @Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("ROLE_USER");
    }

    @Transient
    @Override
    public String getPassword() {
        return "";
    }

    @Transient
    @Override
    public String getUsername() {
        return getUserOpenIdsSet().toArray(new UserOpenIds[0])[0].getOpenidUrl();
    }

    @Transient
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Transient
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Transient
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Transient
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return String.format("User: [name : %s, telephone : %s]", fullName, telephone);
    }

    @Column(name = "steam_nickname")
    public String getSteamNickname() {
        return steamNickname;
    }

    public void setSteamNickname(String steamNickname) {
        this.steamNickname = steamNickname;
    }

    @Column(name = "avatar_url")
    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
