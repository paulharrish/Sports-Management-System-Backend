package com.project.sportsManagement.views;

import com.project.sportsManagement.entity.Institution;
import com.project.sportsManagement.entity.Student;
import com.project.sportsManagement.service.AuthenticationService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;

import java.util.List;

public class InstitutionProfileViewForm extends FormLayout {

    IntegerField institutionCode = new IntegerField("Your Institution Code");
    TextField institutionName = new TextField("Institution Name");
    TextField email = new TextField("Email");
    LocationField address = new LocationField();
    Button save = new Button("Save");
    Button  edit = new Button("Edit");
    Button cancel = new Button("Cancel");
    Binder<Institution> binder = new Binder<>(Institution.class);
    AuthenticationService authenticationService;

    Institution institution;

    public InstitutionProfileViewForm( boolean readOnly, Institution institution, AuthenticationService authenticationService) {
        setReadOnlyMode(readOnly);
        this.authenticationService =authenticationService;

        //Reading the field form the current user and populating it in the forms.
        this.institution =institution;
        binder.setBean(institution);


        HorizontalLayout buttonsLayout = new HorizontalLayout(save,edit,cancel);
        buttonsLayout.getStyle().set("margin-top","20px");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.setEnabled(false);
        edit.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        cancel.addThemeVariants(ButtonVariant.LUMO_ERROR);


        add(institutionCode,institutionName,email,address,buttonsLayout);

        binder.forField(institutionCode).asRequired("Institution Code cannot be empty.").bind(Institution::getInstitutionCode,Institution::setInstitutionCode);
        binder.forField(institutionName).asRequired("Institution name cannot be empty").bind(Institution::getInstitutionName,Institution::setInstitutionName);
        binder.forField(email).withValidator(new EmailValidator("Please enter a valid email",false)).bind(Institution::getEmail,Institution::setEmail);
        binder.forField(address).bind(Institution::getAddress,Institution::setAddress);

        setWidth("25%");



        edit.addClickListener(click -> {
            setReadOnlyMode(false);
            save.setEnabled(true);

        });
        cancel.addClickListener(click -> {
            setReadOnlyMode(true);
            save.setEnabled(false);
        });


        save.addClickListener(click -> {
            if (binder.validate().isOk()){
                authenticationService.modifyInstitutionDetails(this.institution);
                setReadOnlyMode(true);
                save.setEnabled(false);
            }

        });

    }


    public void setReadOnlyMode(boolean readOnly) {
        institutionCode.setReadOnly(readOnly);
        institutionName.setReadOnly(readOnly);
        email.setReadOnly(readOnly);
        address.setReadOnlyMode(readOnly);
    }

}
