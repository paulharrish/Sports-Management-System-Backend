package com.project.sportsManagement.views;

import com.project.sportsManagement.entity.Event;
import com.project.sportsManagement.entity.Institution;
import com.project.sportsManagement.entity.Student;
import com.project.sportsManagement.repo.EventRepository;
import com.project.sportsManagement.service.AuthenticationService;
import com.project.sportsManagement.service.EventService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
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
import java.util.List;
import java.util.Map;

@Route(value = "",layout = MainLayout.class)
@PermitAll
@PageTitle("Vaadin || SMS")
public class ListView extends VerticalLayout implements BeforeEnterObserver {
    Grid<Event> eventGrid = new Grid<>(Event.class);
    TextField filterByName = new TextField();
    TextField filterByCollege = new TextField();
    Span infoText =  new Span("Discover sports events hosted by different institutions. Click to view more details and expand your selection.");


    @Autowired
    EventService eventService;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    public ListView(EventService eventService,AuthenticationService authenticationService, EventRepository eventRepository) {
        this.eventService = eventService;
        this.authenticationService = authenticationService;
        this.eventRepository = eventRepository;
        addClassName("list-view");
        setSizeFull();
        infoText.addClassName("info-text");
        infoText.getStyle().set("color","#185396");
        
        configureGrid();
        add(
                getGreetingText(),
                getInfoArea(),
                eventGrid

        );
        
        updateList();
    }


    private Component getInfoArea() {
        filterByName.setPlaceholder("Filter by Event Name");
        filterByCollege.setPlaceholder("Filter By College");
        filterByName.getStyle().set("margin-left","auto");
        filterByName.setClearButtonVisible(true);
        filterByCollege.setClearButtonVisible(true);
        filterByName.setValueChangeMode(ValueChangeMode.EAGER);
        filterByCollege.setValueChangeMode(ValueChangeMode.EAGER);
        filterByName.addValueChangeListener(e -> updateList());
        filterByCollege.addValueChangeListener(e -> filterByCollege());
        HorizontalLayout header = new HorizontalLayout(infoText,filterByName,filterByCollege);
        header.getStyle().set("width","100%");
        header.setDefaultVerticalComponentAlignment(Alignment.END);
        return header;

    }

    private void updateList() {
        eventGrid.setItems(eventService.filterByName(filterByName.getValue()));
    }

    private Component getGreetingText() {
        H2 greetingText = null;
        if (authenticationService.getAuthenticatedUser() instanceof Student student){
            greetingText = new H2("Welcome, " + student.getFirstName() + ".");
        }
        if (authenticationService.getAuthenticatedUser() instanceof Institution institution){
            greetingText = new H2("Welcome, " + institution.getInstitutionName() + ".");
        }
        return greetingText;
    }

    private void filterByCollege() {
        eventGrid.setItems(eventService.filterByCollege(filterByCollege.getValue()));
    }

    private void configureGrid() {
        eventGrid.addClassName("event-grid");
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
        eventGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS,GridVariant.LUMO_ROW_STRIPES);
        eventGrid.setAllRowsVisible(true);
        eventGrid.getColumns().forEach(column -> column.setAutoWidth(true));
        eventGrid.asSingleSelect().addValueChangeListener(e -> {
            getUI().ifPresent(ui -> ui.navigate("event/" + e.getValue().getEventId()));
        });

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
