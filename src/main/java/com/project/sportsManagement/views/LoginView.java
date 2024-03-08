package com.project.sportsManagement.views;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@AnonymousAllowed
@CssImport(value = "style.css")
public class LoginView extends Div implements BeforeEnterObserver {
    LoginI18n login = LoginI18n.createDefault();
    LoginForm loginForm = new LoginForm();
    Span signUpText1 = new Span("Don't have an account? ");
    Anchor signUpLink = new Anchor("","Create an account here.");
    Span signUpText2 = new Span(signUpText1,signUpLink);
    VerticalLayout vl = new VerticalLayout();


    public LoginView() {
        addClassName("login-view");
        setSizeFull();
        getStyle().set("display","flex");
        getStyle().setAlignItems(Style.AlignItems.CENTER);
        getStyle().setJustifyContent(Style.JustifyContent.CENTER);
        configureLoginForm();
        signUpText2.addClassName("signup-link");
        vl.add(loginForm,signUpText2);

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
