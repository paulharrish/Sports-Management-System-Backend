package com.project.sportsManagement.dto;

import com.project.sportsManagement.entity.Institution;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class StudentRegistrationDto {
    @NotEmpty(message = "firstName cannot Be Empty")
    private String firstName;
    @NotEmpty(message = "lastName cannot be Empty")
    private String lastName;
    @NotNull(message = "Roll number cannot be empty")
    private Long rollNo;
    @NotEmpty(message = "Email Cannot be Empty")
    @Email(message = "Invalid Email")
    private String email;
    @NotNull(message = "Institution code cannot be empty")
    private int institutionCode;
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

    public Long getRollNo() {
        return rollNo;
    }

    public void setRollNo(Long rollNo) {
        this.rollNo = rollNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getInstitutionCode() {
        return institutionCode;
    }

    public void setInstitutionCode(int institutionCode) {
        this.institutionCode = institutionCode;
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

    public StudentRegistrationDto(String firstName, String lastName, Long rollNo, String email, int institutionCode, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.rollNo = rollNo;
        this.email = email;
        this.institutionCode = institutionCode;
        this.password = password;
    }
}
