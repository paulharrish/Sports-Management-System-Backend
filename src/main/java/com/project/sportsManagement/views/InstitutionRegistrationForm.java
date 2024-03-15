package com.project.sportsManagement.views;

import com.project.sportsManagement.entity.Location;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

public class InstitutionRegistrationForm extends FormLayout {

IntegerField institutionCode = new IntegerField("Institution code");
TextField institutionName = new TextField("Institution name");
EmailField email = new EmailField("Email Id");

PasswordField password = new PasswordField("Password");

LocationField address = new LocationField();


    public InstitutionRegistrationForm() {
        setWidth("30%");
        add(institutionCode,institutionName,email,password,address);
    }
}
