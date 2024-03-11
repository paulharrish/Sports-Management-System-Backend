package com.project.sportsManagement.views;

import com.project.sportsManagement.entity.Student;
import com.project.sportsManagement.service.AuthenticationService;
import com.project.sportsManagement.service.UserService;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "profile",layout = MainLayout.class)
@PermitAll
public class ProfileView extends VerticalLayout implements BeforeEnterObserver {
    private AuthenticationService authenticationService;
    private UserService userService;

    StudentProfileViewForm studentProfileViewForm;
    @Autowired
    public ProfileView(AuthenticationService authenticationService,UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;

        studentProfileViewForm = new StudentProfileViewForm(userService.getAllInstitution(),true);
        studentProfileViewForm.save.setEnabled(false);
        if (authenticationService.getAuthenticatedUser() instanceof Student){
            add(studentProfileViewForm);

        }


        studentProfileViewForm.binder.readBean(getCurrentUser());

        studentProfileViewForm.binder.setBean(getCurrentUser());

        studentProfileViewForm.save.addClickListener(click -> {
            if (studentProfileViewForm.binder.validate().isOk()) {
                authenticationService.modifyStudentDetails(getCurrentUser());
                add(new Span("Student Details Modified SuccessFully."));
                studentProfileViewForm.setReadOnlyMode(true);
                studentProfileViewForm.save.setEnabled(false);
            }
        });


    }

    private Student getCurrentUser(){
        return (Student)authenticationService.getAuthenticatedUser();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        studentProfileViewForm.setReadOnlyMode(true);

    }
}
