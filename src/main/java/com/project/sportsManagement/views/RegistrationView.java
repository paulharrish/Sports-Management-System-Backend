package com.project.sportsManagement.views;

import com.project.sportsManagement.entity.Student;
import com.project.sportsManagement.repo.StudentRepository;
import com.project.sportsManagement.service.AuthenticationService;
import com.project.sportsManagement.service.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "register",layout = MainLayout.class)
@AnonymousAllowed
public class RegistrationView extends VerticalLayout {


    private final UserService userService;

    private final AuthenticationService authenticationService;

    private final StudentRepository studentRepository;
    Button registerButton = new Button("Click here");
    Span registerTextInstitution = new Span("Want to Register Your Institution,");
    Span enterDetailsText = new Span("Enter Your Details Here.");

    HorizontalLayout  header = new HorizontalLayout(registerTextInstitution,registerButton);
    private Student student;




    @Autowired
    public RegistrationView(UserService userService, AuthenticationService authenticationService, StudentRepository studentRepository) {
        setAlignItems(Alignment.CENTER);
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.studentRepository = studentRepository;
        this.student= new Student();
        StudentRegistrationForm studentRegistrationForm = new StudentRegistrationForm(userService.getAllInstitution(),studentRepository);
        InstitutionRegistrationForm institutionRegistrationForm = new InstitutionRegistrationForm();
        header.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(header,enterDetailsText,studentRegistrationForm);
        addClassName("registration-view");
        setSizeFull();

        registerButton.addClickListener(click -> {
            remove(header,studentRegistrationForm);
            add(institutionRegistrationForm);


        });


        studentRegistrationForm.binder.setBean(student);

        studentRegistrationForm.save.addClickListener(click ->{
            if (studentRegistrationForm.binder.validate().isOk()){
                authenticationService.registerStudent(student);
                studentRegistrationForm.binder.setBean(new Student());
                add(new Span("User registered Succesfully."));
                UI.getCurrent().navigate("login");
            }
        });

        studentRegistrationForm.delete.addClickListener(click -> {
            studentRegistrationForm.binder.setBean(new Student());
        });

    }
}
