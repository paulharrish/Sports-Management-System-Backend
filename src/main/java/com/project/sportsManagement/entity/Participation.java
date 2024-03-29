package com.project.sportsManagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "participation")
public class Participation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participation_id")
    private int participationId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id",referencedColumnName = "student_id")
    private Student student;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_code",referencedColumnName = "game_code")
    private EventGame gameCode;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id",referencedColumnName = "team_id")
    private Team team;

    public Participation() {
    }

    public Participation(Student student, EventGame gameCode) {
        this.student = student;
        this.gameCode = gameCode;
        this.team = null;
    }

    public Participation(Team team,EventGame gameCode){
        this.team = team;
        this.gameCode = gameCode;
        this.student = null;
    }

    public int getParticipationId() {
        return participationId;
    }

    public void setParticipationId(int participationId) {
        this.participationId = participationId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public EventGame getGameCode() {
        return gameCode;
    }

    public void setGameCode(EventGame gameCode) {
        this.gameCode = gameCode;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
