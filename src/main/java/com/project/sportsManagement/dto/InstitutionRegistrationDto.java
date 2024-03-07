package com.project.sportsManagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.userdetails.UserDetails;

public class InstitutionRegistrationDto {
    @NotEmpty(message = "Institution code cannot be empty")
    private int institutionCode;
    private String institutionName;
    @NotEmpty(message = "Email cannot be empty")
    @Email
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    private String password;

    @NotEmpty(message = "Institution address cannot be empty")
    private String institutionAddress;
    @NotEmpty(message = "District cannot be empty")
    private String district;
    @NotEmpty(message = "State cannot be empty")
    private String state;

    public InstitutionRegistrationDto() {
    }

    public InstitutionRegistrationDto(int institutionCode, String institutionName, String email, String password, String institutionAddress, String district, String state) {
        this.institutionCode = institutionCode;
        this.institutionName = institutionName;
        this.email = email;
        this.password = password;
        this.institutionAddress = institutionAddress;
        this.district = district;
        this.state = state;
    }

    public int getInstitutionCode() {
        return institutionCode;
    }

    public void setInstitutionCode(int institutionCode) {
        this.institutionCode = institutionCode;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInstitutionAddress() {
        return institutionAddress;
    }

    public void setInstitutionAddress(String institutionAddress) {
        this.institutionAddress = institutionAddress;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
