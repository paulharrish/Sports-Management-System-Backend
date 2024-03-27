package com.project.sportsManagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "student")
public class Student implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(authority.getAuthority()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true
                ;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Id
    @Column(name = "student_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "roll_no")
    private String rollNo;

    @Column(name = "email")
    @NaturalId
    private String email;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "institution_code",referencedColumnName = "institution_code")
    private Institution institution;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id",referencedColumnName = "role_id")
    private Role authority;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Participation> participation;


    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name = "student_team",
            joinColumns = @JoinColumn(name = "student_id",referencedColumnName = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id",referencedColumnName = "team_id")
    )
    public Set<Team> teams;


    @OneToMany(mappedBy = "creator",fetch = FetchType.EAGER)
    private Set<Team> createdTeams;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;


    public Student() {
        super();
    }

    public Student(String firstName, String lastName, String rollNo, String email, String password, Institution institution, Role authority,Set<Team> teams,Set<Team> createdTeams) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.rollNo = rollNo;
        this.email = email;
        this.password = password;
        this.institution = institution;
        this.authority = authority;
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.participation = new HashSet<>();
        this.teams = teams;
        this.createdTeams = createdTeams;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public String getAuthority() {
        return authority.getAuthority();
    }

    public void setAuthority(Role authority) {
        this.authority = authority;
    }

    public Set<Participation> getParticipation() {
        return participation;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public Set<Team> getCreatedTeams() {
        return createdTeams;
    }

    public void setCreatedTeams(Set<Team> createdTeams) {
        this.createdTeams = createdTeams;
    }
}
