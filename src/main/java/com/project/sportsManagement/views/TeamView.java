package com.project.sportsManagement.views;

import com.project.sportsManagement.entity.Student;
import com.project.sportsManagement.entity.Team;
import com.project.sportsManagement.service.AuthenticationService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;

@Route(value = "team",layout = MainLayout.class)
@PermitAll
public class TeamView extends VerticalLayout {

    H2 headerText = new H2("Currently, You don't have any teams.");

    H2 createdteamsHeading = new H2("The teams you have created");

    H2 teamsHeading = new H2("The teams you are currently in");
    Button create = new Button("Create a team");

    Grid<Team> createdTeams = new Grid<>(Team.class);
    Grid<Team> teams = new Grid<>(Team.class);

    private AuthenticationService authenticationService;

    public TeamView(@Autowired AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;

        create.addClickListener(click -> {

        });

        configureTeamsGrid();
        configureCreatedTeamsGrid();
        add(create,teamsHeading,teams,createdteamsHeading,createdTeams);

    }

    private void configureCreatedTeamsGrid() {
    }

    private void configureTeamsGrid() {
    }

    private Student getCurrentUser() {
        return (Student)authenticationService.getAuthenticatedUser();
    }
}
