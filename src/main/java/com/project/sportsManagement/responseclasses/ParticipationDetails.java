package com.project.sportsManagement.responseclasses;

public class ParticipationDetails {

    private int participationId;
    private String eventName;
    private String gameName;

    public ParticipationDetails(int participationId, String eventName, String gameName) {
        this.participationId = participationId;
        this.eventName = eventName;
        this.gameName = gameName;
    }

    public int getParticipationId() {
        return participationId;
    }

    public void setParticipationId(int participationId) {
        this.participationId = participationId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
}
