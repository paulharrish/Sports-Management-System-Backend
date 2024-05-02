package com.project.sportsManagement.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private int eventId;
    @Column(name = "event_name")
    private String eventName;
    @Column(name = "description")
    private String description;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "end_time")
    private Timestamp EndTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "host",referencedColumnName = "institution_code")
    private Institution host;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address",referencedColumnName = "location_id")
    private Location location;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "level",referencedColumnName = "level_id")
    private EventLevel level;

    @OneToMany(mappedBy = "eventId",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<EventGame> games;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    public Event() {
    }

    public Event(int eventId, String eventName, String description, Timestamp startTime, Timestamp endTime, Institution host, Location location, EventLevel level) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.description = description;
        this.startTime = startTime;
        EndTime = endTime;
        this.host = host;
        this.location = location;
        this.level = level;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }


    public Event(Institution institution) {
        this.host = institution;
        this.location = institution.getAddress();
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return EndTime;
    }

    public void setEndTime(Timestamp endTime) {
        EndTime = endTime;
    }

    public Institution getHost() {
        return host;
    }

    public void setHost(Institution host) {
        this.host = host;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public EventLevel getLevel() {
        return level;
    }

    public void setLevel(EventLevel level) {
        this.level = level;
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

    public Set<EventGame> getGames() {
        return games;
    }

    public void setGames(Set<EventGame> games) {
        this.games = games;
    }
}
