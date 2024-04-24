package com.project.sportsManagement.views;

import com.project.sportsManagement.entity.Institution;
import com.project.sportsManagement.entity.Student;
import com.project.sportsManagement.service.AuthenticationService;
import com.project.sportsManagement.service.UserService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "profile",layout = MainLayout.class)
@PermitAll
public class ProfileView extends VerticalLayout {
    private AuthenticationService authenticationService;
    private UserService userService;

    StudentProfileViewForm studentProfileViewForm;
    InstitutionProfileViewForm institutionProfileViewForm;

    @Autowired
    public ProfileView(AuthenticationService authenticationService,UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        if (authenticationService.getAuthenticatedUser() instanceof Student){
            // creating a student profile view form if the current user is a student.
            studentProfileViewForm = new StudentProfileViewForm(userService.getAllInstitution(),true,(Student)authenticationService.getAuthenticatedUser(),authenticationService);
            add(studentProfileViewForm);
        }
        if (authenticationService.getAuthenticatedUser() instanceof Institution){
            institutionProfileViewForm = new InstitutionProfileViewForm(true,(Institution) authenticationService.getAuthenticatedUser(),authenticationService);
            add(institutionProfileViewForm);

        }



    }


//    @Override
//    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
//        studentProfileViewForm.setReadOnlyMode(true);
//
//    }
}
