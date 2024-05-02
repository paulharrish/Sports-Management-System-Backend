package com.project.sportsManagement.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "game")
public class Game {
    @Id
    @Column(name = "game_id")
    private int game_id;
    @Column(name = "game")
    private String game;
    @OneToMany(mappedBy = "gameId",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<EventGame> events;
    @Column(name = "")
    private boolean isSoloParticipationAllowed;

    public Game() {
    }

    public Game(int game_id, String game) {
        this.game_id = game_id;
        this.game = game;
    }

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public boolean isSoloParticipationAllowed() {
        return isSoloParticipationAllowed;
    }

    public void setSoloParticipationAllowed(boolean soloParticipationAllowed) {
        isSoloParticipationAllowed = soloParticipationAllowed;
    }
}
