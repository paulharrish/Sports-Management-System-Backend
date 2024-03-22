package com.project.sportsManagement.service;

import com.project.sportsManagement.entity.Event;
import com.project.sportsManagement.entity.Game;
import com.project.sportsManagement.entity.Participation;
import com.project.sportsManagement.entity.Student;
import com.project.sportsManagement.repo.EventRepository;
import com.project.sportsManagement.repo.GameRepository;
import com.project.sportsManagement.repo.ParticipationRepository;
import com.project.sportsManagement.responseclasses.ParticipationDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private  EventRepository eventRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    public List<Event> filterByName(String filterText){
        if (filterText == null || filterText.isEmpty()){
            return eventRepository.findAll();
        }else {
            return eventRepository.search(filterText);
        }

    }

    public List<Event> filterByCollege(String filterText){
        if (filterText == null || filterText.isEmpty()){
            return eventRepository.findAll();
        }else {
            return eventRepository.searchByCollege(filterText);
        }
    }

    public List<Event> getParticipatedEvents(Student student){
        return eventRepository.getParticipatedEvents(student);
    }

    public Event getEventById(int eventId){
        return eventRepository.findById(eventId).get();
    }

    public Game getGameById(int gameId) {
        return gameRepository.findById(gameId).get();
    }

    List<ParticipationDetails> getParticipationDetailsForStudent(Student student){
        List<Participation> participations = participationRepository.findAllByStudent(student);
        return participations.stream()
                .map(participation -> {
                    ParticipationDetails participationDetails = new ParticipationDetails(participation.getParticipationId(),participation.getGameCode().getEventId().getEventName(),participation.getGameCode().getGameId().getGame());
                    return participationDetails;
                }).collect(Collectors.toList());
    }

    List<Student> getEventParticipants(int eventId){
        return eventRepository.getEventParticipants(eventId);
    }
}
