package com.project.sportsManagement.views;

import com.project.sportsManagement.dto.LoginDto;
import com.project.sportsManagement.dto.LoginResponse;
import com.project.sportsManagement.exception.UserNotFoundException;
import com.project.sportsManagement.service.AuthenticationService;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

@Route("login")
public class LoginView extends Div {
    LoginI18n login = LoginI18n.createDefault();
    LoginForm loginForm = new LoginForm();

    Binder<LoginDto> binder = new BeanValidationBinder<>(LoginDto.class);
    @Autowired
    private AuthenticationService authenticationService;




    public LoginView(AuthenticationService authenticationService) {

        this.authenticationService = authenticationService;
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
        form.setTitle("Login To continue");
        login.setForm(form);
        loginForm.setI18n(login);
        loginForm.addLoginListener(loginEvent -> {
            LoginDto loginDto = new LoginDto();
            loginDto.setEmail(loginEvent.getUsername());
            loginDto.setPassword(loginEvent.getPassword());

            try{
                LoginResponse response = authenticationService.login(loginDto);
                VaadinSession session = VaadinSession.getCurrent();
                session.setAttribute("jwt",response.getJwt());
                session.setAttribute("user",response.getUser());
                getUI().ifPresent(ui -> ui.navigate("home"));
                loginForm.setEnabled(true);



            } catch (UserNotFoundException e) {
               loginForm.setError(true);
            }

        });

    }
}
