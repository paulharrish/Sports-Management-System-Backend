package com.project.sportsManagement.views;

import com.project.sportsManagement.entity.Student;
import com.project.sportsManagement.repo.InstitutionRepository;
import com.project.sportsManagement.repo.StudentRepository;
import com.project.sportsManagement.service.AuthenticationService;
import com.project.sportsManagement.service.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "register")
@AnonymousAllowed
public class RegistrationView extends VerticalLayout {


    private final UserService userService;

    private final AuthenticationService authenticationService;

    private final StudentRepository studentRepository;

    private final InstitutionRepository institutionRepository;
    Button registerButton = new Button("Click here");
    Span registerTextInstitution = new Span("Want to Register Your Institution,");
    Span enterDetailsText = new Span("Enter Your Details Here.");

    HorizontalLayout  header = new HorizontalLayout(registerTextInstitution,registerButton);




    @Autowired
    public RegistrationView(UserService userService, AuthenticationService authenticationService, StudentRepository studentRepository, InstitutionRepository institutionRepository) {
        setAlignItems(Alignment.CENTER);
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.studentRepository = studentRepository;
        this.institutionRepository = institutionRepository;
        StudentRegistrationForm studentRegistrationForm = new StudentRegistrationForm(userService.getAllInstitution(),studentRepository,authenticationService);
        studentRegistrationForm.setWidth("300px");
        studentRegistrationForm.getStyle().setAlignSelf(Style.AlignSelf.CENTER);
        InstitutionRegistrationForm institutionRegistrationForm = new InstitutionRegistrationForm(authenticationService,institutionRepository);
        institutionRegistrationForm.setWidth("300px");
        institutionRegistrationForm.getStyle().setAlignSelf(Style.AlignSelf.CENTER);
        header.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(header,enterDetailsText,studentRegistrationForm);
        setSizeFull();

        registerButton.addClickListener(click -> {
            remove(header,studentRegistrationForm);
            add(institutionRegistrationForm);

        });

    }
}
