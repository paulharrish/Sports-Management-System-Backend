package com.project.sportsManagement.views;

import com.project.sportsManagement.entity.Student;
import com.project.sportsManagement.entity.Team;
import com.project.sportsManagement.repo.InstitutionRepository;
import com.project.sportsManagement.repo.StudentRepository;
import com.project.sportsManagement.service.AuthenticationService;
import com.project.sportsManagement.service.UserService;
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

    H2 teamsHeading = new H2("The teams you are currently in");
    Button create = new Button("Create a team");
    Grid<Team> teams = new Grid<>(Team.class);

    VerticalLayout content = new VerticalLayout();

    private AuthenticationService authenticationService;

    private InstitutionRepository institutionRepository;

    private StudentRepository studentRepository;

    private UserService userService;

    public TeamView(@Autowired AuthenticationService authenticationService,@Autowired InstitutionRepository institutionRepository, @Autowired StudentRepository studentRepository, @Autowired UserService userService) {
        this.authenticationService = authenticationService;
        this.institutionRepository = institutionRepository;
        this.studentRepository = studentRepository;
        this.userService = userService;
        configureTeamsGrid();
        content.add(teamsHeading,teams);
        add(create,content);

        create.addClickListener(click -> {
            remove(content);
            TeamRegistrationForm teamRegistrationForm = new TeamRegistrationForm(institutionRepository,studentRepository,authenticationService,userService);
            add(teamRegistrationForm);
            teamRegistrationForm.setWidth("300px");

        });

    }



    private void configureTeamsGrid() {
        teams.setColumns("teamId","teamName");
        teams.addColumn(team -> team.getCreator().getFirstName() + "" + team.getCreator().getLastName()).setHeader("Created By");
        teams.addColumn(team -> team.getTeamInstitution().getInstitutionName()).setHeader("Institution");
        teams.setItems(getCurrentUser().getTeams());
    }

    private Student getCurrentUser() {
        return (Student)authenticationService.getAuthenticatedUser();
    }
}
