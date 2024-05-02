package com.project.sportsManagement.views;

import com.project.sportsManagement.entity.Event;
import com.project.sportsManagement.entity.Institution;
import com.project.sportsManagement.repo.EventLevelRepo;
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
    @Autowired
    public CreateEvent(EventLevelRepo eventLevelRepo, AuthenticationService authenticationService, EventService eventService) {
        this.eventService = eventService;
        this.authenticationService = authenticationService;
        EventCreationForm eventCreationForm = new EventCreationForm(eventLevelRepo,new Event(getCurrentUser()),eventService,getCurrentUser());
        add(eventCreationForm);
        eventCreationForm.setWidth("300px");
    }


    private Institution getCurrentUser() {
        return (Institution) authenticationService.getAuthenticatedUser();
    }
}
