package com.project.sportsManagement.views;

import com.project.sportsManagement.entity.Event;
import com.project.sportsManagement.entity.Institution;
import com.project.sportsManagement.entity.Student;
import com.project.sportsManagement.service.AuthenticationService;
import com.project.sportsManagement.service.EventService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Timestamp;
import java.time.Instant;

@Route(value = "",layout = MainLayout.class)
@PermitAll
@PageTitle("Vaadin || SMS")
public class ListView extends VerticalLayout implements BeforeEnterObserver {
    Grid<Event> eventGrid = new Grid<>(Event.class);
    TextField filterByName = new TextField();
    TextField filterByCollege = new TextField();

    @Autowired
    EventService eventService;

    @Autowired
    AuthenticationService authenticationService;

    public ListView(EventService eventService,AuthenticationService authenticationService) {
        this.eventService = eventService;
        this.authenticationService = authenticationService;
        addClassName("list-view");
        setSizeFull();
        
        configureGrid();
        add(
                getToolBar(),
                eventGrid
        );
        
        updateList();
    }

    private void updateList() {
        eventGrid.setItems(eventService.getAllEvents(filterByName.getValue()));
    }

    private Component getToolBar() {
        H2 greetingText = null;
        if (authenticationService.getAuthenticatedUser() instanceof Student student){
            greetingText = new H2("Welcome, " + student.getFirstName());
        }
        if (authenticationService.getAuthenticatedUser() instanceof Institution institution){
            greetingText = new H2("Welcome, " + institution.getInstitutionName());
        }
        filterByName.setPlaceholder("Filter by Event Name");
        filterByCollege.setPlaceholder("Filter By College");
        filterByName.getStyle().set("margin-left","auto");
        filterByName.setClearButtonVisible(true);
        filterByCollege.setClearButtonVisible(true);
        filterByName.setValueChangeMode(ValueChangeMode.EAGER);
        filterByCollege.setValueChangeMode(ValueChangeMode.EAGER);
        filterByName.addValueChangeListener(e -> updateList());
        HorizontalLayout header = new HorizontalLayout(greetingText,filterByName);
        header.getStyle().set("width","100%");
        return header;
    }

    private void configureGrid() {
        eventGrid.addClassName("event-grid");
        eventGrid.setSizeFull();
        eventGrid.setColumns("eventId","eventName");
        eventGrid.addColumn(event -> event.getHost().getInstitutionName()).setHeader("Host");
        eventGrid.addColumn(event -> event.getLevel().getLevel()).setHeader("Level");
        eventGrid.addColumn(new ComponentRenderer<>(event -> {
            String status = determineStatus(event.getStartTime(), event.getEndTime());
            Span statusBadge = new Span(status);
            if ("Live".equals(status)) {
                statusBadge.getElement().getThemeList().add("badge success");
            } else if ("Past".equals(status)) {
                statusBadge.getElement().getThemeList().add("badge error");
            } else {
                statusBadge.getElement().getThemeList().add("badge primary");
            }
            return statusBadge;
        })).setHeader("Status").setAutoWidth(true);
        eventGrid.getColumns().forEach(column -> column.setAutoWidth(true));

    }

    private String determineStatus(Timestamp startTime, Timestamp endTime) {
        Instant currentInstant = Instant.now();
        if (startTime.toInstant().isBefore(currentInstant) && endTime.toInstant().isBefore(currentInstant)){
            return "Past";
        }else if(startTime.toInstant().isBefore(currentInstant) && endTime.toInstant().isAfter(currentInstant)){
            return "Live";
        }else {
            return "Upcoming";
        }
    }


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (authenticationService.getAuthenticatedUser() == null || !SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
            event.rerouteTo("");
        }
    }
}
