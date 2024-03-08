package com.project.sportsManagement.views;

import com.project.sportsManagement.entity.Institution;
import com.project.sportsManagement.entity.Student;
import com.project.sportsManagement.service.AuthenticationService;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.Lumo;
@CssImport(value = "style.css")
public class NavBarCreator {

    public NavBarCreator() {
    }

    public static HorizontalLayout getNavbar(AuthenticationService authenticationService, DrawerToggle toggle) {

        H1 title = new H1("Sports Management System");
        title.getStyle().set("font-size", "var(--lumo-font-size-m)").set("margin-right", "1137px");
        title.addClassName("title");
        toggle.getStyle().set("color","white");
        HorizontalLayout header = null;
        if (authenticationService.getAuthenticatedUser() != null) {
            Avatar userAvatar;
            Span greetingText;

            if (authenticationService.getAuthenticatedUser() instanceof Student) {
                Student student = (Student) authenticationService.getAuthenticatedUser();
                userAvatar = new Avatar(student.getFirstName() + " " + student.getLastName());
                userAvatar.addClassName("avatar");
                userAvatar.getStyle().set("margin-left", "175px");
                userAvatar.getStyle().set("background-color","#208070");
                userAvatar.getStyle().set("color","white");
                userAvatar.getStyle().set("border","0.2px #dadada solid");
                greetingText = new Span("Welcome " + student.getFirstName());
                greetingText.addClassName("greeting-text");
                header = new HorizontalLayout(toggle, title, userAvatar);
            } else if (authenticationService.getAuthenticatedUser() instanceof Institution) {
                Institution institution = (Institution) authenticationService.getAuthenticatedUser();
                userAvatar = new Avatar(institution.getInstitutionName());
                userAvatar.addClassName("avatar");
                userAvatar.getStyle().set("margin-left", "175px");
                userAvatar.getStyle().set("background-color","#208070");
                userAvatar.getStyle().set("color","white");
                userAvatar.getStyle().set("border","0.2px #dadada solid");
                greetingText = new Span("Welcome " + institution.getInstitutionName());
                greetingText.addClassName("greeting-text");
                header = new HorizontalLayout(toggle, title, userAvatar);
            }

        }
        else {
            header = new HorizontalLayout(toggle, title);
        }
        assert header != null;
        header.addClassName("header");
        header.setWidthFull();
        header.setHeight("65px");
        header.getThemeList().add(Lumo.DARK);
        return header;
    }
}
