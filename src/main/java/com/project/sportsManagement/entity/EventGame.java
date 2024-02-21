package com.project.sportsManagement.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "event_game")
public class EventGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_code")
    private int gameCode;
    @ManyToOne()
    @JoinColumn(name = "event_id",referencedColumnName = "event_id")
    private Event eventId;
    @ManyToOne()
    @JoinColumn(name = "game_id",referencedColumnName = "game_id")
    private Game gameId;
    @OneToMany(mappedBy = "gameCode",cascade = CascadeType.ALL)
    private Set<Participation> participation;

    public EventGame() {
    }

    public EventGame( Event eventId, Game gameId) {
        this.eventId = eventId;
        this.gameId = gameId;
    }

    public int getGameCode() {
        return gameCode;
    }


    public Event getEventId() {
        return eventId;
    }

    public void setEventId(Event eventId) {
        this.eventId = eventId;
    }

    public Game getGameId() {
        return gameId;
    }

    public void setGameId(Game gameId) {
        this.gameId = gameId;
    }

    public Set<Participation> getParticipation() {
        return participation;
    }
}
