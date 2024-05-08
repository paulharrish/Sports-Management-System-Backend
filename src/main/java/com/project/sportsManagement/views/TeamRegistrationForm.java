package com.project.sportsManagement.views;

import com.project.sportsManagement.entity.Institution;
import com.project.sportsManagement.entity.Student;
import com.project.sportsManagement.entity.Team;
import com.project.sportsManagement.repo.InstitutionRepository;
import com.project.sportsManagement.repo.StudentRepository;
import com.project.sportsManagement.service.AuthenticationService;
import com.project.sportsManagement.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

public class TeamRegistrationForm extends FormLayout {


    private TextField teamName = new TextField("Team name");

    private ComboBox<Institution> teamInstitution = new ComboBox<>("Select your Institution");

    private MultiSelectComboBox<Student> teamMembers = new MultiSelectComboBox<>("Add members");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");


    Binder<Team> binder = new Binder<>();

    private Team team;


    public TeamRegistrationForm(Team team, InstitutionRepository institutionRepository, StudentRepository studentRepository , AuthenticationService auth, UserService userService, EntityManager entityManager) {
        this.team = team;


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
        save.addThemeVariants(ButtonVariant.LUMO_SUCCESS,ButtonVariant.LUMO_PRIMARY);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        buttonLayout.add(save,delete,cancel);
        buttonLayout.getStyle().setMarginTop("15px");

        binder.forField(teamName).asRequired("Team name cannot be Empty").bind(Team::getTeamName,Team::setTeamName);
        binder.forField(teamInstitution).bind(Team::getTeamInstitution,Team::setTeamInstitution);


        binder.setBean(team);

        save.addClickListener(clickEvent -> {
            if (binder.isValid()){
                try {
                    userService.createATeam(team,currentStudent,teamMembers.getValue());
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                this.getParent().get().getElement().getChild(0).setVisible(true);
                this.getParent().get().getElement().getChild(1).setVisible(true);
                removeFromParent();
                binder.setBean(new Team());

            }
        });

        delete.addClickListener(clickEvent -> {
            binder.setBean(new Team());
        });


        add(teamName,teamInstitution,teamMembers,buttonLayout);

    }

}
