package com.project.sportsManagement.controller;

import com.project.sportsManagement.entity.Event;
import com.project.sportsManagement.service.EventService;
import com.project.sportsManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomePageController {

    @Autowired
    EventService eventService;

    @Autowired
    UserService userService;



}
