package com.project.sportsManagement.dto;

import com.project.sportsManagement.entity.Institution;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class StudentRegistrationDto {
    private String firstName;
    @NotEmpty(message = "lastName cannot be Empty")
    private String lastName;
    @NotNull(message = "Roll number cannot be empty")
    private String rollNo;
    @NotEmpty(message = "Email Cannot be Empty")
    @Email(message = "Invalid Email")
    private String email;
    @NotNull(message = "Institution code cannot be empty")
    private Institution institution;
    @NotEmpty(message = "Password Cannot be Empty")
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public StudentRegistrationDto() {
        super();
    }

    public StudentRegistrationDto(String firstName, String lastName, String rollNo,Institution institution, String email,String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.rollNo = rollNo;
        this.email = email;
        this.institution = institution;
        this.password = password;
    }
}
