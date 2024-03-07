package com.project.sportsManagement.views;

import com.project.sportsManagement.entity.Institution;
import com.project.sportsManagement.entity.Student;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

@Route("")
@PermitAll
public class HomeView extends AppLayout {


    private AuthenticationContext authenticationContext;
    @Autowired
    public HomeView(AuthenticationContext authenticationContext) {
        this.authenticationContext = authenticationContext;
        DrawerToggle toggle  = new DrawerToggle();

        H1 title = new H1("Sports Management System");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)").set("margin", "0");
        VaadinSession currentSession = VaadinSession.getCurrent();
        Span greetingText = new Span(authenticationContext.getPrincipalName().get());
        greetingText.getElement().getThemeList().add("badge contrast pill");



        SideNav nav = getSideNav();

        addToDrawer(nav);
        addToNavbar(toggle,title,greetingText);

    }

    private Span getGreetingMessage(VaadinSession session) {
        Span greetingText;
        if (session.getAttribute("user") instanceof Student){
            String name = ((Student) session.getAttribute("user")).getFirstName();
            greetingText = new Span("Hii "+name);
            return greetingText;
        }
        if (session.getAttribute("user") instanceof Institution){
            String name = ((Institution)session.getAttribute("user")).getInstitutionName();
            greetingText = new Span("Hii "+ name);
            return greetingText;
        }
        return null;
    }

    private SideNav getSideNav() {
        SideNav nav = new SideNav();
        nav.addItem(
                new SideNavItem("Profile", "/profile", VaadinIcon.USER.create()),
                new SideNavItem("My events","/my-events",VaadinIcon.PLAY.create())
        );
        return nav;
    }
}
