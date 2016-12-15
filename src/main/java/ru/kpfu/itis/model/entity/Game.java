package ru.kpfu.itis.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "games")
public class Game {

    private Long id;
    private String game;
    private Set<User> userSet = new HashSet<>();

    public Game(){};
    public Game(Long id, String game){
        this.id = id;
        this.game = game;
    }


    @Id
    @Column(name = "id", nullable=false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Column(name = "game", nullable=false)
    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }


    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }
    @ManyToMany
    @JoinTable(name = "user_game",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    public Set<User> getUserSet() {
        return userSet;
    }
}
