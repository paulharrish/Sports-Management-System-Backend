package com.project.sportsManagement.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "profile",layout = MainLayout.class)
@PermitAll
public class ProfileView extends VerticalLayout {
    public ProfileView() {
    }
}
