package ru.kpfu.itis.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class User implements UserDetails {

    private Long id;

    private String fullName;

    private LocalDateTime joinDateTime;

    private String aboutMe;

    private String telephone;

    private Set<UserOpenIds> userOpenIdsSet = new HashSet<>();

    @JsonIgnore
    private Set<Chat> chatSet = new HashSet<>();

    public User() {
    }

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


    @Column(name = "full_name")
    public String getFullName() {
        return fullName;
    }

    @JsonIgnore
    @Column(name = "joined_at")
    public LocalDateTime getJoinDateTime() {
        return joinDateTime;
    }


    @JsonIgnore
    @Column(name = "telephone")
    public String getTelephone() {
        return telephone;
    }


    @JsonIgnore
    @Column(name = "about_me")
    public String getAboutMe() {
        return aboutMe;
    }


    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<UserOpenIds> getUserOpenIdsSet() {
        return userOpenIdsSet;
    }

    @JsonIgnore
    @ManyToMany(mappedBy = "userSet", cascade = CascadeType.ALL)
    public Set<Chat> getChatSet() {
        return chatSet;
    }


    public void addOpenId(UserOpenIds openIds) {
        openIds.setUser(this);
        this.userOpenIdsSet.add(openIds);
    }


    @PrePersist
    public void setUpBeforePersist() {
        //set up joined at date
        this.joinDateTime = LocalDateTime.now();
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setJoinDateTime(LocalDateTime joinDateTime) {
        this.joinDateTime = joinDateTime;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setUserOpenIdsSet(Set<UserOpenIds> userOpenIdsSet) {
        this.userOpenIdsSet = userOpenIdsSet;
    }

    public void setChatSet(Set<Chat> chatSet) {
        this.chatSet = chatSet;
    }

    @JsonIgnore
    @Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("ROLE_USER");
    }

    @JsonIgnore
    @Transient
    @Override
    public String getPassword() {
        return "";
    }

    @JsonIgnore
    @Transient
    @Override
    public String getUsername() {
        return getUserOpenIdsSet().toArray(new UserOpenIds[0])[0].getOpenidUrl();
    }

    @JsonIgnore
    @Transient
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Transient
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Transient
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Transient
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return String.format("User: [name : %s, telephone : %s]", fullName, telephone);
    }
}
