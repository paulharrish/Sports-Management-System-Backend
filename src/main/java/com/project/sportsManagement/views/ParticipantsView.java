package com.project.sportsManagement.views;

import com.project.sportsManagement.entity.Event;
import com.project.sportsManagement.entity.Participation;
import com.project.sportsManagement.service.EventService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "participants",layout = MainLayout.class)
@PermitAll
public class ParticipantsView extends VerticalLayout implements HasUrlParameter<Integer> {


    private EventService eventService;

    public ParticipantsView(@Autowired EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Integer eventId) {
        Event event = eventService.getEventById(eventId);

        H1 eventName = new H1("Participant details for "+event.getEventName()+":");
        add(eventName);

        H3 soloParticipants = new H3("Solo Participations:");
        H3 teamParticipants = new H3("Team Participations:");



        Grid<Participation> soloParticipationGrid = new Grid<>(Participation.class);
        soloParticipationGrid.setItems(eventService.getSoloParticipants(event));

        soloParticipationGrid.setColumns("participationId");
        soloParticipationGrid.addColumn(participation -> participation.getStudent().getRollNo()).setHeader("Roll no");
        soloParticipationGrid.addColumn(participation -> participation.getStudent().getEmail()).setHeader("Email");
        soloParticipationGrid.addColumn(participation -> participation.getStudent().getEmail()).setHeader("Email");
        soloParticipationGrid.addColumn(participation -> participation.getStudent().getInstitution().getInstitutionName()).setHeader("Institution");
        soloParticipationGrid.addColumn(participation -> participation.getGameCode().getGameId().getGame()).setHeader("Game");
        soloParticipationGrid.setAllRowsVisible(true);

        Grid<Participation> teamParticipationGrid = new Grid<>(Participation.class);
        teamParticipationGrid.setItems(eventService.getTeamParticipants(event));

        teamParticipationGrid.setColumns("participationId");
        teamParticipationGrid.addColumn(participation -> participation.getTeam().getTeamId()).setHeader("Team Id");
        teamParticipationGrid.addColumn(participation -> participation.getTeam().getTeamName()).setHeader("Team Name");
        teamParticipationGrid.addColumn(participation -> participation.getTeam().getTeamInstitution().getInstitutionName()).setHeader("Institution");
        teamParticipationGrid.addColumn(participation -> participation.getGameCode().getGameId().getGame()).setHeader("Game");
        teamParticipationGrid.setAllRowsVisible(true);


        add(soloParticipants,soloParticipationGrid,teamParticipants,teamParticipationGrid);
    }
}
