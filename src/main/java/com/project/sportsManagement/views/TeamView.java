package com.project.sportsManagement.views;

import com.project.sportsManagement.entity.Student;
import com.project.sportsManagement.entity.Team;
import com.project.sportsManagement.repo.InstitutionRepository;
import com.project.sportsManagement.repo.StudentRepository;
import com.project.sportsManagement.service.AuthenticationService;
import com.project.sportsManagement.service.UserService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "team",layout = MainLayout.class)
@PermitAll
public class TeamView extends VerticalLayout {

    H2 headerText = new H2("The teams you are currently in");
    Button createBtn = new Button("Create a team");
    Grid<Team> teamGrid = new Grid<>(Team.class);

    VerticalLayout content = new VerticalLayout();

    private AuthenticationService authenticationService;

    private InstitutionRepository institutionRepository;

    private StudentRepository studentRepository;

    private UserService userService;

    private EntityManager entityManager;

    public TeamView(@Autowired AuthenticationService authenticationService, @Autowired InstitutionRepository institutionRepository, @Autowired StudentRepository studentRepository, @Autowired UserService userService, @Autowired EntityManager entityManager) {
        this.authenticationService = authenticationService;
        this.institutionRepository = institutionRepository;
        this.studentRepository = studentRepository;
        this.userService = userService;
        this.entityManager = entityManager;
        configureTeamsGrid();
        content.add(headerText, teamGrid);
        content.setPadding(false);
        add(createBtn,content);
        createBtn.addClickListener(click -> {
            openTeamForm(new Team());
        });



    }



    private void configureTeamsGrid() {
        teamGrid.setColumns("teamId","teamName");
        teamGrid.addColumn(team -> team.getCreator().getFirstName() + "" + team.getCreator().getLastName()).setHeader("Created By");
        teamGrid.addColumn(team -> team.getTeamInstitution().getInstitutionName()).setHeader("Institution");
        teamGrid.setItems(userService.getAllTeamsOfAUser(getCurrentUser()));
        teamGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS,GridVariant.LUMO_ROW_STRIPES);
        teamGrid.setAllRowsVisible(true);
        teamGrid.asSingleSelect().addValueChangeListener(e -> {
            openTeamForm(e.getValue());
        });
    }

    private Student getCurrentUser() {
        return (Student)authenticationService.getAuthenticatedUser();
    }


    private void openTeamForm(Team team){
        createBtn.setVisible(false);
        content.setVisible(false);

        //back utton
        Button backButton = new Button("Back");
        backButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);


        //form
        TeamRegistrationForm teamRegistrationForm = new TeamRegistrationForm(team,institutionRepository,studentRepository,authenticationService,userService,entityManager);

        //grid
        H3 teamMembersHeaderText = new H3("Team Members");
        Grid<Student> teamMembersGrid = new Grid<>(Student.class);
        configureTeamMembersGrid(teamMembersGrid,team);


        //placing all in a vertical layout
        VerticalLayout teamFormLayout = new VerticalLayout(backButton,teamRegistrationForm,teamMembersHeaderText,teamMembersGrid);
        add(teamFormLayout);


        //adding listener for back button.
        backButton.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                remove(teamFormLayout);
                createBtn.setVisible(true);
                content.setVisible(true);
            }
        });


        teamRegistrationForm.setWidth("300px");
        teamRegistrationForm.cancel.addClickListener(clickEvent -> {
            remove(teamFormLayout);
            createBtn.setVisible(true);
            content.setVisible(true);
        });


    }

    private void configureTeamMembersGrid(Grid<Student> grid,Team team) {
        grid.setColumns("rollNo","email");
        grid.addColumn(student -> student.getFirstName() + " " + student.getLastName()).setHeader("Name");
        grid.addColumn(student -> student.getInstitution().getInstitutionName()).setHeader("Institution");
        grid.setAllRowsVisible(true);
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.setItems(team.teamMembers);
    }
}
