package com.project.sportsManagement.views;

import com.project.sportsManagement.entity.Participation;
import com.project.sportsManagement.entity.Student;
import com.project.sportsManagement.repo.ParticipationRepository;
import com.project.sportsManagement.service.AuthenticationService;
import com.project.sportsManagement.service.EventService;
import com.project.sportsManagement.service.UserService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "my-events",layout = MainLayout.class)
@PermitAll
public class ParticipatedEventView extends VerticalLayout implements BeforeEnterObserver {

    Span headerText = new Span("Find the list of events you have participated below.");
    Span soloParticipationHeaderText = new Span("The events you have participated as Solo:");
    Span teamParticipationHeaderText = new Span("The events you have participated as a Team");

    Grid<Participation> soloParticipationGrid = new Grid<>(Participation.class);
    Grid<Participation> teamParticipationGrid = new Grid<>(Participation.class);

    H2 noParticipatedEvents = new H2("Sorry, You have currently not Participated in any events.");


    EventService eventService;

    UserService userService;


    ParticipationRepository participationRepository;


    AuthenticationService authenticationService;

    @Autowired
    public ParticipatedEventView(AuthenticationService authenticationService, EventService eventService,ParticipationRepository participationRepository,UserService userService) {
        this.authenticationService = authenticationService;
        this.eventService = eventService;
        this.participationRepository = participationRepository;
        this.userService = userService;
        if (!participationRepository.findAllByStudent(getCurrentUser().getStudentId()).isEmpty()){
            add(headerText);
            if (!userService.getSoloParticipationsOfAStudent(getCurrentUser().getStudentId()).isEmpty()){
                configureSoloGrid();
                add(soloParticipationHeaderText,soloParticipationGrid);
            }
            if (!userService.getTeamParticipationsOfAStudent(getCurrentUser().getStudentId()).isEmpty()){
                configureTeamGrid();
                add(teamParticipationHeaderText,teamParticipationGrid);
            }


        }else {
            add(headerText,noParticipatedEvents);
        }

    }

    private void configureTeamGrid() {
        teamParticipationGrid.setColumns("participationId");
        teamParticipationGrid.addColumn(participation -> participation.getTeam().getTeamName()).setHeader("Team Name");
        teamParticipationGrid.addColumn(participation -> participation.getGameCode().getEventId().getEventName()).setHeader("Event Name");
        teamParticipationGrid.addColumn(participation -> participation.getGameCode().getGameId().getGame()).setHeader("Game");
        teamParticipationGrid.addColumn(participation -> participation.getGameCode().getEventId().getHost().getInstitutionName()).setHeader("Host");
        teamParticipationGrid.addColumn(participation -> participation.getGameCode().getEventId().getLevel().getLevel()).setHeader("Event level");
        teamParticipationGrid.getColumns().forEach(column -> column.setAutoWidth(true));
        teamParticipationGrid.setAllRowsVisible(true);
        teamParticipationGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS,GridVariant.LUMO_ROW_STRIPES);
        teamParticipationGrid.setItems(userService.getTeamParticipationsOfAStudent(getCurrentUser().getStudentId()));
    }


    private void configureSoloGrid() {
        soloParticipationGrid.setColumns("participationId");
        soloParticipationGrid.addColumn(participation -> participation.getGameCode().getEventId().getEventName()).setHeader("Event Name");
        soloParticipationGrid.addColumn(participation -> participation.getGameCode().getGameId().getGame()).setHeader("Game");
        soloParticipationGrid.addColumn(participation -> participation.getGameCode().getEventId().getHost().getInstitutionName()).setHeader("Host");
        soloParticipationGrid.addColumn(participation -> participation.getGameCode().getEventId().getLevel().getLevel()).setHeader("Event level");
        soloParticipationGrid.getColumns().forEach(column -> column.setAutoWidth(true));
        soloParticipationGrid.setAllRowsVisible(true);
        soloParticipationGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS,GridVariant.LUMO_ROW_STRIPES);
        soloParticipationGrid.setItems(userService.getSoloParticipationsOfAStudent(getCurrentUser().getStudentId()));
    }

    private Student getCurrentUser() {
        return (Student)authenticationService.getAuthenticatedUser();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        configureSoloGrid();
    }
}
