package com.project.sportsManagement.views;

import com.project.sportsManagement.entity.Institution;
import com.project.sportsManagement.entity.Student;
import com.project.sportsManagement.service.AuthenticationService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@CssImport(value = "styles.css")
public class NavBarCreator {



    public NavBarCreator() {
    }

    public static HorizontalLayout getNavbar(AuthenticationService authenticationService, DrawerToggle toggle) {

        H1 title = new H1("Sports Management System");
        title.getStyle().set("font-size", "var(--lumo-font-size-xl)");
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
                userAvatar.getStyle().set("margin-left", "auto");
                userAvatar.getStyle().set("margin-right","20px");
                userAvatar.getStyle().set("background-color","#208070");
                userAvatar.getStyle().set("color","white");
                userAvatar.getStyle().set("border","0.2px #dadada solid");
                ContextMenu contextMenu = new ContextMenu(userAvatar);
                contextMenu.addItem("logout",click ->{
                    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
                    logoutHandler.logout(VaadinServletRequest.getCurrent().getHttpServletRequest(),null,null);

                    //Closing the Vaadin Session explicitly.
                    VaadinSession vaadinSession = VaadinSession.getCurrent();
                    vaadinSession.close();
                });
                contextMenu.addItem(new RouterLink("Manage Profile", ProfileView.class));

                greetingText = new Span("Welcome " + student.getFirstName());
                greetingText.addClassName("greeting-text");
                header = new HorizontalLayout(toggle, title, userAvatar);
            } else if (authenticationService.getAuthenticatedUser() instanceof Institution) {
                Institution institution = (Institution) authenticationService.getAuthenticatedUser();
                userAvatar = new Avatar(institution.getInstitutionName());
                userAvatar.addClassName("avatar");
                userAvatar.getStyle().set("margin-left", "auto");
                userAvatar.getStyle().set("margin-right","20px");
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
