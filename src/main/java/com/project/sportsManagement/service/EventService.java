package com.project.sportsManagement.service;

import com.project.sportsManagement.entity.Event;
import com.project.sportsManagement.repo.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private  EventRepository eventRepository;

    public List<Event> getAllEvents(){
        return eventRepository.findAll();
    }
}
