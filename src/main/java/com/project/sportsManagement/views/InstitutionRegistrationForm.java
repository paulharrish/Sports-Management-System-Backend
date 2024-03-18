package com.project.sportsManagement.views;

import com.project.sportsManagement.entity.Location;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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

Button save = new Button("Save");
Button delete = new Button("Delete");
Button close = new Button("Cancel");


    public InstitutionRegistrationForm() {
        setWidth("30%");
        add(institutionCode,institutionName,email,password,address,getButtonsLayout());
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
