package com.project.sportsManagement.views;

import com.project.sportsManagement.entity.Event;
import com.project.sportsManagement.repo.EventLevelRepo;
import com.project.sportsManagement.repo.EventRepository;
import com.project.sportsManagement.repo.GameRepository;
import com.project.sportsManagement.service.EventService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

@PermitAll
@Route(value = "edit/event",layout = MainLayout.class)
public class EventEditView extends VerticalLayout implements HasUrlParameter<Integer> {

    private EventRepository eventRepository;

    private EventLevelRepo eventLevelRepo;

    private EventService eventService;

    private GameRepository gameRepository;
    private Event eventTobeEdit;



    @Autowired
    public EventEditView(EventRepository eventRepository, EventLevelRepo eventLevelRepo, EventService eventService, GameRepository gameRepository) {
        this.eventRepository = eventRepository;
        this.eventLevelRepo = eventLevelRepo;
        this.eventService = eventService;
        this.gameRepository = gameRepository;
    }


    @Override
    public void setParameter(BeforeEvent beforeEvent, Integer eventId) {
        Event event = eventRepository.findById(eventId).get();
        this.eventTobeEdit = event;
        EventForm eventForm = new EventForm(eventLevelRepo, event, eventService, gameRepository,true);
        removeAll(); // Remove existing components
        add(eventForm);
        eventForm.setWidth("350px");
    }
}
