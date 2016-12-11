package ru.kpfu.itis.model.entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import org.joda.time.LocalDateTime;

/**
 * Created by root on 08.12.16.
 */
@Entity
@Table(name = "groups")
public class Group {

    private Long id;

    private String name;

    private String description;

    private User owner;

    private Set<User> participantList = new HashSet<>();

    private LocalDateTime createdTime;

    public Group() {
    }

    public Group(String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "group_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    public Set<User> getParticipantList() {
        return participantList;
    }

    public void setParticipantList(Set<User> participantList) {
        this.participantList = participantList;
    }

    @Column(name = "created_at")
    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    @PrePersist
    public void setUpBeforePersist() {

        this.createdTime = org.joda.time.LocalDateTime.now();
    }
}
