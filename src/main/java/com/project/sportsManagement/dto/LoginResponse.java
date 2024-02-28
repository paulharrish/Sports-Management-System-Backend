package com.project.sportsManagement.dto;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class LoginResponse  {

    private UserDetails user;
    private String jwt;


    public LoginResponse(UserDetails user, String jwt) {
        this.user = user;
        this.jwt = jwt;
    }

    public LoginResponse() {
    }

    public UserDetails getUser() {
        return user;
    }

    public void setUser(UserDetails user) {
        this.user = user;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
