package com.project.sportsManagement.views;

import com.project.sportsManagement.entity.Institution;
import com.project.sportsManagement.entity.Student;
import com.project.sportsManagement.repo.StudentRepository;
import com.project.sportsManagement.service.AuthenticationService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.dom.Style;

import java.util.List;
import java.util.Optional;

public class StudentRegistrationForm extends FormLayout {



    TextField firstName = new TextField("First Name*");
    TextField lastName = new TextField("Last Name*");
    TextField rollNo = new TextField("Roll no*");
    TextField email = new TextField("Email Id*");
    PasswordField password = new PasswordField("Password*");

    ComboBox<Institution> institution = new ComboBox<>("Institution");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<Student> binder = new Binder<>(Student.class);


    private final StudentRepository studentRepository;

    private final AuthenticationService authenticationService;

    private Student student;


    public StudentRegistrationForm(List<Institution> institutions, StudentRepository studentRepository,AuthenticationService authenticationService) {
        this.studentRepository = studentRepository;
        this.authenticationService = authenticationService;
        institution.setItems(institutions);
        institution.setItemLabelGenerator(Institution::getInstitutionName);
        this.student = new Student();


        binder.forField(firstName).asRequired("FirstName Cannot be empty").bind(Student::getFirstName,Student::setFirstName);
        binder.forField(lastName).asRequired("Last Name Cannot be empty").bind(Student::getLastName,Student::setLastName);
        binder.forField(rollNo).asRequired("Roll no cannot be empty").bind(Student::getRollNo,Student::setRollNo);
        binder.forField(email).withValidator(new EmailValidator("Please enter a valid email",false)).withValidator(validate -> isalreadyRegistered(email.getValue()),"This email is already registered").bind(Student::getEmail,Student::setEmail);
        binder.forField(password).asRequired("Password Cannot be Empty").bind(Student::getPassword,Student::setPassword);
        binder.bind(institution,Student::getInstitution,Student::setInstitution);
        add(firstName,lastName,rollNo,email,password,institution,getButtonsLayout());

        //to read and write the student bean automatically.
        binder.setBean(student);


        save.addClickListener(click ->{
            if (binder.validate().isOk()){
                authenticationService.registerStudent(student);
                binder.setBean(new Student());
                UI.getCurrent().navigate("login");
            }
        });

        delete.addClickListener(click -> {
            binder.setBean(new Student());
        });

        close.addClickListener(click -> {
           UI.getCurrent().navigate("login");
        });

    }

    private Boolean isalreadyRegistered(String email) {
        Optional<Student> studentOptional = studentRepository.findByEmail(email);
        if (studentOptional.isPresent()){
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
        buttonLayout.getStyle().set("margin-top","20px");
        buttonLayout.getStyle().setJustifyContent(Style.JustifyContent.SPACE_BETWEEN);
        return buttonLayout;
    }
}
