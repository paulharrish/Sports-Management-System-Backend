package com.project.sportsManagement.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private int teamId;
    @Column(name = "team_name")
    private String teamName;

    @JoinColumn(name = "institution",referencedColumnName = "institution_code")
    @ManyToOne(fetch = FetchType.EAGER)
    private Institution teamInstitution;
    @ManyToMany(mappedBy = "teams",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    public Set<Student> teamMembers = new HashSet<>();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by",referencedColumnName = "student_id")
    private Student creator;

    @OneToMany(mappedBy = "team",fetch = FetchType.EAGER)
    private Set<Participation> participations;


    public Team() {
    }

    public Team(String teamName, Institution teamInstitution, Set<Student> teamMembers, Student creator) {
        this.teamName = teamName;
        this.teamInstitution = teamInstitution;
        this.teamMembers = teamMembers;
        this.creator = creator;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Institution getTeamInstitution() {
        return teamInstitution;
    }

    public void setTeamInstitution(Institution teamInstitution) {
        this.teamInstitution = teamInstitution;
    }

    public Set<Student> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(Set<Student> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public Student getCreator() {
        return creator;
    }

    public void setCreator(Student creator) {
        this.creator = creator;
    }
}
