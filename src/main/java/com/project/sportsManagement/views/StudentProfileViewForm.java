package com.project.sportsManagement.views;

import com.project.sportsManagement.entity.Institution;
import com.project.sportsManagement.entity.Student;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;

import java.util.List;

public class StudentProfileViewForm extends FormLayout {

    TextField firstName = new TextField("First Name");
    TextField lastName = new TextField("Last Name");
    TextField rollNo = new TextField("Roll No");
    TextField email = new TextField("Email");
    ComboBox<Institution> institution = new ComboBox<>("Institution");

    Button save = new Button("Save");
    Button  edit = new Button("Edit");

    Button cancel = new Button("Cancel");

    Binder<Student> binder = new Binder<>(Student.class);

    public StudentProfileViewForm(List<Institution> institutionsList ,boolean readOnly) {
       institution.setItems(institutionsList);
       institution.setItemLabelGenerator(Institution::getInstitutionName);

       binder.forField(firstName).asRequired("Name cannot be empty.").bind(Student::getFirstName,Student::setFirstName);
       binder.forField(lastName).asRequired("Last name cannot be empty").bind(Student::getLastName,Student::setLastName);
       binder.forField(rollNo).asRequired("Roll no cannot be empty").bind(Student::getRollNo,Student::setRollNo);
       binder.forField(email).withValidator(new EmailValidator("Please enter a valid email",false)).bind(Student::getEmail,Student::setEmail);
       binder.forField(institution).bind(Student::getInstitution,Student::setInstitution);

       setWidth("25%");

       setReadOnlyMode(readOnly);

       save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
       edit.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
       cancel.addThemeVariants(ButtonVariant.LUMO_ERROR);
       HorizontalLayout buttonsLayout = new HorizontalLayout(save,edit,cancel);
       buttonsLayout.getStyle().set("margin-top","20px");

        add(firstName,lastName,rollNo,email,institution,buttonsLayout);

        edit.addClickListener(click -> {
            setReadOnlyMode(false);
            save.setEnabled(true);

        });
        cancel.addClickListener(click -> {
            setReadOnlyMode(true);
            save.setEnabled(false);
        });

    }

    public void setReadOnlyMode(boolean readOnly) {
        firstName.setReadOnly(readOnly);
        lastName.setReadOnly(readOnly);
        rollNo.setReadOnly(readOnly);
        email.setReadOnly(readOnly);
        institution.setReadOnly(readOnly);
    }
}
