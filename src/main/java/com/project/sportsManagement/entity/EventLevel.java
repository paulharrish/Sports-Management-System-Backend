package com.project.sportsManagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "event_level")
public class EventLevel {
    @Id
    @Column(name = "level_id")
    private int levelId;

    @Column(name = "level")
    private String level;

    public EventLevel() {
    }

    public EventLevel(int levelId, String level) {
        this.levelId = levelId;
        this.level = level;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
