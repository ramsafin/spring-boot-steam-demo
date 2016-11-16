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
public class User implements UserDetails {

    private Long id;

    private String fullName;

    private LocalDateTime joinDateTime;

    private boolean sex; //0 - female, 1 - male

    private String aboutMe;

    //TODO validate
    private String telephone;

    private Set<UserOpenIds> userOpenIdsSet = new HashSet<>();

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

    @Column(name = "sex")
    public boolean getSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
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

}
