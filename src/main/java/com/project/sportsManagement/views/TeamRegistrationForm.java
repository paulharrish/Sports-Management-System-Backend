package com.project.sportsManagement.views;

import com.project.sportsManagement.entity.Institution;
import com.project.sportsManagement.entity.Student;
import com.project.sportsManagement.entity.Team;
import com.project.sportsManagement.repo.InstitutionRepository;
import com.project.sportsManagement.repo.StudentRepository;
import com.project.sportsManagement.repo.TeamRepository;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import java.util.Collection;

public class TeamRegistrationForm extends FormLayout {


    private TextField teamName = new TextField("Team name");

    private ComboBox<Institution> teamInstitution = new ComboBox<>("Select your Institution");

    private MultiSelectComboBox<Student> teamMembers = new MultiSelectComboBox<>("Add members");

    Button create = new Button("Create");
    Button delete = new Button("Create");
    Button cancel = new Button("Create");


    Binder<Team> binder = new Binder<>();

    private Team team;


    public TeamRegistrationForm(InstitutionRepository institutionRepository, StudentRepository studentRepository) {
        this.team = new Team();
        teamInstitution.setItems(institutionRepository.findAll());
        teamInstitution.setItemLabelGenerator(Institution::getInstitutionName);

        teamMembers.setItems(studentRepository.findAll());
        teamMembers.setItemLabelGenerator(Student::getFirstName);


        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(create,delete,cancel);

        binder.forField(teamName).asRequired("Team name cannot be Empty").bind(Team::getTeamName,Team::setTeamName);
        binder.forField(teamInstitution).bind(Team::getTeamInstitution,Team::setTeamInstitution);
        binder.forField(teamMembers).bind(Team::getTeamMembers,Team::setTeamMembers);

        binder.setBean(team);

        add(teamName,teamInstitution,teamMembers,buttonLayout);

    }
}
