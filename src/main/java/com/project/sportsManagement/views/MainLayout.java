package com.project.sportsManagement.views;

import com.project.sportsManagement.entity.Institution;
import com.project.sportsManagement.entity.Student;
import com.project.sportsManagement.service.AuthenticationService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

@AnonymousAllowed
public class MainLayout extends AppLayout {

    private AuthenticationService authenticationService;
    DrawerToggle toggle  = new DrawerToggle();

    @Autowired
    public MainLayout(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
        toggle.addClassName("drawer-toggle");
        HorizontalLayout header = NavBarCreator.getNavbar(authenticationService,toggle);
        addToNavbar(header);
        SideNav nav = getSideNav();
        Scroller navBar = new Scroller(nav);
        addToDrawer(navBar);
    }
    private SideNav getSideNav() {
        SideNav nav = new SideNav();
        nav.addItem(
                new SideNavItem("Home","",VaadinIcon.HOME.create()),
                new SideNavItem("Profile", "profile", VaadinIcon.USER.create())
        );
        if (authenticationService.getAuthenticatedUser() instanceof Student){
            nav.addItem(new SideNavItem("Team","team",VaadinIcon.GROUP.create()));
            nav.addItem(new SideNavItem("Participation Details","my-events",VaadinIcon.PLAY.create()));
        }

        if (authenticationService.getAuthenticatedUser() instanceof Institution){
            nav.addItem(new SideNavItem("Create","create",VaadinIcon.PENCIL.create()));

        }

        nav.getItems().forEach(sideNavItem -> sideNavItem.getStyle().set("margin","10px"));



        return nav;
    }

}
