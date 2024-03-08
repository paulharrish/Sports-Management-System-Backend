package com.project.sportsManagement.views;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

@Route("login")
@AnonymousAllowed
@CssImport(value = "style.css")
public class LoginView extends Div {
    LoginI18n login = LoginI18n.createDefault();
    LoginForm loginForm = new LoginForm();

    @Autowired
    private final AuthenticationManager authenticationManager;




    public LoginView(AuthenticationManager authenticationManager, AuthenticationManager authenticationManager1) {
        this.authenticationManager = authenticationManager1;
        addClassName("login-page-div");
        setSizeFull();
        getStyle().set("display","flex");
        getStyle().setAlignItems(Style.AlignItems.CENTER);
        getStyle().setJustifyContent(Style.JustifyContent.CENTER);
        configureLoginForm();
        add(loginForm);
    }

    private void configureLoginForm() {
        LoginI18n.Form form = login.getForm();
        form.setUsername("Email");
        form.setTitle("Login");
        login.setForm(form);
        loginForm.setI18n(login);
        loginForm.setAction("login");
    }
}
