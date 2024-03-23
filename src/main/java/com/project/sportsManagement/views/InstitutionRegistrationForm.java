package com.project.sportsManagement.views;

import com.project.sportsManagement.entity.Institution;
import com.project.sportsManagement.entity.Location;
import com.project.sportsManagement.repo.InstitutionRepository;
import com.project.sportsManagement.service.AuthenticationService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;

import java.util.Optional;

public class InstitutionRegistrationForm extends FormLayout {

IntegerField institutionCode = new IntegerField("Institution code");
TextField institutionName = new TextField("Institution name");
EmailField email = new EmailField("Email Id");

PasswordField password = new PasswordField("Password");

LocationField address = new LocationField();

Button save = new Button("Save");
Button delete = new Button("Delete");
Button close = new Button("Cancel");

Binder<Institution> binder = new Binder<>(Institution.class);

private final Institution institution;

private final InstitutionRepository institutionRepository;

private final AuthenticationService authenticationService;


    public InstitutionRegistrationForm(AuthenticationService authenticationService, InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
        this.authenticationService = authenticationService;
        this.institution = new Institution();


        binder.forField(institutionCode).asRequired("Institution Code Cannot be empty").withValidator(validate -> isalreadyRegisteredCode(institutionCode.getValue()),"This code has been already registered.").bind(Institution::getInstitutionCode,Institution::setInstitutionCode);
        binder.forField(institutionName).asRequired("Institution Name cannot be empty").bind(Institution::getInstitutionName,Institution::setInstitutionName);
        binder.forField(email).withValidator(new EmailValidator("Enter a valid email address")).withValidator(validate -> isalreadyRegisteredEmail(email.getValue()),"This email is already registered.").bind(Institution::getEmail,Institution::setEmail);
        binder.forField(password).asRequired("Password cannot be empty").bind(Institution::getPassword,Institution::setPassword);
        binder.forField(address).bind(Institution::getAddress,Institution::setAddress);
        add(institutionCode,institutionName,email,password,address,getButtonsLayout());

        //to read and write values automatically.
        binder.setBean(institution);

        save.addClickListener(click ->{
            if (binder.validate().isOk()){
                authenticationService.registerInstitution(institution);
                UI.getCurrent().navigate("login");
            }
        });

        delete.addClickListener(click -> {
            institutionCode.clear();
            institutionName.clear();
            email.clear();
            password.clear();
            address.setPresentationValue(new Location("","",""));
        });

        close.addClickListener(click -> {
            UI.getCurrent().navigate("login");
        });
    }


    private Boolean isalreadyRegisteredEmail(String email) {
        Optional<Institution> institutionOptional = institutionRepository.findByEmail(email);
        if (institutionOptional.isPresent()){
            return false;
        }
        return true;
    }

    private Boolean isalreadyRegisteredCode(Integer institutionCode) {
        Optional<Institution> institutionOptional = institutionRepository.findByInstitutionCode(institutionCode);
        if (institutionOptional.isPresent()){
            return false;
        }
        return true;
    }

    private HorizontalLayout getButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        HorizontalLayout buttonLayout =  new HorizontalLayout(save, delete, close);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        buttonLayout.getStyle().set("margin-top","20px");
        buttonLayout.getStyle().set("width","60%");
        return buttonLayout;
    }
}
