package com.project.sportsManagement.views;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;

public class InstitutionRegistrationForm extends FormLayout {

TextField textField = new TextField("Institution");

    public InstitutionRegistrationForm() {
        add(textField);
    }
}
