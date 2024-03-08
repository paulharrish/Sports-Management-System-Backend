package com.project.sportsManagement.views;

import com.project.sportsManagement.service.AuthenticationService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import org.springframework.beans.factory.annotation.Autowired;


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
                new SideNavItem("Profile", "profile", VaadinIcon.USER.create()),
                new SideNavItem("My Events","/my-events",VaadinIcon.PLAY.create())

        );
        nav.getItems().forEach(sideNavItem -> sideNavItem.getStyle().set("margin","10px"));
        return nav;
    }

}
