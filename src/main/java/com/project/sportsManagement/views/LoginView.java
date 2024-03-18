package com.project.sportsManagement.views;

import com.project.sportsManagement.service.AuthenticationService;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

@Route("login")
@AnonymousAllowed
@CssImport(value = "styles.css")
public class LoginView extends Div implements BeforeEnterObserver {

    @Autowired
    private AuthenticationService authenticationService;
    LoginI18n login = LoginI18n.createDefault();
    LoginForm loginForm = new LoginForm();
    VerticalLayout vl = new VerticalLayout();


    public LoginView(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
        addClassName("login-view");
        setSizeFull();
        getStyle().set("display","flex");
        getStyle().setAlignItems(Style.AlignItems.CENTER);
        getStyle().setJustifyContent(Style.JustifyContent.CENTER);
        configureLoginForm();
        vl.add(loginForm,new RouterLink("Don't have an account? Register here.", RegistrationView.class));
        add(vl);


        vl.addClassName("login-layout");
    }

    private void configureLoginForm() {
        LoginI18n.Form form = login.getForm();
        form.setUsername("Email");
        form.setTitle("Login");
        login.setForm(form);
        loginForm.setI18n(login);
        loginForm.setAction("login");



    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (beforeEnterEvent.getLocation().getQueryParameters().getParameters().containsKey("error")){
            loginForm.setError(true);
        }
    }
}
