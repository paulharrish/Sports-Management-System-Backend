package com.project.sportsManagement.views;

import com.project.sportsManagement.entity.Event;
import com.project.sportsManagement.entity.Institution;
import com.project.sportsManagement.repo.EventLevelRepo;
import com.project.sportsManagement.repo.GameRepository;
import com.project.sportsManagement.service.AuthenticationService;
import com.project.sportsManagement.service.EventService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "create",layout = MainLayout.class)
@PermitAll
public class CreateEvent extends VerticalLayout {

    private AuthenticationService authenticationService;


    private EventService eventService;

    private GameRepository gameRepository;
    @Autowired
    public CreateEvent(EventLevelRepo eventLevelRepo, AuthenticationService authenticationService, EventService eventService, GameRepository gameRepository) {
        this.eventService = eventService;
        this.authenticationService = authenticationService;
        this.gameRepository = gameRepository;
        EventForm eventForm = new EventForm(eventLevelRepo,new Event(getCurrentUser()),eventService,gameRepository,false);
        add(eventForm);
        eventForm.setWidth("350px");
    }


    private Institution getCurrentUser() {
        return (Institution) authenticationService.getAuthenticatedUser();
    }
}
