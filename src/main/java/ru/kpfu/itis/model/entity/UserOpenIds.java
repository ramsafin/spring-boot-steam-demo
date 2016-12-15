package ru.kpfu.itis.model.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_openid")
public class UserOpenIds implements Serializable {

    private String openidUrl;

    private User user;

    public UserOpenIds() {
    }

    public UserOpenIds(String openidUrl) {
        this.openidUrl = openidUrl;
    }

    @Id
    @Column(name = "openid_url", length = 100)
    public String getOpenidUrl() {
        return openidUrl;
    }

    public void setOpenidUrl(String openidUrl) {
        this.openidUrl = openidUrl;
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
