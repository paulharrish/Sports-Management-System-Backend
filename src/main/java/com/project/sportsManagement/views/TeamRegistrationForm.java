package com.project.sportsManagement.views;

import com.project.sportsManagement.entity.Institution;
import com.project.sportsManagement.entity.Student;
import com.project.sportsManagement.entity.Team;
import com.project.sportsManagement.repo.InstitutionRepository;
import com.project.sportsManagement.repo.StudentRepository;
import com.project.sportsManagement.repo.TeamRepository;
import com.project.sportsManagement.service.AuthenticationService;
import com.project.sportsManagement.service.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.ComponentRenderer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class TeamRegistrationForm extends FormLayout {


    private TextField teamName = new TextField("Team name");

    private ComboBox<Institution> teamInstitution = new ComboBox<>("Select your Institution");

    private MultiSelectComboBox<Student> teamMembers = new MultiSelectComboBox<>("Add members");

    Button create = new Button("Create");
    Button delete = new Button("delete");
    Button cancel = new Button("cancel");


    Binder<Team> binder = new Binder<>();

    private Team team;


    public TeamRegistrationForm(InstitutionRepository institutionRepository, StudentRepository studentRepository , AuthenticationService auth, UserService userService) {
        this.team = new Team();


        Student currentStudent = (Student) auth.getAuthenticatedUser();
        teamInstitution.setItems(currentStudent.getInstitution());
        teamInstitution.setItemLabelGenerator(Institution::getInstitutionName);

        Set<Student> students = currentStudent.getInstitution().getStudents();
        Set<Student> teamMembersList = new HashSet<>();
        for (Student student: students){
            if (currentStudent.equals(student)) {
                continue;
            }
            teamMembersList.add(student);
        }

        teamMembers.setItems(teamMembersList);
        teamMembers.setItemLabelGenerator(student -> student.getFirstName()+ " "+student.getLastName());


        HorizontalLayout buttonLayout = new HorizontalLayout();
        create.addThemeVariants(ButtonVariant.LUMO_SUCCESS,ButtonVariant.LUMO_PRIMARY);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        buttonLayout.add(create,delete,cancel);
        buttonLayout.getStyle().setMarginTop("15px");

        binder.forField(teamName).asRequired("Team name cannot be Empty").bind(Team::getTeamName,Team::setTeamName);
        binder.forField(teamInstitution).bind(Team::getTeamInstitution,Team::setTeamInstitution);


        binder.setBean(team);

        create.addClickListener(clickEvent -> {
            if (binder.isValid()){
                userService.createATeam(team,currentStudent,teamMembers.getValue());
            }
        });

        add(teamName,teamInstitution,teamMembers,buttonLayout);

    }
}
