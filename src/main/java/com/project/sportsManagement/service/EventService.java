package com.project.sportsManagement.service;

import com.project.sportsManagement.entity.Event;
import com.project.sportsManagement.entity.Game;
import com.project.sportsManagement.entity.Student;
import com.project.sportsManagement.repo.EventRepository;
import com.project.sportsManagement.repo.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private  EventRepository eventRepository;

    @Autowired
    private GameRepository gameRepository;

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
}
