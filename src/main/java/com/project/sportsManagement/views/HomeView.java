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
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.core.context.SecurityContextHolder;

@Route("")
@PermitAll
public class HomeView extends AppLayout implements BeforeEnterObserver {


    public HomeView() {
        DrawerToggle toggle  = new DrawerToggle();

        H1 title = new H1("Sports Management System");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)").set("margin", "0");
        Span greetingText = getCurrentUserGreetingMessage();
        greetingText.getElement().getThemeList().add("badge contrast pill");



        SideNav nav = getSideNav();

        addToDrawer(nav);
        addToNavbar(toggle,title,greetingText);

    }

    private Span getCurrentUserGreetingMessage(){
       Span greetingText;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof Student){
            Student student = (Student)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            greetingText = new Span("Welcome, " + student.getFirstName());
            return greetingText;
        }
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof Institution ) {
            Institution institution = (Institution) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            greetingText = new Span("Welcome, "+ institution.getInstitutionName());
            return greetingText;
        }
        else {
            greetingText = new Span("Welcome Guest");
            return greetingText;
        }
    }



    private SideNav getSideNav() {
        SideNav nav = new SideNav();
        nav.addItem(
                new SideNavItem("Profile", "/profile", VaadinIcon.USER.create()),
                new SideNavItem("My events","/my-events",VaadinIcon.PLAY.create())
        );
        return nav;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (SecurityContextHolder.getContext().getAuthentication() == null || !SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
            event.rerouteTo("login");
        }
    }
}
